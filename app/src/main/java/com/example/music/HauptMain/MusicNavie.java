package com.example.music.HauptMain;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.Notifaction.Notification;
import com.example.music.NotificationServiceAction.onClearFromRecentServic;
import com.example.music.R;
import com.example.music.Share.Uppop;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;


public class MusicNavie extends Fragment implements Playble, WorkwithFirbase,AudioManager.OnAudioFocusChangeListener {
    ListView songView;

    ImageButton next , last;
    ImageView stop;
    View view;
    ArrayList<Songinfo> songinfos;
    Cursor crusor;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    int sitution, lastint, nextint, musicpos;
    boolean isselect,sorstop,isplaing;
    Notification notification;
    Runnable runnable;
    Handler handler;
    SaveInfoUserselect saveInfoUserselect;
    int prmistion = 1;
    RelativeLayout relativeLayout;
    GradientDrawable[] drawables;
     AudioManager AudioManager;
     LinearLayout linearLayout;

    public MusicNavie(LinearLayout linearLayout) {
        this.linearLayout=linearLayout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //if scheilfe
        view = inflater.inflate(R.layout.musicfragment, container, false);
        View view2 = inflater.inflate(R.layout.main, container, false);
        songView = view.findViewById(R.id.liedlist);
        last = view.findViewById(R.id.start);
        stop = view.findViewById(R.id.pause);
        next = view.findViewById(R.id.stop);
        songinfos = new ArrayList<>();
        longdruck(songView);
        premtion();
        audioManger();
        seekBar = view.findViewById(R.id.laufm);
        view.getContext().registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        view.getContext().startService(new Intent(getActivity().getBaseContext(), onClearFromRecentServic.class));
        mediaPlayer = new MediaPlayer();
        notification = new Notification(view.getContext());
        notification.creatchanel();
        handler = new Handler();
        saveInfoUserselect=SaveInfoUserselect.getContext(view.getContext());
        relativeLayout=view.findViewById(R.id.relative);
        drawables = new GradientDrawable[3];
        drawables[0] = (GradientDrawable) stop.getBackground().mutate();
        drawables[1] = (GradientDrawable) next.getBackground().mutate();
        drawables[2] = (GradientDrawable) last.getBackground().mutate();
        sekk(seekBar);
        onClick();
        current();
        buttonColor();
        if (!saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("")) {
            pullFoto(relativeLayout, view.getContext());
        }

        return view;
    }

    private void loadSong() {
        Log.i("namesong", "getallsong: " +"");
        Toast.makeText(getActivity(),"name",Toast.LENGTH_LONG).show();
        songinfos = new ArrayList<>();
        Uri allsong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Log.i("namesong", "getallsong: " +"");
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        crusor = getActivity().managedQuery(allsong, null, selection, null, null);

        if (crusor != null) {
            if (crusor.moveToNext()) {
                do {
                    String name = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String fullp = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String alpum = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String artist = crusor.getString(crusor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    Log.i("namesong", "getallsong: " + name.toString());
                    if (!name.contains("WA")) {


                        songinfos.add(new Songinfo(fullp, name, alpum, artist));

                    }

                } while (crusor.moveToNext());
                //       Toast.makeText(this,"sdaaaaaaa"+songinfos.get(0).getSong_name(),Toast.LENGTH_LONG).show();
                Adapter adabter = new Adapter(songinfos, view.getContext());


                songView.setAdapter(adabter);
                list(songView, songinfos);

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
                    nextint = position;
                    lastint = position;
                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(songinfos.get(position).getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    stop.setImageResource(R.drawable.start);
                    isselect = true;
                    seekBar.setMax(mediaPlayer.getDuration());
                    linearLayout.setVisibility(View.VISIBLE);
                    current();
                    notification.greatNafi(0, songinfos.get(position), R.drawable.ic_baseline_pause_circle_filled_24, position, songinfos.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onTaskplay() {
        mediaPlayer.start();
        stop.setImageResource(R.drawable.start);
        onTaskplay();
        notification.creatchanel();
        notification.greatNafi(0, songinfos.get(sitution), R.drawable.start, sitution, songinfos.size() - 1);
        isselect = true;
        current();

    }

    @Override
    public void onTaskprovis() {
        lastint--;
        nextint--;
        isselect = true;
        //  audioManger();

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

        } catch (IOException e) {
            e.printStackTrace();
        }
        stop.setImageResource(R.drawable.stop);
        stop.setImageResource(R.drawable.start);


        notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_pause_circle_filled_24, sitution, songinfos.size() - 1);

    }

    @Override
    public void onTaskNext() {
        nextint++;
        lastint++;
        isselect = true;
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        stop.setImageResource(R.drawable.stop);
        stop.setImageResource(R.drawable.start);
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
            isselect = true;
            current();
            mediaPlayer.start();
            stop.setImageResource(R.drawable.start);
            sorstop = false;
            Log.i("hier", "onTaskpause: " + "hier1");
            notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_pause_circle_filled_24, sitution, songinfos.size() - 1);
        } else if (isselect) {
            isselect = false;
            stop.setImageResource(R.drawable.stop);
            notification.greatNafi(0, songinfos.get(sitution), R.drawable.ic_baseline_play_circle_outline_24, sitution, songinfos.size() - 1);
            Log.i("hier", "onTaskpause: " + "hier2");
            sorstop = true;

        } else {
            Snackbar.make(songView, "dies hat kein mehr foto foto", Snackbar.LENGTH_LONG).show();

        }


        isplaing = false;


    }

    private void onClick() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskNext();
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskprovis();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskpause();
            }
        });
    }

    private void sekk(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicpos = progress;
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
                mediaPlayer.seekTo(musicpos);


                ;


            }
        });
    }

    private void media() {
        try {
            sitution += 1;
            if (sitution >= songinfos.size()) {
                sitution = 0;
            }
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(songinfos.get(sitution).getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Log.i("warum", "onTaskpause: " + "von hier media");
            seekBar.setMax(mediaPlayer.getDuration());

        } catch (IOException e) {
            e.printStackTrace();
        }


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

    @Override
    public void pushAudio(UploadTask.TaskSnapshot snapshot, Context context) {

    }

    @Override
    public void pullFoto(RelativeLayout linearLayout, Context context) {
        if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n") || saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
            Log.i("draw1", "pullFoto: ");
            if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n")) {
                Log.i("draw2", "pullFoto: ");
                linearLayout.setBackground(view.getContext().getDrawable(R.drawable.n));
            } else if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
                Log.i("draw3", "pullFoto: ");
                linearLayout.setBackground(view.getContext().getDrawable(R.drawable.app));
            }
        } else if (!saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n") || saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
            Drawable drawable = BitmapDrawable.createFromPath(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY));
            if (drawable == null) {
                relativeLayout.setBackground(view.getContext().getDrawable(R.drawable.n));

            } else {
                linearLayout.setBackground(drawable);
            }
        }
    }

    @Override
    public void cutchAduio(String songs, Context context) {

    }
    private void buttonColor() {
        if (!saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY).equals("")) {
            drawables[0].setColor(Color.parseColor(saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY)));
            drawables[1].setColor(Color.parseColor(saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY)));
            drawables[2].setColor(Color.parseColor(saveInfoUserselect.loadColorBu(SaveInfoUserselect.USER_ColorB_KEY)));
            ;
        }
    }
    private void longdruck(ListView listView) {
        Log.i("long", "longdruck: " + "rstrzt");


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Fierbase fierbase=new Fierbase();
                //fierbase.imageStroge(Uri.parse(songinfos.get(position).getPath()),Music.this);
                //checklong=false;
                /*
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(songinfos.get(position).getPath()));
                sendIntent.setType("audio/*");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

                 */
                FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                Uppop uppop = new Uppop(view.getContext(), songinfos, position);
                uppop.show(fragmentManager, null);


                return true;
            }
        });

    }
    private void premtion() {
        boolean sta;
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.MANAGE_DOCUMENTS) == PackageManager.PERMISSION_GRANTED) {
            loadSong();
            Log.i("prem", "premtion: "+"von hier");

        } else {
            requstpremstion();
        }

    }

    private void requstpremstion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.MANAGE_DOCUMENTS)) {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("premision")
                    .setMessage("dsa")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listfullen("ok");
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, prmistion);
                            Toast.makeText(view.getContext(), "pre", Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, prmistion);

            Log.i("prem", "listfullen: "+"ja");
        }
    }

    private void listfullen(String s) {
        Log.i("prem", "listfullen: "+s);
        loadSong();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.i("req", "onRequestPermissionsResult: "+requestCode);
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listfullen("ok");
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
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
    public void audioManger() {
        try {
            AudioManager am = (AudioManager) view.getContext().getSystemService(Context.AUDIO_SERVICE);
            am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            Log.i("exsfrom", "audioManger: " );
        } catch (IllegalStateException e) {
            Log.i("exsfrom", "audioManger: " + e.toString());
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(view.getContext(), "onstop", Toast.LENGTH_SHORT).show();
        // hier muss die miderplayer geschpeicher werden
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (isselect) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ) {
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
}

