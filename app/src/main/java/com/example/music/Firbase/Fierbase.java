package com.example.music.Firbase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.music.DatenBank.SaveInfoUserselect;
import com.example.music.HauptMain.Music;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Fierbase {
    FirebaseAuth firebaseAuth;
    SaveInfoUserselect saveInfoUserselect;
    Intent intent;
    public void imageStroge(Uri uri , final Context context) {
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseStorage storage =FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference().child("userAudio").child(firebaseAuth.getUid());
        // Get the data from an ImageView as bytes
       final ProgressDialog builder;
        UploadTask uploadTask = storageRef.putFile(Uri.fromFile(new File(uri.toString())));
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull final UploadTask.TaskSnapshot snapshot) {
                 double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                ProgressDialog  builder =ProgressDialog.show(context,"plase Wait ",progress+"%",true,false);
                if(snapshot.){
                   builder.dismiss();
                }



                Log.i("progr", "onProgress: "+progress);


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
    public void signin(final String email, String pass, final Context context){
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email.trim(),pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
               try {
                   Toast.makeText(context, "successfully registered", Toast.LENGTH_LONG).show();
                    saveInfoUserselect=SaveInfoUserselect.getContext(context);
                    saveInfoUserselect.saveUseremail(SaveInfoUserselect.User_email,email);
                       }catch (Exception e){
                   Toast.makeText(context, "check your info "+e, Toast.LENGTH_LONG).show();
               }
                }

            }
        });


    }
    public void login(String email, String pass, final Context context){
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email.trim(),pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    intent=new Intent(context, Music.class);
                    context.startActivity(intent);
                }
            }
        });
    }

}
