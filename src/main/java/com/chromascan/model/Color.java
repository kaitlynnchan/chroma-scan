package com.chromascan.model;

/**
 * COLOR CLASS
 * Illustrate information about a Color object.
 * Creates the hex and comes up with a name from 
 * the rgb value.
 */
public class Color {

    private Rgb rgb;
    private String hex;
    private String name;

    public Color(Rgb rgb){
        this.rgb = rgb;
        setHex();
        setName();
    }

    public Color(Rgb rgb, String hex){
        this.rgb = rgb;
        this.hex = hex;
        this.name = "";
    }

    public Color(Rgb rgb, String hex, String name){
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

    /**
     * Parse the values of rgb to get the hex
     */
    private void setHex(){
        this.hex = String.format(
            "#%02X%02X%02X",
            this.rgb.getRed(),
            this.rgb.getGreen(),
            this.rgb.getBlue()
        );
    }

    /**
     * Creates openai wrapper object to come up 
     * with a name for the color
     */
    private void setName(){
        // TEMP: remove openai due to service limitations
        // OpenAiWrapper wrapper = new OpenAiWrapper(1.0, 20);
        // wrapper.addMessage("system", "You are a creative assistant.");
        // wrapper.addMessage("user", "Come up with a fun name that describes the color " + this.hex + ".");
        // this.name = wrapper.getChatCompletionRequestContent();
        this.name = "";
    }

    @Override
    public String toString() {
        return "Color [rgb=" + rgb + ", hex=" + hex + ", name=" + name + "]";
    }
    
}