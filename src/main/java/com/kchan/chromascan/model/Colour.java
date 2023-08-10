package com.kchan.chromascan.model;

import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

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
        OpenAiService service = new OpenAiService(Constant.OPENAI_API_KEY);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(
                List.of(
                    new ChatMessage("system", "You are a creative assistant."),
                    new ChatMessage("user", "Come up with a fun name that describes the color " + this.hex + ".")
                )
            )
            .temperature(1.0)
            .maxTokens(20)
            .build();
        
        ChatCompletionResult response = service.createChatCompletion(request);
        ChatMessage message = response.getChoices().get(0).getMessage();
        this.name = message.getContent();
    }

}