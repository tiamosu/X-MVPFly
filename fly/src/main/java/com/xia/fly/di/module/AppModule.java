package com.xia.fly.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xia.fly.integration.IRepositoryManager;
import com.xia.fly.integration.RepositoryManager;
import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author xia
 * @date 2018/9/14.
 */
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

    @Singleton
    @Provides
    static Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        //noinspection unchecked
        return cacheFactory.build(CacheType.EXTRAS);
    }

//    @Binds
//    @Named("ActivityLifecycle")
//    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

//    @Binds
//    @Named("ActivityLifecycleForRxLifecycle")
//    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycleForRxLifecycle(ActivityLifecycleForRxLifecycle activityLifecycleForRxLifecycle);

//    @Binds
//    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

//    @Singleton
//    @Provides
//    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
//        return new ArrayList<>();
//    }

    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }
}
