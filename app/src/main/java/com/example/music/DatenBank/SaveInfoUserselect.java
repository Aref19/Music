package com.example.music.DatenBank;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveInfoUserselect {
  public static final String SHARED_PREFS_KEY="com.android.application.shared_prefs";
    public static final String USER_ColorT_KEY = "colorT_key";
    public static final String USER_ColorB_KEY = "colorB_key";
    public static final String USER_Image_KEY = "Image_key";
    public static final String USER_Int_Spinnerindex = "SpinnerIndex_key";
    public static final String USER_Intbutton_Spinnerindex = "SpinnerIndexbutton_key";
    public static final String  User_email="User_Email";

   private Context context;
    private  SaveInfoUserselect(Context context){
        this.context=context;
    }

    public static SaveInfoUserselect getContext(Context context) {
        return new SaveInfoUserselect(context);
    }
    public void saveColorText(String keyT,String valueC){
        SharedPreferences.Editor editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE).edit();
       editor.putString(keyT,valueC);
        editor.apply();
    }
    public void saveColorButton(String keyT,String valueB){
        SharedPreferences.Editor editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE).edit();
        editor.putString(keyT,valueB);
        editor.apply();
    }
    public void saveURIImage(String keyImage,String valueImage){
        SharedPreferences.Editor editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE).edit();
        editor.putString(keyImage,valueImage);
        editor.apply();
    }

    public String loadColorTe(String key){
        SharedPreferences editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
         return  editor.getString(key,"");
    }
    public String loadColorBu(String key){
        SharedPreferences editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
        return  editor.getString(key,"");
    }
    public String loadImage(String key){
        SharedPreferences editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
        return  editor.getString(key,"");
    }
    public void savespinnerSelection(String keyImage,int valueImage){
        SharedPreferences.Editor editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE).edit();
        editor.putInt(keyImage,valueImage);
        editor.apply();
    }
    public int loadspinnerSelection(String keyImage){
        SharedPreferences editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
       return editor.getInt(keyImage,0);

    }
    public void savesbuttonSelection(String keyImage,int valueImage){
        SharedPreferences.Editor editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE).edit();
        editor.putInt(keyImage,valueImage);
        editor.apply();
    }
    public int loadbuttonSelection(String keyImage){
        SharedPreferences editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
        return editor.getInt(keyImage,0);

    }
    public void  saveUseremail(String user,String user_email){
        SharedPreferences.Editor editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE).edit();
        editor.putString(user,user_email);
        editor.apply();
    }
    public String  getUseremail(String user){
        SharedPreferences editor=context.getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
        return editor.getString(user,"");

    }



}
