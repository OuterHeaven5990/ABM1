package com.example.abm1.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.abm1.models.NoteEntity;

import java.util.List;

@Dao
public interface NoteDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity note);

    @Delete
    void deleteNote(NoteEntity note);

    @Query("SELECT*FROM note")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("SELECT * FROM note where id = :id")
    NoteEntity getNoteById(int id);

}
