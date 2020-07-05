package com.example.abm1.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.abm1.database.AppRepo;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    public AppRepo repository;
    public LiveData<List<CourseEntity>> courses;

    public CourseViewModel(@NonNull Application application) {
        super(application);


        repository = AppRepo.getInstance(application.getApplicationContext());
        courses = repository.repoCourses;
    }
}
