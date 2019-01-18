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
 * Created by EG on 11/19/2017.
 */

public class MediaGalleryVideoAdapter extends RecyclerView.Adapter<MediaGalleryVideoAdapter.ViewHolder> {

    private ArrayList<String> mVidData;
    private Context context;
    private Listener mListener;

    public interface Listener{
        void onClick(View v, String i, String type);
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public MediaGalleryVideoAdapter(Context context, ArrayList<String> imageList) {
        this.context = context;;
        this.mVidData = imageList;
    }

    @Override
    public MediaGalleryVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.gallery_videos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MediaGalleryVideoAdapter.ViewHolder holder, int position) {

        String imageUrl = mVidData.get(position);

        Glide.with(context).load("file://" + imageUrl).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onClick(view, mVidData.get(holder.getAdapterPosition()), "v");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mVidData.size() != 0) {
            return mVidData.size();
        }else{
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gallery_videos_thumbnail);
        }
    }
}
