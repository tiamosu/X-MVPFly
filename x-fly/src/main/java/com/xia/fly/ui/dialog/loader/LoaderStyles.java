package com.xia.fly.ui.dialog.loader;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xia
 * @date 2018/7/29.
 */
@SuppressWarnings("WeakerAccess")
public final class LoaderStyles {
    public static final String BallPulseIndicator = "BallPulseIndicator";
    public static final String BallGridPulseIndicator = "BallGridPulseIndicator";
    public static final String BallClipRotateIndicator = "BallClipRotateIndicator";
    public static final String BallClipRotatePulseIndicator = "BallClipRotatePulseIndicator";
    public static final String SquareSpinIndicator = "SquareSpinIndicator";
    public static final String BallClipRotateMultipleIndicator = "BallClipRotateMultipleIndicator";
    public static final String BallPulseRiseIndicator = "BallPulseRiseIndicator";
    public static final String BallRotateIndicator = "BallRotateIndicator";
    public static final String CubeTransitionIndicator = "CubeTransitionIndicator";
    public static final String BallZigZagIndicator = "BallZigZagIndicator";
    public static final String BallZigZagDeflectIndicator = "BallZigZagDeflectIndicator";
    public static final String BallTrianglePathIndicator = "BallTrianglePathIndicator";
    public static final String BallScaleIndicator = "BallScaleIndicator";
    public static final String LineScaleIndicator = "LineScaleIndicator";
    public static final String LineScalePartyIndicator = "LineScalePartyIndicator";
    public static final String BallScaleMultipleIndicator = "BallScaleMultipleIndicator";
    public static final String BallPulseSyncIndicator = "BallPulseSyncIndicator";
    public static final String BallBeatIndicator = "BallBeatIndicator";
    public static final String LineScalePulseOutIndicator = "LineScalePulseOutIndicator";
    public static final String LineScalePulseOutRapidIndicator = "LineScalePulseOutRapidIndicator";
    public static final String BallScaleRippleIndicator = "BallScaleRippleIndicator";
    public static final String BallScaleRippleMultipleIndicator = "BallScaleRippleMultipleIndicator";
    public static final String BallSpinFadeLoaderIndicator = "BallSpinFadeLoaderIndicator";
    public static final String LineSpinFadeLoaderIndicator = "LineSpinFadeLoaderIndicator";
    public static final String TriangleSkewSpinIndicator = "TriangleSkewSpinIndicator";
    public static final String PacmanIndicator = "PacmanIndicator";
    public static final String BallGridBeatIndicator = "BallGridBeatIndicator";
    public static final String SemiCircleSpinIndicator = "SemiCircleSpinIndicator";
    public static final String CustomIndicator = "CustomIndicator";

    @StringDef({BallPulseIndicator, BallGridPulseIndicator, BallClipRotateIndicator,
            BallClipRotatePulseIndicator, SquareSpinIndicator, BallClipRotateMultipleIndicator,
            BallPulseRiseIndicator, BallRotateIndicator, CubeTransitionIndicator,
            BallZigZagIndicator, BallZigZagDeflectIndicator, BallTrianglePathIndicator,
            BallScaleIndicator, LineScaleIndicator, LineScalePartyIndicator,
            BallScaleMultipleIndicator, BallPulseSyncIndicator, BallBeatIndicator,
            LineScalePulseOutIndicator, LineScalePulseOutRapidIndicator, BallScaleRippleIndicator,
            BallScaleRippleMultipleIndicator, BallSpinFadeLoaderIndicator, LineSpinFadeLoaderIndicator,
            TriangleSkewSpinIndicator, PacmanIndicator, BallGridBeatIndicator,
            SemiCircleSpinIndicator, CustomIndicator})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoaderStyle {
    }
}
