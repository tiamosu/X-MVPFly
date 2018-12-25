package com.xia.fly.ui.activities;

import android.app.Activity;
import android.widget.EditText;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.LruCache;

import androidx.annotation.NonNull;

/**
 * @author xia
 * @date 2018/9/11.
 */
public interface IActivity {

    /**
     * 提供在 {@link Activity} 生命周期内的缓存容器, 可向此 {@link Activity} 存取一些必要的数据
     * 此缓存容器和 {@link Activity} 的生命周期绑定, 如果 {@link Activity} 在屏幕旋转或者配置更改的情况下
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
     * {@link SupportActivity#initData()}
     * {@link SupportActivity#initView()}
     * {@link SupportActivity#initEvent()}
     * 之后，可用于加载网络数据等
     */
    void onLazyLoadData();

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

    /**
     * @return 是否点击空白区域隐藏软键盘，默认为true
     */
    boolean isDispatchTouchHideKeyboard();

    /**
     * 点击空白区域，隐藏软键盘时，EditText进行相关设置
     */
    void onDispatchTouchHideKeyboard(EditText editText);
}
