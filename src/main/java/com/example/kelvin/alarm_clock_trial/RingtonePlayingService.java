package com.example.kelvin.alarm_clock_trial;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by Kelvin on 7/10/2017.
 */
public class RingtonePlayingService extends Service {


    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    public int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.e("MyActivity", "In the Ringtone service");

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Get up..Time for work" + "!")
                .setContentText("Click me!")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);


        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }



        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");


            mMediaPlayer = MediaPlayer.create(this, R.raw.longsong);
            mMediaPlayer.start();

            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;


        }
        else if (!this.isRunning && startId == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        }

        else if (this.isRunning && startId == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");

            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }




        Log.e("MyActivity", "In the service");


        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }


}
