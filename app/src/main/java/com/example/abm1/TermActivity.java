package com.example.abm1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.adapters.TermAdapter;
import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {

    //Declare variables needed globally////////////////////////////////////////////////////////////
    ArrayList<TermEntity> terms = new ArrayList<>();
    TermAdapter termAdapter;
///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        ///Set up recycler view/////////////////////////////////////////////////////////////////////
        RecyclerView termRV = (RecyclerView) findViewById(R.id.term_recycler_view);
        RecyclerView.LayoutManager termLM;
        termRV.setHasFixedSize(true);
        termLM = new LinearLayoutManager(this);
        termRV.setLayoutManager(termLM);
        ///Link adapter/////////////////////////////////////////////////////////////////////////////
        termAdapter = new TermAdapter(terms);
         termRV.setAdapter(termAdapter);
    }

    ///Generate Sample Data method ////////////////////////////////////////////////////////////////
    public void generateSampleData() {
        terms.addAll(SampleData.getTerms());
        termAdapter.notifyDataSetChanged();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_SampleData) {
                generateSampleData();
                 return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
