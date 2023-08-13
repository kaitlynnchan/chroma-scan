package com.chromascan.model;

/**
 * RGB CLASS
 * Illustrates information about an Rgb object.
 */
public class Rgb {

    private int red;
	private int green;
	private int blue;

    public Rgb(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed(){
        return this.red;
    }

    public void setRed(int val){
        this.red = val;
    }

    public int getGreen(){
        return this.green;
    }
    
    public void setGreen(int val){
        this.green = val;
    }

    public int getBlue(){
        return this.blue;
    }
    
    public void setBlue(int val){
        this.blue = val;
    }

    @Override
    public String toString() {
        return "(" +this.red + ", " + this.green + ", " + this.blue + ")";
    }
}
