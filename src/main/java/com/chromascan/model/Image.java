package com.chromascan.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class Image {

    private BufferedImage image;
    private Rgb arr[][];
    private List<Map.Entry<String,Long>> list;
    private int width;
    private int height;

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

    public Rgb getRgbObject(int x, int y){
        return arr[y][x];
    }

    public int getMaxColours(){
        return list.size();
    }

    public long getNumMatching(Rgb comp){
        // find comp.string in key
        Iterator<Map.Entry<String,Long>> positions = this.list.iterator();
        while(positions.hasNext()){
            Map.Entry<String,Long> val = positions.next();
            if(val.getKey().equals(comp.toString())){
                return val.getValue();
            }
        }
        return 0;
    }

    private Rgb parseStringToRgb(String rgbString){
        String stripped = rgbString.replaceAll("[()]", "");
        String[] parse = stripped.split("[, ]+");
        
        int red = Integer.parseInt(parse[0]);
        int green = Integer.parseInt(parse[1]);
        int blue = Integer.parseInt(parse[2]);
        return new Rgb(red, green, blue);
    }

    public Rgb getTopNRgb(int n){
        if(n > getMaxColours()){
            return parseStringToRgb(list.get(getMaxColours()-1).getKey());
        }
        // validate n
        // List<Rgb[]> list = Arrays.asList(arr);
        // List<Map.Entry<String,Long>> topResult = list.stream()
        //     .flatMap(Arrays::stream)
        //     .collect(
        //         Collectors.groupingBy(
        //             Rgb::toString, Collectors.counting()
        //         )
        //     )
        //     .entrySet().stream()
        //     .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        //     .collect(Collectors.toList());
        return parseStringToRgb(list.get(n-1).getKey());
    }

    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file)); 
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void evaluateImage(){
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

    private void sortArr(){
        List<Rgb[]> list = Arrays.asList(this.arr);
        this.list = list.stream()
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
