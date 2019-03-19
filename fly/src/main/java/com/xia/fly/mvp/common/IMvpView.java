package com.xia.fly.mvp.common;

import android.content.Context;

/**
 * @author weixia
 * @date 2019/3/19.
 */
public interface IMvpView<P> {

    P newP();

    Context getContext();
}
