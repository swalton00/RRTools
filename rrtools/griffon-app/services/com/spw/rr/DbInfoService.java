package com.spw.rr;

import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

@ArtifactProviderFor(GriffonService.class)
public class DbInfoService  extends AbstractGriffonService {
    private static final Logger log = LoggerFactory.getLogger(DbInfoService.class);
    private static final String URL_START = "jdbc:";
    private boolean valuesSet = false;

    private void checkInit() {
        if (!valuesSet) {
            throw new RuntimeException("Access to database info attempted before information is available ");
        }
    }

    private String url;
    private String databaseName;
    private String dbUsername;
    private String dbPassword;
    private String dbms;
    private String dbLocation;
    private boolean urlRequired;

    public void setDatabaseInfo(String dbUrl, String dbUser, String dbPassword) {
        url = dbUrl;
        dbUser = dbUser;
        this.dbPassword = dbPassword;
        valuesSet = true;
        testUrl(dbUrl, true);
    }

    public boolean urlRequired() {
        checkInit();
        return urlRequired;
    }

    public boolean testDbData(String url, String user, String password) {
        boolean retVal = false;
        log.debug("trying the connection {}", url);
        try {
            if (dbms == null) {
                return false;
            }
            if (user == null || password == null || user.isEmpty() || password.isEmpty()) {
                return false;
            }
            String driverName = getdbmsClassName();
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, user, password);
            retVal = true;
            con.close();
        } catch (Exception e) {
            log.error("Error establishing the database connection", e);
            return false;
        }
        return retVal;
    }

    /**
     *
     * @param url The URL to be tested
     * @return  An empty String if good, otherwise an error message about the type of error
     */
    public String testUrl(String url) {
        return testUrl(url, false);
    }

    /**
     *
     * @param url       The URL to be checked
     * @param setValues Is this for real? (Set the saved values if true)
     * @return          An empty String if good, an error message if not
     */
    private String testUrl(String url, boolean setValues) {
        log.debug("testing the url {}", url);
        String retVal = "";
        if (url == null || url.isEmpty()) {
            return "URL is required";
        }
        // check for jdbc:
        if (!url.startsWith(URL_START) || url.length() <= URL_START.length()) {
            return "Bad URL format";
        }
        String temp = url.substring(URL_START.length());
        int semiPos = temp.indexOf(":");
        // dbms name follows a semicolon
        if (semiPos < 0) {
            return "Incorrect URL format";
        }
        String thisDbms = temp.substring(0, semiPos);
        // the only (currently) supported DBMS's are DB2 and H2
        if (!(thisDbms.equals("h2") || thisDbms.equals("db2"))) {
            return "Unrecognized DBMS";
        }
        temp = temp.substring(semiPos + 1);
        boolean urlRequired = false;  // if true, can only use the URL
        String tempDbName = "";  // this will hold the database name (if found)
        String tempDbLoc = "";  // and the location (also if found);
        // if it is the normal H2, it will be followed by "file:"
        // the location is followed by the DB name and terminated by a semicolon
        if (temp.startsWith("file:")) {
            temp = temp.substring("file:".length());
            semiPos = temp.indexOf(";");
            if (semiPos == -1) {
                urlRequired = true;
            } else {
                temp = temp.substring(0, semiPos);
                // now have location/dbname
                semiPos = temp.lastIndexOf("/");
                if (semiPos == -1 || semiPos == temp.length() - 1) {
                    urlRequired = true;
                } else {
                    tempDbLoc = temp.substring(0, semiPos);
                    tempDbName = temp.substring(semiPos + 1);
                }
            }
        } else {
            urlRequired = true;
        }
        if (setValues) {
            this.databaseName = tempDbName;
            this.dbms = thisDbms;
            this.dbLocation = tempDbLoc;
            this.urlRequired = urlRequired;
        }
        return retVal;
    }

    public String getDbURL() {
        checkInit();
        return url;
    }

    public String getDbUsername() {
        checkInit();
        return dbUsername;
    }

    public String getDbPassword() {
        checkInit();
        return dbPassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDbLocation() {
        return dbLocation;
    }

    public String getdbmsClassName() {
        switch (dbms) {
            case "h2" :
                return "org.h2.Driver";
            case "DB2":
                return "com.ibm.db2.jcc.DB2Driver";
            default:
                throw new RuntimeException("Unsupported DBMS " +  dbms);
        }
    }
}
