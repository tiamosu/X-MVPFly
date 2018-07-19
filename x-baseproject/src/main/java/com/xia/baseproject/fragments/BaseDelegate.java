package com.xia.baseproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xia.baseproject.R;
import com.xia.baseproject.mvp.BaseMvpPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/7/3.
 */
@SuppressWarnings("all")
public abstract class BaseDelegate<P extends BaseMvpPresenter> extends AbstractMvpFragment<P> {
    private Unbinder mUnbinder = null;

    /**
     * 保证转场动画的流畅性
     */
    private boolean mIsOnSupportVisible;
    private boolean mIsOnEnterAnimationEnd;
    private boolean mNeedInit = true;

    /**
     * 该方法用来替代{@link #onSupportVisible()}，保证转场动画的流畅性
     */
    public void onVisibleLazyLoadData() {
    }

    protected boolean isLoadHeadView() {
        return true;
    }

    protected void onCreateHeadView(FrameLayout headContainer) {
    }

    protected void getBundleExtras(Bundle bundle) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.base_layout_root_view, container, false);
        if (isLoadHeadView()) {
            final FrameLayout headContainer = rootView.findViewById(R.id.layout_root_view_head_container);
            onCreateHeadView(headContainer);
        }
        if (getLayoutId() > 0) {
            final FrameLayout contentContainer = rootView.findViewById(R.id.layout_root_view_content_container);
            View.inflate(getContext(), getLayoutId(), contentContainer);
        }
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 在{@link #loadMultipleRootFragment(int, int, ISupportFragment...)}的情况下，
     * <p>
     * {@link #onEnterAnimationEnd(Bundle)}方法，将会先于{@link #onSupportVisible()}先执行
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mIsOnEnterAnimationEnd = true;
        if (mIsOnSupportVisible) {
            initAll();
        }
    }

    @Override
    public void onSupportVisible() {
        mIsOnSupportVisible = true;
        if (mIsOnEnterAnimationEnd) {
            initAll();
        }
    }

    private void initAll() {
        if (mNeedInit) {
            mNeedInit = false;
            getBundle(getArguments());
            initData();
            initView();
            initEvent();
        }
        onVisibleLazyLoadData();
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        getBundle(args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    private void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            getBundleExtras(bundle);
        }
    }
}
