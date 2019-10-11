package com.xia.fly.integration

import android.app.Application
import android.content.Context
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.CacheType
import com.xia.fly.utils.Preconditions
import dagger.Lazy
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.lang.reflect.Proxy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * @see [RepositoryManager wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki#2.3)
 *
 * @author xia
 * @date 2018/9/14.
 */
@Suppress("UNCHECKED_CAST")
@Singleton
class RepositoryManager @Inject
constructor() : IRepositoryManager {

    @JvmField
    @Inject
    internal var mRetrofit: Lazy<Retrofit>? = null
    @JvmField
    @Inject
    internal var mRxCache: Lazy<RxCache>? = null
    @JvmField
    @Inject
    internal var mApplication: Application? = null
    @JvmField
    @Inject
    internal var mCacheFactory: Cache.Factory<String, Any?>? = null
    @JvmField
    @Inject
    internal var mObtainServiceDelegate: IRepositoryManager.ObtainServiceDelegate? = null

    private var mRetrofitServiceCache: Cache<String, Any?>? = null
    private var mCacheServiceCache: Cache<String, Any?>? = null

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    @Synchronized
    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T? {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory?.build(CacheType.RETROFIT_SERVICE_CACHE)
        }
        Preconditions.checkNotNull<Any>(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method")

        val canonicalName = serviceClass.canonicalName ?: ""
        var retrofitService = mRetrofitServiceCache?.get(canonicalName) as T?
        if (retrofitService == null) {
            retrofitService = mObtainServiceDelegate?.createRetrofitService(mRetrofit?.get(), serviceClass)
            if (retrofitService == null) {
                retrofitService = Proxy.newProxyInstance(
                        serviceClass.classLoader, arrayOf<Class<*>>(serviceClass),
                        RetrofitServiceProxyHandler(mRetrofit?.get(), serviceClass)) as? T
            }
            mRetrofitServiceCache?.put(canonicalName, retrofitService)
        }
        return retrofitService
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache Cache class
     * @param <T>        Cache class
     * @return Cache
     */
    @Synchronized
    override fun <T> obtainCacheService(cache: Class<T>): T? {
        Preconditions.checkNotNull<Any>(cache, "cacheClass == null")
        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCacheFactory?.build(CacheType.CACHE_SERVICE_CACHE)
        }
        Preconditions.checkNotNull<Any>(mCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method")

        val canonicalName = cache.canonicalName ?: ""
        var cacheService = mCacheServiceCache?.get(canonicalName) as T?
        if (cacheService == null) {
            cacheService = mRxCache?.get()?.using(cache)
            mCacheServiceCache?.put(canonicalName, cacheService)
        }
        return cacheService
    }

    /**
     * 清理所有缓存
     */
    override fun clearAllCache() {
        mRxCache?.get()?.evictAll()?.subscribe()
    }

    override fun getContext(): Context {
        return mApplication!!
    }
}
