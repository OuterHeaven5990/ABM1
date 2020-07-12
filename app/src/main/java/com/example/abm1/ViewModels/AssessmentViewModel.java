package com.example.abm1.ViewModels;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.abm1.database.AppRepo;
import com.example.abm1.models.AssessmentEntity;
import com.example.abm1.models.CourseEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AssessmentViewModel extends AndroidViewModel {

    public AppRepo repository;
    public LiveData<List<AssessmentEntity>> assessments;
    public MutableLiveData<AssessmentEntity> liveAssessmentEntity = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentViewModel(@NonNull Application application) {
        super(application);


        repository = AppRepo.getInstance(application.getApplicationContext());
        assessments = repository.repoAssessments;
    }

    public void deleteAssessment() {
        repository.deleteAssessment(liveAssessmentEntity.getValue());
    }


    public void getData(final int assessmentId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AssessmentEntity assessment = repository.getAssessmentById(assessmentId);
                liveAssessmentEntity.postValue(assessment);
            }
        });
    }

    public void saveAssessment(String assessmentTitle, String assessmentType, Date dueDate, int courseId) {
        AssessmentEntity entity = liveAssessmentEntity.getValue();
        if (entity == null) {

            if(TextUtils.isEmpty(assessmentTitle.trim())) {
                return;
            }

            entity = new AssessmentEntity(assessmentTitle.trim(), assessmentType,dueDate,courseId);
        }

        else {
            entity.setAssessmentTitle(assessmentTitle.trim());
            entity.setAssessmentType(assessmentType);
            entity.setDueDate(dueDate);
            entity.setCourseId(courseId);
        }
        repository.insertAssessment(entity);
    }


    public void saveAssessment(String assessmentTitle, String assessmentType, Date dueDate) {
        AssessmentEntity entity = liveAssessmentEntity.getValue();
        if (entity == null) {

            if(TextUtils.isEmpty(assessmentTitle.trim())) {
                return;
            }

            entity = new AssessmentEntity(assessmentTitle.trim(), assessmentType,dueDate);
        }

        else {
            entity.setAssessmentTitle(assessmentTitle.trim());
            entity.setAssessmentType(assessmentType);
            entity.setDueDate(dueDate);
        }
        repository.insertAssessment(entity);
    }
}
