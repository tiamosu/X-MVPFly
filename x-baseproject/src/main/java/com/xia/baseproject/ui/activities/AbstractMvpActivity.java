package com.xia.baseproject.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.mvp.BaseMvpView;

/**
 * @author xia
 * @date 2018/8/16.
 */
public abstract class AbstractMvpActivity<P extends BaseMvpPresenter>
        extends AbstractSupportActivity implements BaseMvpView<P> {

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMvp();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    private void initMvp() {
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
    public AppCompatActivity getContext() {
        return this;
    }
}
