package com.xia.baseproject.ui.activities;

import android.widget.EditText;

/**
 * @author xia
 * @date 2018/9/11.
 */
public interface IActivity {

    /**
     * 该方法执行于
     * {@link SupportActivity#initData()}
     * {@link SupportActivity#initView()}
     * {@link SupportActivity#initEvent()}
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
