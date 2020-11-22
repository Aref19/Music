package com.example.music.AccountUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.music.DatenBank.LocalDatenBank.DataBase;
import com.example.music.DatenBank.LocalDatenBank.SaveThings;
import com.example.music.Firbase.Fierbase;
import com.example.music.Firbase.WorkwithFirbase;
import com.example.music.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcountUser extends AppCompatActivity implements WorkwithFirbase {
     ListView listView;
     SeekBar seekBar;
     ArrayList<String > songslocal,uris;
     ArrayAdapter<String> stringArrayAdapter;
     MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_user);
        listView=findViewById(R.id.liedlistonline);
        seekBar=findViewById(R.id.laufmonline);
        DataBase dataBase=DataBase.getInstance(this);
        mediaPlayer=new MediaPlayer();
        songslocal=new ArrayList<>();
        uris=new ArrayList<>();
        for (int i=0;i<dataBase.daoData().getlist().size();i++){
            songslocal.add(dataBase.daoData().getlist().get(i).getNamesong());
        }

       getSongsStorge(dataBase.daoData().getlist(),this);
        stringArrayAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,songslocal);
              listView.setAdapter(stringArrayAdapter);
        getallsong();
    }

    private void getallsong() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("uros", "onItemClick: "+uris.get(0));

                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(uris.get(position));
                    Log.i("uri1", "onItemClick: "+uris.get(position));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("exs", "onItemClick: "+e.getMessage());
                }


            }
        });

    }

    @Override
    public void pushAudio(UploadTask.TaskSnapshot snapshot, Context context) {

    }

    @Override
    public void pullFoto(RelativeLayout linearLayout, Context context) {

    }

    @Override
    public void cutchAduio(String songs,Context context) {
        DataBase dataBase=DataBase.getInstance(getApplication());

        Log.i("nameso", "cutchAduio: "+songs);
         if(context instanceof AcountUser){
             ((AcountUser) context).songslocal.add(songs);
             Log.i("list", "cutchAduio: "+((AcountUser) context).songslocal.size());
             if(((AcountUser) context).songslocal.size()==dataBase.daoData().getlist().size()){

                 ((AcountUser) context). listView.setAdapter( ((AcountUser) context).stringArrayAdapter);

             }

    }
    }
    public void getSongsStorge(List<SaveThings> name, final Context context) {
        int i=0;


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final ArrayList<String> strings=new ArrayList<>();
        do {


            Log.i("songsf", "onSuccess: "+i);
            StorageReference storageR = storage.getReference().child(firebaseAuth.getUid()).child(name.get(i).getNamesong().trim());
            storageR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
               uris.add(uri.toString());
                    Log.i("songsur", "onSuccess: "+uri.toString());
                    AcountUser acountUser = new AcountUser();
                    if(uri!=null){
                        Log.i("songsur", "onSuccess: "+uri.toString());

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("songf", "onFailure: "+e.toString());
                }
            });
            i++;
        }while (i<name.size());

    }


}
