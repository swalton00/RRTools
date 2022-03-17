package com.spw.rr;

import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavacomm.*;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

@Singleton
@ArtifactProviderFor(GriffonService.class)
public class SerialDataService extends AbstractGriffonService {
    private static final Logger log = LoggerFactory.getLogger(SerialDataService.class);

    boolean commOpen = false;
    CommPort commPort;
    private SerialPort serialPort = null;
    String savedCommPort = "<none>";
    String currentStatus = "OK";
    InputStream serialStream = null;
    static boolean continueReading = true;
    private static final int MESSAGE_LENGTH = 16;

    public ArrayList<String> getSerialPortList() {
        log.debug("returning a list of Comm Ports");
        ArrayList<String> retList = new ArrayList<>();
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier cpi = portList.nextElement();
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                retList.add(cpi.getName());
            }
        }
        log.debug("returning a list of ports {}", retList);
        return retList;
    }

    private void purgePort( InputStream inputStream) throws IOException {
        int count = inputStream.available();
        log.debug("RFID reader input stream shows {} bytes available - discarding");
        while (count > 0) {
            serialStream.skip(count);
            count = inputStream.available();
        }
    }

    void readSerialPort() {
        byte[] dataReceived = new byte[16];
        try {
            int received = 0;
            while (received < MESSAGE_LENGTH) {
                received += serialStream.read(dataReceived, received, MESSAGE_LENGTH - received);
            }

        } catch (Exception e) {
            log.error("Exception reading the serial port");
        }
    }

    public boolean checkValid(byte[] inputData, int length) {
        if (length != 15) {
            return false;  // must be exactly 16 bytes of input
        }
        if (inputData[0] != 0x02) {
            return false;  // first byte must be STX
        }
        if (inputData[15] != 0x03) {
            return false;  // last byte must be ETX
        }
        if (inputData[13] != 0x0d) {
            return false; // should end with carriage return
        }
        if (inputData[14] != 0x0a) {
            return false; // plus a line feed
        }
        byte[] byteVal = new byte[12];
        for (int i = 0; i < 12; i++) {
            byteVal[i] = inputData[i + 1];
        }
        String stringValue;
        try {
             stringValue = new String(byteVal, "UTF-8");
        } catch (Exception e) {
            log.error("Exception converting data read to hex");
            return false;
        }
        byte[] decoded = new byte[6];
        for (int i = 0; i < 6; i++) {
            int newVal = Integer.parseInt(stringValue.substring(i*2, (i*2)+2), 16);
            decoded[i] = (byte) newVal;
        }
        int checkSum = 0;
        for (int i = 0; i < 5; i++) {
            checkSum = checkSum ^ decoded[i];
        }
        return checkSum == decoded[5];
    }

    public String returnTag(byte[] inputData){
        byte[] byteVal = new byte[12];
        for (int i = 0; i < 12; i++) {
            byteVal[i] = inputData[i+1];
        }
        String stringVal = null;
        try {
            stringVal = new String(byteVal, "UTF-8");
        } catch (Exception e) {
            log.error("Exception converting new tag to a String", e);
        }
        return stringVal;
    }

    public void closePort() {
        log.debug("closing the serial port");
        continueReading = false;
        if (commOpen) {
            try {
                serialPort.close();
                commOpen = false;
                serialPort = null;
            } catch (Exception e) {
                log.error("Error closing the serial port", e);
            }
        }
    }

    public void listener(SerialPortEvent portEvent) {
        if (portEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            readSerialPort();
        } else {
            log.error("got a serial port event of unknown type {}", portEvent);
        }
    }

    public void setCommPort(String port) {
        log.debug("attempting to open the com port");
        if (commOpen) {
            log.error("attempting to open the com port again");
            return;
        }
        savedCommPort = port;
        try {
            CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(port);
            serialPort = (SerialPort) cpi.open("RR Tools", 1500);
            commOpen = true;
            currentStatus = "Serial Port Connected";
            serialPort.enableReceiveTimeout(20000);
            serialPort.enableReceiveThreshold(16);
            serialStream = serialPort.getInputStream();
            purgePort(serialStream);
            serialPort.addEventListener(this::listener);
            serialPort.notifyOnDataAvailable(true);
        } catch (NoSuchPortException nse) {
            log.debug("No such port {}", port);
            currentStatus = "No such port " + port;
        } catch (PortInUseException inUse) {
            log.debug("Port {} is already in use", port);
            currentStatus = "Port in use";
        } catch (UnsupportedCommOperationException uco) {
            log.error("Unsupported operation on the serial port", uco);
            currentStatus = "Connection failed";
        } catch (Exception e) {
            log.error("Port open failed");
            currentStatus = "Port open failed";
        }
     }

}
