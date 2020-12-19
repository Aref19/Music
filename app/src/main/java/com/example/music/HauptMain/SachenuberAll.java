package com.example.music.HauptMain;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

public class SachenuberAll extends Activity implements AudioManager.OnAudioFocusChangeListener {
    public static Button next;
    public static ImageButton  startb;
    public static SeekBar  lauf;
    public  static LinearLayout linearLayoutm;
    public static MediaPlayer mediaPlayer;
    public static boolean status=false;
    public static int pos;
    AudioManager mAudioManager;

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            mediaPlayer.pause();
            Log.i("onfuc", "onAudioFocusChange: " + "1");
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mediaPlayer.start();
            Log.i("onfuc", "onAudioFocusChange: " + "2");
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            mediaPlayer.pause();
            Log.i("onfuc", "onAudioFocusChange: " + "3");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public  void audioManger(Context context) {
        try {

            mAudioManager = (AudioManager) this.getSystemService(this.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            Log.i("exsfrom", "audioManger: " );
        } catch (IllegalStateException e) {
            Log.i("exsfrom", "audioManger: " + e.toString());
        }

    }
}
