package com.xia.fly.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.xia.fly.imageloader.integration.GlideApp;
import com.xia.fly.imageloader.integration.GlideRequest;
import com.xia.fly.imageloader.integration.GlideRequests;
import com.xia.fly.ui.imageloader.BaseImageLoaderStrategy;
import com.xia.fly.ui.imageloader.ImageConfig;
import com.xia.fly.utils.Platform;
import com.xia.fly.utils.Preconditions;

import io.reactivex.schedulers.Schedulers;

/**
 * 此类只是简单的实现了 Glide 加载的策略,方便快速使用,但大部分情况会需要应对复杂的场景
 * 这时可自行实现 {@link BaseImageLoaderStrategy} 和 {@link ImageConfig} 替换现有策略
 *
 * @author xia
 * @date 2018/9/17.
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    @Override
    public void loadImage(Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");

        final GlideRequests requests = GlideApp.with(context);
        GlideRequest<Drawable> glideRequest = requests.load(config.getObject());
        if (config.getUrl() != null) {
            glideRequest = requests.load(config.getUrl());
        } else if (config.getResId() != 0) {
            glideRequest = requests.load(config.getResId());
        } else if (config.getFile() != null) {
            glideRequest = requests.load(config.getFile());
        } else if (config.getUri() != null) {
            glideRequest = requests.load(config.getUri());
        }

        switch (config.getCacheStrategy()) {//缓存策略
            case CacheStrategy.ALL:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case CacheStrategy.NONE:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case CacheStrategy.RESOURCE:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case CacheStrategy.DATA:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case CacheStrategy.AUTOMATIC:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        //是否使用淡入淡出过渡动画
        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        //是否将图片剪切为 CenterCrop
        if (config.isCenterCrop()) {
            glideRequest.centerCrop();
        } else if (config.isCenterInside()) {
            glideRequest.centerInside();
        }

        //是否将图片剪切为圆形
        if (config.isCircleCrop()) {
            glideRequest.circleCrop();
        }

        //设置圆角大小
        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

        //高斯模糊值, 值越大模糊效果越大(blurValue 建议设置为 15)
        if (config.isBlurImage()) {
            glideRequest.transform(new BlurTransformation(config.getBlurValue()));
        }

        //glide用它来改变图形的形状
        if (config.getTransformation() != null) {
            glideRequest.transform(config.getTransformation());
        }

        //设置占位符
        if (config.getPlaceholder() != 0) {
            glideRequest.placeholder(config.getPlaceholder());
        }

        //设置错误的图片
        if (config.getErrorPic() != 0) {
            glideRequest.error(config.getErrorPic());
        }

        //设置请求 url 为空图片
        if (config.getFallback() != 0) {
            glideRequest.fallback(config.getFallback());
        }

        //设定大小
        if (config.getTargetWidth() > 0 && config.getTargetHeight() > 0) {
            glideRequest.override(config.getTargetWidth(), config.getTargetHeight());
        }

        //添加请求配置
        if (config.getRequestOptions() != null) {
            glideRequest.apply(config.getRequestOptions());
        }

        //添加图片加载监听
        if (config.getRequestListener() != null) {
            glideRequest.addListener(config.getRequestListener());
        }

        //不加载动画
        if (config.isDontAnimate()) {
            glideRequest.dontAnimate();
        }

        if (config.getImageView() != null) {
            glideRequest.into(config.getImageView());
        } else if (config.getTarget() != null) {
            glideRequest.into(config.getTarget());
        }
    }

    @Override
    public void clear(Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");

        final Glide glide = GlideApp.get(context);
        final RequestManager requestManager = glide.getRequestManagerRetriever().get(context);
        if (config.getImageView() != null) {
            requestManager.clear(config.getImageView());
        }

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                requestManager.clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {//清除本地缓存
            Platform.post(Schedulers.io(), glide::clearDiskCache);
        }

        if (config.isClearMemory()) {//清除内存缓存
            Platform.post(glide::clearMemory);
        }
    }

    @Override
    public void applyGlideOptions(Context context, GlideBuilder builder) {
    }
}

