package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.ViewModels.noteViewModel;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.NoteEntity;

import java.text.ParseException;

public class EditNoteActivity extends AppCompatActivity {

    private noteViewModel NoteViewModel;
    private TextView noteText;
    private Button saveButton;
    private Boolean newNote;

    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_create_note);
        noteText = (TextView) findViewById(R.id.noteText);
        saveButton = (Button) findViewById(R.id.saveButton);
        ///Save Button onClick listener/////////////////////////////////////////////////////////////
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    onSaveButtonClick();
                }
        });

        initViewModel();

    }


    private void initViewModel() {
        NoteViewModel = ViewModelProviders.of(this).get(noteViewModel.class);
        NoteViewModel.liveNoteEntity.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntities) {
                if (noteEntities != null) {


                    ////////////////////////////////////////////////////////////////////////////////////
                    noteText.setText(noteEntities.getNoteText());

                    }
                }
        });

        Bundle extras = getIntent().getExtras();
        int nId = extras.getInt("Note_ID");
        if (nId < 1) {
            setTitle("New Note");
            newNote = true;

        } else {
            newNote = false;
            setTitle("Edit Note");
            int noteId = extras.getInt("Note_ID");
            NoteViewModel.getData(noteId);
        }
    }

    private void onSaveButtonClick(){
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
        int noteId = extras.getInt("Note_ID");

        if(noteText.getText().toString().isEmpty()) {Toast.makeText(this,"Note text cannot be empty",Toast.LENGTH_LONG).show();}

        else {
            Intent intent = new Intent(this, ViewNoteActivity.class);
            intent.putExtra("Note_ID", noteId);
            intent.putExtra("Course_ID", courseId );
            NoteViewModel.saveNote(noteText.getText().toString(), courseId);
            finish();

            }
        }


}
