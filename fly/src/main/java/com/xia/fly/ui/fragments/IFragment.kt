package com.xia.fly.ui.fragments

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.LruCache

/**
 * @author xia
 * @date 2018/8/1.
 */
interface IFragment {

    /**
     * @return 用于布局加载
     * 如果[getLayoutId]返回0，则不会生产视图
     */
    fun getLayoutId(): Int

    /**
     * @return 是否检查网络状态，默认为true
     */
    fun isCheckNetWork(): Boolean

    /**
     * 提供在 [Fragment] 生命周期内的缓存容器, 可向此 [Fragment] 存取一些必要的数据
     * 此缓存容器和 [Fragment] 的生命周期绑定, 如果 [Fragment] 在屏幕旋转或者配置更改的情况下
     * 重新创建, 那此缓存容器中的数据也会被清空, 如果你想避免此种情况请使用 [LifecycleModel](https://github.com/JessYanCoding/LifecycleModel)
     *
     * @return like [LruCache]
     */
    fun provideCache(): Cache<String, Any>

    /**
     * 用于初始化MVP
     */
    fun initMvp()

    /**
     * 用于初始化数据
     */
    fun initData()

    /**
     * 用于初始化View
     */
    fun initView()

    /**
     * 用于初始化事件
     */
    fun initEvent()

    /**
     * 该方法执行于
     * [FlySupportFragment.initData]
     * [FlySupportFragment.initView]
     * [FlySupportFragment.initEvent]
     * 之后，可用于加载网络数据等
     */
    fun onLazyLoadData()

    /**
     * 该方法确保已执行完[FlySupportFragment.onEnterAnimationEnd]
     * 保证转场动画的流畅性。
     * 可见时执行
     * 并执行于
     * [FlySupportFragment.initData]
     * [FlySupportFragment.initView]
     * [FlySupportFragment.initEvent]
     * 之后
     */
    fun onVisibleLazyLoad()

    /**
     *是否加载TitleBar容器[onCreateTitleBar]
     */
    fun isLoadTitleBar(): Boolean

    /**
     * @param titleBarContainer 头部标题栏容器，可用于自定义添加视图
     */
    fun onCreateTitleBar(titleBarContainer: FrameLayout)

    /**
     * @param bundle 用于获取页面跳转传参数据
     */
    fun getBundleExtras(bundle: Bundle)

    /**
     * @param isAvailable 网络是否连接可用
     */
    fun onNetworkState(isAvailable: Boolean)

    /**
     * 用于网络连接恢复后加载
     */
    fun onNetReConnect()
}
