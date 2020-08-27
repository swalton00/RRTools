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

    public void closePort() {
        log.debug("closing the commport")
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

    public void setCommPort(String port) {
        log.debug("setting comm port to {}", port)
        try {
            CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(port)
            SerialPort (SerialPort) cpi.open("RR Tools", 1500)
            serialStream = serialPort.getInputStream()
            purgePort(serialStream)
            commOpen = true
            savedCommPort = port
            currentStatus = "Serial Port Connected"
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