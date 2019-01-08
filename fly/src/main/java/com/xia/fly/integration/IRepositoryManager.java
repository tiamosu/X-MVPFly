package com.xia.fly.integration;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 *
 * @author xia
 * @date 2018/9/14.
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.3">RepositoryManager wiki 官方文档</a>
 */
public interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     * 该方法默认实现会将网络请求切到 IO 线程执行
     * 说明：如果使用 Rxjava 的 merge 操作符通过该 API
     * 进行多个网络请求，将可能造成所获得的数据流错乱（因为异步了）
     * 因此此时请使用 obtainRetrofitServiceSynchronized()
     *
     * @param service Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
     */
    @NonNull
    <T> T obtainRetrofitService(@NonNull Class<T> service);

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     * 该方法默认实现会保留最原本的逻辑
     *
     * @param service Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
     */
    @NonNull
    <T> T obtainRetrofitServiceSynchronized(@NonNull Class<T> service);

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache RxCache service class
     * @param <T>   RxCache service 类型
     * @return RxCache service
     */
    @NonNull
    <T> T obtainCacheService(@NonNull Class<T> cache);

    /**
     * 清理所有缓存
     */
    void clearAllCache();

    /**
     * 获取 {@link Context}
     *
     * @return {@link Context}
     */
    @NonNull
    Context getContext();
}
