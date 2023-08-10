package com.kchan.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

/**
 * COLOUR EVALUATOR CLASS
 * Given a image and pixel positions, this class 
 * will find the colours in the image.
 */
public class ColourEvaluator {

    private BufferedImage image;
    private ArrayList<PixelPosition> pp;
    private ArrayList<ColourBreakdown> cb; // most dominant to least dominant

    public ColourEvaluator(String file, int x, int y){
        this.image = createImage(file);
        this.pp = new ArrayList<PixelPosition>(){{
            add(new PixelPosition(x, y));
        }};
        this.cb = new ArrayList<ColourBreakdown>();
    }

    public ColourEvaluator(String file, ArrayList<PixelPosition> pp){
        this.image = createImage(file);
        this.pp = pp;
        this.cb = new ArrayList<ColourBreakdown>();
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

    public ColourBreakdown getDominantColour(){
        // primary, dominant?
        return cb.get(0);
    }

    /**
     * Create the image object from the file given
     * 
     * @param file  name of file
     * @return      image object
     */
    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file)); 
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Find the colours in all of the positions.
     * Populate colour breakdown array list with the 
     * parsed colours.
     */
    public void findColours(){
        Iterator<PixelPosition> positions = pp.iterator();
        while(positions.hasNext()){
            PixelPosition p = positions.next();
            // findColour(p.getX(), p.getY());

            Rgb rgbObj = getRgbFromPosition(p.getX(), p.getY());
            this.cb.add(new ColourBreakdown(rgbObj, 100));
            System.out.println("RGB: " + cb.get(cb.size()-1).getRgb());
            System.out.println("Hex: " + cb.get(cb.size()-1).getHex());
            System.out.println("Name: " + cb.get(cb.size()-1).getName());
        }
    }

    /**
     * Finds the rgb value from the position in
     * the image
     * 
     * @param x horizontal value
     * @param y vertical value
     * @return  rgb object
     */
    private Rgb getRgbFromPosition(int x, int y){
        int pixel = this.image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    // public static void main(String[] args) {
    //     ColourEvaluator ce = new ColourEvaluator(null, 0, 0);
    //     ce.findColours();
    // }
}
