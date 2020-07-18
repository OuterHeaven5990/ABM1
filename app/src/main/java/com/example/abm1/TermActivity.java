package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.ViewModels.TermViewModel;
import com.example.abm1.adapters.TermAdapter;
import com.example.abm1.models.TermEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {

    //Declare variables needed globally////////////////////////////////////////////////////////////
    ArrayList<TermEntity> terms = new ArrayList<>();
    TermAdapter termAdapter;
    private TermViewModel termViewModel;
    private RecyclerView termRV;
    private FloatingActionButton fab;
///////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        getSupportActionBar().setTitle("Term List");
        termRV = (RecyclerView) findViewById(R.id.term_recycler_view);
        ///Set up recycler view/////////////////////////////////////////////////////////////////////
        RecyclerView.LayoutManager termLM;
        termRV.setHasFixedSize(true);
        termLM = new LinearLayoutManager(this);
        termRV.setLayoutManager(termLM);
        initViewModel();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {addButtonClick();}
        });
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
////Get Main Menu for sub activities//////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    //Handle menu selections//////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_SampleData) { generateSampleData(); return true; }

        return super.onOptionsItemSelected(item);
    }



    ///Create link view model/////////////////////////////////////////////////////////////////////////
    private void initViewModel() {

        final Observer<List<TermEntity>> termsObserver = new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(List<TermEntity> termEntities) {
                terms.clear();
                terms.addAll(termEntities);

                if (termAdapter == null) {
                    termAdapter = new TermAdapter(terms, TermActivity.this);
                    termRV.setAdapter(termAdapter);
                } else {termAdapter.notifyDataSetChanged();}
            }
        };

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.terms.observe(this,termsObserver);
    }

    ///Generate Sample Data method ////////////////////////////////////////////////////////////////
    private void generateSampleData() {
        termViewModel.generateSampleData();
    }


    //Add Button Click//////////////////////////////////////////////////////////////////////////
    private void addButtonClick() {
        Intent intent = new Intent(this, EditTermActivity.class);
        startActivity(intent);
    }



}
