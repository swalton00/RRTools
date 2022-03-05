package com.spw.rr;

import griffon.core.artifact.GriffonController;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

@ArtifactProviderFor(GriffonController.class)
public class HelpController extends AbstractGriffonController {
    @MVCMember @Nonnull
    private HelpModel model;
    @MVCMember @Nonnull
    private HelpView view;

    private Logger log = LoggerFactory.getLogger(HelpController.class);

    public void close() {
        log.debug("closing the help window");
        application.getWindowManager().hide("help");
    }

}
