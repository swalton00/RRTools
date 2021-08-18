package com.spw.rr.database

import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Singleton
class DbDB2Setup {

    private static final Logger log = LoggerFactory.getLogger(DbDB2Setup.class)

    void setup(String url, String user, String pw) {
        log.debug("Starting DB2 Setup")
    }

}
