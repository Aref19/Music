package com.example.music.HauptMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.music.DatenBank.LocalDatenBank.DataBase;
import com.example.music.DatenBank.LocalDatenBank.SongLate;
import com.example.music.HauptMain.MusicNavie;
import com.example.music.R;
import com.example.music.Video.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class Main extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {
    LinearLayout linearLayout;
    SeekBar seekBar;
    ImageButton button;
    MediaPlayer mediaPlayer;
    DataBase dataBase;
    boolean start=false;
    int musicpos;
    Runnable runnable;
    Handler handler;
    LaufendeSong laufendeSong;
    AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        linearLayout=Main.this.findViewById(R.id.laufendemusic);
        seekBar=Main.this.findViewById(R.id.sekunten);
        button=Main.this.findViewById(R.id.startunten);
        SachenuberAll.linearLayoutm=linearLayout;
        mediaPlayer=new MediaPlayer();
        SachenuberAll.lauf=seekBar;
        SachenuberAll.startb=button;
        BottomNavigationView bottomNavigationView=findViewById(R.id.navibut);
        bottomNavigationView.setOnNavigationItemSelectedListener(nav);
       getSupportFragmentManager().beginTransaction().replace(R.id.fram,new MusicNavie(linearLayout)).commit();
       dataBase=DataBase.getInstance(this);
       button.setImageIcon(Icon.createWithResource("com.example.music",R.drawable.ic_baseline_play_circle_outline_24));
        try {
            laufen();
            seekBar.setMax(SachenuberAll.mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler=new Handler();
       sekk(seekBar);
       current();
      SachenuberAll sachenuberAll=new SachenuberAll();
      sachenuberAll.audioManger(this);
        //   getSupportFragmentManager().beginTransaction().replace(R.id.fram,new SelectViewFragement()).commit();
    }



    private  BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.music:
                    fragment = new MusicNavie(linearLayout);
                    Log.i("frag", "onNavigationItemReselected: " + "fra");
                    break;
                case R.id.video:
                    fragment = new VideoFragment();
                    break;
                case R.id.selectview:
                    fragment=new SelectViewFragement();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fram, fragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Main.super.onBackPressed();
                    }
                }).create().show();
    }
    public void museiclauf(){

        this.linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"stop",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    //    SongLate songLate=new SongLate(SachenuberAll.mediaPlayer.getDuration(),SachenuberAll.mediaPlay);
      laufendeSong=LaufendeSong.getContext(false);
      if(laufendeSong.getMediaPlayer()!=null){
          SongLate songLate=new SongLate((laufendeSong.getMediaPlayer().getDuration()),laufendeSong.getPath(),laufendeSong.getPostion());
          dataBase.daoData().deltetableSong();
          dataBase.daoData().insertSong(songLate);
      }

        Toast.makeText(this,"Destroy"+laufendeSong.getPostion(),Toast.LENGTH_LONG).show();
    }


    public void startunte(View view) throws IOException {
        if(SachenuberAll.status==false){

            SachenuberAll.mediaPlayer.start();
            Log.i("nexw", "startunte: "+"reagieren"+SachenuberAll.status);
            SachenuberAll.status=true;
            button.setImageIcon(Icon.createWithResource("com.example.music",R.drawable.ic_baseline_pause_circle_filled_24));
        }else {
            SachenuberAll.mediaPlayer.pause();
            SachenuberAll.status=false;
            button.setImageIcon(Icon.createWithResource("com.example.music",R.drawable.ic_baseline_play_circle_outline_24));

        }

       // Log.i("nexw", "startunte: "+"reagieren"+SachenuberAll.status);

        //   LaufendeSong laufendeSong=LaufendeSong.getContext(false);
        //laufendeSong.getMediaPlayer().pause();
    }
    private void sekk(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SachenuberAll.pos = progress;
                    //SachenuberAll.mediaPlayer.seekTo( progress );
                //    Log.i("seekk", "onProgressChanged: postio :"+SachenuberAll.mediaPlayer.getCurrentPosition()+"postion 2:"+SachenuberAll.pos);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                SachenuberAll.mediaPlayer.seekTo(SachenuberAll.pos);
                Log.i("seekk", "onProgressChanged: postio :"+SachenuberAll.mediaPlayer.getCurrentPosition()+"  postion 2:"+SachenuberAll.pos);
                Log.i("seekk", "onProgressChanged: postio :"+SachenuberAll.mediaPlayer.getDuration()+"  postion 2:"+SachenuberAll.pos);
            }

        });

    }
    private void current() {

            seekBar.setProgress(SachenuberAll.mediaPlayer.getCurrentPosition());


        runnable = new Runnable() {
            @Override
            public void run() {
                current();
            }
        };
        handler.postDelayed(runnable, 1000);

    }
    public void laufen() throws IOException {
        laufendeSong=LaufendeSong.getContext(false);
        if(laufendeSong.getMediaPlayer()==null){
            dataBase.daoData().getSongList();
            Log.i("baseunt", "laufen: "+"von data1");
            if(dataBase.daoData().getSongList()==null){
                linearLayout.setVisibility(View.GONE);

            }else {
                mediaPlayer.setDataSource( dataBase.daoData().getSongList().getPath());
                mediaPlayer.prepare();
                SachenuberAll.mediaPlayer=mediaPlayer;
                Log.i("baseunt", "laufen: "+"von data3");
            }


        }else {
           mediaPlayer=SachenuberAll.mediaPlayer;


                //mediaPlayer.stop();


                Log.i("baseunt", "laufen: "+"von data2");

            Log.i("baseunt", "laufen: "+"von data");
         //   mediaPlayer.prepare();

        }


    }


    @Override
    public void onAudioFocusChange(int focusChange) {
     //   if (isMusicActive) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                Log.i("onfuc", "onAudioFocusChange: " + "1");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
                sekk(seekBar);
                current();
                Log.i("onfuc", "onAudioFocusChange: " + "2");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                onDestroy();
                Log.i("onfuc", "onAudioFocusChange: " + "3");
            }
        }
   // }
}