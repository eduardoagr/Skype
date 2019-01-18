package com.example.eg.skype.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.eg.skype.Adapters.HomeImagesAdapter;
import com.example.eg.skype.Adapters.HomeVideosAdapter;
import com.example.eg.skype.R;

import java.util.ArrayList;

/**
 * Created by EG on 11/9/2017.
 */

public class Home extends AppCompatActivity {


    private static final String TAG = "aaa" ;
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> vids = new ArrayList<>();

    RecyclerView imgRecycler;
    RecyclerView vidRecycler;

    HomeImagesAdapter homeImagesAdapter;
    HomeVideosAdapter homeVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        getData();

        setupUI();

        setAdapters();

    }

    private void setAdapters() {
        homeImagesAdapter = new HomeImagesAdapter(imgs, this);
        homeVideosAdapter = new HomeVideosAdapter(vids, this);

        imgRecycler.setAdapter(homeImagesAdapter);
        vidRecycler.setAdapter(homeVideosAdapter);
    }

    private void getData() {
        imgs = getIntent().getStringArrayListExtra("img");
        vids = getIntent().getStringArrayListExtra("vid");
    }

    private void setupUI() {
        imgRecycler = findViewById(R.id.home_recyclerImg);
        vidRecycler = findViewById(R.id.home_recyclerVid);
        imgRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        vidRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imgRecycler.setHasFixedSize(true);
        vidRecycler.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.MainMenu_Add_To_Library:

                Intent mLibraryIntent = new Intent(this, MediaGallery.class);
                startActivity(mLibraryIntent);

                break;

           default:
               return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
