package com.example.xuzhengyi.eg23;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

public class Detail2Activity extends AppCompatActivity {
    private IndicatorViewPager indicatorViewPager;
    private int[] chart={R.id.chart,R.id.chart2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        getSupportActionBar().hide();

        ImageView back =(ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView head = (TextView) findViewById(R.id.app_bar_other_head);
        head.setText("Detail for 2");

        ViewPager viewPager = (ViewPager) findViewById(R.id.moretab_viewPager);
        ScrollIndicatorView scrollIndicatorView = (ScrollIndicatorView) findViewById(R.id.moretab_indicator);

        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(0xFF2196F3, Color.GRAY).setSize(selectSize, unSelectSize));

        scrollIndicatorView.setScrollBar(new ColorBar(this, 0xFF2196F3, 4));

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter());
        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                Draw_pie(chart[currentItem]);
            }
        });
    }

    public void Draw_pie(int p){
        PieChart chart=(PieChart) findViewById(p);
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

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        private String[] versions = {"Joueur1", "Joueur2"};
        private int[] r={R.layout.detail_layout,R.layout.detail2_layout};

        @Override
        public int getCount() {
            return versions.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(versions[position]);

            int witdh = getTextWidth(textView);
            textView.setWidth((int) (witdh * 1.3f));

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

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }

    }
}


