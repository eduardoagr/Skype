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
 * Created by EG on 11/12/2017.
 */

public class MediaGalleryImageAdapter extends RecyclerView.Adapter<MediaGalleryImageAdapter.ViewHolder> {

    private ArrayList<String> mImgData;
    private Context context;
    private Listener mListener;

    public interface Listener{
        void onClick(View v, String i, String type);
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }


    public MediaGalleryImageAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.mImgData = imageList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.gallery_images, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MediaGalleryImageAdapter.ViewHolder holder, int position) {

        String imageUrl = mImgData.get(position);

        Glide.with(context).load("file://" + imageUrl).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(view, mImgData.get(holder.getAdapterPosition()), "img");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mImgData.size() != 0) {
            return mImgData.size();
        }else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gallery_images_thumbnail);
        }
    }
}
