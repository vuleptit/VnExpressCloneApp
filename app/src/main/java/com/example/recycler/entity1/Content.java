package com.example.recycler.entity1;

public class Content {
    private int state;
    private String text;
    private String linkImage;
    private String textImage;

    public Content(int state, String text) {
        this.state = state;
        this.text = text;
    }

    public Content(int state, String linkImage, String textImage) {
        this.state = state;
        this.linkImage = linkImage;
        this.textImage = textImage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getTextImage() {
        return textImage;
    }

    public void setTextImage(String textImage) {
        this.textImage = textImage;
    }
}
