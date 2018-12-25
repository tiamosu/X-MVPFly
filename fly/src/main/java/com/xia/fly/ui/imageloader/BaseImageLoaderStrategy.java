package com.xia.fly.ui.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * 图片加载策略,实现 {@link BaseImageLoaderStrategy}
 * 并通过 {@link ImageLoader#setLoadImgStrategy(BaseImageLoaderStrategy)} 配置后,才可进行图片请求
 *
 * @author xia
 * @date 2018/9/17.
 */
@SuppressWarnings("WeakerAccess")
public interface BaseImageLoaderStrategy<T extends ImageConfig> {

    /**
     * 加载图片
     *
     * @param context {@link Context}
     * @param config  图片加载配置信息
     */
    void loadImage(@Nullable Context context, @Nullable T config);

    /**
     * 停止加载
     *
     * @param context {@link Context}
     * @param config  图片加载配置信息
     */
    void clear(@Nullable Context context, @Nullable T config);
}
