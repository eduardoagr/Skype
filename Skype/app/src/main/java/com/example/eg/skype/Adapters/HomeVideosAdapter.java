package com.example.eg.skype.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.eg.skype.R;

import java.util.ArrayList;

/**
 * Created by EG on 11/26/2017.
 */

public class HomeVideosAdapter extends RecyclerView.Adapter<HomeVideosAdapter.ViewHolder> {

    private ArrayList<String> mVidData;
    private Context context;

    public HomeVideosAdapter(ArrayList<String> mVidData, Context context) {
        this.mVidData = mVidData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_videos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String vidUrl = mVidData.get(position);

        Glide.with(context).load(vidUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (mVidData.size() != 0) {
            return mVidData.size();
        }else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.home_videos_thumbnail);
        }
    }
}
