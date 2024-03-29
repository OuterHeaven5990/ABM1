package com.example.abm1.ViewModels;

import android.app.Application;
import android.text.TextUtils;

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

    public void deleteTerm() {
        repository.deleteTerm(liveTermEntity.getValue());
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
        if (term == null) {

            if(TextUtils.isEmpty(termtitle.trim())) {
                return;
            }

            term = new TermEntity(startdate,enddate,termtitle.trim());
        }

        else {
            term.setTermTitle(termtitle.trim());
            term.setStartDate(startdate);
            term.setEndDate(enddate);
        }
        repository.insertTerm(term);
    }

    public TermEditorViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepo.getInstance(getApplication());

    }

}
