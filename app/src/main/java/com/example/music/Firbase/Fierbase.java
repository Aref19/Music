package com.example.music.Firbase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.music.AccountUser.Account;
import com.example.music.AccountUser.AcountUser;
import com.example.music.DatenBank.LocalDatenBank.SaveThings;
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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fierbase {

    FirebaseAuth firebaseAuth;
    SaveInfoUserselect saveInfoUserselect;
    Intent intent;
    Date date;


    public void imageStroge(Uri uri, final Context context, String name) {
        date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ss ");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference().child(firebaseAuth.getUid()).child(name);
        // Get the data from an ImageView as bytes
        final ProgressDialog builder;
        UploadTask uploadTask = storageRef.putFile(Uri.fromFile(new File(uri.toString())));
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull final UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage(progress + "%");
                if (progress == 100) {
                    progressDialog.dismiss();

                }

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                if (progress == 100) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setTitle(Html.fromHtml("<font color='#509324'>Sucsess</font>"));
                    builder.setMessage("sucsess");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

                }
            }
        });

    }

    public void loadImage(final RelativeLayout linearLayout) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("imagesbackground").child("user");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String image = uri.toString();
                Bitmap bitmap = BitmapFactory.decodeFile(image);
                //Canvas canvas=new Canvas(bitmap);
                Drawable drawable = new BitmapDrawable(bitmap);
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
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("imagesbackground").child("user");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable drawable = new BitmapDrawable(bitmap);
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

    public void signin(final String email, String pass, final Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email.trim(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    try {
                        Toast.makeText(context, "successfully registered", Toast.LENGTH_LONG).show();
                        saveInfoUserselect = SaveInfoUserselect.getContext(context);
                        saveInfoUserselect.saveUseremail(SaveInfoUserselect.User_email, email);
                    } catch (Exception e) {
                        Toast.makeText(context, "check your info " + e, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    public void login(String email, String pass, final Context context) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email.trim(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    intent = new Intent(context, Music.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void getSongsStorge(List<SaveThings> name, final Context context) {
        int i=0;


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final ArrayList<String> strings=new ArrayList<>();
            do {
            i++;
            Log.i("songsf", "onSuccess: "+name.get(i).getNamesong().trim());
                Log.i("songsf", "onSuccess: 0"+name.get(0).getNamesong().trim());
                Log.i("songsf", "onSuccess: 1"+name.get(1).getNamesong().trim());
                Log.i("songsf", "onSuccess: "+name.size());
                Log.i("songsf", "onSuccess: "+i);
            StorageReference storageR = storage.getReference().child(firebaseAuth.getUid()).child(name.get(i).getNamesong().trim());
            storageR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    strings.add(uri.toString());
                    Log.i("songsur", "onSuccess: "+uri.toString());
                    AcountUser acountUser = new AcountUser();
                    if(uri!=null){
                        Log.i("songsur", "onSuccess: "+uri.toString());
                        acountUser.cutchAduio(uri.toString(),context);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("songf", "onFailure: "+e.toString());
                }
            });
        }while (i!=name.size()-1);

    }


}

class MyTask extends AsyncTask<UploadTask.TaskSnapshot, String, String> {
    ProgressDialog progressDialog;
    Context context;
    UploadTask.TaskSnapshot taskSnapshot;

    public MyTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(UploadTask.TaskSnapshot... taskSnapshots) {
        double progress = (100.0 * taskSnapshots[0].getBytesTransferred()) / taskSnapshots[0].getTotalByteCount();
        if (progress == 100) {
            return "suc";
        }

        return "er";


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "plase Wait ", "Sending", true, false);
    }


    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        if (o.equals("suc")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle(Html.fromHtml("<font color='#509324'>Sucsess</font>"));
            builder.setMessage("sucsess");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

        } else {
            Toast.makeText(context, "Somthin " + o, Toast.LENGTH_LONG).show();
            Log.i("Gmail", "onPostExecute: " + o);
        }

    }

}

