package com.chromascan.model;

/**
 * RGB CLASS
 * Illustrates information about an Rgb object.
 */
public class Rgb {

    private int red;
	private int green;
	private int blue;

    public Rgb(String rgbString){
        String stripped = rgbString.replaceAll("[()]", "");
        String[] parse = stripped.split("[, ]+");
        
        this.red = Integer.parseInt(parse[0]);
        this.green = Integer.parseInt(parse[1]);
        this.blue = Integer.parseInt(parse[2]);
    }

    public Rgb(int red, int green, int blue){
        if( red < 0 || green < 0 || blue < 0 
            || red > 255 || green > 255 || blue > 255 ){
                throw new IllegalArgumentException("(" + red + ", " + green + ", " + blue + ") are invalid paramaters.");
            }
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
