package com.xia.baseproject.demo.ui.activities;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.EditText;

import com.xia.baseproject.demo.ui.fragments.MainFragment;
import com.xia.baseproject.ui.activities.ProxyActivity;
import com.xia.baseproject.ui.fragments.SupportFragment;

import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author xia
 */
public class MainActivity extends ProxyActivity {

    @NonNull
    @Override
    public Class<? extends SupportFragment> setRootFragment() {
        return MainFragment.class;
    }

    @Override
    public void onBeforeCreateView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void dispatchTouchHideKeyboard(EditText editText, MotionEvent event) {
        editText.setFocusable(false);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

//    @Override
//    public void onNetworkState(boolean isAvailable) {
//        Log.e("weixi", "onNetworkState: " + isAvailable);
//    }
//
//    @Override
//    public void onNetReConnect() {
//        Log.e("weixi", "onNetReConnect: ");
//    }
}
