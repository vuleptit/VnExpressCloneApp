package com.example.recycler.dao1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.recycler. entity1.Description;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DescriptionDao {
    @Insert
    void insert(Description description);
    @Insert
    void insertList(Description... description);
    @Delete
    void delete(Description description);
    @Query("Select * FROM description WHERE id_article == :ariticleId")
    List<Description> getAllDescription(int ariticleId);
    @Query("Select * FROM description WHERE id_article == :ariticleId AND state = 2")
    List<Description> getListImageDescription(int ariticleId);
    @Query("DELETE FROM description WHERE id_article == :ariticleId")
    void deleteAll(int ariticleId);
}
