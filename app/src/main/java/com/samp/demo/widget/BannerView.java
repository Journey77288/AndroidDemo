package com.samp.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.samp.demo.R;
import com.samp.demo.callback.OnPageSelectedCallback;
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
public class BannerView extends FrameLayout implements DefaultLifecycleObserver {
    private final static int BANNER_CHANNEL = 99;//Handler消息标识
    private final static long BANNER_HANDOFF_DURATION = 1000;//自动切换时间
    private BannerHandler mBannerHandler = new BannerHandler(this);
    private ViewPager2 mViewPager;
    private List<String> mBannerList = new ArrayList<>();//轮播图集合
    private int mCurrentPosition;//当前坐标
    private boolean mIsLoop = false;//是否循环播放
    private boolean mIsRun = false;//用来标志是否正在轮播
    private int mAutoPlayTime = 3000;//轮播间隔默认3秒
    private float mRadius;//圆角
    private OnPageSelectedCallback mOnPageSelectedCallback;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBannerView(context, attrs);
    }

    private void initBannerView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mIsLoop = typedArray.getBoolean(R.styleable.BannerView_banner_loop, false);
        mAutoPlayTime = typedArray.getInteger(R.styleable.BannerView_banner_time, 3000);
        mRadius = typedArray.getColor(R.styleable.BannerView_banner_radius, 0);
        mViewPager = new ViewPager2(context);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        RecyclerView recyclerView = (RecyclerView) mViewPager.getChildAt(0);
//        recyclerView.setPadding(30, 0, 30, 0);
        recyclerView.setClipToPadding(false);
        recyclerView.setOverScrollMode(ViewPager2.OVER_SCROLL_NEVER);
//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
//        mViewPager.setPageTransformer(compositePageTransformer);
//        mViewPager.setPageTransformer(new ScaleInTransformer());
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (mOnPageSelectedCallback == null) {
                    return;
                }
                if (position == mBannerList.size() - 1) {
                    mOnPageSelectedCallback.onPageSelected(0);
                } else {
                    mOnPageSelectedCallback.onPageSelected(position - 1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (mViewPager == null) {
                    return;
                }
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(mBannerList.size() - 2, false);
                    } else if (mViewPager.getCurrentItem() == mBannerList.size() - 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                    mCurrentPosition = mViewPager.getCurrentItem();
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    mCurrentPosition = mViewPager.getCurrentItem();
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
        stopBanner();
        //添加真实最后一条数据到开头，真实第一条数据到结尾用于切换到最后一条时无缝选中到第一条轮播
        imgList.add(0, imgList.get(imgList.size() - 1));
        imgList.add(imgList.get(1));
        mBannerList = imgList;
        mCurrentPosition = 1;
        mViewPager.setCurrentItem(1, false);
        BannerViewAdapter bannerViewAdapter = new BannerViewAdapter(context, imgList);
        mViewPager.setAdapter(bannerViewAdapter);
        if (mBannerList.size() > 1) {
            mViewPager.setOffscreenPageLimit(mBannerList.size() - 1);
        }
        mCurrentPosition = 1;
        mViewPager.setCurrentItem(mCurrentPosition, false);
        startBanner();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mViewPager != null && mViewPager.getCurrentItem() == 0 && getChildCount() == 0) {
            return false;//当数据为0的时候不接收事件
        }
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL ||
                action == MotionEvent.ACTION_OUTSIDE) {
            startBanner();
        } else if (action == MotionEvent.ACTION_DOWN) {
            stopBanner();
        }
        return super.dispatchTouchEvent(ev);
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
            if (msg.what == BANNER_CHANNEL) {
                if (bannerView.mBannerList.size() == 0 || !bannerView.mIsRun || !bannerView.mIsLoop) {
                    return;
                }
                bannerView.mCurrentPosition++;
                if (bannerView.mCurrentPosition >= bannerView.mBannerList.size() - 1) {
                    bannerView.mCurrentPosition = 1;
                    bannerView.mViewPager.setCurrentItem(bannerView.mCurrentPosition, false);
                } else {
                    bannerView.mViewPager.setCurrentItem(bannerView.mCurrentPosition);
                }
                bannerView.mBannerHandler.sendEmptyMessageDelayed(BANNER_CHANNEL, bannerView.mAutoPlayTime);
            }
        }
    }

    public void setOnPageSelectedCallback(OnPageSelectedCallback callback) {
        this.mOnPageSelectedCallback = callback;
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        startBanner();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        stopBanner();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        destroyBanner();
    }


    /**
     * 开始轮播
     */
    public void startBanner() {
        if (!mIsRun) {
            mIsRun = true;
            mBannerHandler.sendEmptyMessageDelayed(BANNER_CHANNEL, mAutoPlayTime);
        }
    }

    /**
     * 停止轮播
     */
    public void stopBanner() {
        if (mIsRun) {
            mIsRun = false;
            mBannerHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 销毁轮播
     */
    public void destroyBanner() {
        mBannerHandler.removeCallbacksAndMessages(null);
        mIsRun = false;
        mBannerHandler = null;
    }

}
