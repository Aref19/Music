package com.example.music.Video;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

public class MyVideoView extends VideoView {
    private int mVideoWidth;
    private int mVideoHeight;
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setVideoSize(int width,int height) {
        mVideoHeight=height;
        mVideoWidth=width;
        Log.i("bitte", "aspect ratio is correct: " +
                width+"/"+height+"="+
                mVideoWidth+"/"+mVideoHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                // Log.i("@@@", "image too tall, correcting");
                height = width * mVideoHeight / mVideoWidth;
                Log.i("bitte", "aspect ratio is correct: " +
                        width+"/"+height+"="+
                        mVideoWidth+"/"+mVideoHeight);
            } else if (mVideoWidth * height < width * mVideoHeight) {
                // Log.i("@@@", "image too wide, correcting");
                width = height * mVideoWidth / mVideoHeight;
                Log.i("bitte", "aspect ratio is correct: " +
                        width+"/"+height+"="+
                        mVideoWidth+"/"+mVideoHeight);
            } else {
                 Log.i("@@@", "aspect ratio is correct: " +
                 width+"/"+height+"="+
                 mVideoWidth+"/"+mVideoHeight);
            }
        }
        // Log.i("@@@", "setting size: " + width + 'x' + height);
        setMeasuredDimension(width, height);
    }


    }

