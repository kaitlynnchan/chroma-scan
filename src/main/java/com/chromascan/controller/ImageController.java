package com.chromascan.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.chromascan.model.*;

/**
 * COLOUR EVALUATOR CLASS
 * Given a image and pixel positions, this class 
 * will find the colours in the image.
 */
public class ImageController {

    private static final int NUM_TOP_COLOURS = 3;

    private Image imgObj;
    private ArrayList<DataPoint> dp;
    private ArrayList<ColourBreakdown> cb; // most dominant to least dominant

    public ImageController(String file){
        this.imgObj = new Image(file);
        this.dp = null;
        this.cb = new ArrayList<ColourBreakdown>();
    }

    public ImageController(String file, ArrayList<DataPoint> dp){
        this.imgObj = new Image(file);
        this.dp = dp;
        this.cb = new ArrayList<ColourBreakdown>();
    }

    public Image getImage() {
        return this.imgObj;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return this.dp;
    }

    public ArrayList<ColourBreakdown> getColourBreakdowns() {
        return this.cb;
    }

    public ColourBreakdown getDominantColour(){
        // primary, dominant?
        return cb.get(0);
    }

    // public Colour getComplimentingColour(){

    // }

    public Colour getColourMix(){
        // sum percentages
        Double total = cb.stream()
            .mapToDouble(c -> c.getPercentage())
            .sum();
        
        Rgb mix = new Rgb(0, 0, 0);
        // iterate through each colour
        for(ColourBreakdown b : cb){
            Double ratio = b.getPercentage() / total;
            int red = (int) Math.round(b.getRgb().getRed() * ratio);
            int green = (int) Math.round(b.getRgb().getGreen() * ratio);
            int blue = (int) Math.round(b.getRgb().getBlue() * ratio);
            // add value to Rgb
            mix.setRed(mix.getRed() + red);
            mix.setGreen(mix.getGreen() + green);
            mix.setBlue(mix.getBlue() + blue);
        }
        return new Colour(mix);
    }

    public void populateBreakdownArr(){
        if(this.dp != null){
            Iterator<DataPoint> positions = this.dp.iterator();
            while(positions.hasNext()){
                DataPoint p = positions.next();
                Rgb rgbObj = this.imgObj.getRgbObject(p.getX(), p.getY());
                addColourBreakdown(rgbObj);
            }
        } else{
            for(int i = 1; i <= NUM_TOP_COLOURS; i++){
                Rgb rgbObj = this.imgObj.getTopNRgb(i);
                addColourBreakdown(rgbObj);
            }
        }
    }

    private void addColourBreakdown(Rgb rgbObj){
        long count = this.imgObj.getNumMatching(rgbObj);
        int size = this.imgObj.getSize();
        ColourBreakdown breakdown = new ColourBreakdown(rgbObj, count * 100 / size);

        System.out.println("======");
        System.out.println("RGB: " + breakdown.getRgb());
        System.out.println("Hex: " + breakdown.getHex());
        System.out.println("Name: " + breakdown.getName());
        System.out.println("Percentage: " + breakdown.getPercentage());

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
