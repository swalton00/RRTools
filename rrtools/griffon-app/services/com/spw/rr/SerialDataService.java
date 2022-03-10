package com.spw.rr;

import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import purejavacomm.*;

import javax.inject.Singleton;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

@Singleton
@ArtifactProviderFor(GriffonService.class)
public class SerialDataService extends AbstractGriffonService {
    private static final Logger log = LoggerFactory.getLogger(SerialDataService.class);

    boolean commOpen = false;
    CommPort commPort;
    String savedCommPort = "<none>";
    String currentStatus = "OK";
    InputStream serialStream = null;
    static boolean continueReading = true;

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

}
