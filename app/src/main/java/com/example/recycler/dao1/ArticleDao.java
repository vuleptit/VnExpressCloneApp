package com.example.recycler.dao1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.recycler.entity1.Article;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface ArticleDao {
    @Insert
    void insert(Article article);
    @Insert
    void insertList(Article... articles);
    @Delete
    void delete(Article article);
    @Query("SELECT * FROM article")
    List<Article> getAllArticle();
    @Query("SELECT * FROM article WHERE state = :state")
    List<Article> getAllArticleState(int state);
    @Query("SELECT * FROM article ORDER BY id DESC LIMIT 1")
    Article getLastArticle();

}
