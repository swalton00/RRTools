package com.spw.rr

import griffon.javafx.test.GriffonTestFXRule
import org.junit.Rule
import org.junit.Test

import static org.junit.Assert.fail

class ReferenceIntegrationTest {
    @Rule
    public GriffonTestFXRule testfx = new GriffonTestFXRule('mainWindow')

    @Test
    void smokeTest() {
        fail('Not implemented yet!')
    }
}
