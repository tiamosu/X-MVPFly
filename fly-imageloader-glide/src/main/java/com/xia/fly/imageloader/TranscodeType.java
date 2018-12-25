package com.xia.fly.imageloader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author xia
 * @date 2018/9/28.
 */
@SuppressWarnings("WeakerAccess")
public final class TranscodeType {
    public static final int AS_DRAWABLE = 0;
    public static final int AS_BITMAP = 1;
    public static final int AS_FILE = 2;
    public static final int AS_GIF = 3;

    @IntDef({AS_DRAWABLE, AS_BITMAP, AS_FILE, AS_GIF})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
}
