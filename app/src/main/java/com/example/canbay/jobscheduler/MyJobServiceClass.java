package com.example.canbay.jobscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by canbay on 11.04.2017.
 */

public class MyJobServiceClass extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {

        if(params.getJobId() ==1) {
            Toast.makeText(getApplicationContext(), "ON START JOB WITH ID "+params.getJobId(), Toast.LENGTH_LONG).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(SendSmsActivity.etPhone.getText().toString(), null, "Hello World!", null, null);
        }
        else if (params.getJobId() == 2){
            Toast.makeText(getApplicationContext(), "ON START JOB WITH ID "+params.getJobId(), Toast.LENGTH_LONG).show();
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setSmallIcon(R.drawable.notification);
            notification.setContentTitle("Click Me!");
            notification.setContentText("Hello from Can Canbay !");
            Intent notificationIntent = new Intent(this, ReminderActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notification.build());
        }
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Toast.makeText(getApplicationContext(),"ON STOP JOB",Toast.LENGTH_LONG).show();
        return false;
    }

}
