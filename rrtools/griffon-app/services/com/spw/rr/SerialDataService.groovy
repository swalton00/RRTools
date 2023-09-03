package com.spw.rr

import griffon.core.artifact.GriffonService
import griffon.metadata.ArtifactProviderFor
import jdk.internal.util.xml.impl.Input
import purejavacomm.CommPortIdentifier
import purejavacomm.*

@javax.inject.Singleton
@ArtifactProviderFor(GriffonService)
class SerialDataService {

    boolean commOpen = false
    CommPort commPort
    String savedCommPort = "<None>"
    SerialPort serialPort = null;
    String currentStatus = "OK"
    InputStream serialStream = null
    static boolean continueReading = true

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final int MESSAGE_LENGTH = 16
    public static String bytesToHex(byte[] bytes, int count) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < count; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }



    public static boolean checkValid(byte[] input, int length) {
        if (length != 16)
            return false;  // must be exactly 16 bytes long
        if (input[0] != 0x02 )
            return false; // byte 1 must be a STX
        if (input[15] != 0x03)
            return false;  // last byte must be ETX
        if (input[13] != 0x0d)
            return false;   // chould end with CR
        if (input[14] != 0x0a)
            return false;    // plus LF
        byte[] byteVal = new byte[12]; // 10 bytes of data plue 2 bytes for checksum
        for (int i = 0; i < 12 ; i++) {
            byteVal[i] = input[i+1];
        }
        String stringVal;
        try {
            stringVal = new String(byteVal, "UTF-8");
        } catch (Exception e){
            return false;  // if not valid hex, can't be a good tag
        }
        byte[] decoded = stringVal.decodeHex();
        if (decoded.length != 6)
            return false; // must be 5 bytes + a check digit
        int checksum = 0;
        for (int i = 0; i < 5; i++) {
            checksum = checksum ^ decoded[i];
        }
        return checksum == decoded[5];
    }


    public String returnTag(byte[] input) {
        byte[] byteVal = new byte[12]; // 10 bytes of data plus 2 bytes for checksum
        for (int i = 0; i < 12 ; i++) {
            byteVal[i] = input[i+1];
        }
        String stringVal = "bad hex value";
        try {
            stringVal = new String(byteVal, "UTF-8");
        } catch (Exception e){
            log.error("bad hex value {}", stringVal )
        }
        return stringVal;
    }



    public void closePort() {
        log.debug("closing the commport")
        continueReading = false
        if (commOpen) {
            try {
                serialPort.close()
                commOpen = false
                serialPort = null
            } catch (IOException e) {
                log.error("Error closing the comm port", e)
            }
        }
    }

    public void doShutdown() {
        log.debug("shutting down the Serial Port")
        closePort()
    }

    private void purgePort( InputStream inputStream) throws IOException {
        int count = inputStream.available()
        log.debug("RFID Reader input stream shows {} bytes available - skipping", count)
        while (count > 0) {
            serialStream.skip(count)
            count = serialStream.available()
        }
    }

    public ArrayList<String> getSerialPortList() {
        log.debug("returning a list of the Comm Ports")
        ArrayList<String> retList = new ArrayList<String>()
        Enumeration<String> portList = CommPortIdentifier.getPortIdentifiers()
        while (portList.hasMoreElements()) {
            CommPortIdentifier cpi = portList.nextElement()
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                retList.add(cpi.getName())
            }
        }
        log.debug("returning list of serial ports - {}", retList)
        return retList
    }

    public readSerialPort() {
        byte[] dataReceived = new byte[16]
        try {
            int received = 0
            while (received < MESSAGE_LENGTH) {
                received += serialStream.read(dataReceived, received, MESSAGE_LENGTH - received)
            }
            if (checkValid(dataReceived,received)) {
                String newTag = returnTag(dataReceived)
                newTag = newTag.substring(0, 10)
                log.debug("received a new tag of {}", newTag)
                println("read a new tag ${newTag}")
                ArrayList<String> tagReceived = new ArrayList<>()
                tagReceived.add(newTag)
                application.getEventRouter().publishEvent("Tag_Read", tagReceived)
            } else {
                log.error("bad tag checksum for {}", bytesToHex(dataReceived, received))
            }
        } catch (Exception e) {
            log.error("Exception reading - {}", e.getMessage(), e)
        }
    }

    void listener(SerialPortEvent portEvent) {
        if (portEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            readSerialPort()
        } else {
            log.error("Got a serial port event of unknown type, {}", portEvent)
        }
    }

    public void setCommPort(String port) {
        log.debug("setting comm port to {}", port)
        if (commOpen) {
            log.error("cannot open Comm port twice")
            return
        }

        try {
            CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(port)
            if (commOpen) {
                try {
                    serialPort.close()
                } catch ( Exception e) {
                    log.error("Error closing serial port", e)
                }
            }
            serialPort =  cpi.open("RR Tools", 1500)
            commOpen = true
            savedCommPort = port
            currentStatus = "Serial Port Connected"
            serialPort.enableReceiveTimeout(20000)
            serialPort.enableReceiveThreshold(16)
            serialStream = serialPort.getInputStream()
            purgePort(serialStream)
            log.info("starting a reader on the Serial Port -- waiting for tags")
            serialPort.addEventListener([serialEvent: { event -> listener(event)}] as SerialPortEventListener)
            serialPort.notifyOnDataAvailable(true)
            log.debug("new thread started")
        } catch (NoSuchPortException nspe) {
            log.debug("No Such port for port {}", port)
            currentStatus = "No such port" + port
        } catch (PortInUseException) {
            log.debug("Port {} is already in use", port)
            currentStatus = "Port " + port + " is already in use"
        } catch (UnsupportedCommOperationException uco) {
            log.error("Unsupported comm operation thrown for port {}", port, uco)
            currentStatus("Connection failed")
        } catch (Exception e) {
            log.error("Exception opening port {}", port, e)
            currentStatus = "Port open failed"
        }
        ArrayList<String> status = new ArrayList<>()
        status.add(currentStatus)
        application.getEventRouter().publishEvent("Status_Update", status)
    }

    public String getCommPortStatus() {
        log.debug("returning status of comm port")
        if (savedCommPort.equals("<None>")) {
            return "No Comm port specified"
        }
        return currentStatus
    }
}