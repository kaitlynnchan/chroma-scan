package com.chromascan.model;

/**
 * COLOR BREAKDOWN CLASS
 * Extends from Color Class to include the
 * percentage of how much the color covers
 * the image.
 */
public class ColorBreakdown extends Color {

    private double percentage; // [0-100]

    public ColorBreakdown(Rgb rgb, double percentage){
        super(rgb);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return this.percentage;
    }

    @Override
    public String toString() {
        return "ColorBreakdown [rgb=" + getRgb() 
            + ", hex=" + getHex()
            + ", name=" + getName()
            + ", percentage=" + percentage + "%"
            + "]";
    }
}
