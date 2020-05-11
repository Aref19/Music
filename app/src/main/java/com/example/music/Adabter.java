package com.example.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adabter extends BaseAdapter {
    ArrayList<Songinfo>areArrayList;
    Context context;
    public Adabter (ArrayList areArrayList,Context context){
        this.areArrayList=areArrayList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return areArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return areArrayList.get(position).getSong_name();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
          view= LayoutInflater.from(context).inflate(R.layout.activity_adbter,parent,false);
        TextView textView=view.findViewById(R.id.name);
        TextView textView2=view.findViewById(R.id.lauf);
        textView.setText(areArrayList.get(position).getSong_name());
        textView2.setText(areArrayList.get(position).getAlbum_name());

        return view;
    }
}
