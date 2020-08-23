dataSource {
    driverClassName = 'org.h2.Driver'
    username = 'rr'
    password = 'rrpass'
    pool {
        idleTimeout = 60000
        maximumPoolSize = 8
        minimumIdle = 5
    }
}

environments {
    development {
        dataSource {
            dbCreate = 'skip' // one of ['create', 'skip']
            url = 'jdbc:h2:file:D:/Projects/RailRoad_Database/Dev/test-dev;AUTO_SERVER=TRUE'
        }
    }
    test {
        dataSource {
            dbCreate = 'skip'
            url = 'jdbc:h2:file:D:/Projects/RailRoad_Database/Test/test-test;AUTO_SERVER=TRUE'
        }
    }
    production {
        dataSource {
            dbCreate = 'skip'
            url = 'jdbc:h2:file:D:/Projects/RailRoad_Database/Prod/prod;AUTO_SERVER=TRUE'
        }
    }
}
