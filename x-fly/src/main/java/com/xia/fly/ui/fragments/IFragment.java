package com.xia.fly.ui.fragments;

import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * @author xia
 * @date 2018/8/1.
 */
@SuppressWarnings("WeakerAccess")
public interface IFragment {

    /**
     * @return 是否加载头部标题栏，默认为true
     */
    boolean isLoadHeadView();

    /**
     * @param titleBarContainer 头部标题栏容器，可用于自定义添加视图
     */
    void onCreateTitleBar(FrameLayout titleBarContainer);

    /**
     * @param bundle 用于获取页面跳转传参数据
     */
    void getBundleExtras(Bundle bundle);

    /**
     * 该方法确保已执行完{@link SupportFragment#onEnterAnimationEnd(Bundle)}
     * 保证转场动画的流畅性。
     * 并执行于
     * {@link SupportFragment#initData()}
     * {@link SupportFragment#initView()}
     * {@link SupportFragment#initEvent()}
     * 之后，可用于网路数据请求等
     */
    void onVisibleLazyLoad();

    /**
     * @return 是否检查网络状态，默认为true
     */
    boolean isCheckNetWork();

    /**
     * @param isAvailable 网络是否连接可用
     */
    void onNetworkState(boolean isAvailable);

    /**
     * 用于网络连接恢复后加载
     */
    void onNetReConnect();
}
