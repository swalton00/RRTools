dataSource {
    driverClassName = 'org.h2.Driver'
    username = 'rr'
    password = 'rrpass'
    jmx = 'true'
    pool {
        idleTimeout = 60000
        maximumPoolSize = 2
        minimumIdle = 1
    }
}

environments {
    development {
        dataSource {
            dbCreate = 'skip' // one of ['create', 'skip']
            url = 'jdbc:h2:file:D:/Projects/RailRoad_Database/Dev/test-dev;AUTO_SERVER=TRUE;SCHEMA=RR;MODE=DB2'
        }
    }
    DB2Dev {
        dataSource {
            dbCreate = 'skip'
            driverClassName = 'com.ibm.db2.jcc.DB2Driver'
            url='jdbc:db2://baker:50000/RRSample:currentSchema=RR;retrieveMessagesFromServerOnGetMessage=true;'
            username="RRUser"
            password="rrpass"
        }
    }
    test {
        dataSource {
            dbCreate = 'skip'
            url = 'jdbc:h2:file:D:/Projects/RailRoad_Database/Test/test-test;AUTO_SERVER=TRUE;SCHEMA=RR;MODE=DB2'
        }
    }
    production {
        dataSource {
            dbCreate = 'skip'
            url = 'jdbc:h2:file:D:/Projects/RailRoad_Database/Prod/prod;AUTO_SERVER=TRUE;SCHEMA=RR;MODE=DB2'
        }
    }
}
