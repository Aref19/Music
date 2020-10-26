package com.example.music.HauptMain;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.music.Notifaction.Notification;
import com.example.music.Notifaction.Tarck;
import com.example.music.NotificationServiceAction.onClearFromRecentServic;
import com.example.music.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



    public  class MainActivity extends AppCompatActivity implements Playble {
        SeekBar seekBar;
        MediaPlayer mediaPlayer = new MediaPlayer();
        Cursor crusor;
        ArrayList<Songinfo> songinfos;
        ListView listView;
        int prmistion = 1, postion, prograss,nextint,lastint,gesamt;
        ImageButton last, next;
        Runnable runnable, runn;
        Handler handler, handel;
        boolean sorstop;
        ImageView bause;
        RelativeLayout relativeLayout;
        List<Tarck> tarcks;
        boolean isplaing;
        Notification notification;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            premtion();
            seekBar = findViewById(R.id.laufm);
            listView = findViewById(R.id.liedlist);
            handler = new Handler();
            handel = new Handler();
            tarcks();
            getallsong();
            sekk(seekBar);
            bause = findViewById(R.id.pause);
            relativeLayout = findViewById(R.id.relative);
            last = findViewById(R.id.start);
            next = findViewById(R.id.stop);
            buttonClick();
            current();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), onClearFromRecentServic.class));


        }

        @Override
        protected void onPause() {
            super.onPause();
            Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
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
                        nextint=position;
                        lastint=position;
                        mediaPlayer.stop();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(songinfos.get(position).getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        bause.setImageResource(R.drawable.start);

                        seekBar.setMax(mediaPlayer.getDuration());
                        notification = new Notification(getApplication());
                        notification.creatchanel();
                        notification.greatNafi(0, songinfos.get(postion), R.drawable.stop, postion, songinfos.size());
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
                    onTaskpause();
                    /*
                    mediaPlayer.pause();
                    if (sorstop) {
                        current();
                        mediaPlayer.start();
                        bause.setImageResource(R.drawable.start);
                        sorstop = false;
                    } else {
                        bause.setImageResource(R.drawable.stop);
                        sorstop = true;

                    }

                     */

                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTaskNext();
    /*
                    nextint++;
                    lastint++;
                    if(nextint>=songinfos.size()){
                        nextint=0;
                    }

                    mediaPlayer.stop();
                    try {
                        mediaPlayer=new MediaPlayer();
                        mediaPlayer.setDataSource(songinfos.get(nextint).getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Log.i("prog", "onClick: "+prograss);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bause.setImageResource(R.drawable.stop);
                    bause.setImageResource(R.drawable.start);
                    seekBar.setProgress(0); */
                }


            });
            last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTaskprovis();
                }
                    /*
                    lastint--;
                    nextint--;
                    if(lastint<0){
                        lastint=(songinfos.size()-1);
                    }

                    mediaPlayer.stop();
                    try {
                        mediaPlayer=new MediaPlayer();
                        mediaPlayer.setDataSource(songinfos.get(lastint).getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Log.i("prog", "onClick: "+prograss);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bause.setImageResource(R.drawable.stop);
                    bause.setImageResource(R.drawable.start);

                }

                    */
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
                if (prograss >= songinfos.size()) {
                    prograss = 0;
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
            Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
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
            last = findViewById(R.id.start);
            next = findViewById(R.id.stop);
            buttonClick();
            Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
            current();
        }

        @Override
        protected void onStart() {
            super.onStart();
            getallsong();
            sekk(seekBar);
            buttonClick();
            Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
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
            last = findViewById(R.id.start);
            next = findViewById(R.id.stop);
            buttonClick();
            Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
            current();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.background, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.back) {
                imageholen();

            }
            return super.onOptionsItemSelected(item);
        }

        private void imageholen() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

        @Override
        public void startActivityForResult(Intent intent, int requestCode) {
            Log.i("imageee", "startActivityForResult: " + intent.getData());
            super.startActivityForResult(intent, requestCode);
        }

        private void tarcks() {
            tarcks = new ArrayList<>();
            tarcks.add(new Tarck("ers", "asd", R.drawable.music));
            tarcks.add(new Tarck("zwe", "asd", R.drawable.music));
            tarcks.add(new Tarck("dritte", "asd", R.drawable.music));
        }

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getExtras().getString("action");
                switch (action) {
                    case Notification.Action:
                        onTaskprovis();
                        break;
                    case Notification.actionplay:

                            onTaskpause();

                         //  onTaskplay();

                        break;
                    case Notification.actionnext:
                        onTaskNext();
                        break;
                }
            }
        };

        @Override
        public void onTaskplay() {
            mediaPlayer.start();
            bause.setImageResource(R.drawable.start);
            onTaskplay();
            notification.creatchanel();
            notification.greatNafi(0, songinfos.get(postion), R.drawable.start, postion, tarcks.size() - 1);
            isplaing = true;


        }

        @Override
        public void onTaskprovis() {
            lastint--;
            nextint--;
            if(lastint<0){
                lastint=(songinfos.size()-1);
            }

            mediaPlayer.stop();
            try {
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(songinfos.get(lastint).getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                Log.i("prog", "onClick: "+prograss);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bause.setImageResource(R.drawable.stop);
            bause.setImageResource(R.drawable.start);


            notification.greatNafi(0, songinfos.get(postion), R.drawable.ic_stopmusic, prograss, songinfos.size() - 1);

        }

        @Override
        public void onTaskNext() {
            nextint++;
            lastint++;
            if(nextint>=songinfos.size()){
                nextint=0;
            }

            mediaPlayer.stop();
            try {
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(songinfos.get(nextint).getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                Log.i("prog", "onClick: "+prograss);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bause.setImageResource(R.drawable.stop);
            bause.setImageResource(R.drawable.start);
            seekBar.setProgress(0);

            notification.greatNafi(0, songinfos.get(postion), R.drawable.ic_stopmusic, prograss, songinfos.size() - 1);

        }

        @Override
        public void onTaskpause() {
            mediaPlayer.pause();
            notification.creatchanel();
            if (sorstop) {

                current();
                mediaPlayer.start();
                bause.setImageResource(R.drawable.start);
                sorstop = false;
                Log.i("hier", "onTaskpause: "+"hier1");
                notification.greatNafi(0,songinfos.get(postion),R.drawable.start,postion,songinfos.size()-1);
            } else {
                bause.setImageResource(R.drawable.stop);
                notification.greatNafi(0,songinfos.get(postion),R.drawable.ic_baseline_play_circle_outline_24,postion,songinfos.size()-1);
                Log.i("hier", "onTaskpause: "+"hier2");
                sorstop = true;

            }


            isplaing=false;

        }



        @Override
        protected void onDestroy() {
            super.onDestroy();
            NotificationManager notificationManager= null;
            notificationManager.cancelAll();
            unregisterReceiver(broadcastReceiver);

        }
    }
}
