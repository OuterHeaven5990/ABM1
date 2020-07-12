package com.example.abm1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.abm1.models.AssessmentEntity;
import com.example.abm1.models.CourseEntity;

import java.util.List;


@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessment);

    @Delete
    void deleteAssessment(AssessmentEntity assessment);

    @Query("SELECT*FROM assessment")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("SELECT * FROM assessment where id = :id")
    AssessmentEntity getAssessmentById(int id);
}
