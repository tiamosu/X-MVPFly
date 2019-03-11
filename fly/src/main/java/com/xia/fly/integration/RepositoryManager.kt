package com.xia.fly.integration

import android.app.Application
import android.content.Context
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.CacheType
import com.xia.fly.utils.Preconditions
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 *
 * @author xia
 * @date 2018/9/14.
 * @see [RepositoryManager wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.2.3)
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
    internal var mCacheFactory: Cache.Factory<String, Any>? = null

    private var mRetrofitServiceCache: Cache<String, Any>? = null
    private var mCacheServiceCache: Cache<String, Any>? = null

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service ApiService class
     * @param <T>          ApiService class
     * @return ApiService
    </T> */
    @Synchronized
    override fun <T> obtainRetrofitService(service: Class<T>): T {
        return createWrapperService(service)
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
    </T> */
    private fun <T> createWrapperService(serviceClass: Class<T>): T {
        Preconditions.checkNotNull<Any>(serviceClass, "serviceClass == null")

        // 二次代理
        return Proxy.newProxyInstance(serviceClass.classLoader,
                arrayOf<Class<*>>(serviceClass), InvocationHandler { proxy, method, args ->
            // 此处在调用 serviceClass 中的方法时触发
            if (method.returnType == Observable::class.java) {
                // 如果方法返回值是 Observable 的话，则包一层再返回。
                // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
                // 如此对原项目的影响为 0，且更可控。
                return@InvocationHandler Observable.defer {
                    val service = getRetrofitService(serviceClass)
                    // 执行真正的 Retrofit 动态代理的方法
                    getRetrofitMethod(service, method)
                            .invoke(service, *args) as Observable<*>
                }
            } else if (method.returnType == Single::class.java) {
                // 如果方法返回值是 Single 的话，则包一层再返回。
                return@InvocationHandler Single.defer {
                    val service = getRetrofitService(serviceClass)
                    // 执行真正的 Retrofit 动态代理的方法
                    getRetrofitMethod(service, method)
                            .invoke(service, *args) as Single<*>
                }
            }

            // 返回值不是 Observable 或 Single 的话不处理。
            val service = getRetrofitService(serviceClass)
            getRetrofitMethod(service, method).invoke(service, *args)
        }) as T
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
    </T> */
    private fun <T> getRetrofitService(serviceClass: Class<T>): T {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory!!.build(CacheType.RETROFIT_SERVICE_CACHE)
        }
        Preconditions.checkNotNull<Any>(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method")
        var retrofitService = mRetrofitServiceCache!![serviceClass.canonicalName!!] as T?
        if (retrofitService == null) {
            retrofitService = mRetrofit!!.get().create(serviceClass)
            mRetrofitServiceCache!!.put(serviceClass.canonicalName!!, retrofitService!!)
        }
        return retrofitService
    }

    @Throws(NoSuchMethodException::class)
    private fun <T> getRetrofitMethod(service: T, method: Method): Method {
        return (service as Any).javaClass.getMethod(method.name, *method.parameterTypes)
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache Cache class
     * @param <T>        Cache class
     * @return Cache
    </T> */
    @Synchronized
    override fun <T> obtainCacheService(cache: Class<T>): T {
        Preconditions.checkNotNull<Any>(cache, "cacheClass == null")

        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCacheFactory!!.build(CacheType.CACHE_SERVICE_CACHE)
        }
        Preconditions.checkNotNull<Any>(mCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method")
        var cacheService = mCacheServiceCache!![cache.canonicalName!!] as T?
        if (cacheService == null) {
            cacheService = mRxCache!!.get().using(cache)
            mCacheServiceCache!!.put(cache.canonicalName!!, cacheService!!)
        }
        return cacheService
    }

    /**
     * 清理所有缓存
     */
    override fun clearAllCache() {
        mRxCache!!.get().evictAll().subscribe()
    }

    override fun getContext(): Context {
        return mApplication!!
    }
}
