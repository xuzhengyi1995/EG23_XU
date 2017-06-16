package com.example.xuzhengyi.eg23;

import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;


public class SetCoupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_coup);

        getSupportActionBar().hide();

        ImageView back =(ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView head = (TextView) findViewById(R.id.app_bar_other_head);
        head.setText("Coup");

        Button go = (Button)findViewById(R.id.button5);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetCoupActivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });

        Button set_dur=(Button) findViewById(R.id.button_settime);
        set_dur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimeSelect();
            }
        });


    }

    private void popTimeSelect()
    {
        Calendar p=Calendar.getInstance();
        p.set(2000,1,1,0,0);
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                TextView t=(TextView)findViewById(R.id.textView_t);
                t.setText(Integer.toString(date.getHours())+"  H  "+Integer.toString(date.getMinutes())+"  Min");
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "Hour", "Min", "")
                .setDate(p)
                .build();
        pvTime.show();
    }
}
