package com.example.music.Firbase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.UploadTask;

class Task extends AsyncTask<UploadTask.TaskSnapshot,String,String> {
ProgressDialog progressDialog;
Context context;

  public Task(Context context){
      this.context=context;
  }

    @Override
    protected String doInBackground(UploadTask.TaskSnapshot... taskSnapshots) {
      try {
          double progress = (100.0 * taskSnapshots[0].getBytesTransferred()) / taskSnapshots[0].getTotalByteCount();
          if(progress==100){
              return "suc";
          }
      }catch (Exception e){
          return "er";
      }
      return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =ProgressDialog.show(context,"plase Wait ","Sending",true,false);
    }



    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
      progressDialog.dismiss();
        if(o.equals("suc")){
            AlertDialog.Builder builder =new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle(Html.fromHtml("<font color='#509324'>Sucsess</font>"));
            builder.setMessage("sucsess");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

        }else {
            Toast.makeText(context,"Somthin "+o,Toast.LENGTH_LONG).show();
            Log.i("Gmail", "onPostExecute: "+o);
        }

    }
}
