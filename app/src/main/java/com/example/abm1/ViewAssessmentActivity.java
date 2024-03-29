package com.example.abm1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.AssessmentViewModel;
import com.example.abm1.database.AppDatabase;
import com.example.abm1.models.AssessmentEntity;
import com.example.abm1.utilities.AlertReceiver;

import java.text.DateFormat;

public class ViewAssessmentActivity extends AppCompatActivity {

    //Variables needed for activity///////////////////////////////////////////////////////////////

    private AssessmentViewModel assessmentViewModel;
    private TextView assessmentTitleText,assessmentDueDate,assessmentType;
    private AppDatabase myDb = AppDatabase.getInstance(this);
    private AssessmentEntity tempEntity;
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
                    tempEntity = assessmentEntity;
                    setTitle("Assessment Overview");
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
        MenuItem item_start_alert = menu.findItem(R.id.action_enable_start_notification);
        MenuItem item_end_alert = menu.findItem(R.id.action_enable_end_notification);
        MenuItem item_add_note = menu.findItem(R.id.action_add_note);
        MenuItem item_edit_note = menu.findItem(R.id.action_edit_note);
        item_Delete.setTitle("Delete Assessment");
        item_end_alert.setTitle("Set Due Date Alert");
        item_drop_course.setVisible(false);
        item_mark_complete.setVisible(false);
        item_start_course.setVisible(false);
        item_start_alert.setVisible(false);
        item_add_note.setVisible(false);
        item_edit_note.setVisible(false);

        return true;
    }

    //Options selected logic///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete: assessmentViewModel.deleteAssessment(); finish(); return true;
            case R.id.action_enable_end_notification: setDueDateAlert(); return true;
            default:super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void setDueDateAlert() {
        long temp = tempEntity.getDueDate().getTime();

        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("Alert Title","Assessment due date Reminder");
        intent.putExtra("Alert Text",tempEntity.getAssessmentTitle() + " due today");
        intent.setAction(tempEntity.getAssessmentTitle() + " due today");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0 ,intent,0);
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, temp + (1000 * 60 * 60 * 7) , pendingIntent);


    }

}
