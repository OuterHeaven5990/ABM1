package com.example.abm1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        /** Home Logo on click action **/

        ImageView img = findViewById(R.id.termImage);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {startTermActivity();}
        });
    }
    /********************************************************************************************/

    /** Navigate to the Term activity ***********************************************************/
    public void startTermActivity() {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }
    /********************************************************************************************/
}