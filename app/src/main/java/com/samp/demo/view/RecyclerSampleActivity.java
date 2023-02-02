package com.samp.demo.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samp.demo.R;
import com.samp.demo.base.BaseActivity;
import com.samp.demo.entity.RecyclerSampleEntity;
import com.samp.demo.adapter.RecyclerSampleAdapter;
import com.samp.demo.utils.AppBars;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/4
 *     desc   : RecyclerView Sample
 * </pre>
 */
public class RecyclerSampleActivity extends BaseActivity {
    private final String mAvatarUrl = "https://img1.baidu.com/it/u=1496908812,2074868618&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281";
    private final String mDesc = "渐渐模糊的约定 星空下流浪的你 仍然秘密的距离 温度消失的瞬间 无法触摸的明天 没有引力的世界 没有脚印的光年";
    private final List<RecyclerSampleEntity> mData = new ArrayList<>();
    private RecyclerSampleAdapter mRecyclerSampleAdapter;
    private RecyclerView mRootRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_sample);
        initData();
        initViews();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mData.add(new RecyclerSampleEntity(mAvatarUrl, "咕咚" + i + "号", mDesc, false));
        }
    }

    private void initViews() {
        AppBars.AppBarTranslucent(this);
        AppBars.StatusBarLightStyle(this);
        AppBars.NavigationBarLightStyle(this);
        int statusHeight = AppBars.StatusBarHeight(this);
        findViewById(R.id.tv_add).setOnClickListener(view -> handleAddItem());
        findViewById(R.id.tv_delete).setOnClickListener(view -> handleDeleteItem());
        mRootRv = findViewById(R.id.rv_root);
        mRootRv.setPadding(0, statusHeight, 0, 0);
        mRootRv.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerSampleAdapter = new RecyclerSampleAdapter(this, mData);
        mRecyclerSampleAdapter.setOwner(this);
        mRootRv.setAdapter(mRecyclerSampleAdapter);
    }

    private void handleAddItem() {
        int originCount = mData.size();
        mData.add(new RecyclerSampleEntity(mAvatarUrl, (mData.size()) + "号", mDesc, false));
        mRecyclerSampleAdapter.notifyItemRangeInserted(originCount, mData.size());
        mRootRv.scrollToPosition(mData.size() - 1);
    }

    private void handleDeleteItem() {
        for (int i = mData.size() - 1; i < mData.size(); i--) {
            if (i < 0) {
                return;
            }
            if (mData.get(i).isSelected()) {
                mData.remove(mData.get(i));
                mRecyclerSampleAdapter.notifyItemRemoved(i);
                mRecyclerSampleAdapter.notifyDataSetChanged();
            }
        }
    }
}
