package com.xia.baseproject.ui.activities;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.AppUtils;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.receiver.NetworkChangeReceiver;
import com.xia.baseproject.rxbus.IRxBusCallback;
import com.xia.baseproject.rxbus.RxBusHelper;
import com.xia.baseproject.rxhttp.AutoDisposable;

import butterknife.ButterKnife;

/**
 * @author xia
 * @date 2018/8/16.
 */
public abstract class SupportActivity<P extends BaseMvpPresenter> extends AbstractMvpActivity<P> {

    /**
     * @return 加载布局（layout）
     */
    public abstract int getLayoutId();

    /**
     * 该方法执行于{@link #setContentView(int)}之前
     */
    protected void onCreateConfiguration() {
    }

    /**
     * @return 是否点击空白区域隐藏软键盘
     */
    protected boolean isDispatchTouchHideKeyboard() {
        return true;
    }

    protected void dispatchTouchHideKeyboard(EditText editText, MotionEvent event) {
    }

    /**
     * 网络状态监听广播
     */
    private NetworkChangeReceiver mNetworkChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //APP被杀死时进行重启
        if (savedInstanceState != null) {
            AppUtils.relaunchApp();
            return;
        }
        onCreateConfiguration();
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        if (Rest.getConfiguration(RestConfigKeys.NETWORK_CHECK)) {
            netWorkChangeReceiver();
        }
    }

    @Override
    protected void onDestroy() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver);
        }
        final String httpTag = getClass().getSimpleName();
        AutoDisposable.getInstance().remove(httpTag);
        Rest.getHandler().removeCallbacksAndMessages(null);
        RxBusHelper.unregister(this);
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    private void netWorkChangeReceiver() {
        //注册网络状态监听广播
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkChangeReceiver, filter);
    }

    protected void subscribeWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeWithTags(this, callback, tags);
    }

    protected void subscribeStickyWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeStickyWithTags(this, callback, tags);
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isDispatchTouchHideKeyboard() &&
                ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                final IBinder iBinder = v.getWindowToken();
                final InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (iBinder != null && imm != null) {
                    imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            final EditText editText = (EditText) v;
            dispatchTouchHideKeyboard(editText, event);
            final int[] l = {0, 0};
            v.getLocationInWindow(l);
            final int left = l[0];
            final int top = l[1];
            final int bottom = top + v.getHeight();
            final int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
