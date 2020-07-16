package com.example.abm1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.models.CourseEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditCourseActivity extends AppCompatActivity {

    ///Private variables needed to activity////////////////////////////////////////////////////////
    private CourseViewModel viewModel;
    private TextView courseText, mentorname,mentorphone,mentoremail;
    private Button startDateButton,endDateButton,saveButton;
    private String courseStatus ="";
    private Date startDate,endDate;
    private boolean newCourse;
    private Spinner status;
    GregorianCalendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    DateFormat format_short = DateFormat.getDateInstance(DateFormat.MEDIUM);
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_create_course);

        courseText = (TextView) findViewById(R.id.courseTitleText);
        mentorname = (TextView) findViewById(R.id.mentorNameText);
        mentorphone = (TextView) findViewById(R.id.mentorPhoneNumberText);
        mentoremail = (TextView) findViewById(R.id.mentorEmailAddText);
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
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(0);
                                cal.set(year, month, day, 0, 0, 0);
                                startDate = cal.getTime();
                                String displayDate = format_short.format(startDate);
                                startDateButton.setText(displayDate);
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
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(0);
                                cal.set(year, month, day, 0, 0, 0);
                                endDate = cal.getTime();
                                String displayDate = format_short.format(endDate);
                                endDateButton.setText(displayDate);
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
            @Override
            public void onChanged(CourseEntity courseEntity)  {
                if(courseEntity != null) {


                    ////////////////////////////////////////////////////////////////////////////////////
                    courseText.setText(courseEntity.getCourseTitle());
                    startDateButton.setText(format_short.format(courseEntity.getStartDate()));
                    endDateButton.setText(format_short.format(courseEntity.getEndDate()));
                    startDate = courseEntity.getStartDate();
                    endDate = courseEntity.getEndDate();
                    mentorname.setText(courseEntity.getMentorName());
                    mentorphone.setText(courseEntity.getMentorPhoneNumber());
                    mentoremail.setText(courseEntity.getMentorEmailAddress());

                    //Set Spinner value/////////////////////////////////////////////////////////////
                    String compareValue = courseEntity.getStatus();
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.course_status, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    status.setAdapter(adapter);
                    if (compareValue != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        status.setSelection(spinnerPosition);
                        ///////////////////////////////////////////////////////////////////////////

                    }
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        int cId = extras.getInt("Course_ID");
        if (cId  < 1) {
            setTitle("New Course");
            newCourse = true;
        }

        else {
            newCourse = false;
            setTitle("Edit Course");
            int courseId = extras.getInt("Course_ID");
            viewModel.getData(courseId);
        }
    }


    private void onSaveButtonClick() throws ParseException {
        courseStatus = status.getSelectedItem().toString();

        if(courseText.getText().toString().isEmpty() && endDate == null && startDate == null && courseStatus.equals("") && mentorname.getText().toString().isEmpty() && mentorphone.getText().toString().isEmpty() && mentoremail.getText().toString().isEmpty()) { Toast.makeText(this,"All fields are empty",Toast.LENGTH_LONG).show();}
        else if(courseText.getText().toString().isEmpty()) { Toast.makeText(this,"Title must contain a value",Toast.LENGTH_LONG).show(); }
        else if(mentorname.getText().toString().isEmpty()) { Toast.makeText(this,"Mentor name must contain a value",Toast.LENGTH_LONG).show();}
        else if(mentorphone.getText().toString().isEmpty()) { Toast.makeText(this,"Mentor phone must contain a value",Toast.LENGTH_LONG).show();}
        else if(mentoremail.getText().toString().isEmpty()) { Toast.makeText(this,"Mentor email must contain a value",Toast.LENGTH_LONG).show();}
        else if(startDate == null) { Toast.makeText(this,"Start Date must contain a value",Toast.LENGTH_LONG).show();}
        else if(endDate == null) { Toast.makeText(this,"End Date must contain a value",Toast.LENGTH_LONG).show();}
        else if(courseStatus.equals("")) { Toast.makeText(this,"Course Status must contain a value",Toast.LENGTH_LONG).show();}


        else{
            Bundle extras = getIntent().getExtras();
            int TermId = extras.getInt("Term_ID");


            if(newCourse == false) {
                viewModel.updateCourse(courseText.getText().toString(),startDate,endDate,courseStatus,mentorname.getText().toString(),mentorphone.getText().toString(),mentoremail.getText().toString()); finish();
            }

            else {
                viewModel.saveCourse(courseText.getText().toString(), endDate, startDate, courseStatus,mentorname.getText().toString(),mentorphone.getText().toString(),mentoremail.getText().toString(), TermId);
                finish(); }
            }

    }


}
