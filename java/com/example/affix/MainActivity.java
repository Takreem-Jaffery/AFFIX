package com.example.affix;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.NavigationUI;
import com.example.affix.R;
import com.example.affix.databinding.ActivityMainBinding;

import com.example.affix.Fragment.ChatFragment;
import com.example.affix.Fragment.ExploreFragment;
import com.example.affix.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item->{

            if (item.getItemId() == R.id.chatFragment) {
                replaceFragment(new ChatFragment());
            }
            else if (item.getItemId() == R.id.exploreFragment){
                replaceFragment(new ExploreFragment());
            }
            else if(item.getItemId() == R.id.profileFragment){
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}