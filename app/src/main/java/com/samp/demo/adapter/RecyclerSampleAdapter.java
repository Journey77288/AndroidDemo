package com.samp.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.samp.demo.R;
import com.samp.demo.RecyclerSampleEntity;
import com.samp.demo.view.RecyclerSampleActivity;

import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/4
 *     desc   : RecyclerView Sample Adapter
 * </pre>
 */
public class RecyclerSampleAdapter extends RecyclerView.Adapter<RecyclerSampleAdapter.ViewHolder> {
    private final Context mContext;
    private final List<RecyclerSampleEntity> mData;
    private RecyclerSampleActivity mActivity;

    public void setOwner(RecyclerSampleActivity activity){
        this.mActivity = activity;
    }

    public RecyclerSampleAdapter(Context context, List<RecyclerSampleEntity> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConstraintLayout rootCly = holder.rootCly;
        ImageView avatarIv = holder.avatarIv;
        TextView titleTv = holder.titleTv;
        TextView descTv = holder.descTv;
        Glide.with(mActivity)
                .asBitmap()
                .load(mData.get(position).getAvatar())
                .into(avatarIv);
        titleTv.setText(mData.get(position).getTitle());
        descTv.setText(mData.get(position).getDesc());
        rootCly.setSelected(mData.get(position).isSelected());
        rootCly.setOnClickListener(view -> {
            boolean isSelected = !rootCly.isSelected();
            rootCly.setSelected(isSelected);
            mData.get(position).setSelected(isSelected);
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout rootCly;
        private final ImageView avatarIv;
        private final TextView titleTv;
        private final TextView descTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootCly = itemView.findViewById(R.id.cly_root);
            avatarIv = itemView.findViewById(R.id.iv_avatar);
            titleTv = itemView.findViewById(R.id.tv_title);
            descTv = itemView.findViewById(R.id.tv_desc);
        }
    }
}
