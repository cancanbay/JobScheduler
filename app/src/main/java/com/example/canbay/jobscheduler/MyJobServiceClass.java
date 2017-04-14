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
    public boolean isWaited = false;
    JobParameters paramsCopy;
    public boolean isFirst = true;

    @Override
    public boolean onStartJob(JobParameters params) {

        this.paramsCopy = params;
        if(params.getJobId() == 1) {
                waitSec();
            if(isWaited){
                Toast.makeText(getApplicationContext(), "ON START JOB WITH ID " + params.getJobId(), Toast.LENGTH_LONG).show();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(SendSmsActivity.etPhone.getText().toString(), null, "Hello World!", null, null);
                isWaited = false;
            }
            }


        else if (params.getJobId() == 2 ) {
            waitSec();
            if (isWaited) {
                Toast.makeText(getApplicationContext(), "ON START JOB WITH ID " + params.getJobId(), Toast.LENGTH_LONG).show();
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
                notification.setSmallIcon(R.drawable.notification);
                notification.setContentTitle("Click Me!");
                notification.setContentText("Hello from Can Canbay !");
                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(contentIntent);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notification.build());
                isWaited = false;
            }
        }
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Toast.makeText(getApplicationContext(),"ON STOP JOB",Toast.LENGTH_LONG).show();
        return false;
    }

    private void waitSec(){
        if(paramsCopy.getJobId() ==1) {
            CountDownTimer countDownTimer = new CountDownTimer(SendSmsActivity.workDuration * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(isFirst) {
                        Toast.makeText(getApplicationContext(), "-This toast is not synchronized with 1 second- Time Remaining :" + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFinish() {
                    if (isFirst) {
                        Toast.makeText(getApplicationContext(), "Time is up!", Toast.LENGTH_SHORT).show();
                        isWaited = true;
                        isFirst = false;
                        onStartJob(paramsCopy);
                    }

                }
            }.start();
        }
        else if (paramsCopy.getJobId() ==2){
            CountDownTimer countDownTimer = new CountDownTimer(ReminderActivity.workDuration * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(isFirst) {
                        Toast.makeText(getApplicationContext(), "-This toast is not synchronized with 1 second- Time Remaining :" + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFinish() {
                    if (isFirst) {
                        Toast.makeText(getApplicationContext(), "Time is up!", Toast.LENGTH_SHORT).show();
                        isWaited = true;
                        isFirst = false;
                        onStartJob(paramsCopy);
                    }

                }
            }.start();
        }
    }

}
