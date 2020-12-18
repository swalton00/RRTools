package com.spw.rr

import griffon.core.test.GriffonUnitRule
import griffon.core.test.TestFor
import org.junit.Rule
import org.junit.Test

import static org.junit.Assert.fail

@TestFor(MaintenanceController)
class MaintenanceControllerTest {
    static {
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel()
    }

    private MaintenanceController controller

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule()

    @Test
    void smokeTest() {
        fail('Not yet implemented!')
    }
}