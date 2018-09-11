package com.xia.baseproject.ui.activities.delegate;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.constant.NetworkState;
import com.xia.baseproject.receiver.NetworkChangeReceiver;
import com.xia.baseproject.rxbus.RxBusHelper;
import com.xia.baseproject.ui.activities.SupportActivity;
import com.xia.baseproject.utils.NetworkHelper;

import butterknife.ButterKnife;

/**
 * @author xia
 * @date 2018/8/16.
 */
public class SupportActivityDelegate {
    private SupportActivity mActivity;

    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络状态监听广播
    private NetworkChangeReceiver mNetworkChangeReceiver;
    //网络是否重新连接
    private boolean mNetReConnect;

    public SupportActivityDelegate(SupportActivity activity) {
        mActivity = activity;
    }

    public void onCreate() {
        mActivity.onBeforeCreateView();
        if (mActivity.getLayoutId() > 0) {
            mActivity.setContentView(mActivity.getLayoutId());
            ButterKnife.bind(mActivity);
        }
        if (Rest.getConfiguration(RestConfigKeys.NETWORK_CHECK)) {
            netWorkChangeReceiver();
        }

        initAll();
    }

    public void onResume() {
        if (NetworkHelper.isGlobalCheckNetwork(mActivity.isCheckNetWork())) {
            hasNetWork(NetworkUtils.isConnected());
        }
    }

    public void onDestroy() {
        if (mNetworkChangeReceiver != null) {
            mActivity.unregisterReceiver(mNetworkChangeReceiver);
        }
        Rest.getHandler().removeCallbacksAndMessages(null);
        RxBusHelper.unregister(mActivity);
    }

    private void initAll() {
        initNetworkChangeEvent();
        mActivity.initData();
        mActivity.initView();
        mActivity.initEvent();
        mActivity.onVisibleLazyLoad();
    }

    private void initNetworkChangeEvent() {
        if (NetworkHelper.isGlobalCheckNetwork(mActivity.isCheckNetWork())) {
            NetworkHelper.networkChangeEvent(mActivity, rxBusMessage -> {
                final boolean isAvailable = (boolean) rxBusMessage.mObj;
                hasNetWork(isAvailable);
            });
        }
    }

    private void hasNetWork(boolean isAvailable) {
        final int currentNetStatus = isAvailable ? NetworkState.NETWORK_ON : NetworkState.NETWORK_OFF;
        if (currentNetStatus != mLastNetStatus || mNetReConnect) {
            //判断网络是否是重连接的
            if (isAvailable && mLastNetStatus == NetworkState.NETWORK_OFF) {
                mNetReConnect = true;
            }
            //APP位于前台并且当前页处于栈顶（可见）时执行
            if (AppUtils.isAppForeground()
                    && ActivityUtils.getTopActivity() == mActivity) {
                mActivity.onNetworkState(isAvailable);
                if (isAvailable && mNetReConnect) {
                    mActivity.onNetReConnect();
                    mNetReConnect = false;
                }
                mLastNetStatus = currentNetStatus;
            }
        }
    }

    private void netWorkChangeReceiver() {
        //注册网络状态监听广播
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mActivity.registerReceiver(mNetworkChangeReceiver, filter);
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    public void dispatchTouchEvent(MotionEvent ev) {
        if (mActivity.isDispatchTouchHideKeyboard() &&
                ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = mActivity.getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                final IBinder iBinder = v.getWindowToken();
                final InputMethodManager imm = (InputMethodManager)
                        mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (iBinder != null && imm != null) {
                    imm.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            final EditText editText = (EditText) v;
            mActivity.dispatchTouchHideKeyboard(editText, event);
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
