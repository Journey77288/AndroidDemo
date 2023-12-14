package com.samp.demo.widget.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.samp.demo.R;

import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/8/10
 *     desc   : 自定义轮播图适配器
 * </pre>
 */
public class BannerViewAdapter extends RecyclerView.Adapter<BannerViewAdapter.BannerViewHolder> {
    private final Context mContext;
    private final List<String> mData;

    public BannerViewAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        ImageView coverIv = holder.coverIv;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        coverIv.setLayoutParams(params);
        Glide.with(mContext)
                .asBitmap()
                .load(mData.get(position))
                .into(coverIv);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView coverIv;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            coverIv = itemView.findViewById(R.id.iv_cover);
        }
    }

}
