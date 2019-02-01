package com.xia.fly.ui.activities;

import android.view.MotionEvent;
import android.widget.EditText;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.KeyboardHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import me.yokeyword.fragmentation.AbstractSupportActivity;

/**
 * @author xia
 * @date 2018/8/16.
 */
public abstract class SupportActivity<P extends BaseMvpPresenter>
        extends AbstractSupportActivity implements IActivity, BaseMvpView<P> {

    private P mPresenter;
    private Cache<String, Object> mCache;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initMvp() {
        if (mPresenter == null) {
            mPresenter = newP();
            if (mPresenter != null) {
                mPresenter.attachView(this);
                getLifecycle().addObserver(mPresenter);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardHelper.dispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @SuppressWarnings("unchecked")
    protected P getP() {
        return mPresenter == null ? newP() : mPresenter;
    }

    @Override
    public AppCompatActivity getContext() {
        return this;
    }

    @Override
    public boolean isCheckNetWork() {
        return false;
    }

    @Override
    public void onNetworkState(boolean isAvailable) {
    }

    @Override
    public void onNetReConnect() {
    }

    @Override
    public boolean isDispatchTouchHideKeyboard() {
        return true;
    }

    @Override
    public void onDispatchTouchHideKeyboard(EditText editText) {
    }

    protected void subscribeWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeWithTags(this, callback, tags);
    }

    protected void subscribeStickyWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeStickyWithTags(this, callback, tags);
    }
}
