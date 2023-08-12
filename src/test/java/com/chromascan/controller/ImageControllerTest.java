package com.chromascan.controller;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.chromascan.model.ColourBreakdown;
import com.chromascan.model.DataPoint;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageControllerTest {

    ImageController ic;
    
    @Test
    public void successOnCreate() {
        ic = new ImageController("test-img2.png", 
            new ArrayList<DataPoint>(){{
                add(new DataPoint(0, 0));
            }}
        );
        ic.getImage().evaluateImage();
        ic.populateBreakdownArr();

        ColourBreakdown cb = ic.getDominantColour();
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
        assertThrows(IllegalArgumentException.class, () -> new ImageController(nullFile));
    }

    
    @Test
    public void successOnLargeSource() {
        ic = new ImageController("test-img3.png",
            new ArrayList<DataPoint>(){{
                add(new DataPoint(10, 500));
                add(new DataPoint(358, 285));
                add(new DataPoint(600, 10));
            }}
        );
        ic.getImage().evaluateImage();
        ic.populateBreakdownArr();

        ColourBreakdown cb = ic.getDominantColour();
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
