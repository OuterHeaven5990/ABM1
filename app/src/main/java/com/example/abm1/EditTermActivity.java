package com.example.abm1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.models.TermEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class EditTermActivity  extends AppCompatActivity{
    ///Private variables needed to activity////////////////////////////////////////////////////////
    private TermEditorViewModel termViewModel;
    private TextView termTitle;
    private Button startDateButton,endDateButton,saveButton;
    private Date startDate, endDate;
    private boolean newTerm;
    GregorianCalendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    DateFormat format_short = DateFormat.getDateInstance(DateFormat.MEDIUM);
///////////////////////////////////////////////////////////////////////////////////////////////////
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_createterm);

        termTitle = (TextView) findViewById(R.id.termTitleText);
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

                DatePickerDialog dpd = new DatePickerDialog(EditTermActivity.this,
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

                DatePickerDialog dpd = new DatePickerDialog(EditTermActivity.this,
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

        initViewModel();

}


    private void initViewModel()  {
        termViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termViewModel.liveTermEntity.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(TermEntity termEntity)  {
                if(termEntity != null) {


                ////////////////////////////////////////////////////////////////////////////////////
                        termTitle.setText(termEntity.getTermTitle());
                        startDateButton.setText(format_short.format(termEntity.getStartDate()));
                        endDateButton.setText(format_short.format(termEntity.getEndDate()));
                        startDate = termEntity.getStartDate();
                        endDate = termEntity.getEndDate();

                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New Term");
            newTerm = true;
        }

        else {
            setTitle("Edit Term");
            int termId = extras.getInt("Term_ID");
            termViewModel.getData(termId);
        }
    }


    private void onSaveButtonClick() throws ParseException {
        if(termTitle.getText().toString().isEmpty() && startDate == null && endDate == null ) {Toast.makeText(this,"All fields are empty",Toast.LENGTH_LONG).show();}
        else if(termTitle.getText().toString().isEmpty()) { Toast.makeText(this,"Title must contain a value",Toast.LENGTH_LONG).show(); }
        else if(startDate == null) { Toast.makeText(this,"Start Date must contain a value",Toast.LENGTH_LONG).show();}
        else if(endDate == null) { Toast.makeText(this,"End Date must contain a value",Toast.LENGTH_LONG).show();}
        else {

            termViewModel.saveTerm(termTitle.getText().toString(), startDate, endDate);
            finish();
        }
    }


}
