package com.example.xuzhengyi.eg23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        ImageView back =(ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Draw_pie();

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.putExtra("email","test@test.fr");
        startActivity(intent);
    }


    public void Draw_pie(){
        PieChart chart=(PieChart) findViewById(R.id.chart);
        List<PieEntry> entries =new ArrayList<PieEntry>();
        entries.add(new PieEntry(47l,""));
        entries.add(new PieEntry(29l,""));
        entries.add(new PieEntry(21l,""));
        entries.add(new PieEntry(28l,""));
        entries.add(new PieEntry(80l,""));
        entries.add(new PieEntry(30l,""));
        entries.add(new PieEntry(49l,""));
        entries.add(new PieEntry(18l,""));

        PieDataSet dataSet=new PieDataSet(entries,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data=new PieData(dataSet);
        chart.setData(data);
        Description description= new Description();
        description.setText("Frappes");
        chart.setDescription(description);

        chart.setCenterText("340");
        chart.setDrawCenterText(true);

        chart.animateXY(3000,3000);

    }
}
