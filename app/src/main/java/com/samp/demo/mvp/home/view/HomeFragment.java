package com.samp.demo.mvp.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.samp.demo.R;
import com.samp.demo.adapter.RecyclerSampleAdapter;
import com.samp.demo.entity.RecyclerSampleEntity;
import com.samp.demo.utils.AppBars;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/24
 *     desc   : 主页 Fragment
 * </pre>
 */
public class HomeFragment extends Fragment {
    private final String mAvatarUrl = "https://img1.baidu.com/it/u=1496908812,2074868618&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281";
    private final String mDesc = "渐渐模糊的约定 星空下流浪的你 仍然秘密的距离 温度消失的瞬间 无法触摸的明天 没有引力的世界 没有脚印的光年";
    private final List<RecyclerSampleEntity> mData = new ArrayList<>();
    private RecyclerSampleAdapter mRecyclerSampleAdapter;
    private RecyclerView mRootRv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mData.add(new RecyclerSampleEntity(mAvatarUrl, "咕咚" + i + "号", mDesc, false));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRootRv = view.findViewById(R.id.rv_content);
        AppBarLayout layout = view.findViewById(R.id.appbar);
        int statusHeight = AppBars.StatusBarHeight(getContext());
        layout.setPadding(0, statusHeight, 0, 0);
        mRootRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerSampleAdapter = new RecyclerSampleAdapter(getContext(), mData);
        mRecyclerSampleAdapter.setOwner(getActivity());
        mRootRv.setAdapter(mRecyclerSampleAdapter);
        return view;
    }
}
