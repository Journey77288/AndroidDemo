package com.samp.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samp.demo.R;
import com.samp.demo.callback.OnClickCallback;

import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/4
 *     desc   : MainAdapter
 * </pre>
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private final List<String> mData;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private OnClickCallback mOnClickCallback;

    public MainAdapter(Context context, List<String> mData, OnClickCallback onClickCallback) {
        this.mData = mData;
        this.mContext = context;
        this.mOnClickCallback = onClickCallback;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTv.setText(mData.get(position));
        holder.titleTv.setOnClickListener(view -> mOnClickCallback.onClickItem(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
        }
    }
}
