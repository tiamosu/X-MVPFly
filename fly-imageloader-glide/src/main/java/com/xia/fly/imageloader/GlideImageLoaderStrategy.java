package com.xia.fly.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
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

        final GlideRequest glideRequest = getGlideRequest(context, config);

        //缓存策略
        switch (config.mCacheStrategy) {
            case DiskCacheStrategy.ALL:
                glideRequest.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL);
                break;
            case DiskCacheStrategy.NONE:
                glideRequest.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE);
                break;
            case DiskCacheStrategy.RESOURCE:
                glideRequest.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE);
                break;
            case DiskCacheStrategy.DATA:
                glideRequest.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.DATA);
                break;
            case DiskCacheStrategy.AUTOMATIC:
                glideRequest.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL);
                break;
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
        if (config.mRoundingRadius != 0) {
            glideRequest.transform(new RoundedCorners(config.mRoundingRadius));
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

        GlideRequest finalRequest = glideRequest;
        if (config.mTranscodeType == TranscodeType.AS_DRAWABLE) {
            final GlideRequest<Drawable> drawableGlideRequest = (GlideRequest<Drawable>) glideRequest;
            //是否使用淡入淡出过渡动画
            if (config.mIsCrossFade) {
                drawableGlideRequest.transition(DrawableTransitionOptions.withCrossFade());
            }
            finalRequest = drawableGlideRequest;
        } else if (config.mTranscodeType == TranscodeType.AS_BITMAP) {
            final GlideRequest<Bitmap> bitmapGlideRequest = (GlideRequest<Bitmap>) glideRequest;
            if (config.mIsCrossFade) {
                bitmapGlideRequest.transition(BitmapTransitionOptions.withCrossFade());
            }
            finalRequest = bitmapGlideRequest;
        }
        if (config.mImageView != null) {
            finalRequest.into(config.mImageView);
        } else if (config.mTarget != null) {
            finalRequest.into(config.mTarget);
        }
    }

    @SuppressWarnings("unchecked")
    private static GlideRequest getGlideRequest(final Context context, final ImageConfigImpl config) {
        final GlideRequests request = GlideApp.with(context);
        GlideRequest glideRequest;
        switch (config.mTranscodeType) {
            case TranscodeType.AS_BITMAP:
                glideRequest = request.asBitmap();
                break;
            case TranscodeType.AS_FILE:
                glideRequest = request.asFile();
                break;
            case TranscodeType.AS_GIF:
                glideRequest = request.asGif();
                break;
            default:
                glideRequest = request.asDrawable();
                break;
        }

        final Object o = config.mObject;
        if (o instanceof Bitmap) {
            return glideRequest.load((Bitmap) o);
        } else if (o instanceof Drawable) {
            return glideRequest.load((Drawable) o);
        } else if (o instanceof Integer) {
            return glideRequest.load((Integer) o);
        } else if (o instanceof byte[]) {
            return glideRequest.load((byte[]) o);
        }
        return glideRequest.load(o);
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

