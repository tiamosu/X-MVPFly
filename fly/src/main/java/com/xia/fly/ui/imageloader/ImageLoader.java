package com.xia.fly.ui.imageloader;

import android.support.annotation.Nullable;

import com.xia.fly.utils.Preconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author xia
 * @date 2018/9/17.
 */
@SuppressWarnings("WeakerAccess")
@Singleton
public final class ImageLoader {

    @Inject
    @Nullable
    BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader() {
    }

    /**
     * 加载图片
     */
    @SuppressWarnings("unchecked")
    public <T extends ImageConfig> void loadImage(T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.loadImage(config);
    }

    /**
     * 停止加载或清理缓存
     */
    @SuppressWarnings("unchecked")
    public <T extends ImageConfig> void clear(T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.clear(config);
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }

    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }
}
