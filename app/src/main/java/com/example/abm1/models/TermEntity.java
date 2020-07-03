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

    @Ignore
    public TermEntity() {
    }

    public TermEntity(int id, Date sdate, Date eDate, String termtitle) {
        this.id = id;
        this.startDate = sdate;
        this.endDate = eDate;
        this.termTitle = termtitle;
    }

    @Ignore
    public TermEntity(Date sdate, Date edate, String termtitle) {
        this.startDate = sdate;
        this.endDate = edate;
        this.termTitle = termtitle;
    }

   //Getters and Setters///////////////////////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public Date getEndDate(Date date) {return  endDate;}

    public void setEndDate(Date date) {this.endDate = date;};

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String text) {
        this.termTitle = text;
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
