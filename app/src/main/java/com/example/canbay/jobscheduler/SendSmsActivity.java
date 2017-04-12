package com.example.canbay.jobscheduler;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by canbay on 11.04.2017.
 */

public class SendSmsActivity extends AppCompatActivity {

    Button btnSchedule;
    static EditText etDuration,etPhone;
    static int workDuration;
    int networkType;
    String phoneNumber;
    RadioButton radioAny,radioWifi;
    CheckBox chkPlug;
    boolean requiresCharging=false;
    JobScheduler jobScheduler;
    JobInfo jobInfo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);
        btnSchedule = (Button) findViewById(R.id.btnSchedule);
        etDuration = (EditText) findViewById(R.id.etDuration);
        etPhone = (EditText) findViewById(R.id.etPhone);
        radioAny = (RadioButton) findViewById(R.id.radioAny);
        radioWifi = (RadioButton) findViewById(R.id.radioWifi);
        chkPlug = (CheckBox) findViewById(R.id.chkPlug);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParams();
                ScheduleJob();
                finish();
            }
        });
    }

    private void getParams(){
        if(radioAny.isChecked()){
            networkType = JobInfo.NETWORK_TYPE_ANY;
        }
        else{
            networkType = JobInfo.NETWORK_TYPE_UNMETERED;
        }

        if(chkPlug.isChecked()){
            requiresCharging = true;
        }

        try {
            workDuration = Integer.parseInt(etDuration.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            workDuration = 0;
        }
        phoneNumber = etPhone.getText().toString();
    }
    private void ScheduleJob(){
        jobScheduler  = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobInfo = new JobInfo.Builder(1, new ComponentName(this, MyJobServiceClass.class))
                .setRequiredNetworkType(networkType)
                .setRequiresCharging(requiresCharging)
                .build();
        int result = jobScheduler.schedule(jobInfo);
        if(result == JobScheduler.RESULT_SUCCESS){
            Toast.makeText(getApplicationContext(),"Job will be scheduled with network type of "+networkType+" required charging type with "+requiresCharging,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
