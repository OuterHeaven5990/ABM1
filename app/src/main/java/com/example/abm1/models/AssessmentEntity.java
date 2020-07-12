package com.example.abm1.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="assessment", foreignKeys = @ForeignKey(entity = CourseEntity.class,
        parentColumns = "id",
        childColumns = "courseId",
        onDelete = CASCADE))

public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String assessmentTitle;
    private String assessmentType;
    private Date dueDate;
    private int courseId;

    public AssessmentEntity(String assessmentTitle, String assessmentType, Date dueDate, int courseId) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.dueDate = dueDate;
        this.courseId = courseId;
    }


    @Ignore
    public AssessmentEntity(String assessmentTitle, String assessmentType, Date dueDate) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
