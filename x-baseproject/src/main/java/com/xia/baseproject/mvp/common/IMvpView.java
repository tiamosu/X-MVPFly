package com.xia.baseproject.mvp.common;

import android.support.v4.app.FragmentActivity;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * @author xia
 * @date 2018/7/19.
 */
public interface IMvpView<P> {

    P newP();

    FragmentActivity getContext();

    AutoDisposeConverter bindLifecycle();
}
