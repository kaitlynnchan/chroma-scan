package com.kchan.chromascan.model;

/**
 * Models information of the breakdown of a Colour.
 * Extends the Colour object
 * Parameters: percentage
 */
public class ColourBreakdown extends Colour {

    private double percentage;

    public ColourBreakdown(Rgb rgb, double percentage){
        super(rgb);
        this.percentage = percentage;
    }

    public ColourBreakdown(Rgb rgb, String hex, String name, double percentage){
        super(rgb, hex, name);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return this.percentage;
    }
}
