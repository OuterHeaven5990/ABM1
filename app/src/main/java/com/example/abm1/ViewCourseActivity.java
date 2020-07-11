package com.example.abm1;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.database.AppDatabase;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ViewCourseActivity extends AppCompatActivity {
    //Variables needed for activity///////////////////////////////////////////////////////////////

    private CourseViewModel courseViewModel;
    private TextView courseTitleText,courseStartDateText,courseEndDateText,courseStatusText;
    private AppDatabase myDb = AppDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        courseTitleText = (TextView) findViewById(R.id.courseViewTitleText);
        courseStartDateText = (TextView) findViewById(R.id.courseStartDateText);
        courseEndDateText = (TextView) findViewById(R.id.courseEndDateText);
        courseStatusText = (TextView) findViewById(R.id.courseStatus);
        initViewModel();
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(CourseEntity courseEntity)  {
                if(courseEntity != null) {
                    //Date Conversion/////////////////////////////////////////////////////////////////
                    String sDate = courseEntity.getStartDate().toString();
                    String eDate = courseEntity.getEndDate().toString();
                    LocalDate tempStartDate = ZonedDateTime.parse(sDate, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")).toLocalDate();
                    LocalDate tempEndDate = ZonedDateTime.parse(eDate, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")).toLocalDate();
                    int sday = tempStartDate.getDayOfMonth();
                    int smonth = tempStartDate.getMonthValue();
                    int syear = tempStartDate.getYear();
                    String startdate = smonth + "/" + sday + "/" + syear;
                    int eday = tempEndDate.getDayOfMonth();
                    int emonth = tempEndDate.getMonthValue();
                    int eyear = tempEndDate.getYear();
                    String enddate = emonth + "/" + eday + "/" + eyear;
                    ///////////////////////////////////////////////////////////////////////////////////
                    courseTitleText.setText(courseEntity.getCourseTitle());
                    courseStartDateText.setText(startdate);
                    courseEndDateText.setText(enddate);
                    courseStatusText.setText(courseEntity.getStatus());
                    setTitle("Course View");

                }
            }
        });


        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt("Course_ID");
        courseViewModel.getData(courseId);
    }

    //Load Menu into activity//////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
                courseViewModel.deleteCourse();
                finish();
        }
        return  true;
    }


    public void startAssessmentActivity() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt("Term_ID");
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra("Term_ID", termId );
        startActivity(intent);
    }
}
