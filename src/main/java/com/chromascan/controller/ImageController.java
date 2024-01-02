package com.chromascan.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.chromascan.model.*;

/**
 * COLOR EVALUATOR CLASS
 * Given a image and pixel positions, this class 
 * will find the colors in the image.
 */
public class ImageController {

    private static final int NUM_TOP_COLORS = 3;

    private Image imgObj;
    private ArrayList<DataPoint> dp;
    private ArrayList<ColorBreakdown> cb; // most dominant to least dominant

    public ImageController(File file){
        this.imgObj = new Image(file);
        this.dp = null;
        this.cb = new ArrayList<ColorBreakdown>();
    }

    public ImageController(String file){
        this.imgObj = new Image(file);
        this.dp = null;
        this.cb = new ArrayList<ColorBreakdown>();
    }
    
    public ImageController(File file, ArrayList<DataPoint> dp){
        this.imgObj = new Image(file);
        this.dp = validateDataPoints(dp);
        this.cb = new ArrayList<ColorBreakdown>();
    }

    public ImageController(String file, ArrayList<DataPoint> dp){
        this.imgObj = new Image(file);
        this.dp = validateDataPoints(dp);
        this.cb = new ArrayList<ColorBreakdown>();
    }

    public Image getImage() {
        return this.imgObj;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return this.dp;
    }

    public ArrayList<ColorBreakdown> getColorBreakdowns() {
        return this.cb;
    }

    public ColorBreakdown getDominantColor(){
        return cb.get(0);
    }

    /**
     * Finds the color that is a mix of all
     * the colors in color breakdown.
     * @return mixed color object
     */
    public Color getColorMix(){
        // sum of all the percentages
        Rgb mix = new Rgb(0, 0, 0);
        Double total = cb.stream()
            .mapToDouble(c -> c.getPercentage())
            .sum();

        // multiply each color by the new ratio
        for(ColorBreakdown b : cb){
            Double ratio = b.getPercentage() / total;
            int red = (int) Math.round(b.getRgb().getRed() * ratio);
            int green = (int) Math.round(b.getRgb().getGreen() * ratio);
            int blue = (int) Math.round(b.getRgb().getBlue() * ratio);
            // add value to Rgb
            mix.setRed(mix.getRed() + red);
            mix.setGreen(mix.getGreen() + green);
            mix.setBlue(mix.getBlue() + blue);
        }
        return new Color(mix);
    }

    /**
     * Validate data points are within range
     * @param dp    DataPoint array
     * @return      DataPoint array
     */
    private ArrayList<DataPoint> validateDataPoints(ArrayList<DataPoint> dp){
        try {
            for(DataPoint p : dp){
                if(p.getX() >= this.imgObj.getWidth() || p.getX() < 0 
                    || p.getY() >= this.imgObj.getHeight() || p.getY() < 0){
                    throw new IndexOutOfBoundsException("Data Point " + p + " is not a valid point");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return dp;
    }

    /**
     * Fills the breakdown array with the colors
     * in the image
     */
    public void populateBreakdownArr(){
        if(this.dp != null){
            Iterator<DataPoint> positions = this.dp.iterator();
            while(positions.hasNext()){
                DataPoint p = positions.next();
                Rgb rgbObj = this.imgObj.getRgbObject(p.getX(), p.getY());
                addColorBreakdown(rgbObj);
            }
        } else{
            // get the top colors
            for(int i = 1; i <= NUM_TOP_COLORS; i++){
                Rgb rgbObj = this.imgObj.getTopNRgb(i);
                if(rgbObj == null){
                    break;
                }
                addColorBreakdown(rgbObj);
            }
        }
    }

    /**
     * Add the color breakdown object to the
     * arraylist from a given rgb.
     * @param rgbObj rgb to add to arraylist
     */
    private void addColorBreakdown(Rgb rgbObj){
        long count = this.imgObj.getNumMatching(rgbObj);
        int size = this.imgObj.getSize();
        ColorBreakdown breakdown = new ColorBreakdown(rgbObj, count * 100 / size);
        System.out.println(breakdown);

        // add new color into the right position (highest percentage to lowest)
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
    }

}
