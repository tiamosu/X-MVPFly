package com.xia.fly.integration;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.xia.fly.base.IAppLifecycles;
import com.xia.fly.di.module.GlobalConfigModule;

import java.util.List;

/**
 * {@link ConfigModule} 可以给框架配置一些参数,需要实现 {@link ConfigModule} 后,在 AndroidManifest 中声明该实现类
 *
 * @author xia
 * @date 2018/9/14.
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.1">ConfigModule wiki 官方文档</a>
 */
public interface ConfigModule {

    /**
     * 使用{@link GlobalConfigModule.Builder}给框架配置一些配置参数
     */
    void applyOptions(Context context, GlobalConfigModule.Builder builder);

    /**
     * 使用{@link IAppLifecycles}在Application的生命周期中注入一些操作
     */
    void injectAppLifecycle(Context context, List<IAppLifecycles> lifecycles);

    /**
     * 使用{@link Application.ActivityLifecycleCallbacks}在Activity的生命周期中注入一些操作
     */
    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles);

    /**
     * 使用{@link FragmentManager.FragmentLifecycleCallbacks}在Fragment的生命周期中注入一些操作
     */
    void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}
