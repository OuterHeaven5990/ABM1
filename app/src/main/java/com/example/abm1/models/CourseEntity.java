package com.example.abm1.models;

import java.util.Date;

public class CourseEntity {
    private int id;
    private String courseTitle;
    private Date  endDate;
    private Date startDate;
    private String status;
    private int termId;

    public CourseEntity(int id, String courseTitle, Date endDate, Date startDate, String status, int termId) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.termId = termId;
    }

    public CourseEntity(String courseTitle, Date endDate, Date startDate, String status, int termId) {
        this.courseTitle = courseTitle;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.termId = termId;
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

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
