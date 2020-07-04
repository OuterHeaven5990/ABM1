package com.example.abm1.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepo {

    private static  AppRepo instance;
    public LiveData<List<TermEntity>> repoTerms;
    private AppDatabase myDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepo getInstance(Context context) {

        if(instance == null) {instance = new AppRepo(context);}

        return  instance;
    }

    private AppRepo(Context context) {
        myDb = AppDatabase.getInstance(context);
        repoTerms = getAllTerms();
    }

    public void generateSampleData() {
        executor.execute(new Runnable(){
            @Override
            public void run(){myDb.termDAO().insertAllTerms(SampleData.getTerms());}
        });
    }

    public void deleteAllTerms() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myDb.termDAO().deleteAllTerms();
            }
        });
    }

    public TermEntity getTermById(int termId) {
        return myDb.termDAO().getTermById(termId);
    }

    private LiveData<List<TermEntity>> getAllTerms() {
        return myDb.termDAO().getAllTerms();
    }
}
