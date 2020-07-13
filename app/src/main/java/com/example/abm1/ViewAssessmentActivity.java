package com.example.abm1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.AssessmentViewModel;
import com.example.abm1.ViewModels.CourseViewModel;
import com.example.abm1.database.AppDatabase;
import com.example.abm1.models.AssessmentEntity;
import com.example.abm1.models.CourseEntity;

import java.text.DateFormat;

public class ViewAssessmentActivity extends AppCompatActivity {

    //Variables needed for activity///////////////////////////////////////////////////////////////

    private AssessmentViewModel assessmentViewModel;
    private TextView assessmentTitleText,assessmentDueDate,assessmentType;
    private AppDatabase myDb = AppDatabase.getInstance(this);
    DateFormat format_short = DateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);
        assessmentTitleText = (TextView) findViewById(R.id.assessmentTitleText);
        assessmentDueDate = (TextView) findViewById(R.id.assessmentDueDateText);
        assessmentType = (TextView) findViewById(R.id.assessmentTypeText);
        initViewModel();

    }

    private void initViewModel()  {
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.liveAssessmentEntity.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(AssessmentEntity assessmentEntity)  {
                if(assessmentEntity != null) {

                    ///////////////////////////////////////////////////////////////////////////////////
                    assessmentTitleText.setText(assessmentEntity.getAssessmentTitle());
                    assessmentDueDate.setText(format_short.format(assessmentEntity.getDueDate()));
                    assessmentType.setText(assessmentEntity.getAssessmentType());
                    setTitle("Assessment OverView");
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        int assessmentId = extras.getInt("Assessment_ID");
        assessmentViewModel.getData(assessmentId);

    }

    //Load Menu into activity//////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        MenuItem item_Delete = menu.findItem(R.id.action_delete);
        MenuItem item_start_course = menu.findItem(R.id.action_start_course);
        MenuItem item_mark_complete = menu.findItem(R.id.action_mark_completed);
        MenuItem item_drop_course = menu.findItem(R.id.action_drop_course);
        MenuItem item_enable_notifications = menu.findItem(R.id.action_enable_notifications);
        item_Delete.setTitle("Delete Assessment");
        item_drop_course.setVisible(false);
        item_mark_complete.setVisible(false);
        item_start_course.setVisible(false);
        item_enable_notifications.setVisible(false);

        return true;
    }

    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete: assessmentViewModel.deleteAssessment(); finish(); return true;

            default:super.onOptionsItemSelected(item);
        }
        return true;
    }

}
