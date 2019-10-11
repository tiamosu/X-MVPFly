package com.xia.fly.integration

import android.content.Context
import androidx.annotation.Nullable
import retrofit2.Retrofit

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * @see [RepositoryManager wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki#2.3)
 *
 * @author xia
 * @date 2018/9/14.
 */
interface IRepositoryManager {

    /**
     * 获取 [Context]
     *
     * @return [Context]
     */
    fun getContext(): Context

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
    </T> */
    fun <T> obtainRetrofitService(serviceClass: Class<T>): T?

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache RxCache service class
     * @param <T>   RxCache service 类型
     * @return RxCache service
    </T> */
    fun <T> obtainCacheService(cache: Class<T>): T?

    /**
     * 清理所有缓存
     */
    fun clearAllCache()

    interface ObtainServiceDelegate {
        @Nullable
        fun <T> createRetrofitService(retrofit: Retrofit?, serviceClass: Class<T>?): T?
    }
}
