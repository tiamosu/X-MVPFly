package com.xia.fly.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xia.fly.ui.activities.SupportActivity;

/**
 * @author xia
 * @date 2018/9/11.
 */
public final class DispatchTouchEventHelper {

    private DispatchTouchEventHelper() {
        throw new IllegalStateException("u can't instantiate me!");
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    public static void dispatchTouchEvent(SupportActivity activity, MotionEvent ev) {
        if (activity.isDispatchTouchHideKeyboard() &&
                ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = activity.getCurrentFocus();
            if (isShouldHideKeyboard(activity, v, ev)) {
                final IBinder iBinder = v.getWindowToken();
                final InputMethodManager imm = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (iBinder != null && imm != null) {
                    imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    private static boolean isShouldHideKeyboard(SupportActivity activity, View v, MotionEvent event) {
        if (v instanceof EditText) {
            final EditText editText = (EditText) v;
            activity.onDispatchTouchHideKeyboard(editText);
            final int[] l = {0, 0};
            v.getLocationInWindow(l);
            final int left = l[0];
            final int top = l[1];
            final int bottom = top + v.getHeight();
            final int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
