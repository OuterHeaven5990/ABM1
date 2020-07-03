package com.example.abm1.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="terms")

public class TermEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date startDate;
    private Date endDate;
    private String termTitle;

   //Constructors//////////////////////////////////////////////////////////////////////////////


    public TermEntity(int id, Date startDate, Date endDate, String termTitle) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termTitle = termTitle;
    }


    @Ignore
    public TermEntity(Date startDate, Date endDate, String termTitle) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.termTitle = termTitle;
    }

    @Ignore
    public TermEntity() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters and setters//////////////////////////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", Start Date=" + startDate +
                ", Term Title='" + termTitle + '\'' +
                '}';
    }

}
