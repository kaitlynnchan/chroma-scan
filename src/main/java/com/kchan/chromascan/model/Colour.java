package main.java.com.kchan.chromascan.model;

/**
 * Models information about a Colour object
 * Parameters: Rgb object, hex, name
 */
public class Colour {

    private Rgb rgb;
    private String hex;
    private String name;

    public Colour(Rgb rgb, String hex){
        this.rgb = rgb;
        this.hex = hex;
        this.name = "";
    }

    public Colour(Rgb rgb, String hex, String name){
        this.rgb = rgb;
        this.hex = hex;
        this.name = name;
    }

    public Rgb getRgb(){
        return this.rgb;
    }

    public String getHex(){
        return this.hex;
    }

    public String getName(){
        return this.name;
    }
}