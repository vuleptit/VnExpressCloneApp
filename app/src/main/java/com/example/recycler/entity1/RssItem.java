package com.example.recycler.entity1;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssItem {
    private String title;
    private String linkDetail;
    private String linkImage;
    private String description;
    private Date date;//Sat, 9 Mar 2019 10:08:56 +0000 E, d MMM yyyy HH:mm:ss Z

    public RssItem(String title, String linkDetail, String linkImage, String description, Date date) {
        this.title = title;
        this.linkDetail = linkDetail;
        this.linkImage = linkImage;
        this.description = description;
        this.date = date;

    }

    public RssItem() {
        this.title = "";
        this.linkDetail = "";
        this.linkImage = "";
        this.description = "";
        this.date = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkDetail() {
        return linkDetail;
    }

    public void setLinkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
