package com.example.affix.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;*/
import com.example.affix.model.UserModel;
import com.google.firebase.firestore.auth.User;

public class AndroidUtil {

    public static  void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getEmail());
        //intent.putExtra("fcmToken",model.getFcmToken());

    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setEmail(intent.getStringExtra("email"));
        //userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }

   /* public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }*/
}