package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.ViewModels.noteViewModel;
import com.example.abm1.adapters.CourseAdapter;
import com.example.abm1.adapters.NoteAdapter;
import com.example.abm1.database.AppDatabase;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.NoteEntity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewCourseActivity extends AppCompatActivity {
    //Variables needed for activity///////////////////////////////////////////////////////////////

    private CourseViewModel courseViewModel;
    private TextView courseTitleText,courseStartDateText,courseEndDateText,courseStatusText;
    private noteViewModel NoteViewModel;
    private NoteAdapter noteAdapter;
    ArrayList<NoteEntity> notes = new ArrayList<>();
    private AppDatabase myDb = AppDatabase.getInstance(this);
    DateFormat format_short = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private  CourseEntity menuItem;
    private RecyclerView noteRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        courseTitleText = (TextView) findViewById(R.id.courseViewTitleText);
        courseStartDateText = (TextView) findViewById(R.id.courseStartDateText);
        courseEndDateText = (TextView) findViewById(R.id.courseEndDateText);
        courseStatusText = (TextView) findViewById(R.id.courseStatus);
        noteRV = (RecyclerView) findViewById(R.id.noteRecyclerView);
        RecyclerView.LayoutManager noteLM;
        noteRV.setHasFixedSize(true);
        noteLM = new LinearLayoutManager(this);
        noteRV.setLayoutManager(noteLM);
        initViewModel();
        initNoteModel();
        MenuItem item = (MenuItem) findViewById(R.id.action_delete);

        ImageView img = findViewById(R.id.assessmentLogo);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {startAssessmentActivity();}
        });
    }


    private void initViewModel()  {
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.liveCourseEntity.observe(this, new Observer<CourseEntity>() {
            @Override
            public void onChanged(CourseEntity courseEntity)  {
                if(courseEntity != null) {

                    ///////////////////////////////////////////////////////////////////////////////////
                    courseTitleText.setText(courseEntity.getCourseTitle());
                    courseStartDateText.setText(format_short.format(courseEntity.getStartDate()));
                    courseEndDateText.setText(format_short.format(courseEntity.getEndDate()));
                    courseStatusText.setText(courseEntity.getStatus());
                    setTitle("Course View");

                    menuItem = courseEntity;
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
        courseViewModel.getData(courseId);

    }
    private  void initNoteModel() {
        ///////////////////////////////////////////////////////////////////////////////////////////
        //Live Data for Notes Recycler View///////////////////////////////////////////////////////
        final Observer<List<NoteEntity>> noteObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {

                notes.clear();
                notes.addAll(noteEntities);
                if (noteAdapter == null) {
                    noteAdapter = new NoteAdapter(notes, ViewCourseActivity.this);
                    noteRV.setAdapter(noteAdapter);
                } else {noteAdapter.notifyDataSetChanged();}
            }
        };

        NoteViewModel = ViewModelProviders.of(ViewCourseActivity.this).get(noteViewModel.class);
        NoteViewModel.notes.observe(ViewCourseActivity.this,noteObserver);


        ///////////////////////////////////////////////////////////////////////////////////////////////
    }

    //Load Menu into activity//////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        MenuItem item_Delete = menu.findItem(R.id.action_delete);
        MenuItem item_start_course = menu.findItem(R.id.action_start_course);
        MenuItem item_mark_complete = menu.findItem(R.id.action_mark_completed);
        MenuItem item_drop_course = menu.findItem(R.id.action_drop_course);
        item_Delete.setTitle("Delete Course");
        item_drop_course.setVisible(false);
        item_mark_complete.setVisible(false);
        item_start_course.setVisible(false);


       if(menuItem.getStatus().equals("Plan to Take")) {item_start_course.setVisible(true); item_drop_course.setVisible(true);}
       if(menuItem.getStatus().equals("In Progress") || menuItem.getStatus().equals("Plan to Take")) {item_drop_course.setVisible(true); item_mark_complete.setVisible(true);}


        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete: courseViewModel.deleteCourse(); finish(); return true;
            case R.id.action_start_course: courseViewModel.updateCourse("In Progress"); this.recreate(); return true;
            case R.id.action_drop_course: courseViewModel.updateCourse("Dropped"); this.recreate(); return true;
            case R.id.action_mark_completed: courseViewModel.updateCourse("Completed"); this.recreate(); return true;
            case R.id.action_add_note: startNoteActivity(); return true;

            default:super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void startAssessmentActivity() {
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
        Intent intent = new Intent(this, AssessmentActivity.class);
        intent.putExtra("Course_ID", courseId );
        startActivity(intent);
    }

    public void startNoteActivity() {
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("Course_ID", courseId );
        startActivity(intent);
    }
}
