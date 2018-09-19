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
        GlideRequest<Drawable> glideRequest = requests.load(config.mObject);
        if (config.mUrl != null) {
            glideRequest = requests.load(config.mUrl);
        } else if (config.mResId != 0) {
            glideRequest = requests.load(config.mResId);
        } else if (config.mFile != null) {
            glideRequest = requests.load(config.mFile);
        } else if (config.mUri != null) {
            glideRequest = requests.load(config.mUri);
        }

        switch (config.mCacheStrategy) {//缓存策略
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
        if (config.mIsCrossFade) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        //是否将图片剪切为 CenterCrop
        if (config.mIsCenterCrop) {
            glideRequest.centerCrop();
        } else if (config.mIsCenterInside) {
            glideRequest.centerInside();
        }

        //是否将图片剪切为圆形
        if (config.mIsCircleCrop) {
            glideRequest.circleCrop();
        }

        //设置圆角大小
        if (config.mImageRadius != 0) {
            glideRequest.transform(new RoundedCorners(config.mImageRadius));
        }

        //高斯模糊值, 值越大模糊效果越大(blurValue 建议设置为 15)
        if (config.mBlurValue != 0) {
            glideRequest.transform(new BlurTransformation(config.mBlurValue));
        }

        //glide用它来改变图形的形状
        if (config.mTransformation != null) {
            glideRequest.transform(config.mTransformation);
        }

        //设置占位符
        if (config.mPlaceholder != 0) {
            glideRequest.placeholder(config.mPlaceholder);
        }

        //设置错误的图片
        if (config.mErrorPic != 0) {
            glideRequest.error(config.mErrorPic);
        }

        //设置请求 url 为空图片
        if (config.mFallback != 0) {
            glideRequest.fallback(config.mFallback);
        }

        //设定大小
        if (config.mTargetWidth > 0 && config.mTargetHeight > 0) {
            glideRequest.override(config.mTargetWidth, config.mTargetHeight);
        }

        //添加请求配置
        if (config.mRequestOptions != null) {
            glideRequest.apply(config.mRequestOptions);
        }

        //添加图片加载监听
        if (config.mRequestListener != null) {
            glideRequest.addListener(config.mRequestListener);
        }

        //不加载动画
        if (config.mIsDontAnimate) {
            glideRequest.dontAnimate();
        }

        if (config.mImageView != null) {
            glideRequest.into(config.mImageView);
        } else if (config.mTarget != null) {
            glideRequest.into(config.mTarget);
        }
    }

    @Override
    public void clear(Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");

        final Glide glide = GlideApp.get(context);
        final RequestManager requestManager = glide.getRequestManagerRetriever().get(context);
        if (config.mImageView != null) {
            requestManager.clear(config.mImageView);
        }

        if (config.mImageViews != null && config.mImageViews.length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.mImageViews) {
                requestManager.clear(imageView);
            }
        }

        if (config.mIsClearDiskCache) {//清除本地缓存
            Platform.post(Schedulers.io(), glide::clearDiskCache);
        }

        if (config.mIsClearMemory) {//清除内存缓存
            Platform.post(glide::clearMemory);
        }
    }

    @Override
    public void applyGlideOptions(Context context, GlideBuilder builder) {
    }
}

