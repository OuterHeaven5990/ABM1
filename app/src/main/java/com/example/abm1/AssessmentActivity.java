package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.ViewModels.AssessmentViewModel;
import com.example.abm1.adapters.AssessmentAdapter;
import com.example.abm1.models.AssessmentEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity {
    ArrayList<AssessmentEntity> assessments = new ArrayList<>();
    ArrayList<AssessmentEntity> temp = new ArrayList<>();

    private AssessmentAdapter assessmentAdapter;
    private AssessmentViewModel assessmentViewModel;
    private RecyclerView assessmentRV;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Assessment List");
        setContentView(R.layout.activity_assessment);
        assessmentRV = (RecyclerView) findViewById(R.id.assessment_recycler_view);
        ///Set up recycler view/////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager assessmentLM;
        assessmentRV.setHasFixedSize(true);
        assessmentLM = new LinearLayoutManager(this);
        assessmentRV.setLayoutManager(assessmentLM);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {addButtonClick();}
        });
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
////////////////////////////////////////////////////////////////////////////////////////////////////
        initViewModel(courseId);
    }

    private void initViewModel(final int id) {
        final Observer<List<AssessmentEntity>> assessmentObserver = new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessmentEntities) {

                assessments.clear();
                temp.clear();
                assessments.addAll(assessmentEntities);

                for(int i = 0; i < assessments.size(); ++i) {
                    if (assessments.get(i).getCourseId() == id) {temp.add(assessments.get(i));}
                }

                if (assessmentAdapter == null) {
                    assessmentAdapter = new AssessmentAdapter(temp, AssessmentActivity.this);
                    assessmentRV.setAdapter(assessmentAdapter);
                } else {assessmentAdapter.notifyDataSetChanged();}
            }
        };

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.assessments.observe(this,assessmentObserver);
    }

    private void addButtonClick() {
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");

        Intent intent = new Intent(this, EditAssessmentActivity.class);
        intent.putExtra("Course_ID", courseId);
        startActivity(intent);
    }
}
