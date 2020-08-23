package com.spw.rr

import griffon.core.test.GriffonUnitRule
import griffon.core.test.TestFor
import org.junit.Rule
import org.junit.Test

import static org.junit.Assert.fail

@TestFor(SerialDataServiceService)
class SerialDataServiceServiceTest {
    static {
        // force initialization JavaFX Toolkit
        new javafx.embed.swing.JFXPanel()
    }

    private SerialDataServiceService service

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule()

    @Test
    void smokeTest() {
        fail('Not yet implemented!')
    }
}