package test.java.com.kchan.chromascan.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

import org.junit.Test;

import main.java.com.kchan.chromascan.model.ColourBreakdown;
import main.java.com.kchan.chromascan.model.ColourEvaluator;

public class ColourEvaluatorTest {

    ColourEvaluator ce;
    
    @Test
    public void successOnCreate() {
        ce = new ColourEvaluator("/test/java/com/kchan/chromascan/resources/test-img2.png", 0, 0);
        ColourBreakdown cb = ce.getColourBreakdown().get(ce.getColourBreakdown().size() - 1);
        assertEquals("#C2ED6D", cb.getHex());
    }

    // @Test
    // public void failureOnCreate(){
    //     String nullFile = "resources/test-img3.png";
    //     assertThrows(IOException, ColourBreakdown(nullFile, 0, 0));
    // }
}
