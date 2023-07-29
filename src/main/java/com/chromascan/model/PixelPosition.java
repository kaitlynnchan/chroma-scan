package main.java.com.chromascan.model;

public class PixelPosition {
    private int x;
    private int y;

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
