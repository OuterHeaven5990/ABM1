package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.adapters.CourseAdapter;
import com.example.abm1.models.CourseEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    //Declare variables needed globally////////////////////////////////////////////////////////////
    ArrayList<CourseEntity> courses = new ArrayList<>();
    ArrayList<CourseEntity> temp = new ArrayList<>();
    CourseAdapter courseAdapter;
    private CourseViewModel courseViewModel;
    private RecyclerView courseRV;
    private FloatingActionButton fab;
///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setTitle("Course List");
        courseRV = (RecyclerView) findViewById(R.id.course_recycler_view);
        ///Set up recycler view/////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager termLM;
        courseRV.setHasFixedSize(true);
        termLM = new LinearLayoutManager(this);
        courseRV.setLayoutManager(termLM);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {addButtonClick();}
        });
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt("Term_ID");

        ///////////////////////////////////////////////////////////////////////////////////////////
        initViewModel(termId);

    }

    private void initViewModel(final int id) {
         final Observer<List<CourseEntity>> courseObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {

                courses.clear();
                temp.clear();
                courses.addAll(courseEntities);

                for(int i = 0; i < courses.size(); ++i) {
                    if (courses.get(i).getTermId() == id) {temp.add(courses.get(i));}
                }

                if (courseAdapter == null) {
                    courseAdapter = new CourseAdapter(temp, CourseActivity.this);
                    courseRV.setAdapter(courseAdapter);
                } else {courseAdapter.notifyDataSetChanged();}
            }
        };

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.courses.observe(this,courseObserver);
    }



    private void addButtonClick() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt("Term_ID");

        Intent intent = new Intent(this, EditCourseActivity.class);
        intent.putExtra("Term_ID", termId);
        startActivity(intent);
    }
}
