package com.example.xuzhengyi.eg23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;


public class ListViewAdapter_SM extends BaseSwipeAdapter {

    private Context mContext;
    private int sum=1;
    private int l;

    public ListViewAdapter_SM(Context mContext,int layout) {
        this.mContext = mContext;
        this.l=layout;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(l, null);
        final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --sum;
                swipeLayout.close();
                notifyDataSetChanged();
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TimePicker t=(TimePicker) convertView.findViewById(R.id.timePicker3);
        if(t!=null) {
            t.setMinute(0);
            t.setHour(0);
            t.setIs24HourView(true);
        }

        TextView textView=(TextView) convertView.findViewById(R.id.text_seq);
        if(textView!=null){
            textView.setText((position + 1) + ".");
        }
    }

    @Override
    public int getCount() {
        return sum;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add_item(){
        ++sum;
        notifyDataSetChanged();
    }
}