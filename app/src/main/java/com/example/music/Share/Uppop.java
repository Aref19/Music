package com.example.music.Share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.music.DatenBank.LocalDatenBank.DataBase;
import com.example.music.DatenBank.LocalDatenBank.SaveThings;
import com.example.music.Firbase.Fierbase;
import com.example.music.HauptMain.Music;
import com.example.music.HauptMain.Songinfo;
import com.example.music.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class Uppop extends DialogFragment implements View.OnClickListener {

    View view;
    ImageButton share, friend, uploud;
    ArrayList<Songinfo> songinfos;
    int pos;
    Context context;
    SaveThings saveThings;
    DataBase dataBase;

    public Uppop(Context context, ArrayList<Songinfo> songinfos, int pos) {
        this.songinfos = songinfos;
        this.pos = pos;
        this.context = context;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.uppop, container, false);
        share = view.findViewById(R.id.share);
        friend = view.findViewById(R.id.frind);
        uploud = view.findViewById(R.id.uplod);
        share.setOnClickListener(this);
        uploud.setOnClickListener(this);

        dataBase = DataBase.getInstance(context);

        Log.i("size", "onCreateView: " + dataBase.daoData().getlist().size());

        return view;
    }

    @Override
    public void onClick(View v) {
        ImageButton imageButton = (ImageButton) v;
        if (imageButton.getId() == R.id.share) {
            Log.i("sharev", "onClick: " + "share");
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(songinfos.get(pos).getPath()));
            sendIntent.setType("audio/*");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        } else if (imageButton.getId() == R.id.uplod) {
            Log.i("sharev", "onClick: " + "share");
            Fierbase fierbase = new Fierbase();
            this.dismiss();
            fierbase.imageStroge(Uri.parse(songinfos.get(pos).getPath()), context, songinfos.get(pos).getSong_name().trim());
            fierbase.chickDataToFDatabase(songinfos.get(pos).getSong_name());
            Map<String,String> stringStringMap=new HashMap<>();
            stringStringMap.put("namesong",songinfos.get(pos).getSong_name());
            stringStringMap.put("Altist",songinfos.get(pos).getAltist());
           // namesHolder(songinfos.get(pos).getSong_name(),stringStringMap);
           // saveThings = new SaveThings();
            //saveThings.setNamesong(songinfos.get(pos).getSong_name().trim());
          //  dataBase.daoData().insert(saveThings);
        } else if (imageButton.getId() == R.id.frind) {

        }
    }

}
