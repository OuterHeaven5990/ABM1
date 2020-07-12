package com.example.abm1.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="note", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "id",
        childColumns = "courseId",
        onDelete = CASCADE))

public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String noteText;
    private int courseId;

    public NoteEntity(String noteText, int courseId) {
        this.noteText = noteText;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
