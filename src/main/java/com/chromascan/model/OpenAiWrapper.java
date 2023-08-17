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

    /**
     * Creates a chatcompletion request to openai and parse
     * the response.
     * @return  Message from openai chat completion request
     */
    public String getChatCompletionRequestContent(){
        // Free use of the openAI API allows for 3 requests per min
        // In order to bypass this issue, the program will sleep 
        //  for 20sec so each min will only have a max of 3 requests.
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        OpenAiService service = new OpenAiService(Constant.getKey(Constant.OPENAI_API_KEY));
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
