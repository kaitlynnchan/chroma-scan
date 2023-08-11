package com.chromascan.model;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ColourEvaluatorTest {

    ImageEvaluator ce;
    
    @Test
    public void successOnCreate() {
        ImageEvaluator ce = new ImageEvaluator("test-img2.png", 0, 0);
        ce.getImage().evaluateImage();
        ce.createBreakdown();

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
        assertThrows(IllegalArgumentException.class, () -> new ImageEvaluator(nullFile, 0, 0));
    }

    
    @Test
    public void successOnLargeSource() {
        ImageEvaluator ce = new ImageEvaluator("test-img3.png",
            new ArrayList<PixelPosition>(){{
                add(new PixelPosition(10, 500));
                add(new PixelPosition(358, 285));
                add(new PixelPosition(600, 10));
            }}
        );
        ce.getImage().evaluateImage();
        ce.createBreakdown();

        ColourBreakdown cb = ce.getDominantColour();
        assertAll(
            () -> assertEquals("#FFCC8D", cb.getHex()),
            () -> assertEquals(255, cb.getRgb().getRed()),
            () -> assertEquals(204, cb.getRgb().getGreen()),
            () -> assertEquals(141, cb.getRgb().getBlue()),
            // () -> assertEquals(100, cb.getPercentage()),
            () -> assertNotNull(cb.getName())            
        );
    }
}
