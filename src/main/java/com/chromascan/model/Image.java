package com.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class Image {

    private BufferedImage image;
    private Rgb arr[][];
    private int width;
    private int height;

    public Image(String file){
        this.image = createImage(file);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.arr = new Rgb[this.height][this.width];
    }

    public int getSize(){
        return width * height;
    }

    public Rgb getRgbObject(int x, int y){
        return arr[y][x];
    }

    public long getNumMatching(Rgb comp){
        List<Rgb[]> list = Arrays.asList(arr);
        Long val = list.stream()
            .flatMap(Arrays::stream)
            .filter(r -> r.getRed() == comp.getRed() 
                && r.getGreen() == comp.getGreen() 
                && r.getBlue() == comp.getBlue())
            .count();
        return val;
    }

    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file)); 
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void evaluateImage(){
        // iterate through image
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                this.arr[y][x] = getRgbFromPosition(x, y);
            }
        }
    }

    private Rgb getRgbFromPosition(int x, int y){
        int pixel = this.image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}
