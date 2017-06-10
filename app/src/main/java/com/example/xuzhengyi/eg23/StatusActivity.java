package com.example.xuzhengyi.eg23;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StatusActivity extends AppCompatActivity {
    private Handler handler;
    TimePicker picker;
    int minute=0,hour=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        getSupportActionBar().hide();

        ImageView back =(ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView head = (TextView) findViewById(R.id.app_bar_other_head);
        head.setText("Status");

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        handler.removeMessages(0);
                        picker.setHour(hour);
                        picker.setMinute(minute);
                        ++minute;
                        if(minute==60){
                            minute=0;
                            ++hour;
                        }
                        handler.sendEmptyMessageDelayed(0, 60000);
                        break;
                    case 1:
                        handler.removeMessages(0);
                        break;
                    default:
                        break;
                }
            };
        };

        picker=(TimePicker)findViewById(R.id.timePicker2);
        Button start = (Button) findViewById(R.id.start_s);
        Button stop = (Button) findViewById(R.id.stop_s);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        final StatusActivity h=this;
        SweetAlertDialog pDialog = new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Warning").setContentText("Do you really want to quit?")
                .setCancelText("No!")
                .setConfirmText("Yes!").
                showCancelButton(true).
                setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        h.goback();
                    }
                }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                }).show();
    }

    public void goback(){
        super.onBackPressed();
    }
}
