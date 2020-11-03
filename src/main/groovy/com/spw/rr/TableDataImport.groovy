package com.spw.rr

import com.spw.rr.model.ReferenceItem
import javafx.stage.FileChooser
import javafx.stage.Window
import org.jumpmind.symmetric.csv.CsvReader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject

@Singleton
class TableDataImport {

    private static final Logger log = LoggerFactory.getLogger(TableDataImport.class)


    public void performImport(String table, Window owner, DBService dbService,  RrToolsController controller) {
        log.debug("Importing into {}", table)
        log.info("DBservice is {}", dbService)
        FileChooser chooser = new FileChooser()
        chooser.setTitle("Select a the CSV to import")
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Documents", "*.csv")
        )
        chooser.setInitialDirectory(new File("."))
        File selectedCSV
        controller.runInsideUISync {
            selectedCSV = chooser.showOpenDialog(owner)
        }
        log.debug("Selected file {}", selectedCSV)
        if (selectedCSV == null)
            return
        List currentData = dbService.getReferenceList(table)
        log.debug("Read existing data {}", currentData)
        LinkedHashMap<String, ReferenceItem> current = new LinkedHashMap<>()
        currentData.each {
            current.put(it.typeName.toUpperCase(), it)
        }
        InputStream inputStream = new FileInputStream(selectedCSV)
        Reader reader = new InputStreamReader(inputStream)
        CsvReader csvReader = new CsvReader(reader)
        csvReader.readRecord()
        String[] headers = csvReader.getHeaders()
        log.debug("Headers are: {}", headers)
        while (csvReader.readRecord()) {
            String[] currentRow = csvReader.getValues()
            log.debug("value read are {}", currentRow)
            if (currentRow[0] != null) {
                String nameVal = currentRow[0]
                String description = currentRow[1]
                ReferenceItem already = current.get(nameVal.toUpperCase())
                if (already == null) {
                    // don't have it yet - need to add it
                    ReferenceItem newItem = new ReferenceItem()
                    newItem.typeName = nameVal
                    newItem.typeDescription = description
                    dbService.addReferenceItem(newItem, table)
                } else {
                    // have it -- update description and replace in hash
                    already.typeDescription = description
                    dbService.saveReferenceItem(already, table)
                }
            }
        }
    }
}
