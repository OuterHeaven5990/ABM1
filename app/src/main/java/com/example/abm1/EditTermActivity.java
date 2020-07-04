package com.example.abm1;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Date;
import java.util.GregorianCalendar;


@RequiresApi(api = Build.VERSION_CODES.O)
public class EditTermActivity  extends AppCompatActivity {

    private TermEditorViewModel termViewModel;
    private TextView termTitle;
    private TextView startDateText;
    private TextView endDateText;
    private Button saveButton;
    private Date sDate;
    private Date eDate;


    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_createterm);
        getSupportActionBar().setTitle("Edit Term");

        Button save = findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    onSaveButtonClick();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        termTitle = (TextView) findViewById(R.id.termTitleText);
        startDateText = (TextView) findViewById(R.id.startDateText);
        endDateText = (TextView) findViewById(R.id.endDateText);
        saveButton = (Button) findViewById(R.id.saveButton);
        initViewModel();


}



    private void initViewModel()  {
        termViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termViewModel.liveTermEntity.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(TermEntity termEntity)  {
                String sDate = termEntity.getStartDate().toString();
                LocalDate tempStartDate = ZonedDateTime.parse(sDate, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")).toLocalDate();
                String startDate = tempStartDate.getDayOfMonth() + "/" + tempStartDate.getMonthValue() + "/" + tempStartDate.getYear();
                String eDate = termEntity.getEndDate().toString();
                LocalDate tempEndDate = ZonedDateTime.parse(eDate, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")).toLocalDate();
                String endDate = tempEndDate.getDayOfMonth() + "/" + tempEndDate.getMonthValue() + "/" + tempEndDate.getYear();

                startDateText.setText(startDate);
                termTitle.setText(termEntity.getTermTitle());
                endDateText.setText(endDate);
            }
        });
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt("Term_ID");
       termViewModel.getData(termId);
    }

    private void onSaveButtonClick() throws ParseException {
        String temp1 = startDateText.getText().toString();
        String temp2 = endDateText.getText().toString();
        sDate = format.parse(temp1);
        eDate = format.parse(temp2);

        termViewModel.saveTerm(termTitle.getText().toString(),sDate,eDate);

        finish();
    }


}
