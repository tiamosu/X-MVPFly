package com.xia.fly.ui.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.ui.activities.delegate.SupportActivityDelegate;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.KeyboardHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.yokeyword.fragmentation.AbstractSupportActivity;

/**
 * @author xia
 * @date 2018/8/16.
 */
@SuppressWarnings("unused")
public abstract class SupportActivity<P extends BaseMvpPresenter>
        extends AbstractSupportActivity implements IActivity, BaseMvpView<P> {

    private final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    private P mPresenter;
    private Cache<String, Object> mCache;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.Companion.getACTIVITY_CACHE());
        }
        return mCache;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
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
            getLifecycle().removeObserver(mPresenter);
        }
        mDelegate.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KeyboardHelper.dispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @SuppressWarnings("unchecked")
    protected P getP() {
        return mPresenter;
    }

    @NotNull
    @Override
    public AppCompatActivity getContext() {
        return this;
    }

    @Override
    public void onCreateTitleBar(@NotNull FrameLayout titleBarContainer) {
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
    public void onDispatchTouchHideKeyboard(@NotNull EditText editText) {
    }

    protected void subscribeWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeWithTags(this, callback, tags);
    }

    protected void subscribeStickyWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeStickyWithTags(this, callback, tags);
    }
}
