package com.example.music.Video;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.R;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Video extends AppCompatActivity implements WorkwithFirbase {
    VideoView videoView;
    VideoModel videoModel;
    ArrayList<VideoModel> videoModels;
    MovieAdpter movieAdpter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    SaveInfoUserselect saveInfoUserselect;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videorecley);

        videoView = findViewById(R.id.videoView2);
        videoModels = new ArrayList<>();
        movieAdpter = new MovieAdpter();
        recyclerView = findViewById(R.id.rec);
        layoutManager = new LinearLayoutManager(this);
        relativeLayout = findViewById(R.id.videolayout);
        saveInfoUserselect = SaveInfoUserselect.getContext(this);


        // relativeLayout.setBackground(BitmapDrawable.createFromPath(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)));
        Toast.makeText(this,"das",Toast.LENGTH_LONG).show();
        pullFoto(relativeLayout, this);
        catchVideo();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void catchVideo() {
        Uri path = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] file = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        String s = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = getApplication().getContentResolver().query(path, file, null, null, null);
        int columindex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        Log.i("video1", "catchVideo: " + columindex);
        cursor.moveToFirst();
        cursor.getString(columindex);
        Log.i("video2", "catchVideo: " + cursor.getString(columindex));
        if (cursor.moveToNext()) {
            do {
                videoModel = new VideoModel();
                Log.i("video3", "catchVideo: " + cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                videoModel.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
                videoModel.setPathVideo(cursor.getString(columindex));
                videoModels.add(videoModel);

            } while (cursor.moveToNext());
            //    videoView.setVideoURI(Uri.parse(videoModels.get(3).getPathVideo()));
            //  Log.i("video4", "catchVideo: "+videoModels.get(1).getPathVideo());
            //  movieAdpter.setArraylist(videoModels,this);
            // recyclerView.setHasFixedSize(true);
            //recyclerView.setLayoutManager(layoutManager);
            //recyclerView.setAdapter(movieAdpter);

            Task task = new Task(videoModels, this, layoutManager);
            task.execute();


            movieAdpter.setonitem(new MovieAdpter.Onitemclic() {
                @Override
                public void onitemclic(int postion) {
                    vidershower(postion);
                }
            });
            MediaController mediaController = new MediaController(this);
            //videoView.setMediaController(mediaController);
            //mediaController.setAnchorView(videoView);
        }

    }

    private void vidershower(int pos) {
        Intent intent = new Intent(this, ViewshowerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("path", videoModels.get(pos).getPathVideo());
        intent.putExtras(bundle);
        startActivity(intent);


    }




    @Override
    public void pushAudio(UploadTask.TaskSnapshot snapshot, Context context) {

    }


    @Override
    public void pullFoto(RelativeLayout linearLayout, Context context) {
        Toast.makeText(context,"das",Toast.LENGTH_LONG).show();
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
                Log.i("draw4", "pullFoto: ");

            } else {
                linearLayout.setBackground(drawable);
                Log.i("lesh", "pullFoto: "+drawable);
            }
        }


    }

    @Override
    public void cutchAduio(String songs, Context context) {

    }

    private class Task extends AsyncTask<String, String, String> {
        ArrayList<VideoModel> videoModels;
        MovieAdpter movieAdpter;
        Context context;
        RecyclerView.LayoutManager layoutManager;

        public Task(ArrayList<VideoModel> videoModels, Context context, RecyclerView.LayoutManager layoutManager) {
            this.videoModels = videoModels;
            this.movieAdpter = new MovieAdpter();
            this.context = context;

            this.layoutManager = layoutManager;
        }

        @Override
        protected String doInBackground(String... strings) {

            movieAdpter.setArraylist(videoModels, context);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(movieAdpter);
            /*
             */


            movieAdpter.setonitem(new MovieAdpter.Onitemclic() {
                @Override
                public void onitemclic(int postion) {
                    vidershower(postion);
                }
            });
            return null;
        }
    }

}