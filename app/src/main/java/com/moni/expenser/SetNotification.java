package com.moni.expenser;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import static android.app.AlarmManager.INTERVAL_HOUR;

public class SetNotification extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;
    PendingIntent notifyPendingIntent;
    AlarmManager alarmManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification);
        toolbar = findViewById(R.id.toolbarSetNotification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences  preferences = this.getPreferences(Context.MODE_PRIVATE);
        if(preferences!=null) {
            int ID = preferences.getInt("Radio_ID", R.id.btnNone);
            RadioButton button = findViewById(ID);
            button.setChecked(true);
        }
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this,AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getSystemService
                (ALARM_SERVICE);
        createNotificationChannel();
    }
    public void ButtonClicked(View v){
        Boolean checked = ((RadioButton)v).isChecked();
        SharedPreferences preferences = SetNotification.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Radio_ID",v.getId());
        editor.apply();
        switch (v.getId())
        {
            case R.id.btn1:
                if(checked){
                    long repeatInterval = AlarmManager.INTERVAL_HOUR;

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }
                }
                break;
            case R.id.btn2:
                if(checked){
                    long repeatInterval = 2*(AlarmManager.INTERVAL_HOUR);

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }
                }
                break;
            case R.id.btn3:
                if(checked){
                    long repeatInterval = 3*(AlarmManager.INTERVAL_HOUR);

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }

                }
                break;
            case R.id.btn4:
                if(checked){
                    long repeatInterval = 4*(AlarmManager.INTERVAL_HOUR);

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }

                }
                break;
            case R.id.btn5:
                if(checked){
                    long repeatInterval = 5*(AlarmManager.INTERVAL_HOUR);

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }
                }
                break;
            case R.id.btn6:
                if(checked){
                    long repeatInterval = 6*(AlarmManager.INTERVAL_HOUR);

                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    // If the Toggle is turned on, set the repeating alarm with
                    // a 15 minute interval.
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval,
                                        notifyPendingIntent);
                    }

                }
                break;
            case R.id.btnNone:
                mNotificationManager.cancelAll();

                if (alarmManager != null) {
                    alarmManager.cancel(notifyPendingIntent);
                }
                break;
        }
    }
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationChannel.setDescription("Notifies at regular intervals");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
