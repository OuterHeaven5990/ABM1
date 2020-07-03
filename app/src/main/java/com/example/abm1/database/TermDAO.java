package com.example.abm1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.abm1.models.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTerms(List<TermEntity> terms);

    @Delete
    void deleteTerm(TermEntity term);

    @Query("SELECT * FROM terms where id = :id")
    TermEntity getTermById(int id);

    @Query("SELECT*FROM terms")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();
}
