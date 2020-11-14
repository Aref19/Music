package com.example.music.DatenBank;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ConvertData {
    public static byte[] imageViewToBtamp(ImageView imageView){
        byte[]bytes1 = new byte[0];
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        byte[]bytes=byteArrayOutputStream.toByteArray();
        while (bytes.length > 500000) {
            bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
          bytes1=byteArrayOutputStream.toByteArray();
        }
        



        return bytes1;
    }
    public  static Bitmap ByteArraytoBitmap(byte [] vonDatenBank){
        return BitmapFactory.decodeByteArray(vonDatenBank,0,vonDatenBank.length);
    }
    public  static byte[] ByteArraytoklein(byte [] vonDatenBank){
        while (vonDatenBank.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(vonDatenBank, 0, vonDatenBank.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 0, stream);
            Log.i("wie", "ByteArraytoklein: "+"ds");
          vonDatenBank = stream.toByteArray();
        }
        return vonDatenBank;
    }
}
