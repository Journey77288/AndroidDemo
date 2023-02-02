package com.samp.demo.base;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : 对APP中所有的Activity进行管理，统一放在Stack中处理
 * </pre>
 */
public class ActivityManager {
    private final String TAG = "ActivityManager";

    public static Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    private static ActivityManager instance;
    private static Stack<Activity> mActivityStack = new Stack<>();

    private ActivityManager() {
    }

    /**
     * 单一实例
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束所有Activity
     */
    @SuppressWarnings("WeakerAccess")
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            Log.e(TAG, "退出App异常:" + e.getMessage());
        }
    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }
}
