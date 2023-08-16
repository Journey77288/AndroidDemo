package com.samp.demo.mvp.home.view;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.samp.demo.R;
import com.samp.demo.adapter.RecyclerSampleAdapter;
import com.samp.demo.callback.OnPageSelectedCallback;
import com.samp.demo.entity.RecyclerSampleEntity;
import com.samp.demo.utils.AppBars;
import com.samp.demo.widget.BannerView;

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
    private final ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    private final List<RecyclerSampleEntity> mData = new ArrayList<>();
    private final List<String> mCovers = new ArrayList<>();
    private BannerView mBannerView;
    private LinearLayout mIndicationLly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            String mDesc = "渐渐模糊的约定 星空下流浪的你 仍然秘密的距离 温度消失的瞬间 无法触摸的明天 没有引力的世界 没有脚印的光年";
            String mAvatarUrl = "https://img1.baidu.com/it/u=1496908812,2074868618&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281";
            mData.add(new RecyclerSampleEntity(mAvatarUrl, "咕咚" + i + "号", mDesc, false));
        }
        mCovers.add("https://img1.baidu.com/it/u=1496908812,2074868618&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        mCovers.add("https://img1.baidu.com/it/u=3955350673,973289700&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        mCovers.add("https://img1.baidu.com/it/u=2944558387,1958708291&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
        mCovers.add("https://img1.baidu.com/it/u=2191692171,2870223192&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        mCovers.add("https://img1.baidu.com/it/u=841893591,1199794255&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500");
        mCovers.add("https://img0.baidu.com/it/u=3903906014,684020987&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        mCovers.add("https://img0.baidu.com/it/u=2503632899,1245159502&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=270");
        mCovers.add("https://img0.baidu.com/it/u=2083417482,2164326650&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=500");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        int statusHeight = AppBars.StatusBarHeight(getActivity());
        RecyclerView mRootRv = view.findViewById(R.id.rv_content);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setPadding(0, statusHeight, 0, 0);
        ConstraintLayout bannerCly = view.findViewById(R.id.cly_banner);
        bannerCly.setPadding(0, statusHeight, 0, 0);
        AppBarLayout appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            float factor = Math.abs((float) verticalOffset / appBarLayout.getTotalScrollRange());
            int bgColor = (int) mArgbEvaluator.evaluate(
                    factor,
                    ResourcesCompat.getColor(getResources(), R.color.color_00FFFFFF, null),
                    ResourcesCompat.getColor(getResources(), R.color.color_FFFFFF, null)
            );
            toolbar.setBackgroundColor(bgColor);
        });
        mRootRv.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerSampleAdapter mRecyclerSampleAdapter = new RecyclerSampleAdapter(getContext(), mData);
        mRecyclerSampleAdapter.setOwner(getActivity());
        mRootRv.setAdapter(mRecyclerSampleAdapter);
        initIndicationView(view);
        return view;
    }

    private void initIndicationView(View view) {
        mBannerView = view.findViewById(R.id.banner_view);
        mIndicationLly = view.findViewById(R.id.lly_indication);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(16, 16);
        layoutParams.setMargins(5, 5, 5, 5);
        for (String url : mCovers) {
            //初始化指示器
            View indicationView = new View(getContext());
            indicationView.setBackgroundResource(R.drawable.selector_banner_indication);
            indicationView.setLayoutParams(layoutParams);
            if (mCovers.indexOf(url) == 0) {
                indicationView.setSelected(true);
            }
            mIndicationLly.addView(indicationView);
        }
        mBannerView.initBannerData(getContext(), mCovers);
        mBannerView.setOnPageSelectedCallback(new OnPageSelectedCallback() {
            @Override
            public void onPageSelected(int position) {
                changeIndicationView(position);
            }
        });
    }

    public void changeIndicationView(int position) {
        for (int i = 0; i < mIndicationLly.getChildCount(); i++) {
            View view = mIndicationLly.getChildAt(i);
            if (view.isSelected()) {
                view.setSelected(false);
            }
        }
        mIndicationLly.getChildAt(position).setSelected(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBannerView.startBanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerView.stopBanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBannerView.stopBanner();
    }
}
