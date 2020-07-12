package com.example.abm1.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepo {

    private static  AppRepo instance;
    public LiveData<List<TermEntity>> repoTerms;
    public LiveData<List<CourseEntity>> repoCourses;
    private AppDatabase myDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepo getInstance(Context context) {

        if(instance == null) {instance = new AppRepo(context);}

        return  instance;
    }

    private AppRepo(Context context) {
        myDb = AppDatabase.getInstance(context);
        repoTerms = getAllTerms();
        repoCourses = getAllCourses();
    }

    //Term Functions////////////////////////////////////////////////////////////////////////////////
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

    public void insertTerm(final TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myDb.termDAO().insertTerm(term);
            }
        });
    }

    public void deleteTerm(final TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myDb.termDAO().deleteTerm(term);
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Course Functions///////////////////////////////////////////////////////////////////////////////

    public void deleteAllCourses() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myDb.courseDAO().deleteAllCourses();
            }
        });
    }

    public CourseEntity getCourseById(int courseId) {
        return myDb.courseDAO().getCourseById(courseId);
    }

    private LiveData<List<CourseEntity>> getAllCourses() {
        return myDb.courseDAO().getAllCourses();
    }

    public void insertCourse(final CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myDb.courseDAO().insertCourse(course);
            }
        });
    }

    public void deleteCourse(final CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                myDb.courseDAO().deleteCourse(course);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
}
