package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.database.AppDatabase;
import com.example.abm1.models.TermEntity;

import java.text.DateFormat;

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

        MenuItem item_Delete = menu.findItem(R.id.action_delete);
        MenuItem item_start_course = menu.findItem(R.id.action_start_course);
        MenuItem item_mark_complete = menu.findItem(R.id.action_mark_completed);
        MenuItem item_drop_course = menu.findItem(R.id.action_drop_course);
        MenuItem item_add_course = menu.findItem(R.id.action_add_note);
        MenuItem item_edit_note = menu.findItem(R.id.action_edit_note);
        item_Delete.setTitle("Delete Term");
        item_drop_course.setVisible(false);
        item_mark_complete.setVisible(false);
        item_start_course.setVisible(false);
        item_add_course.setVisible(false);
        item_edit_note.setVisible(false);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            Bundle extras = getIntent().getExtras();
            int termId = extras.getInt("Term_ID");
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
