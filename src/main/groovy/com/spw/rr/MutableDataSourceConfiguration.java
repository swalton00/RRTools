package com.spw.rr;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import griffon.core.Configuration;
import org.codehaus.griffon.runtime.core.configuration.ResourceBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class MutableDataSourceConfiguration extends ResourceBundleConfiguration {

    @Inject
    PropertyService propertyService;


    Map<String, Object> config = null;

    private static final Logger log = LoggerFactory.getLogger(MutableDataSourceConfiguration.class);

    @Inject
    public MutableDataSourceConfiguration(@Nonnull @Named("datasource") ResourceBundle resourceBundle) {
        super(resourceBundle);
        log.debug("Entered the constructor for MutableDataSourceConfigurator");
    }

    @Override
    public boolean containsKey(@Nonnull String key) {
        if ( key.equals("dataSource.url") |
                key.equals("dataSource.password") |
                key.equals("dataSource.username") |
                key.equals("dataSource.driverClassName") |
                key.equals("dataSource.dbCreate")) {
            log.debug("MutableDataSource found my key - returning true");
            return true;
        }
        return super.containsKey(key);
    }

    @Override
    public Object get(@Nonnull String key) {
        if (key.equals("dataSource")) {
            log.debug("MutableDataSource - returning a new configuration value");
            config = (Map<String, Object>) super.get("dataSource");
            String url = propertyService.getDbUrl();
            config.put("url", url);
            String dbClassname = "";
            if (url.contains("db2")) {
                dbClassname = "com.ibm.db2.jcc.DB2Driver";
            }
            if (url.contains("h2")) {
                dbClassname = "org.h2.Driver";
            }
            config.put("driverClassName", dbClassname);
            config.put("username", propertyService.getDbUsername());
            config.put("password", propertyService.getDbPassword());
            log.debug("config values are url: {}, user: {}, password: {} and class: {} ", config.get("url"),
                    config.get("username"), config.get("password"), config.get("driverClassName"));
            return config;
        }
        return super.get(key);
    }
}
