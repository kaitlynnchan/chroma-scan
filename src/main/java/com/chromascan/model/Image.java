package com.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class Image {

    private BufferedImage image;
    private Rgb arr[][];
    private List<Map.Entry<String,Long>> imgColours;
    private int width;
    private int height;

    public Image(File file){
        this.image = createImage(file);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.arr = new Rgb[this.height][this.width];

        evaluateImage();
        sortArr();
    }

    public Image(String file){
        this.image = createImage(file);
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        this.arr = new Rgb[this.height][this.width];

        evaluateImage();
        sortArr();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize(){
        return width * height;
    }

    public int getTotalNumColours(){
        return imgColours.size();
    }
    
    public Rgb getRgbObject(int x, int y){
        return arr[y][x];
    }

    /**
     * Finds the rgb object that matches with comp
     * within the image colours list and returns how
     * many pixels have this rgb colour.
     * @param comp  Rgb to find
     * @return      Number of values matching comp
     */
    public long getNumMatching(Rgb comp){
        Iterator<Map.Entry<String,Long>> iterator = this.imgColours.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Long> colour = iterator.next();
            if(colour.getKey().equals(comp.toString())){
                return colour.getValue();
            }
        }
        return 0;
    }

    /**
     * Finds the top n number of colours in the 
     * image. If n is more than the total number of 
     * colours, returns the last val.
     * @param n top n number to return
     * @return  Rgb object of the top number
     */
    public Rgb getTopNRgb(int n){
        if(n > getTotalNumColours()){
            return parseStringToRgb(imgColours.get(getTotalNumColours()-1).getKey());
        }
        return parseStringToRgb(imgColours.get(n-1).getKey());
    }

    /**
     * Parses the rgbString to Rgb object
     * @param rgbString
     * @return
     */
    private Rgb parseStringToRgb(String rgbString){
        String stripped = rgbString.replaceAll("[()]", "");
        String[] parse = stripped.split("[, ]+");
        
        int red = Integer.parseInt(parse[0]);
        int green = Integer.parseInt(parse[1]);
        int blue = Integer.parseInt(parse[2]);
        return new Rgb(red, green, blue);
    }

    /**
     * Creates image from the file
     * @param file  file thats located in resources/com/chromascan/model
     * @return      bufferedImage object
     */
    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private BufferedImage createImage(File file){
        try {
            return ImageIO.read(file);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    /**
     * Map pixels from image to Rgb object
     */
    private void evaluateImage(){
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                this.arr[y][x] = getRgbFromPosition(x, y);
            }
        }
    }

    /**
     * Get the rgb value from the position in
     * image and returns the object.
     * @param x horizontal position
     * @param y vertical position
     * @return  Rgb object with colours
     */
    private Rgb getRgbFromPosition(int x, int y){
        int pixel = this.image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Fills the imageColours list with the grouped
     * and sorted array of all the colours in the
     * image along with the number of matching values.
     */
    private void sortArr(){
        List<Rgb[]> list = Arrays.asList(this.arr);
        this.imgColours = list.stream()
            .flatMap(Arrays::stream)
            .collect(
                Collectors.groupingBy(
                    Rgb::toString, Collectors.counting()
                )
            )
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toList());
    }
}
