package com.kchan.chromascan.model;

/**
 * Models information about a Colour object
 * Parameters: Rgb object, hex, name
 */
public class Colour {

    private Rgb rgb;
    private String hex;
    private String name;

    public Colour(Rgb rgb){
        this.rgb = rgb;
        setHex();
        setName();
    }

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

    private void setHex(){
        this.hex = String.format(
            "#%02X%02X%02X",
            this.rgb.getRed(),
            this.rgb.getGreen(),
            this.rgb.getBlue());
    }

    public String getName(){
        return this.name;
    }

    private void setName(){
        OpenAiWrapper wrapper = new OpenAiWrapper(1.0, 20);
        wrapper.addMessage("system", "You are a creative assistant.");
        wrapper.addMessage("user", "Come up with a fun name that describes the color " + this.hex + ".");
        this.name = wrapper.getChatCompletionRequestContent();
    }

}