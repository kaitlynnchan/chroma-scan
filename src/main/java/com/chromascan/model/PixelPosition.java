package com.chromascan.model;

/**
 * PIXEL POSITION CLASS
 * Illustrates information about the x and y
 * location on an image.
 */
public class PixelPosition {

    private int x;  // horizontal position
    private int y;  // vertical position

    public PixelPosition(int x, int y){
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
