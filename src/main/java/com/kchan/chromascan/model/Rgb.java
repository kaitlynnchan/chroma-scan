package com.kchan.chromascan.model;

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

    public int getGreen(){
        return this.green;
    }

    public int getBlue(){
        return this.blue;
    }

    @Override
    public String toString() {
        return "(" +this.red + ", " + this.green + ", " + this.blue + ")";
    }
}
