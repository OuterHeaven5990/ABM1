package com.example.abm1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
        createNotificationChannel();

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WGU Notification Channel";
            String description = "WGU Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("WGU Notification Channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}