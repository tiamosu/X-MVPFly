package com.xia.fly.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.xia.fly.ui.imageloader.BaseImageLoaderStrategy;

/**
 * 如果你想具有配置 @{@link Glide} 的权利,则需要让 {@link BaseImageLoaderStrategy}
 * 的实现类也必须实现 {@link GlideAppliesOptions}
 *
 * @author xia
 * @date 2018/9/17.
 */
@SuppressWarnings("WeakerAccess")
public interface GlideAppliesOptions {

    /**
     * 配置 @{@link Glide} 的自定义参数,此方法在 @{@link Glide} 初始化时执行(@{@link Glide} 在第一次被调用时初始化),只会执行一次
     *
     * @param context
     * @param builder {@link GlideBuilder} 此类被用来创建 Glide
     */
    void applyGlideOptions(@NonNull Context context, @NonNull GlideBuilder builder);
}
