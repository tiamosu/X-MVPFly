package com.xia.fly.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xia.fly.di.named.ActivityLifecycleNamed;
import com.xia.fly.integration.ActivityLifecycle;
import com.xia.fly.integration.FragmentLifecycle;
import com.xia.fly.integration.IRepositoryManager;
import com.xia.fly.integration.RepositoryManager;
import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 提供一些框架必须的实例的 {@link Module}
 *
 * @author xia
 * @date 2018/9/14.
 */
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        final GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

    @Singleton
    @Provides
    static Cache<String, Object> provideExtras(Cache.Factory<String, Object> cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

    @Binds
    @ActivityLifecycleNamed
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
        return new ArrayList<>();
    }

    public interface GsonConfiguration {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }
}
