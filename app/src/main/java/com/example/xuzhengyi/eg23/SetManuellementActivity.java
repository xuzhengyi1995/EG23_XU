package com.example.xuzhengyi.eg23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class SetManuellementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_manuellement);

        getSupportActionBar().hide();

        ImageView back =(ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView head = (TextView) findViewById(R.id.app_bar_other_head);
        head.setText("Manuellement");

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker = (TimePicker) findViewById(R.id.timePicker2);
        timePicker.setIs24HourView(true);
        timePicker = (TimePicker) findViewById(R.id.timePicker3);
        timePicker.setIs24HourView(true);
        timePicker = (TimePicker) findViewById(R.id.timePicker4);
        timePicker.setIs24HourView(true);

        Button go = (Button)findViewById(R.id.button5);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetManuellementActivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });
    }
}
