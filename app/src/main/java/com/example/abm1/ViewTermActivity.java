package com.example.abm1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.ViewModels.TermViewModel;
import com.example.abm1.database.AppRepo;
import com.example.abm1.database.TermDAO;
import com.example.abm1.models.TermEntity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ViewTermActivity extends AppCompatActivity {

    //Variables needed for activity///////////////////////////////////////////////////////////////

    private TermEditorViewModel termViewModel;
    private TextView termTitleText,termStartDateText,termEndDateText;
    private AppRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewterm);
         termTitleText = (TextView) findViewById(R.id.termViewTitleText);
         termStartDateText = (TextView) findViewById(R.id.termStartDateText);
         termEndDateText = (TextView) findViewById(R.id.termEndDateText);
        initViewModel();

        ImageView img = findViewById(R.id.courseLogo);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {startCourseActivity();}
        });
    }


    private void initViewModel()  {
        termViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termViewModel.liveTermEntity.observe(this, new Observer<TermEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(TermEntity termEntity)  {
                if(termEntity != null) {
                    //Date Conversion/////////////////////////////////////////////////////////////////
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
                    ///////////////////////////////////////////////////////////////////////////////////
                    termTitleText.setText(termEntity.getTermTitle());
                    termStartDateText.setText(startdate);
                    termEndDateText.setText(enddate);

                    setTitle(termEntity.getTermTitle() + " Term View");
                }
            }
        });

        Bundle extras = getIntent().getExtras();

            int termId = extras.getInt("Term_ID");
            termViewModel.getData(termId);
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
        if (item.getItemId() == R.id.action_delete_term) {
            termViewModel.deleteTerm();
            finish();
        }
        return  true;
    }


    public void startCourseActivity() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

}
