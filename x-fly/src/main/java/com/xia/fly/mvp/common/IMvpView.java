package com.xia.fly.mvp.common;

import android.content.Context;

/**
 * @author xia
 * @date 2018/7/19.
 */
public interface IMvpView<P> {

    P newP();

    Context getContext();

    /**
     * @return 用于布局加载
     * 如果{@link #getLayoutId()}返回0，则不会生产视图
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
}
