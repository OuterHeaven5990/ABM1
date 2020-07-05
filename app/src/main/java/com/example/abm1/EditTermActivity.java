package com.example.abm1;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.models.TermEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class EditTermActivity  extends AppCompatActivity{

    private TermEditorViewModel termViewModel;
    private TextView termTitle;
    private Button startDateButton,endDateButton,saveButton;
    private String startDate, endDate;
    private boolean newTerm;
    GregorianCalendar calendar = new GregorianCalendar();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);



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

                DatePickerDialog dpd = new DatePickerDialog(EditTermActivity.this,
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

        initViewModel();

}



    private void initViewModel()  {
        termViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termViewModel.liveTermEntity.observe(this, new Observer<TermEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(TermEntity termEntity)  {
                if(termEntity != null) {


                    String sDate = termEntity.getStartDate().toString();
                    String eDate = termEntity.getEndDate().toString();
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

                        termTitle.setText(termEntity.getTermTitle());
                        startDateButton.setText(startdate);
                        endDateButton.setText(enddate);
                        startDate = startdate;
                        endDate = enddate;

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
        Date enddate;
        Date startdate;
        startdate = format.parse(startDate);
        enddate = format.parse(endDate);
        termViewModel.saveTerm(termTitle.getText().toString(),startdate,enddate);
        finish();
    }




}
