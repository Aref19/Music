package com.example.music.Firbase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public interface WorkwithFirbase {
    void pushFoto(Drawable drawable);
    void pullFoto(RelativeLayout linearLayout, Context context);
}
