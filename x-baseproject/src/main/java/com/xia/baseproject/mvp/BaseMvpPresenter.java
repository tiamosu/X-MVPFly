package com.xia.baseproject.mvp;

import com.xia.baseproject.mvp.common.IMvpPresenter;
import com.xia.baseproject.mvp.nullobject.MvpNullObjectBasePresenter;

/**
 * @author xia
 * @date 2018/7/19.
 */
public abstract class BaseMvpPresenter<V extends BaseMvpView>
        extends MvpNullObjectBasePresenter<V> implements IMvpPresenter<V> {
}
