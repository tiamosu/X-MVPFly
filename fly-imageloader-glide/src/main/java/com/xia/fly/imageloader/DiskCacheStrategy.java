package com.xia.fly.imageloader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author xia
 * @date 2018/9/19.
 */
@SuppressWarnings("WeakerAccess")
public final class DiskCacheStrategy {
    public static final int ALL = 0;
    public static final int NONE = 1;
    public static final int RESOURCE = 2;
    public static final int DATA = 3;
    public static final int AUTOMATIC = 4;

    @IntDef({ALL, NONE, RESOURCE, DATA, AUTOMATIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StrategyType {
    }
}
