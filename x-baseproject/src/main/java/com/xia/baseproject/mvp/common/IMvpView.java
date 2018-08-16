package com.xia.baseproject.mvp.common;

import android.content.Context;

/**
 * @author xia
 * @date 2018/7/19.
 */
public interface IMvpView<P> {

    P newP();

    Context getContext();
}
