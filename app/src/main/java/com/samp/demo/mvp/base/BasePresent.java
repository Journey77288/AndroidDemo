package com.samp.demo.mvp.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : MVP中的BasePresent
 * </pre>
 */
public abstract class BasePresent<T extends BaseView, E extends BaseModel> implements DefaultLifecycleObserver {
    protected T mView;
    protected E mModel;

    public BasePresent(T view) {
        mView = view;
        mView.setPresent(this);
        initModel();
    }

    protected void initModel() {
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        mView = null;
    }
}
