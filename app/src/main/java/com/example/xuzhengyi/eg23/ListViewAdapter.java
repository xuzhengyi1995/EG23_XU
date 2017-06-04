package com.example.xuzhengyi.eg23;

/**
 * Created by XUZhengyi on 2017/6/4.
 */

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.Calendar;
import java.util.Random;


public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private int[] r_bg= new int[8];
    private int[] r_mode=new int[5];
    private int[] r_weather=new int[5];
    private Random random = new Random();

    public ListViewAdapter(Context mContext) {
        this.mContext = mContext;

        r_bg[0]=R.drawable.timeline_default_tile_random_01;
        r_bg[1]=R.drawable.timeline_default_tile_random_02;
        r_bg[2]=R.drawable.timeline_default_tile_random_03;
        r_bg[3]=R.drawable.timeline_default_tile_random_04;
        r_bg[4]=R.drawable.timeline_default_tile_random_05;
        r_bg[5]=R.drawable.timeline_default_tile_random_06;
        r_bg[6]=R.drawable.timeline_default_tile_random_07;
        r_bg[7]=R.drawable.timeline_default_tile_random_08;

        r_mode[0]=R.drawable.notes_mode_bad_on;
        r_mode[1]=R.drawable.notes_mode_best_on;
        r_mode[2]=R.drawable.notes_mode_good_on;
        r_mode[3]=R.drawable.notes_mode_normal_on;
        r_mode[4]=R.drawable.notes_mode_worst_on;

        r_weather[0]=R.drawable.notes_cloudy_on;
        r_weather[1]=R.drawable.notes_night_on;
        r_weather[2]=R.drawable.notes_rainy_on;
        r_weather[3]=R.drawable.notes_sunny_on;
        r_weather[4]=R.drawable.notes_windy_on;

    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {

            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        View lv = convertView.findViewById(R.id.list_item_ll);

        lv.setBackgroundResource(r_bg[Math.abs(random.nextInt())%8]);
        lv.getBackground().setAlpha(60);

        ImageView mode = (ImageView) convertView.findViewById(R.id.mode);
        mode.setImageResource(r_mode[Math.abs(random.nextInt())%5]);

        ImageView weather = (ImageView) convertView.findViewById(R.id.record_weather);
        weather.setImageResource(r_weather[Math.abs(random.nextInt())%5]);

        TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText(randomDate());
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static String randomDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015,11,31);
        calendar.getTime().getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        long min = calendar.getTime().getTime();;
        calendar.set(2017,05,31);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.getTime().getTime();

        long max = calendar.getTime().getTime();
        double randomDate = Math.random()*(max-min)+min;
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar.getTime().toLocaleString();
    }
}