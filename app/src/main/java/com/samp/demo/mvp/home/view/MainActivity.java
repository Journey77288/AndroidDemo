package com.samp.demo.mvp.home.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samp.demo.R;
import com.samp.demo.adapter.MainAdapter;
import com.samp.demo.base.BaseActivity;
import com.samp.demo.callback.OnClickCallback;
import com.samp.demo.mvp.base.BasePresent;
import com.samp.demo.mvp.home.contract.MainContract;
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
    private final List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        mPresent.getData();
    }

    private void initViews() {
        getLifecycle().addObserver(new MainPresent(this));
        AppBars.AppBarTranslucent(this);
        AppBars.StatusBarLightStyle(this);
        AppBars.NavigationBarLightStyle(this);
        int statusHeight = AppBars.StatusBarHeight(this);
        RecyclerView rootRv = findViewById(R.id.rv_root);
        rootRv.setPadding(0, statusHeight, 0, 0);
        rootRv.setLayoutManager(new GridLayoutManager(this, 3));
        OnClickCallback mOnClickCallback = position -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(MainActivity.this, RecyclerSampleActivity.class));
                    break;
                case 1:
                    break;
            }
        };
        MainAdapter mMainAdapter = new MainAdapter(this, mData, mOnClickCallback);
        rootRv.setAdapter(mMainAdapter);
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