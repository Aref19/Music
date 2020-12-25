package com.example.music.Video;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.R;

import java.util.ArrayList;


public class MovieAdpter extends RecyclerView.Adapter<MovieAdpter.MiovieViewHolder> {
    private ArrayList<VideoModel> videoModels = new ArrayList<>();
    Context context;
    SaveInfoUserselect saveInfoUserselect;
    private Onitemclic onitemclic;
    public interface Onitemclic {
        void onitemclic(int postion);
    }

    public void setonitem(Onitemclic lister) {
        onitemclic = lister;
    }

    @NonNull
    @Override
    public MiovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MiovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemvideoview, parent, false), onitemclic);
    }

    @Override
    public void onBindViewHolder(@NonNull MiovieViewHolder holder, int position) {
        holder.videoView3.setVideoURI(Uri.parse(videoModels.get(position).getPathVideo()));
        holder.textView.setText(videoModels.get(position).getName());
        holder.videoView3.seekTo(10);
        //holder.videoView3.start();


    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public class MiovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        VideoView videoView1, videoView2, videoView3;
        TextView textView, textView1;

        public MiovieViewHolder(@NonNull View itemView, final Onitemclic onitemclic) {
            super(itemView);
            textView = itemView.findViewById(R.id.videoname);
            textView1 = itemView.findViewById(R.id.pup);
            if (!saveInfoUserselect.loadColorTe(SaveInfoUserselect.USER_ColorT_KEY).equals("")) {
                textView.setTextColor(Color.parseColor(saveInfoUserselect.loadColorTe(SaveInfoUserselect.USER_ColorT_KEY)));
            }


            videoView3 = itemView.findViewById(R.id.videoView3);
            videoView3.setOnClickListener(this);
            textView1.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onitemclic != null) {
                        int p = getAdapterPosition();
                        if (p != RecyclerView.NO_POSITION) {
                            onitemclic.onitemclic(p);
                        }
                    }

                }
            });

        }

        @Override
        public void onClick(View v) {
            Log.i("idnew", "onClick: " + v.getId() + "v :" + R.id.videoView3);
            if (v.getId() == R.id.videoView3) {
                VideoView videoView3 = (VideoView) v;
                videoView3.start();
                videoView3.setSoundEffectsEnabled(false);
            } else if (v.getId() == R.id.pup) {
                creatpopup(v);

            }

        }

        private void creatpopup(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.person_daten);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();



        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.share:

                  share();
                    break;
                case R.id.delete:


                    break;
                default:

            }
            return false;
        }

        private void share() {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(videoModels.get(getAdapterPosition()).getPathVideo()));
            sendIntent.setType("audio/*");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
           context.startActivity(shareIntent);

        }

    }

    public void setArraylist(ArrayList<VideoModel> videmodels, Context context) {

        this.videoModels = videmodels;
        this.context = context;
        saveInfoUserselect = SaveInfoUserselect.getContext(context);
        notifyDataSetChanged();
    }

    public ArrayList<VideoModel> get() {
        return this.videoModels;
    }

}
