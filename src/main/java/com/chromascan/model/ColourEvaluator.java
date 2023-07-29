package main.java.com.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColourEvaluator {

    // private String file;
    // private int x;
    // private int y;

    // public ColourEvaluator(String file, int x, int y){
    //     this.file = file;
    //     this.x = x;
    //     this.y = y;
    // }

    public static void main(String[] args) {
        String file = "resources/test-img2.png";
        int x = 0;
        int y = 0;

        System.out.println("here1");
        ColourEvaluator ce = new ColourEvaluator();

        // Source src = new Source(file);
        File srcImgFile;
        BufferedImage image;
        try {
            srcImgFile = new File(file);
            image = ImageIO.read(srcImgFile);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        // Get data for creating colour object
        Rgb rgbObj = ce.getRgbFromPosition(image, x, y);
        String hex = ce.getHexFromRgb(rgbObj);
        // create colour object
        Colour colour = new Colour(rgbObj, hex);

        System.out.println(colour.getRgb().getRed() + ", " + colour.getRgb().getGreen() + ", " + colour.getRgb().getBlue());
        System.out.println(colour.getHex());
    }

    private String getHexFromRgb(Rgb rgb){
        return String.format("#%02X%02X%02X", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    private Rgb getRgbFromPosition(BufferedImage image, int x, int y){
        int pixel = image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}
