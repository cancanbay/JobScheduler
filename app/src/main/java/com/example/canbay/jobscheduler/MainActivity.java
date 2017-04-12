package com.example.canbay.jobscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    RadioButton radioSms, radioReminder;
    Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioSms = (RadioButton) findViewById(R.id.radioSms);
        radioReminder = (RadioButton) findViewById(R.id.radioReminder);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        final Intent intentSms = new Intent(this,SendSmsActivity.class);
        final Intent intentReminder = new Intent(this,ReminderActivity.class);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioSms.isChecked()){
                    startActivity(intentSms);
                }
                else if(radioReminder.isChecked()){
                    startActivity(intentReminder);
                }
            }
        });

    }
}
