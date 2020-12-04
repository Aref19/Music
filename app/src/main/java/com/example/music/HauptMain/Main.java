package com.example.music.HauptMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.music.HauptMain.MusicNavie;
import com.example.music.R;
import com.example.music.Video.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        BottomNavigationView bottomNavigationView=findViewById(R.id.navibut);
        bottomNavigationView.setOnNavigationItemReselectedListener(nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fram,new MusicNavie()).commit();
    }
    private  BottomNavigationView.OnNavigationItemReselectedListener nav=new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()){
                case R.id.music:
                    fragment=new MusicNavie();
                    Log.i("frag", "onNavigationItemReselected: "+"fra");
                    break;
                case R.id.video:
                    fragment=new VideoFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fram,fragment).commit();
        }
    };
}