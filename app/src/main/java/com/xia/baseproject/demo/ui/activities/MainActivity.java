package com.xia.baseproject.demo.ui.activities;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.xia.baseproject.demo.ui.fragments.MainFragment;
import com.xia.fly.ui.activities.ProxyActivity;
import com.xia.fly.ui.fragments.SupportFragment;

import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author xia
 */
public class MainActivity extends ProxyActivity {

    @Override
    public boolean isDispatchTouchHideKeyboard() {
        return false;
    }

    @NonNull
    @Override
    public Class<? extends SupportFragment> setRootFragment() {
        return MainFragment.class;
    }

    @Override
    public void dispatchTouchHideKeyboard(EditText editText) {
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
