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

public class HomeImagesAdapter extends RecyclerView.Adapter<HomeImagesAdapter.ViewHolder> {

    private ArrayList<String> mImgData;
    private Context context;

    public HomeImagesAdapter(ArrayList<String> mImgData, Context context) {
        this.mImgData = mImgData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.home_images, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String imgUrl = mImgData.get(position);

        Glide.with(context).load(imgUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mImgData.size() != 0) {
            return mImgData.size();
        }else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.home_images_thumbnail);
        }
    }
}

