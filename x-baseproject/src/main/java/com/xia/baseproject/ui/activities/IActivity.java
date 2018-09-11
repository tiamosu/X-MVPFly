package com.xia.baseproject.ui.activities;

import android.widget.EditText;

/**
 * @author xia
 * @date 2018/9/11.
 */
public interface IActivity {

    /**
     * @return 用于布局加载, 如果{@link #getLayoutId()}返回0,
     * 则不会调用{@link android.app.Activity#setContentView(int)}
     */
    int getLayoutId();

    /**
     * 用于初始化数据
     */
    void initData();

    /**
     * 用于初始化View
     */
    void initView();

    /**
     * 用于初始化事件
     */
    void initEvent();

    /**
     * 该方法执行于
     * {@link #initData()}
     * {@link #initView()}
     * {@link #initEvent()}
     * 之后，可用于加载网络数据等
     */
    void onLazyLoadData();

    /**
     * @return 是否检查网络状态，默认为true
     */
    boolean isCheckNetWork();

    /**
     * @param isAvailable 网络是否连接可用
     */
    void onNetworkState(boolean isAvailable);

    /**
     * 用于网络连接恢复后加载
     */
    void onNetReConnect();

    /**
     * @return 是否点击空白区域隐藏软键盘，默认为true
     */
    boolean isDispatchTouchHideKeyboard();

    /**
     * 点击空白区域，隐藏软键盘时，EditText进行相关设置
     */
    void dispatchTouchHideKeyboard(EditText editText);
}
