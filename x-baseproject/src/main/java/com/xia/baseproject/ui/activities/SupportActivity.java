package com.xia.baseproject.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.EditText;

import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.rxbus.IRxBusCallback;
import com.xia.baseproject.rxbus.RxBusHelper;
import com.xia.baseproject.rxhttp.utils.Platform;
import com.xia.baseproject.ui.activities.delegate.SupportActivityDelegate;

import io.reactivex.functions.Action;

/**
 * @author xia
 * @date 2018/8/16.
 */
public abstract class SupportActivity<P extends BaseMvpPresenter>
        extends AbstractMvpActivity<P> implements IBaseActivity {

    private final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    public String mClsTag;

    /**
     * @return 加载布局（layout）
     */
    public abstract int getLayoutId();

    /**
     * @return 是否检查网络状态，默认为true
     */
    @Override
    public boolean isCheckNetWork() {
        return true;
    }

    /**
     * @return 是否点击空白区域隐藏软键盘
     */
    @Override
    public boolean isDispatchTouchHideKeyboard() {
        return true;
    }

    /**
     * @param editText 所点击的输入框
     */
    @Override
    public void dispatchTouchHideKeyboard(EditText editText, MotionEvent event) {
    }

    /**
     * 该方法执行于{@link #setContentView(int)}之前
     */
    @Override
    public void onBeforeCreateView() {
    }

    /**
     * 该方法执行于{@link #initData()}
     * {@link #initView()}
     * {@link #initEvent()}
     * 之后，可用于网络数据请求操作
     */
    @Override
    public void onVisibleLazyLoad() {
    }

    /**
     * 初始化事件绑定
     */
    @Override
    public void initEvent() {
    }

    /**
     * @param isAvailable 网络是否连接可用
     */
    @Override
    public void onNetworkState(boolean isAvailable) {
    }

    /**
     * 用于网络连接恢复后加载
     */
    @Override
    public void onNetReConnect() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClsTag = getClass().getSimpleName();
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    /**
     * 处理控制 点击屏幕空白区域隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDelegate.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    protected void subscribeWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeWithTags(this, callback, tags);
    }

    protected void subscribeStickyWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeStickyWithTags(this, callback, tags);
    }

    protected void UIExecute(final Action action) {
        Platform.post(action);
    }
}
