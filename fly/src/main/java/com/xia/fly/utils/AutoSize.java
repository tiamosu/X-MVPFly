package com.xia.fly.utils;

import android.app.Activity;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * 屏幕适配方案
 *
 * @author xia
 * @date 2018/8/28.
 */
@SuppressWarnings({"SuspiciousNameCombination", "WeakerAccess"})
public final class AutoSize {

    private AutoSize() {
        throw new IllegalStateException("u can't instantiate me!");
    }

    public static void adaptScreen(final Activity activity,
                                   final int designWidthInPx) {
        adaptScreen(activity, designWidthInPx, designWidthInPx);
    }

    public static void adaptScreen(final Activity activity,
                                   final int designWidthInPx,
                                   final int designHeightInPx) {
        if (ScreenUtils.isPortrait()) {
            ScreenUtils.adaptScreen4VerticalSlide(activity, designWidthInPx);
        } else {
            ScreenUtils.setFullScreen(activity);
            ScreenUtils.adaptScreen4HorizontalSlide(activity, designHeightInPx);
        }
    }

    public static void cancelAdaptScreen(Activity activity) {
        ScreenUtils.cancelAdaptScreen(activity);
    }
}
