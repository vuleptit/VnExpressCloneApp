package com.example.recycler.entity1;

import java.io.Serializable;
import java.util.Date;

public class Video implements Serializable {
    private String title;
    private String linkImage;
    private String linkVideo;
    private String fileSize;
    private Date date;

    public Video(String title, String linkImage, String linkVideo) {
        this.title = title;
        this.linkImage = linkImage;
        this.linkVideo = linkVideo;
    }

    public Video(String title, String linkImage, String linkVideo, String fileSize) {
        this.title = title;
        this.linkImage = linkImage;
        this.linkVideo = linkVideo;
        this.fileSize = fileSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
