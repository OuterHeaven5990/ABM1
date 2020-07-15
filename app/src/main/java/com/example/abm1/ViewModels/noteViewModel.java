package com.example.abm1.ViewModels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.abm1.database.AppRepo;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.NoteEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class noteViewModel extends AndroidViewModel {

    public AppRepo repository;
    public LiveData<List<NoteEntity>> notes;
    public MutableLiveData<NoteEntity> liveNoteEntity = new MutableLiveData<>();
    private Executor executor = Executors.newSingleThreadExecutor();


    public noteViewModel(@NonNull Application application) {
        super(application);


        repository = AppRepo.getInstance(application.getApplicationContext());
        notes = repository.repoNotes;
    }


    public void deleteCourse() {
        repository.deleteNote(liveNoteEntity.getValue());
    }


    public void getData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = repository.getNoteById(noteId);
                liveNoteEntity.postValue(note);
            }
        });
    }


    public void saveNote(String noteText, int courseId) {
        NoteEntity entity = liveNoteEntity.getValue();
        if (entity == null) {

            if(TextUtils.isEmpty(noteText.trim())) {
                return;
            }

            entity = new NoteEntity(noteText.trim(), courseId);
        }

        else {
            entity.setNoteText(noteText.trim());

        }
        repository.insertNote(entity);
    }



}
