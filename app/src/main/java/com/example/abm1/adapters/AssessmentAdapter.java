package com.example.abm1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.EditAssessmentActivity;
import com.example.abm1.EditCourseActivity;
import com.example.abm1.R;
import com.example.abm1.ViewCourseActivity;
import com.example.abm1.models.AssessmentEntity;
import com.example.abm1.models.CourseEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder>{

    private ArrayList<AssessmentEntity> mAssessments;
    private int position;
    private final Context mContext;

    public AssessmentAdapter(ArrayList<AssessmentEntity> assessment, Context context) {
        mAssessments = assessment;
        this.mContext = context;
    }


    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item, parent, false);
        AssessmentAdapter.ViewHolder viewholder = new AssessmentAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String assessment_text = mAssessments.get(position).getAssessmentTitle();
        final Integer assessment_id = mAssessments.get(position).getId();
        holder.assessmentTextView.setText(assessment_text);



        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditAssessmentActivity.class);
                intent.putExtra("Assessment_ID", assessment_id);
                mContext.startActivity(intent);
            }
        });

        holder.assessmentTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewCourseActivity.class);
                intent.putExtra("Assessment_ID", assessment_id );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assessmentTextView;
        private FloatingActionButton fab;
        public ViewHolder(View view) {
            super(view);
            assessmentTextView = (TextView) view.findViewById(R.id.assessment_text);
            fab = (FloatingActionButton) view.findViewById(R.id.fab);
        }
    }
}
