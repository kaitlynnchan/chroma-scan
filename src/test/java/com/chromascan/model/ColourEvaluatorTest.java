package com.chromascan.model;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.chromascan.model.ColourBreakdown;
import com.chromascan.model.ColourEvaluator;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColourEvaluatorTest {

    ColourEvaluator ce;
    
    @Test
    public void successOnCreate() {
        ce = new ColourEvaluator("test-img2.png", 0, 0);
        ce.findColours();
        ColourBreakdown cb = ce.getDominantColour();
        assertAll(
            () -> assertEquals("#C2ED6D", cb.getHex()),
            () -> assertEquals(194, cb.getRgb().getRed()),
            () -> assertEquals(237, cb.getRgb().getGreen()),
            () -> assertEquals(109, cb.getRgb().getBlue()),
            () -> assertEquals(100, cb.getPercentage()),
            () -> assertNotNull(cb.getName())            
        );
    }

    @Test
    public void failureOnCreate(){
        String nullFile = "resources/test-img3.png";
        assertThrows(IllegalArgumentException.class, () -> new ColourEvaluator(nullFile, 0, 0));
    }
}
