package com.samp.demo.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.samp.demo.R;
import com.samp.demo.callback.OnPageSelectedCallback;
import com.samp.demo.transformer.ScaleInTransformer;
import com.samp.demo.utils.ViewPager2Helper;
import com.samp.demo.widget.adapter.BannerViewAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/8/10
 *     desc   : 自定义轮播图
 * </pre>
 */
public class BannerView extends RelativeLayout {
    private final static int BANNER_CHANNEL = 99;//Handler消息标识
    private final static long BANNER_HANDOFF_DURATION = 1000;//自动切换时间
    private final BannerHandler mBannerHandler = new BannerHandler(this);
    private ViewPager2 mViewPager;
    private List<String> mBannerList = new ArrayList<>();//轮播图集合
    private int mCurrentPosition;//当前坐标
    private boolean mIsLoop;//是否循环播放
    private int mAutoPlayTime;//自动播放时间
    private float mRadius;//圆角
    private OnPageSelectedCallback mOnPageSelectedCallback;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBannerView(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBannerView(context, attrs, defStyleAttr);
    }

    private void initBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView, defStyleAttr, 0);
        mIsLoop = typedArray.getBoolean(R.styleable.BannerView_banner_loop, false);
        mAutoPlayTime = typedArray.getInteger(R.styleable.BannerView_banner_time, 3000);
        mRadius = typedArray.getColor(R.styleable.BannerView_banner_radius, 0);
        mViewPager = new ViewPager2(context);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        RecyclerView recyclerView = (RecyclerView) mViewPager.getChildAt(0);
        recyclerView.setPadding(30, 0, 30, 0);
        recyclerView.setClipToPadding(false);
        recyclerView.setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);
        CompositePageTransformer CompositePageTransformer = new CompositePageTransformer();
        CompositePageTransformer.addTransformer(new MarginPageTransformer(40));
        mViewPager.setPageTransformer(CompositePageTransformer);
        mViewPager.setPageTransformer(new ScaleInTransformer());
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentPosition = position;
                mOnPageSelectedCallback.onPageSelected(mCurrentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    mBannerHandler.sendEmptyMessageDelayed(BANNER_CHANNEL, mAutoPlayTime);
                } else {
                    mBannerHandler.removeCallbacksAndMessages(null);
                }
            }
        });
        addView(mViewPager);
        typedArray.recycle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mRadius > 0) {
            Path path = new Path();
            RectF rectF = new RectF(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
            path.addRoundRect(rectF, mRadius, mRadius, Path.Direction.CW);
            canvas.clipPath(path);
        }
        super.dispatchDraw(canvas);
    }

    /**
     * 初始化轮播图数据
     *
     * @param context
     * @param imgList
     */
    public void initBannerData(Context context, List<String> imgList) {
        mBannerList = imgList;
        //添加第一条数据到结尾，用于切换到最后一条时无缝选中到第一条轮播
        BannerViewAdapter bannerViewAdapter = new BannerViewAdapter(context, imgList);
        mViewPager.setAdapter(bannerViewAdapter);

    }

    /**
     * 开始轮播
     */
    public void startBanner() {
        mBannerHandler.sendEmptyMessageDelayed(BANNER_CHANNEL, mAutoPlayTime);
    }

    /**
     * 停止轮播
     */
    public void stopBanner() {
        mBannerHandler.removeCallbacksAndMessages(null);
    }

    private static class BannerHandler extends Handler {
        private final WeakReference<BannerView> mWeakReference;

        public BannerHandler(BannerView view) {
            mWeakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BannerView bannerView = mWeakReference.get();
            switch (msg.what) {
                case BANNER_CHANNEL:
                    if (bannerView.mBannerList.size() == 0) {
                        return;
                    }
                    if (bannerView.mIsLoop) {
                        if (bannerView.mCurrentPosition == bannerView.mBannerList.size() - 1) {
                            removeCallbacksAndMessages(null);
                            bannerView.mCurrentPosition = 0;
                            ViewPager2Helper.setCurrentItem(bannerView.mViewPager, 0, BANNER_HANDOFF_DURATION);
                            sendEmptyMessageDelayed(BANNER_CHANNEL, bannerView.mAutoPlayTime);
                        } else {
                            ViewPager2Helper.setCurrentItem(bannerView.mViewPager, bannerView.mCurrentPosition + 1, BANNER_HANDOFF_DURATION);
                            sendEmptyMessageDelayed(BANNER_CHANNEL, bannerView.mAutoPlayTime);
                        }

                    }
                    break;
            }
        }
    }

    public void setOnPageSelectedCallback(OnPageSelectedCallback callback) {
        this.mOnPageSelectedCallback = callback;
    }
}
