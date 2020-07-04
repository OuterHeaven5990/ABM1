package com.example.abm1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abm1.ViewModels.TermEditorViewModel;
import com.example.abm1.models.TermEntity;


public class EditTermActivity  extends AppCompatActivity {

    private TermEditorViewModel termViewModel;
    private TextView termTitle;
    private TextView startDateText;
    private TextView endDateText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_createterm);
        getSupportActionBar().setTitle("Edit Term");

        termTitle = (TextView) findViewById(R.id.termTitleText);
        startDateText = (TextView) findViewById(R.id.startDateText);
        endDateText = (TextView) findViewById(R.id.endDateText);
        saveButton = (Button) findViewById(R.id.saveButton);
        initViewModel();



}


    private void initViewModel() {
        termViewModel = ViewModelProviders.of(this).get(TermEditorViewModel.class);
        termViewModel.liveTermEntity.observe(this, new Observer<TermEntity>() {
            @Override
            public void onChanged(TermEntity termEntity) {
                termTitle.setText(termEntity.getTermTitle());
                startDateText.setText(termEntity.getStartDate().toString());
                endDateText.setText(termEntity.getEndDate().toString());

            }
        });
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt("Term_ID");
       termViewModel.getData(termId);
    }

}
