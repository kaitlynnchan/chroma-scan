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

    public static void main(String[] args) {
        ImageController ic = new ImageController("test-img3.png");
        ic.getImage().evaluateImage();
        Rgb top1 = ic.getImage().getTopNRgb(1);
        Rgb top2 = ic.getImage().getTopNRgb(2);
        Rgb top3 = ic.getImage().getTopNRgb(3);
    }

}
