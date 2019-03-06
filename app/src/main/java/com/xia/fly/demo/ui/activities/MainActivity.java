package com.xia.fly.demo.ui.activities;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;

import com.xia.fly.demo.R;
import com.xia.fly.demo.ui.fragments.MainFragment;
import com.xia.fly.ui.activities.ProxyActivity;
import com.xia.fly.ui.fragments.SupportFragment;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ContentFrameLayout;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author xia
 */
public class MainActivity extends ProxyActivity {

    @Override
    public boolean isCheckNetWork() {
        return true;
    }

    @Override
    public boolean isDispatchTouchHideKeyboard() {
        return true;
    }

    @Override
    public boolean isRestartRestore() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadProxyRootFragment(int proxyContainerId) {
        super.loadProxyRootFragment(R.id.main_container_fl);
    }

    @NonNull
    @Override
    protected Class<? extends SupportFragment> setRootFragment() {
        return MainFragment.class;
    }

    @Override
    public void initView() {
        final ContentFrameLayout layout = getContainerLayout();
        if (layout != null) {
            layout.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void onDispatchTouchHideKeyboard(@NotNull EditText editText) {
        editText.setFocusable(false);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    @Override
    public void onNetworkState(boolean isAvailable) {
        Log.e("xia", this + "    onNetworkState: " + isAvailable);
    }

    @Override
    public void onNetReConnect() {
        Log.e("xia", this + "    onNetReConnect: ");
    }
}
