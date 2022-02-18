package com.spw.rr;

import javax.inject.Named;
import griffon.core.mvc.MVCGroup;
import org.codehaus.griffon.runtime.core.mvc.AbstractTypedMVCGroup;
import javax.annotation.Nonnull;

@Named("rrTools")
public class RrToolsMVCGroup extends AbstractTypedMVCGroup<RrToolsModel, RrToolsView, RrToolsController> {
    public RrToolsMVCGroup(@Nonnull MVCGroup delegate) {
        super(delegate);
    }
}