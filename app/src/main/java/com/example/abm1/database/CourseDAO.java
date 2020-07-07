package com.example.abm1.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCourses(List<CourseEntity> courses);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("SELECT * FROM courses where id = :id")
    CourseEntity getCourseById(int id);

    @Query("SELECT*FROM courses")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT COUNT(*) FROM courses")
    int getCourseCount();

    @Query("Delete FROM courses")
    int deleteAllCourses();
}
