package com.xia.baseproject.ui.imageloader;

import android.content.Context;

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
     * @param ctx    上下文
     * @param config 图片加载配置
     */
    void loadImage(Context ctx, T config);

    /**
     * 停止加载
     *
     * @param ctx    上下文
     * @param config 图片加载配置
     */
    void clear(Context ctx, T config);
}
