package com.spw.rr

import griffon.core.artifact.GriffonService
import griffon.metadata.ArtifactProviderFor

@javax.inject.Singleton
@ArtifactProviderFor(GriffonService)
class PropertyService {

    private boolean inited = false
    private static String PROPERTY_FILE_NAME = "rrtools.properties"
    Properties properties = new Properties()

    /*  these are the properties externalized to the properties file:   */
    String savedComPort = "<None>"  // key = savedComPort
    int    savedUnits = 0 // key = savedUnits - 0 = English Units, 1 = Metric Units
    int savedScaleRatio = 87 // key = savedScaleRatio - 1:X
    String savedScaleName = "HO" // key = savedScale
    /* end of externalized properties */

    public void saveProperites() {
        log.debug("saving the Properties file")
        try {
            FileOutputStream propertyStream = new FileOutputStream(PROPERTY_FILE_NAME)
            properties.store(propertyStream, "The saved RRTools Property file")
        } catch (Exception e){
            log.error("exception saving the Property file", e)
        }
    }

    private void initTest() {
        if (inited) {
            return
        }
        log.debug("Initializing the Property file")
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
            /* add all default value saved properties here */
            properties.setProperty("savedComPort", savedComPort)
            properties.setProperty("savedUnits", Integer.toString(savedUnits))
            properties.setProperty("savedScaleRatio", Integer.toString(savedScaleRatio))
            properties.setProperty("savedScaleName", savedName)

        }
    }

    public int getUnits() {
        initTest()
        return Integer.parseInt(properties.getProperty("savedUnits"))
    }

    /**
     * Save the selected unit system
     * @param units 0 = English, 1 = Metric
     */
    public void setUnits(int units) {
        savedUnits = units
        properties.setProperty("savedUnits", Integer.toString(units ))
    }

    public int getScaleRatio() {
        initTest()
        return Integer.parseInt(properties.getProperty("savedUnits"))
    }

    public void setScaleRatio(int ratio) {
        savedScaleRatio = ratio
        properties.setProperty("savedScaleRatio", Integer.toString(ratio))
    }

    public String getScaleName() {
        initTest()
        return properties.getProperty("savedScaleName")
    }

    public void setScaleName(String name) {
        savedScaleName = name
        properties.setProperty("savedScaleName", name)
    }

    public String getSavedComPort() {
        log.debug("returning saved com port value")
        initTest()
        return properties.getProperty("savedComPort")
    }

    public void setSavedComPort(String savedPort) {
        log.debug("saving the comPort value of {} ", savedPort  )
        savedComPort = savedPort
        properties.setProperty("savedComPort", savedComPort)
    }
}