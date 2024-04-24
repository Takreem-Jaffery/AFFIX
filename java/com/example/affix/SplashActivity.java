package com.example.affix;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.affix.utils.FirebaseUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (FirebaseUtil.isLoggedIn()) {
                    startActivity((new Intent(SplashActivity.this, ContactsPageActivity.class)));

                } else {
                    startActivity((new Intent(SplashActivity.this, LogInActivity.class)));

                }
                finish();
            }

        },1000);

    }
}
