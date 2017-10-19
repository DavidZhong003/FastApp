package com.example.byfastapp.ui;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.byfastapp.R;
import com.example.byfastapp.base.BaseActivity;
import com.example.byfastapp.config.CommonTitleBarConfig;
import com.example.byfastapp.weight.TitleBar;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/9/20.
 *
 */

public abstract class BaseTitleBarActivity
        extends BaseActivity {

    protected TitleBar mTitleBar;
    protected int   mTitleColor = CommonTitleBarConfig.TITLE_COLOR;
    protected float mTitleSize  = CommonTitleBarConfig.TITLE_SIZE;
    protected
    @DrawableRes
    int mLeftImageResource = CommonTitleBarConfig.LEFT_IMAGE_RESOURCE;
    protected boolean mLeftVisible = CommonTitleBarConfig.LEFT_VISIBLE;//左边按钮可见

    private boolean shouldUseKnife;

    protected LinearLayout getRootView() {
        return mLlRootView;
    }

    private   LinearLayout mLlRootView;
    protected View         mContentView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        shouldUseKnife = mIsApplyButterKnife;
        if (shouldUseKnife){
            mIsApplyButterKnife = false;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mContentView = getLayoutInflater().inflate(getContentLayoutId(), null);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mLlRootView = (LinearLayout) findViewById(R.id.ll_root_view);
        initTitleBar();
        mContentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT));
        mLlRootView.addView(mContentView);
        if (shouldUseKnife){
            mUnbinder= ButterKnife.bind(this);//重新绑定
            mIsApplyButterKnife = true;
        }
        initContentView(mContentView);
    }

    protected void initTitleBar() {
        mTitleBar.setTitle(setTitleContentText())
                 .setTitleColor(mTitleColor)
                 .setTitleSize(mTitleSize)
                 .setLeftImageResource(mLeftImageResource)
                 .setLeftClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         finish();
                     }
                 })
                 .setLeftVisible(mLeftVisible)
                 .setImmersive(isStatusBarTranslucent());
    }

    protected abstract CharSequence setTitleContentText();

    protected abstract
    @LayoutRes
    int getContentLayoutId();

    protected abstract void initContentView(View contentView);

}
