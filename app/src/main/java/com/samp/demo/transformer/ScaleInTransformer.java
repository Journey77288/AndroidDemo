package com.samp.demo.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/8/15
 *     desc   : 缩放ViewPager
 * </pre>
 */
public class ScaleInTransformer implements ViewPager2.PageTransformer {
    private final static float DEFAULT_MIN_SCALE = 0.85f;
    private static final float DEFAULT_CENTER = 0.5f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        view.setPivotY((float) pageHeight / 2);
        view.setPivotX((float) pageWidth / 2);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setScaleX(DEFAULT_MIN_SCALE);
            view.setScaleY(DEFAULT_MIN_SCALE);
            view.setPivotX((float) pageWidth);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) //1-2:1[0,-1] ;2-1:1[-1,0]
            {
                float scaleFactor = (1 + position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position));
            } else  //1-2:2[1,0] ;2-1:2[0,1]
            {
                float scaleFactor = (1 - position) * (1 - DEFAULT_MIN_SCALE) + DEFAULT_MIN_SCALE;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        } else { // (1,+Infinity]
            view.setPivotX(0f);
            view.setScaleX(DEFAULT_MIN_SCALE);
            view.setScaleY(DEFAULT_MIN_SCALE);
        }
    }

}
