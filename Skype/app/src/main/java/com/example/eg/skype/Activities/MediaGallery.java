package com.example.eg.skype.Activities;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eg.skype.Adapters.MediaGalleryVideoAdapter;
import com.example.eg.skype.R;
import com.example.eg.skype.Adapters.MediaGalleryImageAdapter;


import java.text.MessageFormat;
import java.util.ArrayList;


/**
 * Created by EG on 11/9/2017.
 */

/* Since RecyclerViews do not have a listener that we can implement, we are creating an interface on ech adapter, that will help us locate
the position of the item that we have in our RecyclerView
 */
public class MediaGallery extends AppCompatActivity implements
        MediaGalleryImageAdapter.Listener,
        MediaGalleryVideoAdapter.Listener {

    //This is only for debugging purposes
    private static final String TAG = "MediaGallery";

    //Android Widget
    FloatingActionButton mFloating;
    RecyclerView mImg_List;
    RecyclerView mVid_List;
    TextView mPhotosTextView, mVideoTextView;

    //Adapters
    MediaGalleryImageAdapter mImgAdapter;
    MediaGalleryVideoAdapter mVidAdap;


    //Containers
    ArrayList<String> mSelectedImg = new ArrayList<>();
    ArrayList<String> mImagesUrls = new ArrayList<>();
    ArrayList<String> mVideosUrls = new ArrayList<>();
    ArrayList<String> mSelectedVid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_gallery);

        setupUI();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){

                    callSettings();
            }else{
                loadPhotosFromNativeGallery();
                loadVideoSFromGallery();
                updatePhotoText();
                updateVidText();
        }
    }

    private void loadPhotosFromNativeGallery() {
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");

        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                mImagesUrls.add(cursor.getString(dataColumnIndex));
            }

            cursor.close();
        }
    }

    private void loadVideoSFromGallery() {
        String[] columns = {MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID};
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");

        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                mVideosUrls.add(cursor.getString(dataColumnIndex));
            }

            cursor.close();
        }
    }

    @Override
    public void onBackPressed() {}

    private void setupUI() {
        mVid_List = findViewById(R.id.Media_List_For_Videos);
        mImg_List = findViewById(R.id.Media_List_For_Images);
        mPhotosTextView = findViewById(R.id.Media_Image_Msg);
        mVideoTextView = findViewById(R.id.Media_Video_Msg);
        mFloating = findViewById(R.id.Media_floatingActionButton);
        mImg_List.setHasFixedSize(true);

        mVid_List.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImg_List.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mVidAdap = new MediaGalleryVideoAdapter(this, mVideosUrls);
        mImgAdapter = new MediaGalleryImageAdapter(this, mImagesUrls);

        mImgAdapter.setListener(this);
        mVidAdap.setListener(this);


        mImg_List.setAdapter(mImgAdapter);
        mVid_List.setAdapter(mVidAdap);

    }

    private void updatePhotoText() {
        mPhotosTextView.setText(MessageFormat.format("{0}  of {1}", mSelectedImg.size(), mImagesUrls.size()));
    }
    private void updateVidText(){
        mVideoTextView.setText(MessageFormat.format("{0} of {1}", mSelectedVid.size(), mVideosUrls.size()));
    }

    @Override
    public void onClick(View v, String i, String type) {

        switch (type) {

            case "img":
                if (!mSelectedImg.contains(i)) {
                    mSelectedImg.add(i);
                    Toast.makeText(this, "We added your item", Toast.LENGTH_SHORT).show();
                    updatePhotoText();
                } else if (mSelectedImg.contains(i)) {
                    Toast.makeText(this, "Your item already exist", Toast.LENGTH_SHORT).show();
                }
                break;

            case "v":
                if (!mSelectedVid.contains(i)) {
                    mSelectedVid.add(i);
                    Toast.makeText(this, "We added you item", Toast.LENGTH_SHORT).show();
                    updateVidText();
                } else if (mSelectedVid.contains(i)) {
                    Toast.makeText(this, "Your item already exist", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                Log.e(TAG, "This should never happen");
                break;
        }
    }

    public void goHome(View view) {

        switch (view.getId()){

            case R.id.Media_floatingActionButton:

                Intent i = new Intent(getApplicationContext(), Home.class);
                i.putStringArrayListExtra("img", mSelectedImg);
                i.putStringArrayListExtra("vid", mSelectedVid);
                startActivity(i);

                break;
        }
    }

    private void callSettings() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Camera permission necessary");
        alertBuilder.setMessage("We needs permission to access your device storage." + "\n" +
                "We are going to transfer to the settings to and enable the necessary permission");
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callSettingIntent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                startActivity(callSettingIntent);
                finish();
            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}




