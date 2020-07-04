package com.example.abm1.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.abm1.database.AppRepo;
import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {


    public AppRepo repository;
    public LiveData<List<TermEntity>> terms;

    public void generateSampleData(){
        repository.generateSampleData();
    }

    public void deleteAllTerms() {repository.deleteAllTerms();}

    public TermViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepo.getInstance(application.getApplicationContext());
        terms = repository.repoTerms;

    }

}
