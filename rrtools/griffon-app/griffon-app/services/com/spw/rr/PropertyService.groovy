package com.spw.rr

import com.spw.rr.model.Preferences
import griffon.core.artifact.GriffonService
import griffon.metadata.ArtifactProviderFor

@javax.inject.Singleton
@ArtifactProviderFor(GriffonService)
class PropertyService {

    private static PropertyService propertyService = null;

/*

    PropertyService() {
        log.debug("Int the property service constructor")
        this.propertyService = this;
    }

    static PropertyService getInstance() {
        if (this.propertyService == null) {
            this.propertyService = new PropertyService();
        }
        return propertyService;
    }

*/
    private boolean inited = false
    private static String PROPERTY_FILE_NAME = "rrtools.properties"
    Properties properties = new Properties()
    Properties defaultProperties

    /*  these are the properties externalized to the properties file:   */
    String savedComPort = "<None>"  // key = savedComPort
    int    savedUnits = 0 // key = savedUnits - 0 = English Units, 1 = Metric Units
    int savedScaleRatio = 87 // key = savedScaleRatio - 1:X
    String savedScaleName = "HO" // key = savedScale
    String savedInspectFreq = "6" // every 6
    String savedInspectUnits = "Months" // every 6 months
    String dbURL = ""
    String dbUsername = ""
    String dbPassword = ""
    /* end of externalized properties */

    public void saveProperties() {
        log.debug("saving the Properties file")
        try {
            FileOutputStream propertyStream = new FileOutputStream(PROPERTY_FILE_NAME)
            properties.store(propertyStream, "The saved RRTools Property file")
        } catch (Exception e){
            log.error("exception saving the Property file", e)
        }
    }

    Preferences getAllProperties() {
        Preferences preferences = new Preferences()
        preferences.comPort = getSavedComPort()
        preferences.scaleName = getSavedScaleName()
        preferences.scaleRatio = getScaleRatio()
        int unitsValue = savedUnits
        if (unitsValue == 0) {
            preferences.units = "English"
        } else {
            preferences.units = "Metric"
        }
        preferences.inspectionFrequency = getInspectionFrequency()
        preferences.inspectionUnits = getInspectionUnits()
        preferences.dbURL = getDbURL()
        preferences.dbUsername = getDbUsername()
        preferences.dbPassword = getDbPassword()
        return preferences
    }

    String checkProperty(String propName) {
        if (properties.getProperty(propName) != null)
            return properties.getProperty(propName)
        else
            return defaultProperties.getProperty(propName)
    }


    private void initTest() {
        if (inited) {
            return
        }
        inited = true
        log.debug("Initializing the Property file")
        defaultProperties = properties
        /* add all default value saved properties here */
        defaultProperties.setProperty("savedComPort", savedComPort)
        defaultProperties.setProperty("savedUnits", Integer.toString(savedUnits))  // 0 = English, 1 = Metric
        defaultProperties.setProperty("savedScaleRatio", Integer.toString(savedScaleRatio))
        defaultProperties.setProperty("savedScaleName", savedScaleName)
        defaultProperties.setProperty("inspectionFrequency", savedInspectFreq)
        defaultProperties.setProperty("inspectionUnits", savedInspectUnits)
        defaultProperties.setProperty("dbUsername", dbUsername)
        defaultProperties.setProperty("dbPassword", dbPassword.getBytes().encodeHex().toString())
        defaultProperties.setProperty("dbURL", dbURL)
        File file
        InputStream propertiesInput = null
        try {
            propertiesInput = new FileInputStream(new File(PROPERTY_FILE_NAME))
        } catch (FileNotFoundException) {
            log.debug("Property file not found - creating a new one")
        }
        if (propertiesInput != null) {
            try {
                properties.load(propertiesInput)
                propertiesInput.close()
            } catch (IOException e) {
                log.error("Error loading the Properties file", e)
            }

        } else {
            properties = defaultProperties
            /* add all default value saved properties here */
        }
        savedComPort = checkProperty("savedComPort")
        savedUnits = new Integer(checkProperty("savedUnits"))
        savedScaleRatio = new Integer(checkProperty("savedScaleRatio"))
        savedScaleName = checkProperty("savedScaleName")
        savedInspectFreq = checkProperty("inspectionFrequency")
        savedInspectUnits = checkProperty("inspectionUnits")
        dbURL = checkProperty("dbURL")
        dbPassword = new String(checkProperty("dbPassword").decodeHex())
        dbUsername = checkProperty("dbUsername")
    }

    public int getUnits() {
        initTest()
        return savedUnits
    }

    /**
     * Save the selected unit system
     * @param units 0 = English, 1 = Metric
     */
    public void setSavedUnits(int units) {
        log.debug("setting units to {}", units)
        savedUnits = units
        properties.setProperty("savedUnits", Integer.toString(units ))
        saveProperties()
    }

    public int getScaleRatio() {
        initTest()
        return savedScaleRatio
    }

    public void setScaleRatio(int ratio) {
        savedScaleRatio = ratio
        properties.setProperty("savedScaleRatio", Integer.toString(ratio))
        saveProperties()
    }

    public String getScaleName() {
        initTest()
        return savedScaleName
    }

    public void setScaleName(String name) {
        savedScaleName = name
        properties.setProperty("savedScaleName", name)
        saveProperties()
    }

    public String getSavedComPort() {
        log.debug("returning saved com port value")
        initTest()
        return savedComPort
    }

    public void setSavedComPort(String savedPort) {
        log.debug("saving the comPort value of {} ", savedPort  )
        initTest()
        savedComPort = savedPort
        properties.setProperty("savedComPort", savedComPort)
        saveProperties()
    }

    public void setInspection(String units, String frequency) {
        log.debug("saving the inspection frequency and units")
        initTest()
        savedInspectUnits = units
        savedInspectFreq = frequency
        properties.setProperty("inspectionFrequency", frequency)
        properties.setProperty("inspectionUnits", units)
        saveProperties()
    }

    String getInspectionFrequency() {
        initTest()
        return savedInspectFreq
    }

    String getInspectionUnits() {
        initTest()
        return savedInspectUnits
    }

    String getDbUrl() {
        initTest()
        return dbURL
    }

       String getDbUsername() {
        initTest()
        return dbUsername
    }
    String getDbPassword() {
        initTest()
        return dbPassword
    }
    public void setDbUrl(String url) {
        log.debug("saving the database URL")
        initTest()
        dbURL = url
        properties.setProperty("dbURL", dbURL)
    }

      public void setDbUsername(String username) {
        log.debug("saving the database username")
        initTest()
        dbUsername = username
        properties.setProperty("dbUsername", dbUsername)
    }
    public void setDbPassword(String password) {
        log.debug("saving the database password")
        initTest()
        dbPassword = password
        properties.setProperty("dbPassword", dbPassword.getBytes().encodeHex().toString())
    }

    public void setDbItems(Map<String, String> inputData) {
        log.debug("saving the database items")
        initTest()
        if (inputData.containsKey("dbUsername")) {
            dbUsername = inputData.get("dbUsername")
            properties.setProperty("dbUsername", dbUsername)
        }
        if (inputData.containsKey("dbURL")) {
            dbURL = inputData.get("dbURL")
            properties.setProperty("dbURL", dbURL)
        }
        if (inputData.containsKey("dbPassword")) {
            dbPassword = inputData.get("dbPassword")
            properties.setProperty("dbPassword", dbPassword.getBytes().encodeHex().toString())
        }
        saveProperties()
    }




}