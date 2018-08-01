package com.xia.baseproject.demo.base;

import android.view.View;
import android.widget.FrameLayout;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.fragments.SupportFragment;
import com.xia.baseproject.mvp.BaseMvpPresenter;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class AbstractHeadViewDelegate<P extends BaseMvpPresenter> extends SupportFragment<P> {

    @Override
    public void onCreateHeadView(FrameLayout container) {
        final View headView = View.inflate(getContext(), R.layout.layout_head_view, null);
        container.addView(headView);
    }
}
