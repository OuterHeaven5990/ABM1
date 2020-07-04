package com.example.abm1.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.abm1.database.AppRepo;
import com.example.abm1.models.TermEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermEditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> liveTermEntity = new MutableLiveData<>();
    private AppRepo repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepo.getInstance(getApplication());

    }

    public void getData(final int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = repository.getTermById(termId);
                liveTermEntity.postValue(term);
            }
        });
    }

    public void saveTerm(String termtitle, Date startdate, Date enddate) {
        TermEntity term = liveTermEntity.getValue();
        if (term == null) { }

        else {
            term.setTermTitle(termtitle);
            term.setStartDate(startdate);
            term.setEndDate(enddate);
        }
        repository.insertTerm(term);
    }
}
