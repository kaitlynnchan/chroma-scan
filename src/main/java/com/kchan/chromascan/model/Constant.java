package com.kchan.chromascan.model;

public class Constant {
    
    public static final String OPENAI_API_KEY = "OPENAI_API_KEY";

    private Constant(){
        throw new IllegalAccessError("Cannot access constructor");
    }

    public final String getKey(String KEY_NAME){
        return System.getenv().get(KEY_NAME);
    }
}
