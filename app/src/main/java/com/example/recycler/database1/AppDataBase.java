package com.example.recycler.database1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.recycler.dao1.ArticleDao;
import com.example.recycler.dao1.DescriptionDao;
import com.example.recycler.entity1.Article;
import com.example.recycler.entity1.Description;


/**
 * Created by husaynhakeem on 6/12/17.
 */

@Database(entities = {Article.class, Description.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public abstract DescriptionDao descriptionDao();
    public abstract ArticleDao articleDao();


    public static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class,
                    "database_ma,")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
