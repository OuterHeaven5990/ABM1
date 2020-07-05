package com.example.abm1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.ViewModels.TermViewModel;
import com.example.abm1.adapters.CourseAdapter;
import com.example.abm1.adapters.TermAdapter;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    //Declare variables needed globally////////////////////////////////////////////////////////////
    ArrayList<CourseEntity> courses = new ArrayList<>();
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

    }

}
