package com.example.eg.skype;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by EG on 11/9/2017.
 */

public class MediaGallery extends AppCompatActivity {

    private static final String TAG = "ED";

    String[] mData;
    ArrayList<String> mImagesURL = new ArrayList<>();
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_gallery);

        mData = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN};

        Uri imageUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        cur = getContentResolver().query(imageUri,
                mData, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );


        if (cur != null) {
            for (int i = 0; i < cur.getCount(); i++) {
                cur.moveToPosition(i);
                int mDataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
                mImagesURL.add(cur.getString(mDataColumn));
            }
        }


            Log.e(TAG, "onCreate: " + mImagesURL.size());

    }
}

