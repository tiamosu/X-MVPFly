package com.xia.baseproject.mvp;

import android.support.v4.app.Fragment;

/**
 * @author xia
 * @date 2018/7/19.
 */
public interface IMvpView<P> {

    P newP();

    int getLayoutId();

    void initMvp();

    void initData();

    void initView();

    void initEvent();

    Fragment getBaseFragment();
}
