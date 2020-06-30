package com.example.abm1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.adapters.TermAdapter;
import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

     ////Get Recycler View///////////////////////////////////////////////////////////////////////
     RecyclerView termRV = (RecyclerView) findViewById(R.id.term_recycler_view);
     //Set Adapter///////////////////////////////////////////////////////////////////////////////
        TermAdapter termAdapter;
     //Set Layout Manager//////////////////////////////////////////////////////////////////////////
     RecyclerView.LayoutManager termLM;
     /////////////////////////////////////////////////////////////////////////////////////////////
     //Get Sample data////////////////////////////////////////////////////////////////////////////
     ArrayList<TermEntity> terms = new ArrayList<>();
     terms.addAll(SampleData.getTerms());
        //Connect Adapter/////////////////////////////////////////////////////////////////////////
        termRV.setHasFixedSize(true);
        termLM = new LinearLayoutManager(this);
        termRV.setLayoutManager(termLM);
        termAdapter = new TermAdapter(terms);
         termRV.setAdapter(termAdapter);


    }
}
