package com.xia.fly.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * {@link ActivityDelegate} 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
@SuppressWarnings("WeakerAccess")
public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }
}
