package com.xia.baseproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.mvp.BaseMvpView;

/**
 * @author xia
 * @date 2018/7/19.
 */
public abstract class AbstractMvpFragment<P extends BaseMvpPresenter>
        extends AbstractSupportFragment implements BaseMvpView<P> {

    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMvp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @SuppressWarnings("unchecked")
    public void initMvp() {
        if (mPresenter == null) {
            mPresenter = newP();
            if (mPresenter != null) {
                mPresenter.attachView(this);
                getLifecycle().addObserver(mPresenter);
            }
        }
    }

    protected P getP() {
        return mPresenter == null ? newP() : mPresenter;
    }

    @Override
    public FragmentActivity getContext() {
        return super.getContext();
    }

    @Override
    public AutoDisposeConverter bindLifecycle() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this));
    }
}
