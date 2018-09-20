package com.xia.fly.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.LruCache;

/**
 * @author xia
 * @date 2018/8/1.
 */
@SuppressWarnings("WeakerAccess")
public interface IFragment {

    /**
     * 提供在 {@link Fragment} 生命周期内的缓存容器, 可向此 {@link Fragment} 存取一些必要的数据
     * 此缓存容器和 {@link Fragment} 的生命周期绑定, 如果 {@link Fragment} 在屏幕旋转或者配置更改的情况下
     * 重新创建, 那此缓存容器中的数据也会被清空, 如果你想避免此种情况请使用 <a href="https://github.com/JessYanCoding/LifecycleModel">LifecycleModel</a>
     *
     * @return like {@link LruCache}
     */
    @NonNull
    Cache<String, Object> provideCache();

    /**
     * @return 用于布局加载
     * 如果{@link #getLayoutId()}返回0，则不会生产视图
     */
    int getLayoutId();

    /**
     * 用于初始化MVP
     */
    void initMvp();

    /**
     * 用于初始化数据
     */
    void initData();

    /**
     * 用于初始化View
     */
    void initView();

    /**
     * 用于初始化事件
     */
    void initEvent();

    /**
     * 该方法执行于
     * {@link SupportFragment#initData()}
     * {@link SupportFragment#initView()}
     * {@link SupportFragment#initEvent()}
     * 之后，可用于加载网络数据等
     */
    void onLazyLoadData();

    /**
     * 该方法确保已执行完{@link SupportFragment#onEnterAnimationEnd(Bundle)}
     * 保证转场动画的流畅性。
     * 可见时执行
     * 并执行于
     * {@link SupportFragment#initData()}
     * {@link SupportFragment#initView()}
     * {@link SupportFragment#initEvent()}
     * 之后
     */
    void onVisibleLazyLoad();

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
