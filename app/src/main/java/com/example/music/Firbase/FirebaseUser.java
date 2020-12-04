package com.example.music.Firbase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FirebaseUser {
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    public void fotoUser(Uri url, final Context context, ImageView imageView){
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReference().child(firebaseAuth.getUid()).child("userphoto/").child("photo");
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Process");
                builder.setMessage("was nict gekalppt");


                builder.show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Process");
                builder.setMessage("Sucsses");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                // ...

            }
        });

    }
    public void loadUserImage(final ImageView imageView, final Context context){
        firebaseAuth=FirebaseAuth.getInstance();
      FirebaseStorage storage=FirebaseStorage.getInstance();
      StorageReference storageReference=storage.getReference(firebaseAuth.getUid()).child("userphoto/").child("photo");
      storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
          @Override
          public void onSuccess(Uri uri) {
              if(!uri.equals("")){
                  Log.i("image", "onSuccess: "+uri);
               Picasso.with(context).load(uri).into(imageView);
               /*
               new Target() {
                     @Override
                     public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                         bitmap.recycle();
                         imageView.setImageBitmap(bitmap);
                     }

                     @Override
                     public void onBitmapFailed(Drawable errorDrawable) {

                     }

                     @Override
                     public void onPrepareLoad(Drawable placeHolderDrawable) {
                         imageView.setImageDrawable(placeHolderDrawable);
                     }
                 });

                */

              }else {
                  Toast.makeText(context,"you dont have foto",Toast.LENGTH_LONG).show();
              }

          }
      });

    }
}
