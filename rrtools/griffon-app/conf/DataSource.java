import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static griffon.util.CollectionUtils.map;

public class DataSource extends AbstractMapResourceBundle {

    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
                .e("dataSource", map()
                        .e("driverClassName", "org.h2.Driver")
                        .e("username", "rr")
                        .e("password", "rrpass")
                        .e("jmx", "true")
                        .e("pool", map()
                                .e("idleTimeout", "60000")
                                .e("maximumPoolSize", "2")
                                .e("minimumIdle", "1")
                        )

                        .e("environments", map()
                                .e("development", map()
                                        .e("dataSource", map()
                                                .e("dbCreate", "skip")
                                                .e("url", "jdbc:h2:file:D:/Projects/RailRoad_Database/Dev/test-dev;AUTO_SERVER=TRUE;SCHEMA=RR;MODE=DB")
                                        )
                                )
                        )
                );

    }
}
