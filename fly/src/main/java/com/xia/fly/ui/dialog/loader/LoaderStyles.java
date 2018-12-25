package com.xia.fly.ui.dialog.loader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * @author xia
 * @date 2018/7/29.
 */
@SuppressWarnings("WeakerAccess")
public final class LoaderStyles {
    public static final String BALL_PULSE_INDICATOR = "BallPulseIndicator";
    public static final String BALL_GRID_PULSE_INDICATOR = "BallGridPulseIndicator";
    public static final String BALL_CLIP_ROTATE_INDICATOR = "BallClipRotateIndicator";
    public static final String BALL_CLIP_ROTATE_PULSE_INDICATOR = "BallClipRotatePulseIndicator";
    public static final String SQUARE_SPIN_INDICATOR = "SquareSpinIndicator";
    public static final String BALL_CLIP_ROTATE_MULTIPLE_INDICATOR = "BallClipRotateMultipleIndicator";
    public static final String BALL_PULSE_RISE_INDICATOR = "BallPulseRiseIndicator";
    public static final String BALL_ROTATE_INDICATOR = "BallRotateIndicator";
    public static final String CUBE_TRANSITION_INDICATOR = "CubeTransitionIndicator";
    public static final String BALL_ZIG_ZAG_INDICATOR = "BallZigZagIndicator";
    public static final String BALL_ZIG_ZAG_DEFLECT_INDICATOR = "BallZigZagDeflectIndicator";
    public static final String BALL_TRIANGLE_PATH_INDICATOR = "BallTrianglePathIndicator";
    public static final String BALL_SCALE_INDICATOR = "BallScaleIndicator";
    public static final String LINE_SCALE_INDICATOR = "LineScaleIndicator";
    public static final String LINE_SCALE_PARTY_INDICATOR = "LineScalePartyIndicator";
    public static final String BALL_SCALE_MULTIPLE_INDICATOR = "BallScaleMultipleIndicator";
    public static final String BALL_PULSE_SYNC_INDICATOR = "BallPulseSyncIndicator";
    public static final String BALL_BEAT_INDICATOR = "BallBeatIndicator";
    public static final String LINE_SCALE_PULSE_OUT_INDICATOR = "LineScalePulseOutIndicator";
    public static final String LINE_SCALE_PULSE_OUT_RAPID_INDICATOR = "LineScalePulseOutRapidIndicator";
    public static final String BALL_SCALE_RIPPLE_INDICATOR = "BallScaleRippleIndicator";
    public static final String BALL_SCALE_RIPPLE_MULTIPLE_INDICATOR = "BallScaleRippleMultipleIndicator";
    public static final String BALL_SPIN_FADE_LOADER_INDICATOR = "BallSpinFadeLoaderIndicator";
    public static final String LINE_SPIN_FADE_LOADER_INDICATOR = "LineSpinFadeLoaderIndicator";
    public static final String TRIANGLE_SKEW_SPIN_INDICATOR = "TriangleSkewSpinIndicator";
    public static final String PACMAN_INDICATOR = "PacmanIndicator";
    public static final String BALL_GRID_BEAT_INDICATOR = "BallGridBeatIndicator";
    public static final String SEMI_CIRCLE_SPIN_INDICATOR = "SemiCircleSpinIndicator";

    @StringDef({BALL_PULSE_INDICATOR, BALL_GRID_PULSE_INDICATOR, BALL_CLIP_ROTATE_INDICATOR,
            BALL_CLIP_ROTATE_PULSE_INDICATOR, SQUARE_SPIN_INDICATOR, BALL_CLIP_ROTATE_MULTIPLE_INDICATOR,
            BALL_PULSE_RISE_INDICATOR, BALL_ROTATE_INDICATOR, CUBE_TRANSITION_INDICATOR,
            BALL_ZIG_ZAG_INDICATOR, BALL_ZIG_ZAG_DEFLECT_INDICATOR, BALL_TRIANGLE_PATH_INDICATOR,
            BALL_SCALE_INDICATOR, LINE_SCALE_INDICATOR, LINE_SCALE_PARTY_INDICATOR,
            BALL_SCALE_MULTIPLE_INDICATOR, BALL_PULSE_SYNC_INDICATOR, BALL_BEAT_INDICATOR,
            LINE_SCALE_PULSE_OUT_INDICATOR, LINE_SCALE_PULSE_OUT_RAPID_INDICATOR, BALL_SCALE_RIPPLE_INDICATOR,
            BALL_SCALE_RIPPLE_MULTIPLE_INDICATOR, BALL_SPIN_FADE_LOADER_INDICATOR, LINE_SPIN_FADE_LOADER_INDICATOR,
            TRIANGLE_SKEW_SPIN_INDICATOR, PACMAN_INDICATOR, BALL_GRID_BEAT_INDICATOR,
            SEMI_CIRCLE_SPIN_INDICATOR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoaderStyle {
    }
}
