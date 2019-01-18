package com.example.eg.skype;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

      /* Setting up the Android components that we are going to use.
    We are using an ImageView Component, we are going to crate a new instance of the Media player object,
    so we can play a startup sound, to simulate skype when is connecting and finally we are going to crate a TextView,
    to let the use know something is happening in the background.
     */


    private static final int REQUEST_STORAGE_PERMISSION = 78;

    ImageView mLogo;
    MediaPlayer mPlayer = new MediaPlayer();
    TextView mUserMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Connecting our Android components to their XML counterpart, so we can manipulate it in code.
        We are creating the MediaPlayer object using the context of our MainActivity and the sound located in the raw folder.
         */

        checkForPermission();

        mLogo = findViewById(R.id.Main_AppLogo);
        mUserMsg = findViewById(R.id.Main_LoginMsg);

        mPlayer = MediaPlayer.create(this, R.raw.startup);


        //Calling the function (method)

    }



    // This method use a external library named Glide, to play the .Gif from the drawable folder
    private void AnimateAppLogo() {
        Glide.with(this)
                .load(R.drawable.app_logo)
                .into(mLogo);
    }

      /* If this app is installed on and Android, that is higher than Marshmallow, we need to request permission at runtime.
    Is a good idea to let the user know why we need this. If we have permission, we are going to start the cursor which is the
    object in charge of bringing is everything that is related to Media. In other words we can access the MediaStore and query
    the data that we need.

    Edu note: When working with large amount of code, is always a good idea to separate them into
    different lines with a , because this code needs to be readable by others and probably is not a good
    idea to make them scroll all the way just to figure out where we are calling what.

    Now that this is done, we want to callback method.
     */

    private void checkForPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
            }else{
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "We need to access your media", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        }else{}
    }

    /* Here I am doing a switch, to support more permissions that I might need in the future. Good for maintenance,
    the lifecycle of an app is: define user requirements, design, develop, test, maintain and implement
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AnimateAppLogo();
                    mPlayer.start();
                    LongOperation longOperation = new LongOperation(mUserMsg, this);
                    longOperation.execute();
                }else {

                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}