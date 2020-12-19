sessionFactory {
    // specify any properties from org.apache.ibatis.session.Configuration
    lazyLoadingEnabled = false
}

environments {
    development {
        sessionFactory {
            lazyLoadingEnabled = false
        }
    }
    db2dev {
        sessionFactory {
            lazyLoadingEnabled = false
        }
    }

    test {
        sessionFactory {
            lazyLoadingEnabled = false
        }
    }
    production {
        sessionFactory {
            lazyLoadingEnabled = false
        }
    }
}