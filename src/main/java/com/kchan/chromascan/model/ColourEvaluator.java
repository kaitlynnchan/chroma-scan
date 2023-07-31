package com.kchan.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class ColourEvaluator {

    // private String file;
    private BufferedImage image;
    private ArrayList<PixelPosition> pp;
    private ArrayList<ColourBreakdown> cb;
    private int numPos;

    // send a list of positions
    public ColourEvaluator(String file, int x, int y){
        this.image = createImage(file);
        this.pp = new ArrayList<PixelPosition>(){{
            add(new PixelPosition(x, y));
        }};
        this.cb = new ArrayList<ColourBreakdown>();
        populateColourBreakdown();
    }

    public ColourEvaluator(String file, ArrayList<PixelPosition> pp){
        this.image = createImage(file);
        this.pp = pp;
        this.cb = new ArrayList<ColourBreakdown>();
        populateColourBreakdown();
    }

    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file)); 
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private void populateColourBreakdown(){
        Iterator<PixelPosition> positions = pp.iterator();
        while(positions.hasNext()){
            PixelPosition p = positions.next();
            findColour(p.getX(), p.getY());
        }
    }

    private void findColour(int x, int y) {
        // Get data for creating colour object
        Rgb rgbObj = getRgbFromPosition(this.image, x, y);
        String hex = getHexFromRgb(rgbObj);
        // create colour object
        // Colour colour = new Colour(rgbObj, hex);

        cb.add(new ColourBreakdown(rgbObj, hex, 100));

        System.out.println(cb.get(cb.size()-1).getRgb());
        System.out.println(cb.get(cb.size()-1).getHex());
    }

    private String getHexFromRgb(Rgb rgb){
        return String.format("#%02X%02X%02X", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    private Rgb getRgbFromPosition(BufferedImage image, int x, int y){
        int pixel = image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<PixelPosition> getPixelPosition() {
        return pp;
    }

    public ArrayList<ColourBreakdown> getColourBreakdown() {
        return cb;
    }

    
}
