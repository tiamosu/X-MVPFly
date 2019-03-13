package com.xia.fly.ui.imageloader

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.xia.fly.utils.FlyUtils
import com.xia.fly.utils.Preconditions
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author xia
 * @date 2018/9/17.
 */
@Singleton
class ImageLoader @Inject constructor() {

    @JvmField
    @Inject
    internal var mStrategy: BaseImageLoaderStrategy<*>? = null

    /**
     * 加载图片
     */
    fun <T : ImageConfig> loadImage(context: Context, config: T) {
        Preconditions.checkNotNull<Any>(mStrategy, "Please implement BaseImageLoaderStrategy "
                + "and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule")
        this.mStrategy!!.loadImage(context, config)
    }

    /**
     * 停止加载或清理缓存
     */
    fun <T : ImageConfig> clear(context: Context, config: T) {
        Preconditions.checkNotNull<Any>(mStrategy, "Please implement BaseImageLoaderStrategy "
                + "and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule")
        this.mStrategy!!.clear(context, config)
    }

    companion object {

        @JvmStatic
        fun <T : ImageConfig> loadImage(config: T) {
            FlyUtils.getAppComponent().imageLoader()
                    .loadImage(Utils.getApp(), config)
        }

        @JvmStatic
        fun <T : ImageConfig> clear(config: T) {
            FlyUtils.getAppComponent().imageLoader()
                    .clear(Utils.getApp(), config)
        }
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     * FlyUtils.getAppComponent().imageLoader().setLoadImgStrategy(strategy)
     */
    fun setLoadImgStrategy(strategy: BaseImageLoaderStrategy<*>) {
        Preconditions.checkNotNull(strategy, "strategy == null")
        this.mStrategy = strategy
    }

    /**
     * FlyUtils.getAppComponent().imageLoader().getLoadImgStrategy()
     */
    fun getLoadImgStrategy(): BaseImageLoaderStrategy<*> {
        return mStrategy!!
    }
}
