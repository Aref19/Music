package com.example.music.Notifaction;

import android.content.Context;
import android.util.Log;

public class Zwisehen implements Malsehen  {
    private static Malsehen malsehen;
    int local;
    int server;
    Notification notification;
    Context context;

    public  Zwisehen( Context context){
        this.context=context;
    }


    @Override
    public boolean news(int erste, int zweite) {
        Log.i("longM", "zwishen: "+"zweite"+zweite+"erste"+erste);
        if(erste==zweite){

        }else{
            notification=new Notification(context);
            notification.creatchanel();

            Log.i("longM", "zwishen: "+"zweite"+zweite+"erste"+erste);

        }

        return false;
    }
}
