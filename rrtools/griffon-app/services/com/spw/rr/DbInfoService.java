package com.spw.rr;

import griffon.core.artifact.GriffonService;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ArtifactProviderFor(GriffonService.class)
public class DbInfoService  extends AbstractGriffonService {
    private static final Logger log = LoggerFactory.getLogger(DbInfoService.class);

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

    public void setDatabaseInfo(String dbUrl, String dbUser, String dbPassword) {
        url = dbUrl;
        dbUser = dbUser;
        this.dbPassword = dbPassword;
        valuesSet = true;
    }

    private void parseURL() {

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
