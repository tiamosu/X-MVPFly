package com.xia.baseproject.demo.base;

import android.view.View;
import android.widget.FrameLayout;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.ui.fragments.SupportFragment;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class HeadViewFragment<P extends BaseMvpPresenter> extends SupportFragment<P> {

    @Override
    public void onCreateTitleBar(FrameLayout container) {
        final View headView = View.inflate(getContext(), R.layout.layout_head_view, null);
        container.addView(headView);
    }
}
