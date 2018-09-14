package com.xia.baseproject.integration;

import android.app.Application;
import android.content.Context;

import com.xia.baseproject.integration.cache.Cache;
import com.xia.baseproject.integration.cache.CacheType;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @author xia
 * @date 2018/9/14.
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

    @Inject
    Lazy<Retrofit> mRetrofit;
    //    @Inject
//    Lazy<RxCache> mRxCache;
    @Inject
    Application mApplication;
    @Inject
    Cache.Factory mCachefactory;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;

    @Inject
    public RepositoryManager() {
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    @Override
    public synchronized <T> T obtainRetrofitService(Class<T> serviceClass) {
        return createWrapperService(serviceClass);
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    @SuppressWarnings("unchecked")
    private <T> T createWrapperService(Class<T> serviceClass) {
        // 通过二次代理，对 Retrofit 代理方法的调用包进新的 Observable 里在 io 线程执行。
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    if (method.getReturnType() == Observable.class) {
                        // 如果方法返回值是 Observable 的话，则包一层再返回
                        return Observable.defer(() -> {
                            final T service = getRetrofitService(serviceClass);
                            // 执行真正的 Retrofit 动态代理的方法
                            return ((Observable) getRetrofitMethod(service, method)
                                    .invoke(service, args))
                                    .subscribeOn(Schedulers.io());
                        }).subscribeOn(Schedulers.single());
                    }
                    // 返回值不是 Observable 的话不处理
                    final T service = getRetrofitService(serviceClass);
                    return getRetrofitMethod(service, method).invoke(service, args);
                });
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    @SuppressWarnings("unchecked")
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cacheClass Cache class
     * @param <T>        Cache class
     * @return Cache
     */
    @SuppressWarnings("unchecked")
    @Override
    public synchronized <T> T obtainCacheService(Class<T> cacheClass) {
        if (mCacheServiceCache == null) {
            mCacheServiceCache = mCachefactory.build(CacheType.CACHE_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(mCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) mCacheServiceCache.get(cacheClass.getCanonicalName());
        if (cacheService == null) {
//            cacheService = mRxCache.get().using(cacheClass);
//            mCacheServiceCache.put(cacheClass.getCanonicalName(), cacheService);
        }
        return cacheService;
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {
//        mRxCache.get().evictAll().subscribe();
    }

    @Override
    public Context getContext() {
        return mApplication;
    }
}
