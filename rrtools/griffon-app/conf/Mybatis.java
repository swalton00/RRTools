import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static griffon.util.CollectionUtils.map;

public class Mybatis extends AbstractMapResourceBundle {
    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
                .e("sessionFactory", map()
                        .e("lazyLoadingEnabled", "false")
                        .e("cacheEnabled", "false"))
                .e("environments", map()
                        .e("development", map()
                                .e("sessionFactory", map()
                                        .e("lazyLoadingEnabled", "false")))
                        .e("test", map()
                                .e("sessionFactory", map()
                                        .e("lazyLoadingEnabled", "false")))
                        .e("db2dev", map()
                                .e("sessionFactory", map()
                                        .e("lazyLoadingEnabled", "false")))
                        .e("production", map()
                                .e("sessionFactory", map()
                                        .e("lazyLoadingEnabled", "false"))))
                .e("mappers", map()
                        .e("class", "com.spw.rr.DBMapper"))

        ;
    }
}
