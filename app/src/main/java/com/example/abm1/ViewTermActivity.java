package com.example.abm1;

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

import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.ViewModels.TermViewModel;
import com.example.abm1.database.AppDatabase;
import com.example.abm1.database.AppRepo;
import com.example.abm1.database.TermDAO;
import com.example.abm1.models.CourseEntity;
import com.example.abm1.models.TermEntity;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewTermActivity extends AppCompatActivity {

    //Variables needed for activity///////////////////////////////////////////////////////////////

    private TermEditorViewModel termViewModel;
    private TextView termTitleText,termStartDateText,termEndDateText;
    private AppDatabase myDb = AppDatabase.getInstance(this);
    DateFormat format_short = DateFormat.getDateInstance(DateFormat.MEDIUM);

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
            @Override
            public void onChanged(TermEntity termEntity)  {
                if(termEntity != null) {

                    ///////////////////////////////////////////////////////////////////////////////////
                    termTitleText.setText(termEntity.getTermTitle());
                    termStartDateText.setText(format_short.format(termEntity.getStartDate()));
                    termEndDateText.setText(format_short.format(termEntity.getEndDate()));
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
        MenuItem item = menu.findItem(R.id.action_delete);
        item.setTitle("Delete Term");
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            Bundle extras = getIntent().getExtras();
            int termId = extras.getInt("Term_ID");
            System.out.println(termId);
            boolean hasTerms = false;
            if(myDb.courseDAO().getCoursesByTermId(termId) > 0) {hasTerms=true;}

            if(hasTerms == false){
                termViewModel.deleteTerm();
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Cannot delete Term with course(s) assigned",Toast.LENGTH_LONG).show();
            }

        }
        return  true;
    }


    public void startCourseActivity() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt("Term_ID");
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra("Term_ID", termId );
        startActivity(intent);
    }

}
