package com.project.ocr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Untuk menyembunyikan appbar
        getSupportActionBar().hide();

        //tambah fragment
        AuthScreen authScreen = new AuthScreen();
        FragmentManager fm = getSupportFragmentManager();

        //fragment ditambahkan ke mainActivity
        fm.beginTransaction().add(R.id.fragment_container,authScreen).commit();
    }

}