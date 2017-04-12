package com.example.canbay.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by canbay on 11.04.2017.
 */

public class MyJobServiceClass extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        
        Toast.makeText(getApplicationContext(),"ON START JOB",Toast.LENGTH_LONG).show();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(SendSmsActivity.etPhone.getText().toString(), null, "Hello World!", null, null);
        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Toast.makeText(getApplicationContext(),"ON STOP JOB",Toast.LENGTH_LONG).show();
        return false;
    }

}
