package com.spw.rr;

import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;

@javax.inject.Singleton
@ArtifactProviderFor(GriffonService.class)
public class PropertyService extends AbstractGriffonService {

    private static final Logger log = LoggerFactory.getLogger(PropertyService.class);
    private boolean inited = false;
    private boolean propertiesFound = false;
    private static final String PROPERTY_FILE_NAME = "rrtools.properties";
    private static final String PROP_COM_PORT = "savedComPort";
    private static final String PROP_UNITS = "savedUnits";
    private static final String PROP_SCALE = "savedScaleRatio";
    private static final String PROP_SCALE_NAME = "savedScaleName";
    private static final String PROP_INSPECT_FREQ = "savedInspectFreq";
    private static final String PROP_INSPECT_UNITS = "savedInspectUnits";
    private static final String PROP_DB_URL = "dbURL";
    private static final String PROP_DB_USER = "dbUsername";
    private static final String PROP_DB_PASS = "dbPassword";

    Properties properties = new Properties();
    Properties defaultProperties;

    /* the default properties externalized to the properties file */
    String savedScaleName = "HO";  // key = savedScale
    int savedInspectFreq = 6;     // key = savedInspectFreq
    String savedInspectUnits = "Months"; // key = saveInspectUnits
    String dbURL = "";   // key = dbURL -- URL to database (no default)
    String dbUsername = "";   // key = dbUsername
    String dbPassword = "";   // key = dbPassword -- obscured but not encrypted
    /* end of externalized properties */

    String savedComPort = "<None>";  // key = savedComPort
    int savedUnits = 0;         // key = savedUnits - 0 = English, 1 = Metric
    int savedScaleRatio = 87;     // key = savedScaleRatio - 1:x

    public String getSavedComPort() {
        initTest();
        return savedComPort;
    }


    /**
     *
     * @return boolean - true if properties file was found
     *     returns false if no properties were found
     *
     */
    boolean loadValues() {
        initTest();
        return propertiesFound;
    }

    public void setSavedComPort(String savedComPort) {
        initTest();
        this.savedComPort = savedComPort;
    }

    public int getSavedUnits() {
        initTest();
        return savedUnits;
    }

    public void setSavedUnits(int savedUnits) {
        initTest();
        this.savedUnits = savedUnits;
    }

    public int getSavedScaleRatio() {
        initTest();
        return savedScaleRatio;
    }

    public void setSavedScaleRatio(int savedScaleRatio) {
        initTest();
        this.savedScaleRatio = savedScaleRatio;
    }

    public String getSavedScaleName() {
        initTest();
        return savedScaleName;
    }

    public void setSavedScaleName(String savedScaleName) {
        initTest();
        this.savedScaleName = savedScaleName;
    }

    public int getSavedInspectFreq() {
        initTest();
        return savedInspectFreq;
    }

    public void setSavedInspectFreq(int savedInspectFreq) {
        initTest();
        this.savedInspectFreq = savedInspectFreq;
    }

    public String getSavedInspectUnits() {
        initTest();
        return savedInspectUnits;
    }

    public void setSavedInspectUnits(String savedInspectUnits) {
        initTest();
        this.savedInspectUnits = savedInspectUnits;
    }

    public String getDbURL() {
        initTest();
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        initTest();
        this.dbURL = dbURL;
    }

    public String getDbUsername() {
        initTest();
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        initTest();
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        initTest();
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        initTest();
        this.dbPassword = dbPassword;
    }

    public void saveProperties() {
        log.debug("Saving the properties file");
        if (!inited) {
            log.debug("Properties were never inited, not saving");
            return;
        }
        try {
            FileOutputStream propertyStream = new FileOutputStream(PROPERTY_FILE_NAME);
            properties.store(propertyStream, "The saved RrTools Properties file");
        } catch (IOException e) {
            log.error("Error saving properties file", e);
        }
    }

    String checkProperty(String name) {
        if (properties.getProperty(name) != null) {
            return properties.getProperty(name);
        }
        return defaultProperties.getProperty(name);
    }


    private void initTest() {
        if (inited) {
            return;
        }
        log.debug("Initializing the rrTools Properties");
        defaultProperties = new Properties();
        defaultProperties.setProperty(PROP_COM_PORT, savedComPort);
        defaultProperties.setProperty(PROP_UNITS, Integer.toString(savedUnits));
        defaultProperties.setProperty(PROP_SCALE, Integer.toString(savedScaleRatio));
        defaultProperties.setProperty(PROP_SCALE_NAME, savedScaleName);
        defaultProperties.setProperty(PROP_INSPECT_FREQ, Integer.toString(savedInspectFreq));
        defaultProperties.setProperty(PROP_INSPECT_UNITS, savedInspectUnits);
        defaultProperties.setProperty(PROP_DB_URL, dbURL);
        defaultProperties.setProperty(PROP_DB_USER, dbUsername);
        defaultProperties.setProperty(PROP_DB_PASS, DatatypeConverter.printHexBinary(dbPassword.getBytes()));
        /*  end of defaults */
        File file;
        InputStream propertiesInput = null;
        try {
            propertiesInput = new FileInputStream(new File(PROPERTY_FILE_NAME));
        } catch (IOException e) {
            log.debug("Properties file not found, creating a new one");
        }
        if (propertiesInput != null) {
            try {
                properties.load(propertiesInput);
                propertiesInput.close();
                propertiesFound = true;
            } catch (IOException e) {
                log.error("Error loading the properties file");
            }
            if (!propertiesFound) {
                properties = defaultProperties;
            }

        }
        savedComPort = checkProperty(PROP_COM_PORT);
        savedUnits = Integer.valueOf(checkProperty(PROP_UNITS));
        savedScaleRatio = Integer.valueOf(checkProperty(PROP_SCALE));
        savedScaleName = checkProperty(PROP_SCALE_NAME);
        savedInspectFreq = Integer.valueOf(checkProperty(PROP_INSPECT_FREQ));
        savedInspectUnits = checkProperty(PROP_INSPECT_UNITS);
        dbURL = checkProperty(PROP_DB_URL);
        dbUsername = checkProperty(PROP_DB_USER);
        String temp = checkProperty(PROP_DB_PASS);
        byte[] t2 = DatatypeConverter.parseHexBinary(temp);
        try {
            dbPassword = new String(t2, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            log.error("error attempting to convert db password");
        }
        inited = true;
    }
}
