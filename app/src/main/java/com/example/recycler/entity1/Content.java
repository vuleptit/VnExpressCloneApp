package com.example.recycler.entity1;

import java.util.Date;

public class Content {
    private int state;
    private String text;
    private String linkImage;
    private String textImage;
    private Date date;
    private String linkDetail;
    public Content(int state, String text) {
        this.state = state;
        this.text = text;
    }

    public Content(int state, String linkImage, String textImage) {
        this.state = state;
        this.linkImage = linkImage;
        this.textImage = textImage;
    }

    public Content(int state, String linkImage, String textImage, Date date,String linkDetail) {
        this.state = state;
        this.linkImage = linkImage;
        this.textImage = textImage;
        this.date = date;
        this.linkDetail = linkDetail;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLinkDetail() {
        return linkDetail;
    }

    public void setLinkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
    }
}
