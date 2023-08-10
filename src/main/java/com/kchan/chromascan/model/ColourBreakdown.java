package com.kchan.chromascan.model;

/**
 * COLOUR BREAKDOWN CLASS
 * Extends from Colour Class to include the
 * percentage of how much the colour covers
 * the image.
 */
public class ColourBreakdown extends Colour {

    private double percentage; // [0-100]

    public ColourBreakdown(Rgb rgb, double percentage){
        super(rgb);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return this.percentage;
    }
}
