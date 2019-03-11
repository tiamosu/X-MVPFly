package com.xia.fly.di.module

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xia.fly.di.named.ActivityLifecycleNamed
import com.xia.fly.integration.ActivityLifecycle
import com.xia.fly.integration.FragmentLifecycle
import com.xia.fly.integration.IRepositoryManager
import com.xia.fly.integration.RepositoryManager
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.CacheType
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

/**
 * 提供一些框架必须的实例的 [Module]
 *
 * @author xia
 * @date 2018/9/14.
 */
@Suppress("unused")
@Module
abstract class AppModule {

    @Binds
    abstract fun bindRepositoryManager(repositoryManager: RepositoryManager): IRepositoryManager

    @Binds
    @ActivityLifecycleNamed
    abstract fun bindActivityLifecycle(activityLifecycle: ActivityLifecycle): Application.ActivityLifecycleCallbacks

    @Binds
    abstract fun bindFragmentLifecycle(fragmentLifecycle: FragmentLifecycle): FragmentManager.FragmentLifecycleCallbacks

    interface GsonConfiguration {
        fun configGson(context: Context, builder: GsonBuilder)
    }

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideGson(application: Application, configuration: GsonConfiguration?): Gson {
            val builder = GsonBuilder()
            configuration?.configGson(application, builder)
            return builder.create()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideExtras(cacheFactory: Cache.Factory<String, Any>): Cache<String, Any> {
            return cacheFactory.build(CacheType.EXTRAS)
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideFragmentLifecycles(): MutableList<FragmentManager.FragmentLifecycleCallbacks> {
            return ArrayList()
        }
    }
}
