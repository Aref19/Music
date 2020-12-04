package com.example.music.Video;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.HauptMain.Songinfo;
import com.example.music.R;

import java.util.ArrayList;

public class videoAdpter extends BaseAdapter {
    ArrayList<Songinfo> areArrayList
            = new ArrayList();
    Context context;
    SaveInfoUserselect saveInfoUserselect;
    TextView namedersong;

    public videoAdpter(ArrayList areArrayList, Context context) {
        this.areArrayList = areArrayList;
        this.context = context;
        saveInfoUserselect=SaveInfoUserselect.getContext(context);
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
        view = LayoutInflater.from(context).inflate(R.layout.itemvideoview, parent, false);
        TextView namedersinger=view.findViewById(R.id.videoname);
        namedersinger.setText(areArrayList.get(position).getSong_name());
        VideoView videoView=view.findViewById(R.id.videoView3);
        videoView.setVideoURI(Uri.parse( areArrayList.get(position).getPath()));
        Log.i("path2", "getView: "+areArrayList.get(position).getPath());
        return view;
    }
}
