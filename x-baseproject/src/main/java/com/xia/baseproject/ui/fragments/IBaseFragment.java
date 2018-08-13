package com.xia.baseproject.ui.fragments;

import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * @author xia
 * @date 2018/8/1.
 */
@SuppressWarnings("WeakerAccess")
public interface IBaseFragment {

    boolean isLoadHeadView();

    boolean isCheckNetWork();

    void onVisibleLazyLoad();

    void getBundleExtras(Bundle bundle);

    void onCreateHeadView(FrameLayout container);

    void initData();

    void initView();

    void initEvent();

    void reConnect();
}
