package com.xia.baseproject.demo;

import android.content.pm.ActivityInfo;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.EditText;

import com.xia.baseproject.activities.ProxyActivity;
import com.xia.baseproject.fragments.BaseDelegate;

/**
 * @author xia
 */
public class MainActivity extends ProxyActivity {

    @Override
    public BaseDelegate setRootDelegate() {
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
}
