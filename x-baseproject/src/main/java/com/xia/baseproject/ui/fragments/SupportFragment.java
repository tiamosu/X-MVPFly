package com.xia.baseproject.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.rxbus.RxBusMessage;
import com.xia.baseproject.ui.fragments.delegate.SupportFragmentDelegate;
import com.xia.baseproject.mvp.BaseMvpPresenter;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/8/1.
 */
public abstract class SupportFragment<P extends BaseMvpPresenter> extends AbstractMvpFragment<P> implements IBaseFragment {
    private final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);

    /**
     * @return 加载布局（layout）
     */
    public abstract int getLayoutId();

    /**
     * @return 是否加载头部标题栏
     */
    @Override
    public boolean isLoadHeadView() {
        return true;
    }

    /**
     * @return 是否检查网络状态，并进行提示
     */
    @Override
    public boolean isCheckNetWork() {
        return true;
    }

    /**
     * @param container 头部标题栏容器，可用于自定义添加视图
     */
    @Override
    public void onCreateHeadView(FrameLayout container) {
    }

    /**
     * 该方法确保已执行完{@link #onEnterAnimationEnd(Bundle)}
     * 于Fragment可见时执行，保证转场动画的流畅性。
     */
    @Override
    public void onVisibleLazyLoad() {
    }

    /**
     * @param bundle 用于获取页面跳转传参数据
     */
    @Override
    public void getBundleExtras(Bundle bundle) {
    }

    /**
     * 用于绑定各事件
     */
    @Override
    public void initEvent() {
    }

    /**
     * 用于订阅RxBus事件
     */
    @Override
    public void initRxBusEvent() {
    }

    /**
     * 用于网络连接恢复后加载
     */
    @Override
    public void reConnect() {
        Log.e("weixi", this + "reConnect: ");
    }

    /**
     * @param tag          RxBus订阅/发送时的标签
     * @param rxBusMessage 发送事件的信息载体
     */
    @Override
    public void handleRxBusMessage(String tag, RxBusMessage rxBusMessage) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mDelegate.onCreateView(inflater, container);
    }

    /**
     * 在{@link #loadMultipleRootFragment(int, int, ISupportFragment...)}的情况下，
     * <p>
     * 该方法将会先于{{@link #onSupportVisible()}先执行
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mDelegate.onEnterAnimationEnd();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mDelegate.onSupportVisible();
    }

    @Override
    public void onNewBundle(Bundle args) {
        mDelegate.onNewBundle(args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }

    protected void subscribeWithTags(final String... tags) {
        mDelegate.subscribeWithTags(tags);
    }

    protected void subscribeStickyWithTags(final String... tags) {
        mDelegate.subscribeStickyWithTags(tags);
    }
}
