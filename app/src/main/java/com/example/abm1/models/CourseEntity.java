package com.example.abm1.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="courses", foreignKeys = @ForeignKey(entity = TermEntity.class,
        parentColumns = "id",
        childColumns = "termId",
        onDelete = CASCADE))

public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String courseTitle;
    private Date  endDate;
    private Date startDate;
    private String status;
    private String mentorName;
    private String mentorPhoneNumber;
    private String mentorEmailAddress;
    private int termId;



    public CourseEntity(int id, String courseTitle, Date endDate, Date startDate, String status, String mentorName, String mentorPhoneNumber, String mentorEmailAddress, int termId) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmailAddress = mentorEmailAddress;
        this.termId = termId;
    }

    @Ignore
    public CourseEntity(String courseTitle, Date endDate, Date startDate, String status, String mentorName, String mentorPhoneNumber, String mentorEmailAddress, int termId) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmailAddress = mentorEmailAddress;
        this.termId = termId;
    }


    @Ignore
   public  CourseEntity(String courseTitle, Date endDate, Date startDate, String status, String mentorName, String mentorPhoneNumber, String mentorEmailAddress) {
        this.courseTitle = courseTitle;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhoneNumber = mentorPhoneNumber;
        this.mentorEmailAddress = mentorEmailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhoneNumber() {
        return mentorPhoneNumber;
    }

    public void setMentorPhoneNumber(String phoneNumber) {
        this.mentorPhoneNumber = phoneNumber;
    }

    public String getMentorEmailAddress() {
        return mentorEmailAddress;
    }

    public void setMentorEmailAddress(String email) {
        this.mentorEmailAddress = email;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
