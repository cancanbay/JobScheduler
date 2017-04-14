package com.example.canbay.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by canbay on 11.04.2017.
 */

public class ReminderActivity extends AppCompatActivity {
    Button btnSchedule;
    RadioButton radioAny,radioWifi;
    EditText etDuration;
    JobScheduler jobScheduler;
    JobInfo jobInfo;
    boolean requiresCharging = false;
    boolean requiresIdle = false;
    int networkType;
    static int workDuration;
    CheckBox chkPlug,chkIdle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        btnSchedule = (Button) findViewById(R.id.btnSchedule);
        etDuration = (EditText) findViewById(R.id.etDuration);
        radioAny = (RadioButton) findViewById(R.id.radioAny);
        radioWifi = (RadioButton) findViewById(R.id.radioWifi);
        chkPlug = (CheckBox) findViewById(R.id.chkPlug);
        chkIdle = (CheckBox) findViewById(R.id.chkIdle);

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParams();
                scheduleJob();
                finish();
            }
        });

    }
    private void scheduleJob(){
        jobScheduler  = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobInfo = new JobInfo.Builder(2, new ComponentName(this, MyJobServiceClass.class))
                .setRequiredNetworkType(networkType)
                .setRequiresCharging(requiresCharging)
                .setRequiresDeviceIdle(requiresIdle)
                .build();
        int result = jobScheduler.schedule(jobInfo);
        if(result == JobScheduler.RESULT_SUCCESS){
            Toast.makeText(getApplicationContext(),"Job will be scheduled with network type of "+networkType+" required charging type with "+requiresCharging+" and idle mode with " +requiresIdle,Toast.LENGTH_LONG).show();
        }

    }

    private void getParams(){
        if(radioAny.isChecked()){
            networkType = JobInfo.NETWORK_TYPE_ANY;
        }
        else if (radioWifi.isChecked()){
            networkType = JobInfo.NETWORK_TYPE_UNMETERED;
        }

        if(chkPlug.isChecked()){
            requiresCharging = true;
        }
        if(chkIdle.isChecked()){
            requiresIdle = true;
        }
        try {
            workDuration = Integer.parseInt(etDuration.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            workDuration = 0;
        }

    }
}
