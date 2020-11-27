package com.example.music.Notifaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public void greatNafi(int id, Songinfo tarck, int playbutton, int pos, int size) {
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat=new MediaSessionCompat(context,"tag");
        // Bitmap icon= BitmapFactory.decodeResource(context.getResources(),tarck.getImage());
        PendingIntent pendingIntent;
        int drw_privous;
        int drw_next ;
        if(pos==0){
            pendingIntent=null;
            drw_privous=0;
        }else {
            Intent intent=new Intent(context, NotificationService.class).setAction(Action);
            pendingIntent=PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
            drw_privous=R.drawable.ic_baseline_skip_previous_24;

        }
        Intent intent1=new Intent(context, NotificationService.class).setAction(actionplay);
        PendingIntent pendingIntentplay=PendingIntent.getBroadcast(context,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);


        PendingIntent pendingIntentnext ;
        if(pos==size){
            pendingIntentnext=null;
            drw_next=0;

        }else{
            Intent intentnext=new Intent(context, NotificationService.class).setAction(actionnext);
            pendingIntentnext=PendingIntent.getBroadcast(context,0,intentnext,PendingIntent.FLAG_CANCEL_CURRENT);
            drw_next=R.drawable.ic_baseline_skip_next_24;
        }

        android.app.Notification builder = new NotificationCompat.Builder(context, c)
                .setContentTitle(tarck.getSong_name())
                .setContentText(tarck.getAltist())
                .setLargeIcon(BitmapFactory.decodeFile(saveInfoUserselect.loadImage(SaveInfoUserselect.USER_Image_KEY)))
                .setSmallIcon(R.drawable.app)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)

                .addAction(drw_privous,"privous",pendingIntent)
                .addAction(playbutton,"play",pendingIntentplay)
                .addAction(drw_next,"next",pendingIntentnext)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2)

                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        Log.i("pla", "greatNafi: "+playbutton);
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
