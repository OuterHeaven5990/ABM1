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

import com.example.abm1.ViewModels.AssessmentViewModel;
import com.example.abm1.models.AssessmentEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditAssessmentActivity extends AppCompatActivity {

    ///Private variables needed to activity////////////////////////////////////////////////////////
    private AssessmentViewModel viewModel;
    private TextView assessmentText;
    private Button dueDateButton,saveButton;
    private String assessmentType ="";
    private Date dueDate;
    private boolean newAssessment;
    private Spinner type;
    GregorianCalendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    DateFormat format_short = DateFormat.getDateInstance(DateFormat.MEDIUM);
    ///////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        assessmentText = (TextView) findViewById(R.id.assessmentTitleText);
        dueDateButton = (Button) findViewById(R.id.dueDateButton);
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

        ///due Date Button listner////////////////////////////////////////////////////////////////
        dueDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                DatePickerDialog dpd = new DatePickerDialog(EditAssessmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(0);
                                cal.set(year, month, day, 0, 0, 0);
                                dueDate = cal.getTime();
                                String displayDate = format_short.format(dueDate);
                                dueDateButton.setText(displayDate);
                            }
                        },year,month,dayOfMonth);

                dpd.show();
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////
        // implementation of spinner to help choose course status
        type = (Spinner) findViewById(R.id.assesmentTypeSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_type, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        type.setAdapter(adapter);

///////////////////////////////////////////////////////////////////////////////////////////////////////

        initViewModel();

    }


    private void initViewModel()  {
        viewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        viewModel.liveAssessmentEntity.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(AssessmentEntity assessmentEntity)  {
                if(assessmentEntity != null) {


                    ////////////////////////////////////////////////////////////////////////////////////
                    assessmentText.setText(assessmentEntity.getAssessmentTitle());
                    dueDateButton.setText(format_short.format(assessmentEntity.getDueDate()));
                    dueDate = assessmentEntity.getDueDate();


                    //Set Spinner value/////////////////////////////////////////////////////////////
                    String compareValue = assessmentEntity.getAssessmentType();
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.assessment_type, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    type.setAdapter(adapter);
                    if (compareValue != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        type.setSelection(spinnerPosition);
                        ///////////////////////////////////////////////////////////////////////////

                    }
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        int aId = extras.getInt("Assessment_ID");
        if (aId  < 1) {
            setTitle("New Assessment");
            newAssessment = true;
        }

        else {
            newAssessment = false;
            setTitle("Edit Assessment");
            int assessmentID = extras.getInt("Assessment_ID");
            viewModel.getData(assessmentID);
        }
    }


    private void onSaveButtonClick() throws ParseException {

        assessmentType = type.getSelectedItem().toString();
        if(assessmentText.getText().toString().isEmpty()) { Toast.makeText(this,"Title must contain a value",Toast.LENGTH_LONG).show(); }

        else {
            Bundle extras = getIntent().getExtras();
            int courseId = extras.getInt("Course_ID");
            if(newAssessment == true) {
                viewModel.saveAssessment(assessmentText.getText().toString(), assessmentType, dueDate, courseId);
                finish();
            }
            else {
                viewModel.saveAssessment(assessmentText.getText().toString(), assessmentType, dueDate);
                finish();
            }
        }

    }
}
