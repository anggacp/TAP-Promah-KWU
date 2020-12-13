package com.project.ocr;

import com.google.gson.JsonObject;

public class Post {

    private String text;
    private String to;
    /*private Translate translate;*/

    JsonObject translated_text;
    private String translated_characters;
    private String id;
    private String original_text;

/*    public JSONObject getTranslated_text() {
        return translated_text;
}*/

    public JsonObject getTranslated_text(){
        return translated_text;
    }

    public void setTranslated_text(JsonObject translated_text) {
        this.translated_text = translated_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post(String text, String to) {
        this.text = text;
        this.to = to;
    }

    public String getTranslated_characters() {
        return translated_characters;
    }

    public String getOriginal_text() {
        return original_text;
    }
/*
    public String getId() {
        return id;
    }*/
/*    public String getTranslated_text() {
        return translated_text;

    }*/

    public String getText() {
        return text;
    }

    public String getTo() {
        return to;
    }
}
