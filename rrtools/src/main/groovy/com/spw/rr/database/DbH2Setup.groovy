package com.spw.rr.database

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.util.regex.Matcher
import java.util.regex.Pattern

@Singleton
@griffon.transform.ResourcesAware
class DbH2Setup {
    private static final Logger log = LoggerFactory.getLogger(DbH2Setup.class)
    private static final int TABLE_COUNT = 13

    static String findSchema(String url) {
        log.debug("looking for schema in {}", url)
        String found = null
        Pattern pattern = Pattern.compile("(?i)schema\\=([\\w]{1,48});")
        Matcher m = pattern.matcher(url)
        if (!m.find()) {
            return null
        }
        String schema = m.group(1)
        log.debug("url found schema {}", schema)
        return schema
    }

    String setup(String url, String user, String pw) {
        log.debug("Starting H2 Setup")
        String schema = findSchema(url)
        ApplyResources resourceExecutor = null
        int semiPos = url.indexOf(";") // find first semicolon - (end of base + 1)
        String baseURL = url
        if (semiPos != -1) {
            baseURL = url.substring(0, semiPos)
        }
        Connection conn = null
        try {
            conn = DriverManager.getConnection(baseURL, user, pw)
            log.debug("successful connection to {}", url)
        } catch (Exception e) {
            log.error("Connection failed", e)
            return "Unsuccessful connection"
        }
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement("select * from information_schema.schemata where Schema_name = ?")
                stmt.setString(1, schema)
                if (stmt.execute()) {
                    log.debug("got a result from the test for schema {}", schema)
                } else {
                    log.debug("schema not found - creating {}", schema)
                    String sqlStmt = "CREATE SCHEMA " + schema
                    Statement schemaStmt = conn.createStatement()
                    schemaStmt.execute(sqlStmt)
                }
                stmt = conn.prepareStatement("select count(*) from information_schema.tables where table_schema = ?")
                stmt.setString(1, schema)
                if (stmt.execute()) {
                    ResultSet rs = stmt.getResultSet()
                    rs.first()
                    int tableCount = rs.getInt(1)
                    if (tableCount != TABLE_COUNT &  tableCount != 0) {
                        log.error("Incorrect number of tables found in existing database - found {}", tableCount)
                    } else if (tableCount == 0) {
                        log.debug("found no tables in schema - creating them")
                        if (resourceExecutor == null)
                            resourceExecutor = new ApplyResources()
                        resourceExecutor.excuteSQLResource(conn, "sql/Create_H2_DB.sql" )
                        resourceExecutor.excuteSQLResource(conn, "sql/Insert_Data.sql")
                    }
                }
                conn.close()
                conn = DriverManager.getConnection(url, user, pw)
                if (conn != null) {
                    log.debug("got a connection with full URL")

                }
                return null
            } catch (Exception e) {
                log.error("Exception attempting to test for schema", e)
                return "Schema does not exist"
            }
        }
    }

}
