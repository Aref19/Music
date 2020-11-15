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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.os.Messenger;
import android.view.View;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.music.ButtonView.ViewButton;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.Firbase.Fierbase;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.Notifaction.Notification;
import com.example.music.Notifaction.Tarck;
import com.example.music.NotificationServiceAction.onClearFromRecentServic;
import com.example.music.R;
import com.example.music.Video.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Music extends AppCompatActivity implements Playble, WorkwithFirbase, AudioManager.OnAudioFocusChangeListener {
    SeekBar seekBar;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Cursor crusor;
    ArrayList<Songinfo> songinfos;
    ListView listView;
    int prmistion = 1, postion, prograss, nextint, lastint, sitution = 0;
    ImageButton last, next;
    Runnable runnable, runn;
    Handler handler, handel;
    boolean sorstop;
    ImageView bause;
    RelativeLayout relativeLayout;
    List<Tarck> tarcks;
    boolean isplaing, isselect;
    Notification notification;
    SaveInfoUserselect saveInfoUserselect;
    GradientDrawable[] drawables;
    TextView name;
    AudioManager mAudioManager;
    boolean isMusicActive;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.laufm);
        listView = findViewById(R.id.liedlist);
        int d = R.drawable.buton;


        handler = new Handler();
        handel = new Handler();
        premtion();

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
        notification = new Notification(getApplication());
        notification.creatchanel();
        seekBar.setMax(0);
        saveInfoUserselect = SaveInfoUserselect.getContext(this);

       // audioManger();
        //   if(mAudioManager.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
        //     list(listView, songinfos);


        //}

        drawables = new GradientDrawable[3];
        drawables[0] = (GradientDrawable) bause.getBackground().mutate();
        drawables[1] = (GradientDrawable) next.getBackground().mutate();
        drawables[2] = (GradientDrawable) last.getBackground().mutate();
        buttonColor();

        if (!saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("")) {
            pullFoto(relativeLayout, this);
        }

        //loadImage(relativeLayout);

/*
        try {background();
        } catch (IOException e) {
            e.printStackTrace();
        }

 */


    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
        sekk(seekBar);
        buttonClick();
        current();
        //  audioManger();
        // uperpruf();

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
                Adapter adabter = new Adapter(songinfos, this);

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
                    sitution = position;
                    prograss = position;
                    nextint = position;
                    lastint = position;
                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(songinfos.get(position).getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    bause.setImageResource(R.drawable.start);
                    isselect = true;
                    isMusicActive = true;
                    seekBar.setMax(mediaPlayer.getDuration());
                    Log.i("warum", "onTaskpause: " + "von hierlist");

                    notification.greatNafi(0, songinfos.get(position), R.drawable.ic_baseline_pause_circle_filled_24, position, songinfos.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void premtion() {
        if (ContextCompat.checkSelfPermission(Music.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Music.this, Manifest.permission.MANAGE_DOCUMENTS) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requstpremstion();
        }

    }

    private void requstpremstion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.MANAGE_DOCUMENTS)) {
            new AlertDialog.Builder(this)
                    .setTitle("premision")
                    .setMessage("dsa")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Music.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, prmistion);

                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, prmistion);
        }
    }

    private void sekk(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    postion = progress;
                    mediaPlayer.seekTo(progress);

                } else if (progress == mediaPlayer.getDuration() && isselect) {
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
            Log.i("warum", "onTaskpause: " + "von hier media");
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

     // audioManger();

        //  uperpruf();
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        buttonColor();
        pullFoto(relativeLayout, this);
        // loadImage(relativeLayout);
        Adapter adabter = new Adapter(songinfos, this);

        listView.setAdapter(adabter);
        list(listView, songinfos);

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

/*
    @Override
    protected void onResume() {
        super.onResume();
        seekBar = findViewById(R.id.laufm);
        listView = findViewById(R.id.liedlist);
        handler = new Handler();
        handel = new Handler();

        sekk(seekBar);
        // audioManger();
        buttonClick();
        Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
        current();
    }

 */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.background, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            Intent intent = new Intent(this, selectView.class);
            startActivity(intent);
            //   imageholen();

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
        Log.i("warum", "onTaskpause: " + "von hier");
        mediaPlayer.start();
        bause.setImageResource(R.drawable.start);
        onTaskplay();
        notification.creatchanel();
        notification.greatNafi(0, songinfos.get(sitution), R.drawable.start, postion, tarcks.size() - 1);
        isplaing = true;
        isMusicActive = true;
        //audioManger();


    }

    @Override
    public void onTaskprovis() {
        lastint--;
        nextint--;
        isMusicActive = true;
        isselect = true;
      //  audioManger();
        Log.i("warum", "onTaskpause: " + "von hier0");
        if (sitution == 0) {
            sitution = songinfos.size() - 1;
        } else {
            sitution--;
        }

        if (lastint < 0) {
            lastint = (songinfos.size() - 1);
        }

        mediaPlayer.stop();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(songinfos.get(lastint).getPath());
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            Log.i("prog", "onClick: " + prograss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bause.setImageResource(R.drawable.stop);
        bause.setImageResource(R.drawable.start);


        notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_pause_circle_filled_24, sitution, songinfos.size() - 1);

    }

    @Override
    public void onTaskNext() {
        nextint++;
        lastint++;
        isselect = true;
        isMusicActive = true;
        Log.i("warum", "onTaskpause: " + "von hier1");
        //audioManger();
        if (sitution == songinfos.size() - 1) {
            sitution = 0;
        } else {
            sitution++;
        }
        if (nextint >= songinfos.size()) {
            nextint = 0;
        }

        mediaPlayer.stop();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(songinfos.get(nextint).getPath());
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            Log.i("prog", "onClick: " + prograss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bause.setImageResource(R.drawable.stop);
        bause.setImageResource(R.drawable.start);
        seekBar.setProgress(0);

        notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_pause_circle_filled_24, sitution, songinfos.size() - 1);

    }

    @Override
    public void onTaskpause() {
        mediaPlayer.pause();
        notification.creatchanel();
        Log.i("warum", "onTaskpause: " + "von hier2");

     //   audioManger();
        if (sorstop) {
            isMusicActive = true;
            current();
            mediaPlayer.start();
            bause.setImageResource(R.drawable.start);
            sorstop = false;
            Log.i("hier", "onTaskpause: " + "hier1");
            notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_pause_circle_filled_24, sitution, songinfos.size() - 1);
        } else if (isselect) {
            isMusicActive = false;
            bause.setImageResource(R.drawable.stop);
            notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_play_circle_outline_24, sitution, songinfos.size() - 1);
            Log.i("hier", "onTaskpause: " + "hier2");
            sorstop = true;

        } else {
            Snackbar.make(this.listView, "dies hat kein mehr foto foto", Snackbar.LENGTH_LONG).show();

        }


        isplaing = false;

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        isMusicActive = false;

        NotificationManager notificationManager = getApplication().getSystemService(NotificationManager.class);
        notificationManager.cancelAll();
        unregisterReceiver(broadcastReceiver);


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void background() throws IOException {
        Uri uri = Uri.parse(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY));
        Log.i("input1", "onActivityResult: " + uri);
        if (!uri.equals("")) {
            /*
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivity(galleryIntent);
  Bitmap myBitmap = BitmapFactory.decodeFile(uri.toString());
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            Log.i("bitamp", "background: "+bitmap);
             */


            //relativeLayout.setImageBitmap(myBitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                Log.i("Path", "onActivityResult: " + uri);
                Drawable bg = null;
                InputStream inputStream = null;
                try {
                    getApplicationContext().getContentResolver().takePersistableUriPermission(Uri.parse(Uri.decode(uri.toString())), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    inputStream = getContentResolver().openInputStream(uri);
                    Log.i("inputS", "onActivityResult: " + inputStream);
                    bg = Drawable.createFromStream(inputStream, uri.toString());

                } catch (FileNotFoundException e) {
                    relativeLayout.setBackgroundResource(R.drawable.n);
                }
                relativeLayout.setBackground(bg);

            }
            //relativeLayout.setBackground(bg);
        }


        //   }


        //  this.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    private void buttonColor() {
        if (!saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY).equals("")) {
            drawables[0].setColor(Color.parseColor(saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY)));
            drawables[1].setColor(Color.parseColor(saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY)));
            drawables[2].setColor(Color.parseColor(saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY)));
            ;
        }
    }

    @Override
    public void pushFoto(Drawable drawable) {

    }


    @Override
    public void pullFoto(RelativeLayout linearLayout, Context context) {
        /*
        Fierbase fierbase=new Fierbase();
        fierbase.loadImage(linearLayout,context);
        linearLayout.setImageBitmap(BitmapFactory.decodeFile( saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)));

         */

        if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n") || saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
            Log.i("draw1", "pullFoto: ");
            if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n")) {
                Log.i("draw2", "pullFoto: ");
                linearLayout.setBackground(getDrawable(R.drawable.n));
            } else if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
                Log.i("draw3", "pullFoto: ");
                linearLayout.setBackground(getDrawable(R.drawable.app));
            }
        } else if (!saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n") || saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
            Drawable drawable = BitmapDrawable.createFromPath(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY));
            if (drawable == null) {
                relativeLayout.setBackground(getDrawable(R.drawable.n));

            } else {
                linearLayout.setBackground(drawable);
            }
        }


    }

    public void video(View view) {
        Intent intent = new Intent(this, Video.class);

        startActivity(intent);



    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (isMusicActive) {
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
    }

    private void audioManger() {
        try {
            mAudioManager = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        }catch (IllegalStateException e){
            Log.i("exs", "audioManger: "+e.toString());
        }

    }

}






