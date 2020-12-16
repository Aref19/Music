package com.example.music.Video;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;

import java.util.ArrayList;

public class VideoFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    VideoModel videoModel;
    ArrayList<VideoModel>videoModels;
    RecyclerView.LayoutManager layoutManager;
    MovieAdpter movieAdpter;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          view=inflater.inflate(R.layout.videofragment,container,false);
         recyclerView=view.findViewById(R.id.rec);
         videoModels=new ArrayList<>();
        layoutManager = new LinearLayoutManager(view.getContext());
        movieAdpter = new MovieAdpter();
         fullRc();

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
