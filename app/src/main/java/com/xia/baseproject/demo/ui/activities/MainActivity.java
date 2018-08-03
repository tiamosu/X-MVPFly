package com.xia.baseproject.demo.ui.activities;

import android.content.pm.ActivityInfo;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.EditText;

import com.xia.baseproject.ui.activities.ProxyActivity;
import com.xia.baseproject.demo.ui.fragments.MainDelegate;
import com.xia.baseproject.ui.fragments.SupportFragment;

import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author xia
 */
public class MainActivity extends ProxyActivity {

    @Override
    public SupportFragment setRootFragment() {
        return new MainDelegate();
    }

    @Override
    protected void systemConfiguration() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void dispatchTouchHideKeyboard(EditText editText, MotionEvent event) {
        editText.setFocusable(false);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }
}
