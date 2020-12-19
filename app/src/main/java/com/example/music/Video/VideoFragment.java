package com.example.music.Video;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.HauptMain.SachenuberAll;
import com.example.music.R;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements WorkwithFirbase{
    View view;
    AudioManager mAudioManager;
    RecyclerView recyclerView;
    VideoModel videoModel;
    ArrayList<VideoModel>videoModels;
    RecyclerView.LayoutManager layoutManager;
    MovieAdpter movieAdpter;
    SaveInfoUserselect saveInfoUserselect;
    RelativeLayout relativeLayout;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          view=inflater.inflate(R.layout.videofragment,container,false);
         recyclerView=view.findViewById(R.id.rec);
         relativeLayout=view.findViewById(R.id.videolayoutfr);
         videoModels=new ArrayList<>();
        layoutManager = new LinearLayoutManager(view.getContext());
        movieAdpter = new MovieAdpter();
         fullRc();
         saveInfoUserselect=SaveInfoUserselect.getContext(view.getContext());
         pullFoto(relativeLayout,view.getContext());



        return view;
    }
    private void fullRc(){

        Uri path = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] file = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        String s = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = view.getContext().getContentResolver().query(path, file, null, null, null);
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

            Task task = new Task(videoModels, view.getContext(), layoutManager);
            task.execute();

            movieAdpter.setonitem(new MovieAdpter.Onitemclic() {
                @Override
                public void onitemclic(int postion) {
                    vidershower(postion);
                }
            });
            MediaController mediaController = new MediaController(view.getContext());

    }


}

    private void vidershower(int postion) {
        Intent intent = new Intent(view.getContext(), ViewshowerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("path", videoModels.get(postion).getPathVideo());
        intent.putExtras(bundle);
        startActivity(intent);
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
                linearLayout.setBackground(getActivity().getDrawable(R.drawable.n));
            } else if (saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
                Log.i("draw3", "pullFoto: ");
                linearLayout.setBackground(getActivity().getDrawable(R.drawable.app));
            }
        } else if (!saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.n") || saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY).equals("R.drawable.app")) {
            Drawable drawable = BitmapDrawable.createFromPath(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY));
            if (drawable == null) {
               linearLayout.setBackground(getActivity().getDrawable(R.drawable.n));
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






    private class Task extends AsyncTask<String, String, String> implements AudioManager.OnAudioFocusChangeListener {
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
            movieAdpter.setonitem(new MovieAdpter.Onitemclic() {
                @Override
                public void onitemclic(int postion) {
                    vidershower(postion);

                }

            });
            SachenuberAll sachenuberAll=new SachenuberAll();
            sachenuberAll.audioManger(getActivity(),this);
            return null;
        }
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                SachenuberAll.mediaPlayer.pause();
                Log.i("onfuc", "onAudioFocusChange: " + "1");
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                SachenuberAll.mediaPlayer.start();

                Log.i("onfuc", "onAudioFocusChange: " + "2");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                Log.i("onfuc", "onAudioFocusChange: " + "3");
            }
        }
    }


}
