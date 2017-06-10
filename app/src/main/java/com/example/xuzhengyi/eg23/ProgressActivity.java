package com.example.xuzhengyi.eg23;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {
    private IndicatorViewPager indicatorViewPager;
    private boolean is_draw=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        getSupportActionBar().hide();

        ImageView back =(ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView head = (TextView) findViewById(R.id.app_bar_other_head);
        head.setText("");

        final String[] type={"Service","Smash","Volée revers","Volée coup drt","Coup droit lifté","Coup droit slicé","Revers lifté","Revers slicé"};

        Spinner spinner = (Spinner) findViewById(R.id.spinner_pro);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,type);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);


        ViewPager viewPager = (ViewPager) findViewById(R.id.moretab_viewPager);
        ScrollIndicatorView scrollIndicatorView = (ScrollIndicatorView) findViewById(R.id.moretab_indicator);

        scrollIndicatorView.setOnTransitionListener(new Indicator.OnTransitionListener() {
            @Override
            public void onTransition(View view, int position, float selectPercent) {
                ImageView imageView = (ImageView) view;
                imageView.setAlpha(selectPercent%99+0.2f);
            }
        });

        scrollIndicatorView.setScrollBar(new ColorBar(this, 0xFF2196F3, 4));

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        indicatorViewPager.setAdapter(new ProgressActivity.MyAdapter());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if(!is_draw) {
            Draw_chart(0);
            Draw_chart(1);
            Draw_chart(2);
            is_draw = true;
        }
    }
    //IndicatorViewPage adapter
    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        private int[] versions = {R.drawable.ic_tab_spin_normal,R.drawable.ic_tab_swing_speed_normal,R.drawable.ic_tab_ball_speed_normal};
        private int[] r={R.layout.type1_layout,R.layout.type2_layout,R.layout.type3_layout};

        @Override
        public int getCount() {
            return versions.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_img_view, container, false);
            }
            ImageView imageView = (ImageView) convertView;
            imageView.setImageResource(versions[position]);

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(r[position], container, false);
            }
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_UNCHANGED;
        }
    }

    private void Draw_chart(int position){
        int[] chart_layout={R.id.progress_chart1,R.id.progress_chart2,R.id.progress_chart3};
        int[] data={47,29,21,28,80,30,49,18,26};
        final String[] quarters={"1 Jun","2 Jun","3 Jun","4 Jun","5 Jun","6 Jun","7 Jun","8 Jun","9 Jun"};

        List<Entry> dataList = new ArrayList<>();
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        for(int i=0;i<data.length;i++) {
            dataList.add(new Entry(i,data[i]));
        }

        LineDataSet set1 = new LineDataSet(dataList, "");

        set1.setColor(ColorTemplate.JOYFUL_COLORS[0]);

        LineChart lineChart = (LineChart) findViewById(chart_layout[position]);
        Description description = new Description();
        description.setText("Progress");
        lineChart.setDescription(description);
        LineData f_data = new LineData(set1);
        lineChart.setData(f_data);
        lineChart.animateXY(3000,3000);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        lineChart.invalidate();
    }
}