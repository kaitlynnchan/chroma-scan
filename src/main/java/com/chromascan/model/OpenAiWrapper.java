package com.chromascan.model;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

/**
 * OPENAI WRAPPER CLASS
 * Uses the openai API to send and receive
 * a message request.
 */
public class OpenAiWrapper {

    private ArrayList<ChatMessage> messages;
    private double temperature;
    private int maxTokens;
    
    public OpenAiWrapper(double temperature, int maxTokens){
        this.messages = new ArrayList<ChatMessage>();
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void addMessage(String role, String content){
        messages.add(new ChatMessage(role, content));
    }

    public String getChatCompletionRequestContent(){
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        OpenAiService service = new OpenAiService("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");
        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .messages(this.messages)
            .temperature(this.temperature)
            .maxTokens(this.maxTokens)
            .build();
        
        ChatCompletionResult response = service.createChatCompletion(request);
        ChatMessage message = response.getChoices().get(0).getMessage();
        return message.getContent();
    }
}
