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
    private ArrayList<PixelPosition> pp;
    private ArrayList<ColourBreakdown> cb; // most dominant to least dominant

    public ImageController(String file){
        this.imgObj = new Image(file);
        this.pp = null;
        this.cb = new ArrayList<ColourBreakdown>();
    }

    public ImageController(String file, ArrayList<PixelPosition> pp){
        this.imgObj = new Image(file);
        this.pp = pp;
        this.cb = new ArrayList<ColourBreakdown>();
    }

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
        if(this.pp != null){
            Iterator<PixelPosition> positions = this.pp.iterator();
            while(positions.hasNext()){
                PixelPosition p = positions.next();
                // findColour(p.getX(), p.getY());

                Rgb rgbObj = this.imgObj.getRgbObject(p.getX(), p.getY());
                long count = this.imgObj.getNumMatching(rgbObj);
                int size = this.imgObj.getSize();
                addColourBreakdown(new ColourBreakdown(rgbObj, count * 100 / size));
            }
        } else{
            for(int i = 1; i <= NUM_TOP_COLOURS; i++){
                Rgb rgbObj = this.imgObj.getTopNRgb(i);
                long count = this.imgObj.getNumMatching(rgbObj);
                int size = this.imgObj.getSize();
                addColourBreakdown(new ColourBreakdown(rgbObj, count * 100 / size));
            }
        }
    }

    private void addColourBreakdown(ColourBreakdown breakdown){
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
