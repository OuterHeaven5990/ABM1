package com.example.abm1.ViewModels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.abm1.CourseActivity;
import com.example.abm1.database.AppRepo;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseViewModel extends AndroidViewModel {
    public AppRepo repository;
    public LiveData<List<CourseEntity>> courses;
    public MutableLiveData<CourseEntity> liveCourseEntity = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();


    public CourseViewModel(@NonNull Application application) {
        super(application);


        repository = AppRepo.getInstance(application.getApplicationContext());
        courses = repository.repoCourses;
    }


    public void deleteCourse() {
        repository.deleteCourse(liveCourseEntity.getValue());
    }

    public void getData(final int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                CourseEntity course = repository.getCourseById(courseId);
                liveCourseEntity.postValue(course);
            }
        });
    }


    public void saveCourse(String courseTitle, Date startdate, Date enddate, String status, String mentorName, String mentorPhone, String mentorEmail, int TermId) {
        CourseEntity entity = liveCourseEntity.getValue();
        if (entity == null) {

            if(TextUtils.isEmpty(courseTitle.trim())) {
                return;
            }

            entity = new CourseEntity(courseTitle.trim(), startdate,enddate,status,mentorName,mentorPhone,mentorEmail,TermId);
        }

        else {
            entity.setCourseTitle(courseTitle.trim());
            entity.setStartDate(startdate);
            entity.setEndDate(enddate);
            entity.setStatus(status);
            entity.setMentorName(mentorName);
            entity.setMentorPhoneNumber(mentorPhone);
            entity.setMentorEmailAddress(mentorEmail);
            entity.setTermId(TermId);
        }
        repository.insertCourse(entity);
    }

    public void updateCourse(String courseTitle, Date startdate, Date enddate, String status, String mentorName,String mentorPhone,String mentorEmail) {
        CourseEntity entity = liveCourseEntity.getValue();
        if (entity == null) {

            if(TextUtils.isEmpty(courseTitle.trim())) {
                return;
            }

            entity = new CourseEntity(courseTitle.trim(), startdate,enddate,status,mentorName,mentorPhone,mentorEmail);
        }

        else {
            entity.setCourseTitle(courseTitle.trim());
            entity.setStartDate(startdate);
            entity.setEndDate(enddate);
            entity.setStatus(status);
            entity.setMentorName(mentorName);
            entity.setMentorPhoneNumber(mentorPhone);
            entity.setMentorEmailAddress(mentorEmail);
        }
        repository.insertCourse(entity);
    }


}
