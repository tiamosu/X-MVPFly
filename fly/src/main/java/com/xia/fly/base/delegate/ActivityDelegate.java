package com.xia.fly.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * {@link Activity} 代理类,用于框架内部在每个 {@link Activity} 的对应生命周期中插入需要的逻辑
 *
 * @author xia
 * @date 2018/9/20.
 * @see ActivityDelegateImpl
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.13">ActivityDelegate wiki 官方文档</a>
 */
public interface ActivityDelegate {
    String ACTIVITY_DELEGATE = "ACTIVITY_DELEGATE";

    void onCreate(@Nullable Bundle savedInstanceState);

    void onSaveInstanceState(@NonNull Bundle outState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
