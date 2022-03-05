import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static java.util.Arrays.asList;
import static griffon.util.CollectionUtils.map;

public class Config extends AbstractMapResourceBundle {
    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
            .e("application", map()
                    .e("title", "rrTools")
                    .e("startupGroups", asList("rrTools"))
                    .e("autoShutdown", true)
                    .e("startingWindow", "rrTools")
            )
                .e("mvcGroups", map()
                        .e("rrTools", map()
                                .e("model", "com.spw.rr.RrToolsModel")
                                .e("view", "com.spw.rr.RrToolsView")
                                .e("controller", "com.spw.rr.RrToolsController")
                        )
                );

    }
}