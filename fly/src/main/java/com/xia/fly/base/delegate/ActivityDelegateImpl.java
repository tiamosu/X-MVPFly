package com.xia.fly.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xia.fly.ui.activities.IActivity;

/**
 * {@link IActivityDelegate} 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
@SuppressWarnings("WeakerAccess")
public class ActivityDelegateImpl implements IActivityDelegate {
    private Activity mActivity;
    private IActivity mIActivity;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
        this.mIActivity = (IActivity) activity;
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
