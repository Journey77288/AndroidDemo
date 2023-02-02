package com.samp.demo.utils;

import static com.samp.demo.constants.Constants.PREFERENCES_DB;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/30
 *     desc   : SharedPreferences工具类
 * </pre>
 */
public class PreferencesUtils {

    public static SharedPreferences mSharedPreferences;

    public PreferencesUtils() {

    }

    public static void init(Application context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES_DB, Context.MODE_PRIVATE);
    }

    public static PreferencesUtils getInstance() {
        return Singleton.INSTANCES;
    }

    private static class Singleton {
        private static final PreferencesUtils INSTANCES = new PreferencesUtils();
    }

    public void putString(String name, String value) {
        if (mSharedPreferences == null) return;
        mSharedPreferences.edit().putString(name, value).apply();
    }

    public String getString(String name) {
        if (mSharedPreferences == null) return "";
        return mSharedPreferences.getString(name, "");
    }

    public void remove(String name) {
        if (mSharedPreferences == null) return;
        mSharedPreferences.edit().remove(name).apply();
    }
}
