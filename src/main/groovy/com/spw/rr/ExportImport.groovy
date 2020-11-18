package com.spw.rr

import com.spw.rr.model.ExportCar
import javafx.stage.FileChooser
import javafx.stage.Window
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ExportImport {

    private static final Logger log = LoggerFactory.getLogger(Exception.class)

    private void doOne(java.lang.StringBuilder st, def field) {
        if (field != null) {
            st.append(field.toString().trim())
        }
        st.append(",")
    }

    public void performExport(DBService dbService, Window window, RrToolsController controller) {
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
            outputLine.append(it.carType.trim())
            outputLine.append(",")
            doOne(outputLine, it.carLength)
            doOne(outputLine, it.carWeight)
            doOne(outputLine, it.carColor)
            outputLine.append(",")
            doOne(outputLine, it.builtDate)
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

}
