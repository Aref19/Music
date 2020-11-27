package com.example.music.Notifaction;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.NotificationServiceAction.NotificationService;
import com.example.music.R;
import com.example.music.HauptMain.Songinfo;

public class Notification {
    private NotificationManager notificationManager;
    Context context;
    public static final String c = "chanel";
    public static final String actionplay = "actionplay";
    public static final String actionnext = "actionnext";
    public static final String Action = "Actionprevious";

   SaveInfoUserselect saveInfoUserselect;
    public Notification(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
          saveInfoUserselect=SaveInfoUserselect.getContext(context);
    }

    public void greatNafi(int id,Tarck  tarck) {
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat=new MediaSessionCompat(context,"tag");
        Bitmap icon= BitmapFactory.decodeResource(context.getResources(),tarck.getImage());
        android.app.Notification builder = new NotificationCompat.Builder(context, c)

                .setContentTitle(tarck.getTitel())
                .setContentText(tarck.getArtist())
                .setLargeIcon(BitmapFactory.decodeFile(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)))

                .setSmallIcon(R.drawable.app)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        //notificationManager.notify(id, builder);
       notificationManagerCompat.notify(1,builder);

        Log.i("na", "greatNafi: ja +" + id);

    }

    public  void creatchanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(c, "erste", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        creatcManger();
    }

    private void creatcManger() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
    }
}
