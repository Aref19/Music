package com.example.music.AccountUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.music.DatenBank.LocalDatenBank.DataBase;
import com.example.music.DatenBank.LocalDatenBank.SaveThings;
import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.Firbase.Fierbase;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.HauptMain.Playble;
import com.example.music.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcountUser extends AppCompatActivity implements WorkwithFirbase, Playble {
    ListView listView;
    SeekBar seekBar;
    ArrayList<String> songslocal, uris;
    ArrayAdapter<String> stringArrayAdapter;
    MediaPlayer mediaPlayer;
    RelativeLayout relativeLayout;
    SeekBar getSeekBar;
    int prograss,postion;
    Handler handler;
    ImageView stop;
    boolean play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_user);

        relativeLayout = findViewById(R.id.relative);
        listView = findViewById(R.id.liedlistonline);
        seekBar = findViewById(R.id.laufmonline);
        seekBar=findViewById(R.id.laufmonline);
        stop=findViewById(R.id.pauseonline);
        namesHolder();
        DataBase dataBase = DataBase.getInstance(this);
        mediaPlayer = new MediaPlayer();

        songslocal = new ArrayList<>();
        uris = new ArrayList<>();
        SaveInfoUserselect saveInfoUserselect = SaveInfoUserselect.getContext(this);
        relativeLayout.setBackground(BitmapDrawable.createFromPath(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)));
        uris.clear();
        handler = new Handler();
        current();
        stop();

        for (int i=0;i<dataBase.daoData().getlist().size();i++){
            songslocal.add(dataBase.daoData().getlist().get(i).getNamesong());

        }
        seek();

        stringArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, songslocal);
        listView.setAdapter(stringArrayAdapter);
        getallsong();
    }

    private void getallsong() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 stop.setImageResource(R.drawable.start);
                getSongsStorge(songslocal.get(position));


            }
        });

    }

    @Override
    public void pushAudio(UploadTask.TaskSnapshot snapshot, Context context) {

    }

    @Override
    public void pullFoto(RelativeLayout linearLayout, Context context) {

    }

    @Override
    public void cutchAduio(String songs, Context context) {
        DataBase dataBase = DataBase.getInstance(getApplication());

        Log.i("nameso", "cutchAduio: " + songs);
        if (context instanceof AcountUser) {
            ((AcountUser) context).songslocal.add(songs);
            Log.i("list", "cutchAduio: " + ((AcountUser) context).songslocal.size());
            if (((AcountUser) context).songslocal.size() == dataBase.daoData().getlist().size()) {

                ((AcountUser) context).listView.setAdapter(((AcountUser) context).stringArrayAdapter);

            }

        }
    }

    public void getSongsStorge(String name) {
        int i = 0;
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final ArrayList<String> strings = new ArrayList<>();
        //  do {


        Log.i("songsf", "onSuccess: " + name);

        final StorageReference storageR = storage.getReference().child(firebaseAuth.getUid()).child(name);
        final Task<Uri> u = storageR.getDownloadUrl();
        u.addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                try {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.stop();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer = new MediaPlayer();

                    mediaPlayer.setDataSource(task.getResult().toString());
                    Log.i("uri1", "onItemClick: " + task.getResult().toString());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("exs", "onItemClick: " + e.getMessage());
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("songsur", "onSuccess: " + e);
            }
        });
           /*
            storageR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    uris.add(uri.toString());
                    Log.i("songsur", "onSuccess: "+uri.toString());
                    AcountUser acountUser = new AcountUser();
                    if(uri!=null){


                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("songf", "onFailure: "+e.toString());
                }
            });
            i++;
      //  }while (i<name.size());

            */

    }

    private void namesHolder() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Fierbase fierbase = new Fierbase();
        fierbase.namesHolder(this);

    }
    public  void seek(){
      seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                postion=progress;
                mediaPlayer.seekTo(progress);
            }
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
              mediaPlayer.seekTo(postion);

          }
      });
    }
    private void current() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                current();
            }
        };
        handler.postDelayed(runnable, 1000);

    }
    private void stop(){
        Log.i("jetzt" , "onTaskplay: "+play);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play){
                onTaskpause();
                    play=false;
                }else {
                    play=true;
                    onTaskplay();
                }
            }
        });
    }


    @Override
    public void onTaskplay() {
      mediaPlayer.start();
        Log.i("jetzt" , "onTaskplay: "+"von play");
      stop.setImageResource(R.drawable.start);

    }

    @Override
    public void onTaskprovis() {

    }

    @Override
    public void onTaskNext() {

    }

    @Override
    public void onTaskpause() {

        mediaPlayer.pause();
        stop.setImageResource(R.drawable.stop);
    }
}
