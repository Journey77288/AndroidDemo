package com.samp.demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/10
 *     desc   : * App 状态栏与导航栏相关工具类
 *     注：如app有改变状态栏字体颜色(黑/白)需求，可以考虑 <a helf="http://qmuiteam.com/android/documents">团队开源的工具类</a>方案实现
 * </pre>
 */
public class AppBars {

    private AppBars() {
        throw new Error("this is static utils");
    }

    /**
     * 设置状态栏和导航栏透明
     *
     * @param activity
     */
    public static void AppBarTranslucent(Activity activity) {
        StatusBarTranslucent(activity);
        NavigationBarTranslucent(activity);
    }

    /**
     * 设置导航栏透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void NavigationBarTranslucent(Activity activity) {
        NavigationBarColor(activity, Color.TRANSPARENT);
    }

    /**
     * 设置状态栏透明
     *
     * @param activity
     */
    public static void StatusBarTranslucent(Activity activity) {
        AppBarColor(activity, Color.TRANSPARENT);
    }

    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity
     * @param color
     */
    public static void AppBarColor(Activity activity, @ColorInt int color) {
        StatusColor(activity, color);
        NavigationBarColor(activity, color);
    }


    /**
     * 设置statusBar主题为亮色 -- 字体图标为黑色
     *
     * @param activity
     */
    public static void StatusBarLightStyle(Activity activity) {
        StatusBarLightStyle(activity, Color.DKGRAY);
    }


    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity
     * @param color
     */
    @TargetApi(21)
    public static SystemBarTintManager StatusColor(Activity activity, @ColorInt int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return tintManager;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
        return tintManager;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     */
    @TargetApi(21)
    public static SystemBarTintManager changeStatusColor(Activity activity, @ColorInt int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return tintManager;
        }
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
        return tintManager;
    }

    /**
     * 设置statusBar主题为暗色 -- 字体图标为白色
     * 夜间模式不调整字体图标颜色
     *
     * @param activity
     */
    public static void StatusBarDarkStyle(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            if (Screens.isNightMode(activity)) {//夜间暗色模式
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(visibility);
        }
    }


    /**
     * 设置当前状态栏主题为亮色 -- 字体、图标为黑色
     *
     * @param activity
     * @param color    如果当前系统版本不支持状态栏主题模式设置，则给状态栏叠加一个颜色，一般为半透明的灰色
     */
    public static void StatusBarLightStyle(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            if (Screens.isNightMode(activity)) {//夜间暗色模式
                visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(visibility);
        } else {
            StatusColor(activity, color);
        }
    }


    /**
     * 设置NavigationBar主题为暗色 -- 字体图标为白色
     *
     * @param activity
     */
    public static void NavigationBarDarkStyle(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            window.getDecorView().setSystemUiVisibility(visibility);
            NavigationBarColor(activity, Color.BLACK);
        } else {
            NavigationBarColor(activity, Color.WHITE);
        }
    }


    /**
     * 设置NavigationBar主题为亮色 -- 字体、图标为黑色
     *
     * @param activity
     */
    public static void NavigationBarLightStyle(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Window window = activity.getWindow();
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            window.getDecorView().setSystemUiVisibility(visibility);
            NavigationBarColor(activity, Color.WHITE);
        } else {
            NavigationBarColor(activity, Color.BLACK);
        }
    }


    /**
     * 设置状态栏和导航栏的背景
     *
     * @param activity
     * @param drawable
     */
    public static SystemBarTintManager StatusBarDrawable(Activity activity, Drawable drawable) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return tintManager;
        }
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintDrawable(drawable);
        tintManager.setStatusBarTintEnabled(true);
        return tintManager;
    }


    /**
     * 设置状态栏和导航栏的颜色
     *
     * @param activity
     * @param color
     */
    public static SystemBarTintManager NavigationBarColor(Activity activity, @ColorInt int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return tintManager;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        winParams.flags |= bits;
        window.setAttributes(winParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(color);
        }
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintColor(color);
        return tintManager;
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int StatusBarHeight(Context context) {
        return StatusBarHeight(context.getResources());
    }

    /**
     * 获取状态栏高度
     *
     * @param resources
     */
    public static int StatusBarHeight(Resources resources) {
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }


    /**
     * 隐藏NavigationBar
     *
     * @param context
     */
    public static void NavigationBarHide(Context context) {
        if (!(context instanceof Activity) || ((Activity) context).getWindow().getDecorView() == null) {
            return;
        }
        View view = ((Activity) context).getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            view.setSystemUiVisibility(View.GONE);
            return;
        }
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(uiOptions);
    }

}
