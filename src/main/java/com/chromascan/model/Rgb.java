package main.java.com.chromascan.model;

public class Rgb {

    private int redVal;
	private int greenVal;
	private int blueVal;

    public Rgb(int red, int green, int blue){
        this.redVal = red;
        this.greenVal = green;
        this.blueVal = blue;
    }

    public int getRedVal(){
        return this.redVal;
    }

    public int getGreenVal(){
        return this.greenVal;
    }

    public int getBlueVal(){
        return this.blueVal;
    }
}
