package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.noteViewModel;
import com.example.abm1.models.NoteEntity;

public class ViewNoteActivity extends AppCompatActivity {

    private noteViewModel NoteViewModel;
    private TextView noteText;
    private Button shareNote;
    private String notetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        noteText = (TextView) findViewById(R.id.noteTextView);
        shareNote = (Button) findViewById(R.id.shareNoteButton);
        initViewModel();

        shareNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Course Note");
                intent.putExtra(Intent.EXTRA_TEXT, notetext);
                startActivity(Intent.createChooser(intent, "Share Note"));
            }
        });

    }

    private void initViewModel()  {
        NoteViewModel = ViewModelProviders.of(this).get(noteViewModel.class);
        NoteViewModel.liveNoteEntity.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity)  {
                if(noteEntity != null) {

                    ///////////////////////////////////////////////////////////////////////////////////
                    noteText.setText(noteEntity.getNoteText());
                    notetext = noteEntity.getNoteText();
                    setTitle("Note View");
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        int noteId = extras.getInt("Note_ID");
        NoteViewModel.getData(noteId);
    }

    //Load Menu into activity//////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        MenuItem item_Delete = menu.findItem(R.id.action_delete);
        MenuItem item_start_course = menu.findItem(R.id.action_start_course);
        MenuItem item_mark_complete = menu.findItem(R.id.action_mark_completed);
        MenuItem item_drop_course = menu.findItem(R.id.action_drop_course);
        MenuItem item_add_note = menu.findItem(R.id.action_add_note);
        MenuItem item_start_alert = menu.findItem(R.id.action_enable_start_notification);
        MenuItem item_end_alert = menu.findItem(R.id.action_enable_end_notification);
        item_Delete.setTitle("Delete Note");
        item_drop_course.setVisible(false);
        item_mark_complete.setVisible(false);
        item_start_course.setVisible(false);
        item_add_note.setVisible(false);
        item_start_alert.setVisible(false);
        item_end_alert.setVisible(false);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            NoteViewModel.deleteNote();
            finish();
        }
        else if(item.getItemId() == R.id.action_edit_note) {startNoteActivity();}

        return  true;
    }

    public void startNoteActivity() {
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
        int noteId = extras.getInt("Note_ID");
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("Course_ID", courseId );
        intent.putExtra("Note_ID", noteId);
        finish();
        startActivity(intent);
    }
}
