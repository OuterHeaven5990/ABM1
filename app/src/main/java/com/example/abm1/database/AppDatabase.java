package com.example.abm1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.abm1.models.AssessmentEntity;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.NoteEntity;
import com.example.abm1.models.TermEntity;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class, NoteEntity.class}, version = 1)

@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract  TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract  NoteDAO noteDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {

            synchronized (LOCK) {
                if (instance == null) {

                    instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

                }
            }
        }


        return instance;
    }



}
