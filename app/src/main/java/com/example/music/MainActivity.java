package com.example.music;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Cursor crusor;
    ArrayList<Songinfo> songinfos;
    ListView listView;
    int prmistion = 1, postion, prograss;
    Button  start, stop;
    Runnable runnable, runn;
    Handler handler, handel;
    boolean sorstop;
    ImageView bause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        premtion();
        seekBar = findViewById(R.id.laufm);
        listView = findViewById(R.id.liedlist);
        handler = new Handler();
        handel = new Handler();
        getallsong();
        sekk(seekBar);
        bause = findViewById(R.id.pause);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        buttonClick();
        current();


    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show();
        sekk(seekBar);
        buttonClick();
        current();

    }




    private void getallsong() {
        songinfos = new ArrayList<>();
        Uri allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        crusor = managedQuery(allsong, null, selection, null, null);
        if (crusor != null) {
            if (crusor.moveToNext()) {
                do {
                    String name = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String fullp = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String alpum = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    Log.i("name", "getallsong: " + name.toString());
                    if (!name.contains("WA")) {


                        songinfos.add(new Songinfo(fullp, name, alpum, artist));

                    }

                } while (crusor.moveToNext());
                //       Toast.makeText(this,"sdaaaaaaa"+songinfos.get(0).getSong_name(),Toast.LENGTH_LONG).show();
                Adabter adabter = new Adabter(songinfos, this);

                listView.setAdapter(adabter);
                list(listView, songinfos);
            }
            //crusor.close();
        }
    }

    private void list(ListView listView, final ArrayList<Songinfo> songinfos) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    prograss = position;
                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(songinfos.get(position).getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    bause.setImageResource(R.drawable.start);

                    seekBar.setMax(mediaPlayer.getDuration());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void premtion() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requstpremstion();
        }

    }

    private void requstpremstion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("premision")
                    .setMessage("dsa")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, prmistion);

                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, prmistion);
        }
    }

    private void sekk(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    postion = progress;
                    mediaPlayer.seekTo(progress);

                } else if (progress == mediaPlayer.getDuration()) {
                    media();

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(postion);

                ;


            }
        });
    }

    private void buttonClick() {
        bause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                if(sorstop){
                    current();
                    mediaPlayer.start();
                    bause.setImageResource(R.drawable.start);
                    sorstop=false;
                }else{
                    bause.setImageResource(R.drawable.stop);
                    sorstop=true;

                }

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer=new MediaPlayer();
                bause.setImageResource(R.drawable.stop);
                seekBar.setProgress(0);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                bause.setImageResource(R.drawable.start);
            }
        });

    }

    private void current() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        runnable = new Runnable() {
            @Override
            public void run() {
                current();
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    private void media() {
        try {
            prograss += 1;
            if(prograss>=songinfos.size()){
                prograss=0;
            }
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(songinfos.get(prograss).getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        sekk(seekBar);
        current();
        Toast.makeText(this,"onStop",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        seekBar = findViewById(R.id.laufm);
        listView = findViewById(R.id.liedlist);
        handler = new Handler();
        handel = new Handler();
        Uri allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        crusor = managedQuery(allsong, null, selection, null, null);

        sekk(seekBar);
        bause = findViewById(R.id.pause);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        buttonClick();
        Toast.makeText(this,"onRestart",Toast.LENGTH_LONG).show();
        current();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getallsong();
        sekk(seekBar);
        buttonClick();
        Toast.makeText(this,"onStart",Toast.LENGTH_LONG).show();
        current();
    }

    @Override
    protected void onResume() {
        super.onResume();
        seekBar = findViewById(R.id.laufm);
        listView = findViewById(R.id.liedlist);
        handler = new Handler();
        handel = new Handler();

        sekk(seekBar);
        bause = findViewById(R.id.pause);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        buttonClick();
        Toast.makeText(this,"onRestart",Toast.LENGTH_LONG).show();
        current();
    }
}