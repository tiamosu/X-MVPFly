package com.xia.baseproject.ui.activities;

import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @author xia
 * @date 2018/8/16.
 */
public interface IBaseActivity {

    boolean isCheckNetWork();

    boolean isDispatchTouchHideKeyboard();

    void onBeforeCreateView();

    void dispatchTouchHideKeyboard(EditText editText, MotionEvent event);

    void initData();

    void initView();

    void initEvent();

    void onVisibleLazyLoad();

    void onNetReConnect();

    void onNetworkState(boolean isAvailable);
}
