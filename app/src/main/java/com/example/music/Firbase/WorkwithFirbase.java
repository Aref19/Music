package com.example.music.Firbase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.storage.UploadTask;

import java.util.List;

public interface WorkwithFirbase {
    void  pushAudio(UploadTask.TaskSnapshot snapshot,Context context);
    void pullFoto(RelativeLayout linearLayout, Context context);
    void cutchAduio(String songs,Context context);
}
