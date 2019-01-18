package com.example.eg.skype;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by EG on 11/9/2017.
 */

// This class is use to simulate an operation, where the user has to sign in.


@SuppressLint("StaticFieldLeak")
public class LongOperation extends AsyncTask<Void, String, Void> {

    private TextView mUserMsg;
    private Context mContext;

    LongOperation(TextView mUserMsg, Context mContext) {
        this.mUserMsg = mUserMsg;
        this.mContext = mContext;
    }


    @Override
    protected void onPreExecute() {
        mUserMsg.setText(R.string.UserMsg);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        Intent intent = new Intent(mContext, MediaGallery.class);
        mContext.startActivity(intent);
    }
}
