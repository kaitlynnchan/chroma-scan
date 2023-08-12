package com.chromascan.model;

/**
 * Data POINT CLASS
 * Illustrates information about the x and y
 * location on an image.
 */
public class DataPoint {

    private int x;  // horizontal position
    private int y;  // vertical position

    public DataPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
