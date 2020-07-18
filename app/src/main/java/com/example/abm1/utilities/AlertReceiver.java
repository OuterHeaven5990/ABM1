package com.example.abm1.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.abm1.R;

public class AlertReceiver extends BroadcastReceiver {

    static int alertId;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"New Alert",Toast.LENGTH_LONG).show();

        NotificationCompat.Builder alertBuilder = new NotificationCompat.Builder(context, "WGU Notification Channel")
                .setSmallIcon(R.drawable.ic_alert_icon)
                .setContentTitle(intent.getStringExtra("Alert Title"))
                .setContentText(intent.getStringExtra("Alert Text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(alertId++,alertBuilder.build());
    }

}
