package com.example.xuzhengyi.eg23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

public class SetManuellementActivity extends AppCompatActivity {

    private ListViewAdapter_SM listViewAdapter;
    private ListView listView;

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

        listView = (ListView) findViewById(R.id.listview_set_m);
        listViewAdapter= new ListViewAdapter_SM(this,R.layout.set_layout);
        listView.setAdapter(listViewAdapter);
        listViewAdapter.setMode(Attributes.Mode.Single);

        Button go = (Button)findViewById(R.id.button5);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetManuellementActivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });

        Button add = (Button)findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewAdapter.add_item();
            }
        });
    }
}
