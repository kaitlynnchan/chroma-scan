package com.kchan.chromascan.model;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class ColourEvaluator {

    private static final String OPENAI_API_KEY = "";

    private BufferedImage image;
    private ArrayList<PixelPosition> pp;
    private ArrayList<ColourBreakdown> cb;
    private int numPos;

    public ColourEvaluator(String file, int x, int y){
        this.image = createImage(file);
        this.pp = new ArrayList<PixelPosition>(){{
            add(new PixelPosition(x, y));
        }};
        this.cb = new ArrayList<ColourBreakdown>();
        populateColourBreakdown();
    }

    public ColourEvaluator(String file, ArrayList<PixelPosition> pp){
        this.image = createImage(file);
        this.pp = pp;
        this.cb = new ArrayList<ColourBreakdown>();
        populateColourBreakdown();
    }

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage createImage(String file){
        try {
            return ImageIO.read(getClass().getResource(file)); 
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ArrayList<PixelPosition> getPixelPosition() {
        return pp;
    }

    public ArrayList<ColourBreakdown> getColourBreakdown() {
        return cb;
    }

    private void populateColourBreakdown(){
        Iterator<PixelPosition> positions = pp.iterator();
        while(positions.hasNext()){
            PixelPosition p = positions.next();
            findColour(p.getX(), p.getY());
        }
    }

    private void findColour(int x, int y) {
        // Get data for creating colour object
        Rgb rgbObj = getRgbFromPosition(this.image, x, y);
        String hex = getHexFromRgb(rgbObj);
        // create colour object
        // Colour colour = new Colour(rgbObj, hex);
        String name = createName(hex);

        cb.add(new ColourBreakdown(rgbObj, hex, name, 100));

        System.out.println("RGB: " + cb.get(cb.size()-1).getRgb());
        System.out.println("Hex: " + cb.get(cb.size()-1).getHex());
        System.out.println("Name: " + cb.get(cb.size()-1).getName());
    }

    private Rgb getRgbFromPosition(BufferedImage image, int x, int y){
        int pixel = image.getRGB(x, y);
        Color color = new Color(pixel, true);
        return new Rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    private String getHexFromRgb(Rgb rgb){
        return String.format("#%02X%02X%02X", rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    private String createName(String hex){
        OpenAiService service = new OpenAiService(OPENAI_API_KEY);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(
                List.of(
                    new ChatMessage("system", "You are a creative assistant."),
                    new ChatMessage("user", "Come up with a fun name that describes the color " + hex + ".")
                )
            )
            .temperature(1.0)
            .maxTokens(20)
            .build();
        
        ChatCompletionResult response = service.createChatCompletion(request);
        ChatMessage message = response.getChoices().get(0).getMessage();
        return message.getContent();
    }

}
