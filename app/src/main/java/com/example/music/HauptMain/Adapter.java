package com.example.music.HauptMain;

;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.music.R;
import com.example.music.HauptMain.Songinfo;

import java.util.ArrayList;


public class Adapter extends BaseAdapter {
    ArrayList<Songinfo> areArrayList
            = new ArrayList();
    Context context;

    public Adapter(ArrayList areArrayList, Context context) {
        this.areArrayList = areArrayList;
        this.context = context;
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
        view = LayoutInflater.from(context).inflate(R.layout.activity_adbter, parent, false);
         TextView namedersong=view.findViewById(R.id.name);
        TextView namedersinger=view.findViewById(R.id.lauf);
         namedersong.setText(areArrayList.get(position).getSong_name().toString());
         namedersinger.setText(areArrayList.get(position).getAlbum_name());
        return view;
    }
}
