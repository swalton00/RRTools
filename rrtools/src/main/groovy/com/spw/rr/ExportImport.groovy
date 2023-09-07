package com.spw.rr

import com.spw.rr.model.CarType
import com.spw.rr.model.ExportCar
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReportingMark
import javafx.stage.FileChooser
import javafx.stage.Window
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ExportImport {

    private static final Logger log = LoggerFactory.getLogger(Exception.class)

    private static void doOne(StringBuilder st, def field) {
        if (field != null) {
            st.append(field.toString().trim())
        }
        st.append(",")
    }

    static String testValues(String test) {
        if (test == null) {
            return ""
        }
        String retValue = test.trim()
        return retValue
    }

    static String testNumber(Integer test) {
        if (test == null) {
            return ""
        }
        return test.toString()
    }

    static String testNumber(test) {
        if (test == null) {
            return ""
        }
        return test.toString()
    }

    static void performExport(DBService dbService, Window window, RrToolsController controller) {
        log.debug("Export requested")
        List<ExportCar> list = dbService.exportCarList()
        log.debug("Export list contains {}", list)
        FileChooser fileChooser = new FileChooser()
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"))
        fileChooser.setTitle("Select the location and name for the file to be created")
        fileChooser.setInitialDirectory(new File("."))
        File chosenFile = null
        controller.runInsideUISync({
            chosenFile = fileChooser.showSaveDialog(window)
        })
        if (chosenFile == null) {
            log.debug("returning with no action, no file chosen")
            return
        }
        BufferedWriter buff = new BufferedWriter(new FileWriter(chosenFile))
        PrintWriter output = new PrintWriter(buff)
        StringBuilder outputLine = new StringBuilder("")
        outputLine.append("Number,Road,Type,Length,Weight,Color,Owner,Built,Location,-,Track,Load,Kernel,Moves,Value,Comment")
        outputLine.append(",Miscellaneous,Extensions,Wait,Pick up,Last,RWE Location,-,Track")
        outputLine.append(",RWE Load,Train,Destination,-,Track,Final Destination,-,Track\n")
        output.write(outputLine.toString())
        outputLine.setLength(0)
        list.each {
            outputLine.append(it.carNumber.trim())
            outputLine.append(",")
            outputLine.append(it.reporting.trim())
            outputLine.append(",")
            outputLine.append(testValues(it.carType))
            outputLine.append(",")
            doOne(outputLine, testNumber(it.carLength))
            doOne(outputLine, testNumber(it.carWeight))
            doOne(outputLine, testValues(it.carColor))
            outputLine.append(",")
            doOne(outputLine, testValues(it.builtDate))
            outputLine.append(",")            // Location
            outputLine.append("-,")           // empty
            outputLine.append(",")            // track
            outputLine.append("E,")            // load
            outputLine.append(",")            // kernel
            outputLine.append(",")            // moves
            outputLine.append(",")            // Value
            outputLine.append(",")            // comment
            outputLine.append(",")            // misc
            outputLine.append(",")            // extensions
            outputLine.append(",")            // wait
            outputLine.append(",")            // pickup
            outputLine.append(",")            // last
            outputLine.append(",")            // RWE location
            outputLine.append("-,")            // track
            outputLine.append(",")            // RWE Load
            outputLine.append(",")            // Train
            outputLine.append(",")            // Destination
            outputLine.append("-,")            //
            outputLine.append(",")            // Track
            outputLine.append(",")             //
            outputLine.append(",")            // Final Destination
            outputLine.append("-,")           //
            outputLine.append("-,")           // Track
            if (!it.idTag.trim().empty) {
                outputLine.append("ID")
                outputLine.append(it.idTag)
            }
            outputLine.append("\n")
            output.print(outputLine.toString())
            outputLine.setLength(0)
            log.debug("Exported car {}", it.reporting + it.carNumber)
        }
        log.debug("Export operation completed")
        output.close()
    }

    static String cleanImport(String newData) {
        if (newData == null) {
            return ""
        }
        String newValue = newData.trim()
        return newValue
    }

    void addImportedCar(DBService database,
                        ArrayList<ReportingMark> marks,
                        String carNumber,
                        String reportingMark,
                        String carType,
                        String carLength,
                        String carWeight,
                        String carColor,
                        String builtDate,
                        String rfidTag
    ) {
        log.debug("looking to add car " + carNumber + "with mark of " + reportingMark)

        int markNumber = 0
        boolean markFound = false
        boolean carFound = false
        boolean carUpdated = false
        marks.each {
            if (reportingMark.equals(it.mark)) {
                markFound = true
                markNumber = it.id
            }
        }
        if (!markFound) {
            ReportingMark newMark = new ReportingMark()
            newMark.mark = reportingMark
            database.addReportingMark(newMark)
            markNumber = newMark.id
            marks.add(newMark)
        }
        RRCar oldCar = database.findCar(markNumber, carNumber)
        if (oldCar == null) {
            log.debug("adding car --- it was not found")
            carFound = false
            oldCar = new RRCar()
            oldCar.reportingMarkID = markNumber
            oldCar.carNumber = carNumber
        } else {
            log.debug("updating existing car")
            carFound = true
        }
        CarType typeOfCar = database.findType(carType)
        log.debug("car type found was ${typeOfCar}")
        if (typeOfCar == null) {
            typeOfCar = new CarType()
            typeOfCar.carType = carType
            database.addCarType(typeOfCar)
        }
        if (!carFound) {
            oldCar.carTypeID = typeOfCar.typeId
        } else {
            if (!typeOfCar.typeId.equals(oldCar.carTypeID)) {
                carUpdated = true
                oldCar.carTypeID = typeOfCar.typeId
            }

        }
        String newColor = cleanImport(carColor)
        if (!newColor.equals(oldCar.carColor)) {
            oldCar.carColor = newColor
            carUpdated = true
        }
        String newWeight = cleanImport(carWeight)
        if (newWeight.length() > 0) {
            BigDecimal weightValue = null
            try {
                weightValue = new BigDecimal(newWeight)
                if (oldCar.carWeight != null & oldCar.carWeight.equals(weightValue)) {
                    log.debug("car weight unchanged")
                } else {
                    oldCar.carWeight = weightValue
                    carUpdated = true
                }
            } catch (NumberFormatException exception) {
                log.error("exception attempting to convert weight value", exception)
            }
        }
        String newLength = cleanImport(carLength)
        if (newLength.length() > 0) {
            Integer lengthValue = null
            try {
                lengthValue = new Integer(newLength)
                if (oldCar.carLength != null & lengthValue.equals(oldCar.carLength))
                {
                    log.debug("car length value was unchanged on import")
                } else {
                    oldCar.carLength = lengthValue
                    carUpdated = true
                }
            } catch (NumberFormatException exception) {
                log.error("exception converting new length value for car", exception)
            }
        }
        String newBuilt = cleanImport(builtDate)
        if (newBuilt.length() > 0) {
            if (!newBuilt.equals(oldCar.bltDate)) {
                oldCar.bltDate = newBuilt
            }
        }
        String newTag = cleanImport(rfidTag)
        if (newTag.startsWith("ID")) {
            newTag = newTag.substring(2)
        }
        if (!(newTag.length() == 0) & !newTag.equals(oldCar.RFIDtag)) {
            oldCar.RFIDtag = newTag
            carUpdated = true
        }
        if (oldCar.resistanceWheels == null) {
            oldCar.resistanceWheels = false
            carUpdated = true
        }
        if (oldCar.weathered == null) {
            old.weathered = false
            carUpdated = true
        }
        if (oldCar.weathered) {
            oldCar.setWeathered = 1
        } else {
            oldCar.setWeathered = 0
        }
        if (oldCar.resistanceWheels) {
            oldCar.setResistanceWheels = 1
        } else {
            oldCar.setResistanceWheels = 0
        }
        if (carFound) {
            if (carUpdated) {
                // car was found and updated
                database.updateCar(oldCar)
            }
        } else {
            // car is new -- add car
            database.addCar(oldCar)
        }

    }

    void performImport(DBService database, Window window, RrToolsController controller) {
        log.debug("importing cars")
        FileChooser fileChooser = new FileChooser()
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV files", "*.csv"))
        fileChooser.setTitle("Select the import file")
        fileChooser.setInitialDirectory(new File("."))
        File chosenFile = null
        controller.runInsideUISync({
            chosenFile = fileChooser.showOpenDialog(window)
        })
        if (chosenFile == null) {
            log.debug("returning with no action, no file chosen")
            return
        }
        BufferedReader rdr = new BufferedReader(new FileReader(chosenFile))
        log.debug("Reading file " + chosenFile.toString())
        // dispose of header line
        boolean firstLine = true
        ArrayList<ReportingMark> marks = database.getReportingMarks()

        rdr.lines().forEach({
            String[] carElements = it.split(",")
            if (firstLine) {
                firstLine = false
                log.debug("import elements are: " + carElements.toString())
            } else {
                log.debug("split elements are: " + carElements.toString())
                // elements should be:
                //    number(0), reportingMark(1), type(2), length (3), weight(4), color(5), built date(7),
                //    rfid tag(29)
                addImportedCar(database,    // database service
                        marks,              //  reporting marks array
                        carElements[0],     //  car number
                        carElements[1],     //  reporting mark
                        carElements[2],     //  car type
                        carElements[3],     //  car length
                        carElements[4],     //  car weight
                        carElements[5],     //  car color
                        carElements[7],     //  built date
                        carElements[29])    //  rfid tag
            }
        })
    }


}
