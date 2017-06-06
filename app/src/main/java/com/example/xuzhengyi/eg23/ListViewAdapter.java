package com.example.xuzhengyi.eg23;

/**
 * Created by XUZhengyi on 2017/6/4.
 */

import android.app.LauncherActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static android.content.ContentValues.TAG;

class ListObj{
    public int r_bg;
    public int r_mode;
    public int r_weather;
    public int r_type;
    public String r_date;
    public String r_data;
};

public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private int[] r_bg= new int[8];
    private int[] r_mode=new int[5];
    private int[] r_weather=new int[5];
    private int[] r_type=new int[4];
    private int num = 50;
    private List<ListObj> listObj = new ArrayList<ListObj>();
    private List<ListObj> listObj_backup = new ArrayList<ListObj>();
    private Random random = new Random();
    private boolean is_filter=false;
    private int filter;
    private int s;

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

        r_type[0]=R.drawable.ic_2joueurs;
        r_type[1]=R.drawable.ic_coup;
        r_type[2]=R.drawable.ic_manual;
        r_type[3]=R.drawable.ic_sequen;

        productData();

    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        final View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView rp = (TextView)v.findViewById(R.id.real_position);

                AlphaAnimation img_anim = new AlphaAnimation((float)1,(float)0);
                v.startAnimation(img_anim);
                img_anim.setFillAfter(false);
                img_anim.setDuration(1000);

                final int real_position=Integer.parseInt((String)rp.getText());
                Handler goNext =new Handler();
                goNext.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        delItem(real_position);
                    }
                },1000);

                swipeLayout.close(true);
                Toast.makeText(mContext, "Record deleted", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        View lv = convertView.findViewById(R.id.list_item_ll);

        lv.setBackgroundResource(this.listObj.get(position).r_bg);
        lv.getBackground().setAlpha(60);

        ImageView mode = (ImageView) convertView.findViewById(R.id.mode);
        mode.setImageResource(this.listObj.get(position).r_mode);

        ImageView weather = (ImageView) convertView.findViewById(R.id.record_weather);
        weather.setImageResource(this.listObj.get(position).r_weather);

        ImageView type = (ImageView) convertView.findViewById(R.id.record_type);
        type.setImageResource(this.listObj.get(position).r_type);

        TextView t = (TextView) convertView.findViewById(R.id.position);
        t.setText(this.listObj.get(position).r_date);

        TextView data = (TextView) convertView.findViewById(R.id.text_data);
        data.setText(this.listObj.get(position).r_data);

        TextView rp = (TextView) convertView.findViewById(R.id.real_position);
        rp.setText(Integer.toString(position));
    }

    @Override
    public int getCount() {
        return listObj.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listObj.get(position);
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

    private void productData(){
        for(int i=0;i<this.num;i++) {
            ListObj temp = new ListObj();
            temp.r_bg=r_bg[Math.abs(random.nextInt())%8];
            temp.r_mode=r_mode[Math.abs(random.nextInt())%5];
            temp.r_weather=r_weather[Math.abs(random.nextInt())%5];
            temp.r_type=r_type[Math.abs(random.nextInt())%4];
            temp.r_date=randomDate();
            temp.r_data=temp.r_date+" : Some memos here, or the review of the record";

            this.listObj.add(i,temp);
            this.listObj_backup.add(i,temp);
        }

    }

    private void _clone() {
        listObj.clear();
        for(int i=0;i<listObj_backup.size();i++){
            listObj.add(i,listObj_backup.get(i));
        }
    }


    private void delItem(int position) {

        this.listObj_backup.remove(position);
        this.listObj.remove(position);
        this.num--;
        notifyDataSetChanged();
    }

    public void aplyFliter(boolean _is_filter,int _filter){
        _clone();
        if(!_is_filter){
            this.is_filter=false;
            this.filter=0;
        }
        else{
            this.is_filter=true;
            this.filter=_filter;
            this.listObj.removeIf(new Predicate<ListObj>() {
                @Override
                public boolean test(ListObj listObj) {
                    boolean t;
                    if(listObj.r_type==filter){
                        t=false;
                    }
                    else{
                        t=true;
                    }
                    return t;
                }
            });
        }
        notifyDataSetChanged();
    }
}
