package com.chromascan.controller;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.chromascan.model.Colour;
import com.chromascan.model.ColourBreakdown;
import com.chromascan.model.DataPoint;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageControllerTest {

    ImageController ic;
    
    @Test
    public void successOnCreate() {
        System.out.println("====== successOnCreate ======");
        ic = new ImageController("test-img2.png", 
            new ArrayList<DataPoint>(){{
                add(new DataPoint(0, 0));
            }}
        );
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
        System.out.println("====== End ======");
    }

    @Test
    public void failureOnCreate(){
        System.out.println("====== failureOnCreate ======");
        String nullFile = "resources/test-img3.jpg";
        assertThrows(IllegalArgumentException.class, () -> new ImageController(new File(nullFile)));
        System.out.println("====== End ======");
    }

    @Test
    public void successOnIncorrectDataPoint(){
        System.out.println("====== successOnIncorrectDataPoint ======");
        assertDoesNotThrow(() -> new ImageController("test-img1.png", 
            new ArrayList<DataPoint>(){{
                add(new DataPoint(50, 50));
            }}
        ));
        System.out.println("====== End ======");
    }

    @Test
    public void successOnLargeSource() {
        System.out.println("====== successOnLargeSource ======");
        ic = new ImageController("test-img3.png",
            new ArrayList<DataPoint>(){{
                add(new DataPoint(10, 500));
                add(new DataPoint(358, 285));
                add(new DataPoint(600, 10));
            }}
        );
        ic.populateBreakdownArr();

        ColourBreakdown cb = ic.getDominantColour();
        assertAll(
            () -> assertEquals("#FFCC8D", cb.getHex()),
            () -> assertEquals(255, cb.getRgb().getRed()),
            () -> assertEquals(204, cb.getRgb().getGreen()),
            () -> assertEquals(141, cb.getRgb().getBlue()),
            () -> assertEquals(60.0, cb.getPercentage()),
            () -> assertNotNull(cb.getName())            
        );
        System.out.println("====== End ======");
    }

    @Test
    public void successOnColourMix(){
        System.out.println("====== successOnColourMix ======");
        ic = new ImageController("test-img3.png");
        ic.populateBreakdownArr();

        Colour mix = ic.getColourMix();
        assertAll(
            () -> assertEquals("#FFC179", mix.getHex()),
            () -> assertEquals(255, mix.getRgb().getRed()),
            () -> assertEquals(193, mix.getRgb().getGreen()),
            () -> assertEquals(121, mix.getRgb().getBlue()),
            () -> assertNotNull(mix.getName())
        );
        System.out.println("====== End ======");
    }
}
