package com.samp.demo.mvp.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.samp.demo.R;
import com.samp.demo.base.BaseActivity;
import com.samp.demo.callback.OnClickCallback;
import com.samp.demo.mvp.base.BasePresent;
import com.samp.demo.mvp.home.adapter.ViewPagerAdapter;
import com.samp.demo.mvp.home.contract.MainContract;
import com.samp.demo.mvp.home.model.TabEntity;
import com.samp.demo.mvp.home.present.MainPresent;
import com.samp.demo.utils.AppBars;
import com.samp.demo.view.RecyclerSampleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/4
 *     desc   : MainActivity
 * </pre>
 */
public class MainActivity extends BaseActivity implements MainContract.View {
    private MainPresent mPresent;
    private ViewPagerAdapter mViewPagerAdapter;
    private final List<TabEntity> mIcon = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new MainPresent(this));
        initData();
        initViews();
    }

    private void initData() {
        mIcon.add(new TabEntity(getString(R.string.str_home), R.drawable.selector_home_tab));
        mIcon.add(new TabEntity(getString(R.string.str_short_video), R.drawable.selector_video_tab));
        mIcon.add(new TabEntity(getString(R.string.str_mine), R.drawable.selector_mine_tab));

        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new VideoStreamFragment());
        mFragmentList.add(new MineFragment());
        mPresent.getData();
    }

    private void initViews() {
        AppBars.AppBarTranslucent(this);
        AppBars.StatusBarLightStyle(this);
        AppBars.NavigationBarLightStyle(this);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(this, mFragmentList);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setCustomView(getViewAt(position));
        }).attach();
        OnClickCallback mOnClickCallback = position -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(MainActivity.this, RecyclerSampleActivity.class));
                    break;
                case 1:
                    break;
            }
        };
    }

    private View getViewAt(int position) {
        View view = getLayoutInflater().inflate(R.layout.view_tab_layout, null, false);
        ImageView imageView = view.findViewById(R.id.iv_icon);
        TextView textView = view.findViewById(R.id.tv_text);
        imageView.setImageResource(mIcon.get(position).getIcon());
        textView.setText(mIcon.get(position).getText());
        return view;
    }

    @Override
    public void setPresent(BasePresent present) {
        mPresent = (MainPresent) present;
    }

    @Override
    public void setListData(List<String> data) {
        mData.addAll(data);
    }
}