package com.xia.baseproject.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xia.baseproject.R;
import com.xia.baseproject.fragments.BaseDelegate;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class ProxyActivity extends AbstractSupportActivity {

    public abstract BaseDelegate setRootDelegate();

    protected boolean isDispatchTouchHideKeyboard() {
        return true;
    }

    protected void dispatchTouchHideKeyboard(EditText editText, MotionEvent event) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemConfiguration();
        initContainer(savedInstanceState);
    }

    /**
     * before the setContentView(view)
     */
    protected void systemConfiguration() {
    }

    @SuppressLint("RestrictedApi")
    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isDispatchTouchHideKeyboard() &&
                ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                final IBinder iBinder = v.getWindowToken();
                final InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (iBinder != null && imm != null) {
                    imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            final EditText editText = (EditText) v;
            dispatchTouchHideKeyboard(editText, event);
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
