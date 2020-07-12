package com.example.abm1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.EditCourseActivity;
import com.example.abm1.R;
import com.example.abm1.ViewCourseActivity;
import com.example.abm1.models.CourseEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    private ArrayList<CourseEntity> mCourses;
    private int position;
    private final Context mContext;



    public CourseAdapter(ArrayList<CourseEntity> course, Context context) {
        mCourses = course;
        this.mContext = context;
    }


    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_item, parent, false);
        CourseAdapter.ViewHolder viewholder = new CourseAdapter.ViewHolder(view);
        return viewholder;
    }


    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, final int position) {
        final String course_text = mCourses.get(position).getCourseTitle();
        final Integer course_id = mCourses.get(position).getId();
        holder.courseTextView.setText(course_text);



        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditCourseActivity.class);
                intent.putExtra("Course_ID", course_id);
                mContext.startActivity(intent);
            }
        });

        holder.courseTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewCourseActivity.class);
                intent.putExtra("Course_ID", course_id );
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseTextView;
        private FloatingActionButton fab;
        public ViewHolder(View view) {
            super(view);
            courseTextView = (TextView) view.findViewById(R.id.course_text);
            fab = (FloatingActionButton) view.findViewById(R.id.fab);
        }
    }


}
