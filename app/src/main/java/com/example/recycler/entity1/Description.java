package com.example.recycler.entity1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Article.class,
        parentColumns = "id",
        childColumns = "id_article"),
        indices = {@Index("id_article")})
public class Description {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private int state;
    @ColumnInfo(name = "id_article")
    private int idArticle;

    public Description(String content, int state, int idArticle) {
        this.content = content;
        this.state = state;
        this.idArticle = idArticle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
}
