package com.example.xuzhengyi.eg23;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StatusActivity extends AppCompatActivity {
    private Handler handler;
    private TextView h,min,s;
    private ImageView time;
    SweetAlertDialog d;
    private boolean joueur2=false;
    private int[] animate={R.drawable.live_tag_button_circle_anim_02,
            R.drawable.live_tag_button_circle_anim_03,
            R.drawable.live_tag_button_circle_anim_04,
            R.drawable.live_tag_button_circle_anim_05,
            R.drawable.live_tag_button_circle_anim_06,
            R.drawable.live_tag_button_circle_anim_07,
            R.drawable.live_tag_button_circle_anim_08,
            R.drawable.live_tag_button_circle_anim_09};
    int minute=0,hour=0,sec=0,hm=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final StatusActivity p=this;
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

        Intent intent = getIntent();
        joueur2 = intent.getBooleanExtra("p",false);

        h=(TextView) findViewById(R.id.h);
        min=(TextView) findViewById(R.id.min);
        s=(TextView) findViewById(R.id.sec);
        time = (ImageView) findViewById(R.id.timebg);

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 0:
                        handler.removeMessages(0);
                        String s_h=String.format("%d",hour);
                        String s_min=String.format("%d",minute);
                        String s_sec=String.format("%d",sec);

                        if(sec<10){s_sec="0"+s_sec;}
                        if(minute<10){s_min="0"+s_min;}
                        if(hour<10){s_h="0"+s_h;}

                        h.setText(s_h);
                        min.setText(s_min);
                        s.setText(s_sec);
                        ++sec;
                        if(sec==60){
                            ++minute;
                            sec=0;
                        }
                        if(minute==60){
                            minute=0;
                            ++hour;
                        }
                        handler.sendEmptyMessageDelayed(0, 1000);
                        break;
                    case 1:
                        handler.removeMessages(0);
                        handler.removeMessages(2);
                        break;
                    case 2:
                        handler.removeMessages(2);
                        ++hm;
                        hm=hm%8;
                        time.setImageResource(animate[hm]);
                        handler.sendEmptyMessageDelayed(2, 100);
                        break;
                    case 3:
                        handler.removeMessages(3);
                        handler.sendEmptyMessageDelayed(4, 3000);
                        break;
                    case 4:
                        handler.removeMessages(4);
                        d.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        d.setTitleText("OK").setContentText("Data changed successfully.")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent intent = new Intent(StatusActivity.this, Detail2Activity.class);
                                    startActivity(intent);
                                }
                                 }).show();
                        break;
                    default:
                        break;
                }
            };
        };

        Button start = (Button) findViewById(R.id.start_s);
        Button stop = (Button) findViewById(R.id.stop_s);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
                handler.sendEmptyMessage(2);
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
        handler.sendEmptyMessage(1);
        if(joueur2){
            SweetAlertDialog pDialog = new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("Warning").setContentText("Please use NFC to exchange the data.")
                    .setConfirmText("Yes!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Waiting...").changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        d=sDialog;
                        handler.sendEmptyMessage(3);

                    }
                    }).show();
        }
        else {
            Intent intent = new Intent(StatusActivity.this, DetailActivity.class);
            startActivity(intent);
        }
    }
}
