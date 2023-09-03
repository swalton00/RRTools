package com.spw.rr

import com.spw.rr.database.DbDB2Setup
import com.spw.rr.database.DbH2Setup
import com.spw.rr.model.Preferences
import griffon.core.GriffonApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

@Singleton
class DbSetup {

    private static final Logger log = LoggerFactory.getLogger(DbSetup.class)

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
