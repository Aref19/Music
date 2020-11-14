package com.example.music.Firbase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Fierbase {
    public void imageStroge(Drawable drawable) {
        FirebaseStorage storage =FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference().child("imagesbackground").child("user");
        // Get the data from an ImageView as bytes
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("sucss", "onFailure: " + "sucss"+exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

            }
        });

    }
    public void loadImage(final RelativeLayout linearLayout) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("imagesbackground").child("user");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String image = uri.toString();
                Bitmap bitmap= BitmapFactory.decodeFile(image);
                //Canvas canvas=new Canvas(bitmap);
                Drawable drawable=new BitmapDrawable(bitmap);
                    linearLayout.setBackground(drawable);
                Log.i("image1", "onSuccess: " + image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("image2", "onSuccess: " + e.toString());
            }
        });
    }
    public void loadImage(final RelativeLayout linearLayout, final Context context) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("imagesbackground").child("user");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable  drawable=new BitmapDrawable(bitmap);
                        linearLayout.setBackground(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
                // Drawable drawable=new BitmapDrawable(bitmap);
                    /*
                    inputStream = getContentResolver().openInputStream(uri);
                    Log.i("inputS", "onActivityResult: " + inputStream);
                    bg = Drawable.createFromStream(inputStream, uri.toString());

                     */
                //  linearLayout.setBackground(drawable);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("image2", "onSuccess: " + e.toString());
            }
        });
    }

}
