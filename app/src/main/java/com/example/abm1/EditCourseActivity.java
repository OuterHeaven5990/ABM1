package com.example.abm1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditCourseActivity extends AppCompatActivity {

    ///Private variables needed to activity////////////////////////////////////////////////////////
    private CourseViewModel viewModel;
    private TextView courseText;
    private Button startDateButton,endDateButton,saveButton;
    private String startDate, endDate;
    private boolean newCourse;
    private Spinner status;
    GregorianCalendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_create_course);

        courseText = (TextView) findViewById(R.id.courseTitleText);
        startDateButton = (Button) findViewById(R.id.startDateButton);
        endDateButton = (Button) findViewById(R.id.endDateButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        ///Save Button onClick listener/////////////////////////////////////////////////////////////
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    onSaveButtonClick();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////

        ///StartDate Button listner////////////////////////////////////////////////////////////////
        startDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                DatePickerDialog dpd = new DatePickerDialog(EditCourseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                month  = month + 1;
                                startDate = month + "/" + dayOfMonth + "/" + year;
                                startDateButton.setText(startDate);
                            }
                        },year,month,dayOfMonth);

                dpd.show();
            }
        });

        ///End Date Button Listner/////////////////////////////////////////////////////////////////
        endDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                DatePickerDialog dpd = new DatePickerDialog(EditCourseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                month = month + 1;
                                endDate = month + "/" + dayOfMonth + "/" + year;
                                endDateButton.setText(endDate);
                            }
                        },year,month,dayOfMonth);
                dpd.show();
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////
        // implementation of spinner to help choose course status
         status = (Spinner) findViewById(R.id.courseStatusSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_status, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        status.setAdapter(adapter);

///////////////////////////////////////////////////////////////////////////////////////////////////////

        initViewModel();

    }


    private void initViewModel()  {
        viewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        viewModel.liveCourseEntity.observe(this, new Observer<CourseEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(CourseEntity courseEntity)  {
                if(courseEntity != null) {

                    //Date Converter////////////////////////////////////////////////////////////////
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
                    ////////////////////////////////////////////////////////////////////////////////////
                    courseText.setText(courseEntity.getCourseTitle());
                    startDateButton.setText(startdate);
                    endDateButton.setText(enddate);
                    startDate = startdate;
                    endDate = enddate;

                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New Course");
            newCourse = true;
        }

        else {
            setTitle("Edit Course");
            int courseId = extras.getInt("C_ID");
            viewModel.getData(courseId);
        }
    }


    private void onSaveButtonClick() throws ParseException {
        Bundle extras = getIntent().getExtras();
        int TermId = extras.getInt("Term_ID");
        Date enddate;
        Date startdate;
        String courseStatus = status.getSelectedItem().toString();
        startdate = format.parse(startDate);
        enddate = format.parse(endDate);
        viewModel.saveCourse(courseText.getText().toString(),startdate,enddate,courseStatus,TermId);
        finish();
    }


}
