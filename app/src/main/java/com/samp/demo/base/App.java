package com.samp.demo.base;

import android.app.Application;

import com.samp.demo.view.PreferencesUtils;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/30
 *     desc   : 应用程序入口
 * </pre>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.init(this);
    }
}
