package com.xia.baseproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.mvp.BaseMvpView;

/**
 * @author xia
 * @date 2018/7/19.
 */
public abstract class AbstractMvpFragment<P extends BaseMvpPresenter> extends AbstractSupportFragment implements BaseMvpView<P> {
    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMvp();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initMvp() {
        if (mPresenter == null) {
            mPresenter = newP();
            if (mPresenter != null) {
                mPresenter.attachView(this);
            }
        }
    }

    protected P getP() {
        return mPresenter == null ? newP() : mPresenter;
    }

    @Override
    public Fragment getBaseFragment() {
        return this;
    }

    @Override
    public FragmentActivity getContext() {
        return super.getContext();
    }

    @Override
    public LifecycleTransformer bindUntilEvent() {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }
}
