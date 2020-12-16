package com.example.music.HauptMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.music.HauptMain.MusicNavie;
import com.example.music.R;
import com.example.music.Video.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        linearLayout=Main.this.findViewById(R.id.laufendemusic);
        BottomNavigationView bottomNavigationView=findViewById(R.id.navibut);
        bottomNavigationView.setOnNavigationItemSelectedListener(nav);
       getSupportFragmentManager().beginTransaction().replace(R.id.fram,new MusicNavie(linearLayout)).commit();
        //   getSupportFragmentManager().beginTransaction().replace(R.id.fram,new SelectViewFragement()).commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener nav=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.music:
                    fragment = new MusicNavie(linearLayout);
                    Log.i("frag", "onNavigationItemReselected: " + "fra");
                    break;
                case R.id.video:
                    fragment = new VideoFragment();
                    break;
                case R.id.selectview:
                    fragment=new SelectViewFragement();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fram, fragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Main.super.onBackPressed();
                    }
                }).create().show();
    }
    public void museiclauf(){

        this.linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"stop",Toast.LENGTH_LONG).show();
    }
}