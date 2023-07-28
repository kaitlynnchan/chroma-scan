package main.java.com.chromascan.model;

public class ColourEvaluator {
    

    // public ColourEvaluator(){
    //     this.colour = new Colour();
    // }

    public static void main() {
        ColourEvaluator ce = new ColourEvaluator();

        String file = "";
        int x = 0, y = 0;
        
        Source src = new Source(file);
        Rgb rgbObj = src.getRgbFromPosition(x, y);
        String hex = ce.getHexFromRgb(rgbObj);
        Colour colour = new Colour(rgbObj, hex);
    }

    private String getHexFromRgb(Rgb rgb){
        return String.format("#%02x%02x%02x", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }
}
