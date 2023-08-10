package com.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

/**
 * COLOUR EVALUATOR CLASS
 * Given a image and pixel positions, this class 
 * will find the colours in the image.
 */
public class ImageEvaluator {

    private Image imgObj;
    private ArrayList<PixelPosition> pp;
    private ArrayList<ColourBreakdown> cb; // most dominant to least dominant

    public ImageEvaluator(String file, int x, int y){
        this.imgObj = new Image(file);
        this.pp = new ArrayList<PixelPosition>(){{
            add(new PixelPosition(x, y));
        }};
        this.cb = new ArrayList<ColourBreakdown>();
    }

    // public ImageEvaluator(String file, ArrayList<PixelPosition> pp){
    //     this.image = createImage(file);
    //     this.pp = pp;
    //     this.cb = new ArrayList<ColourBreakdown>();
    // }

    public Image getImage() {
        return this.imgObj;
    }

    public ArrayList<PixelPosition> getPixelPosition() {
        return this.pp;
    }

    public ArrayList<ColourBreakdown> getColourBreakdown() {
        return this.cb;
    }

    public ColourBreakdown getDominantColour(){
        // primary, dominant?
        return cb.get(0);
    }

    public void createBreakdown(){
        Iterator<PixelPosition> positions = pp.iterator();
        while(positions.hasNext()){
            PixelPosition p = positions.next();
            // findColour(p.getX(), p.getY());

            Rgb rgbObj = this.imgObj.getRgbObject(p.getX(), p.getY());
            long count = this.imgObj.getNumMatching(rgbObj);
            int size = this.imgObj.getSize();
            addColourBreakdown(new ColourBreakdown(rgbObj, count * 100 / size));
        }
    }

    private void addColourBreakdown(ColourBreakdown breakdown){
        for(int i = 0; i < this.cb.size(); i++){
            if(this.cb.get(i).getPercentage() == breakdown.getPercentage()){
                this.cb.add(i, breakdown);
                return;
            } else if(this.cb.get(i).getPercentage() > breakdown.getPercentage()){
                continue;
            }
            this.cb.add(i, breakdown);
            return;
        }
        this.cb.add(breakdown);

        // this.cb.add(new ColourBreakdown(rgbObj, count * 100 /size));
        System.out.println("======");
        System.out.println("RGB: " + breakdown.getRgb());
        System.out.println("Hex: " + breakdown.getHex());
        System.out.println("Name: " + breakdown.getName());
    }

    // /**
    //  * Create the image object from the file given
    //  * 
    //  * @param file  name of file
    //  * @return      image object
    //  */
    // private BufferedImage createImage(String file){
    //     try {
    //         return ImageIO.read(getClass().getResource(file)); 
    //     } catch (IOException | IllegalArgumentException e) {
    //         System.out.println(e);
    //         throw new IllegalArgumentException(e.getMessage());
    //     }
    // }


    /**
     * Find the colours in all of the positions.
     * Populate colour breakdown array list with the 
     * parsed colours.
     */
    // public void findColours(){
    //     Iterator<PixelPosition> positions = pp.iterator();
    //     while(positions.hasNext()){
    //         PixelPosition p = positions.next();
    //         // findColour(p.getX(), p.getY());

    //         Rgb rgbObj = getRgbFromPosition(p.getX(), p.getY());
    //         this.cb.add(new ColourBreakdown(rgbObj, 100));
    //         System.out.println("RGB: " + cb.get(cb.size()-1).getRgb());
    //         System.out.println("Hex: " + cb.get(cb.size()-1).getHex());
    //         System.out.println("Name: " + cb.get(cb.size()-1).getName());
    //     }
    // }

    // /**
    //  * Finds the rgb value from the position in
    //  * the image
    //  * 
    //  * @param x horizontal value
    //  * @param y vertical value
    //  * @return  rgb object
    //  */
    // private Rgb getRgbFromPosition(int x, int y){
    //     int pixel = this.image.getRGB(x, y);
    //     Color color = new Color(pixel, true);
    //     return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    // }

    // public static void main(String[] args) {
    //     ImageEvaluator ce = new ImageEvaluator("test-img2.png", 0, 0);
    //     ce.getImage().evaluateImage();
    //     ce.createBreakdown();

    //     ColourBreakdown c = ce.getDominantColour();
    //     System.out.println(c);

    // }
}
