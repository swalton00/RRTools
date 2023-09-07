package com.spw.rr

import com.spw.rr.database.DbDB2Setup
import com.spw.rr.database.DbH2Setup
import com.spw.rr.model.Preferences
import griffon.core.GriffonApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement

@Singleton
class DbSetup {

    private static final Logger log = LoggerFactory.getLogger(DbSetup.class)

    String checkURL(String url, String user, String pw) {
        String fixedURL = url
        if (url.startsWith("jdbc:h2:")) {
            if (!url.contains("SCHEMA=")) {
                fixedURL = fixedURL + "SCHEMA=RR;"
            }
            if (url.startsWith("jdbc:h2:file:") & (!url.contains("AUTO_SERVER=TRUE;"))) {
                fixedURL = fixedURL + "AUTO_SERVER=TRUE;"
            }
            log.debug("about to test URL and, if necessary, create RR schema")
            int schemaPos = fixedURL.indexOf("SCHEMA=RR;")
            String leftString = fixedURL.substring(0, schemaPos)
            String rightString = fixedURL.substring(schemaPos + "SCHEMA=RR:".length())
            log.debug("left String is ${leftString}")
            log.debug("right String is ${rightString}")
            String tempURL = leftString + rightString
            log.debug("testing with ${tempURL}")
            try {
                Connection conn = DriverManager.getConnection(tempURL, user, pw)
                PreparedStatement stmt = conn.prepareStatement("CREATE SCHEMA IF NOT EXISTS RR")
                stmt.execute()
            } catch (Exception e) {
                log.error("Exception testing URL", e)
            }
            log.debug("url now is {}", fixedURL)
        } else if (url.startsWith("jdbc:db2:")) {
            log.debug("db2 was requested with a URL of {}", fixedURL)
        } else {
            log.error("unsupported database requested")
        }
        return fixedURL
    }

    String dbStart(String dbURL, String dbUser, String dbPass) {
        log.debug("Starting the database checks")
        Connection conn = null
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass)
            Statement stmt = conn.createStatement()
            boolean res = stmt.execute("select count(*) from cars")
            if (res) {
                return ""            }
        } catch (Exception e) {
            if (e instanceof SQLException) {
                if (((SQLException) e).getSQLState().equals("28000") ) {
                    return "Incorrect Userid or Password"
                }
            }
        }

        if (dbURL.startsWith("jdbc:h2")) {
            DbH2Setup setup = DbH2Setup.getInstance()
            return setup.setup(dbURL, dbUser, dbPass)
        } else if (url.startsWith("jdbc:db2")) {
            DbDB2Setup.setup(dbURL, dbUser, dbPass)
        } else {
            log.error("Unsupported database in URL {}", url)
            return "Unsupported database or Invalid database URL"
        }
        return ""
    }

}
