package com.example.byfastapp.base;

import android.support.annotation.IntRange;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUILoadingView;

import java.util.Map;

/**
 * Created by admin on 2017/9/15.
 */
public class CommonLoadingViewManager
        implements ICommonLoadingView {
    private static volatile CommonLoadingViewManager ourInstance;
    private                 Builder                  mBuilder;
    private static          Map<String, Builder>     sMap;

    private static CommonLoadingViewManager getInstance(Builder builder) {
        if (ourInstance == null) {
            synchronized (CommonLoadingViewManager.class) {
                if (ourInstance == null) {
                    sMap = new ArrayMap<>();
                    ourInstance = new CommonLoadingViewManager(builder);
                }
            }
        }
        return ourInstance;
    }

    private CommonLoadingViewManager(Builder builder) {
        if (builder == null) {
            throw new UnsupportedOperationException("builder not't be null");
        }
        String key = builder.mClassName;
        if (sMap.get(key) == null) {
            sMap.put(key, builder);
            this.mBuilder = builder;
        } else {
            this.mBuilder = sMap.get(key);
        }

    }

    @Override
    public void showLoadingView() {
        mBuilder.mRootView.removeViewAt(mBuilder.mContentViewIndex);
        mBuilder.mRootView.addView(mBuilder.mLoadingView);
    }

    @Override
    public void showEmptyView() {
        mBuilder.mRootView.removeViewAt(mBuilder.mContentViewIndex);
        mBuilder.mRootView.addView(mBuilder.mEmptyView);
    }

    @Override
    public void showErrorView() {
        mBuilder.mRootView.removeViewAt(mBuilder.mContentViewIndex);
        mBuilder.mRootView.addView(mBuilder.mErrorView);
    }

    @Override
    public void showContentView() {
        mBuilder.mRootView.removeViewAt(mBuilder.mContentViewIndex);
        mBuilder.mRootView.addView(mBuilder.mContentView);
    }


    public static class Builder {
        private String    mClassName;
        private ViewGroup mRootView;
        private View      mContentView;
        private View      mLoadingView;
        private View      mEmptyView;
        private View      mErrorView;
        @IntRange(from = 0)
        private int       mContentViewIndex;

        public Builder(String className, ViewGroup rootView, int contentViewIndex) {
            mClassName = className;
            mRootView = rootView;
            mContentViewIndex = contentViewIndex;
            mContentView = mRootView.getChildAt(contentViewIndex);
            mLayoutParams= mContentView.getLayoutParams();
            if (mLoadingView==null){
                mLoadingView = new QMUILoadingView(rootView.getContext());
            }
            if (mEmptyView ==null){
                mEmptyView = new QMUIEmptyView(rootView.getContext());
                ((QMUIEmptyView)mEmptyView).show("空页面",null);
            }
            if (mErrorView ==null){
                mErrorView = new QMUIEmptyView(rootView.getContext());
                ((QMUIEmptyView)mErrorView).show("错误页面",null);
            }
            mLoadingView.setLayoutParams(mLayoutParams);
            mEmptyView.setLayoutParams(mLayoutParams);
            mErrorView.setLayoutParams(mLayoutParams);
        }

        public Builder setLoadingView(View loadingView) {
            mLoadingView = loadingView;
            return this;
        }

        public Builder setEmptyView(View emptyView) {
            mEmptyView = emptyView;
            return this;
        }

        public Builder setErrorView(View errorView) {
            mErrorView = errorView;
            return this;
        }

        private ViewGroup.LayoutParams mLayoutParams;


        public CommonLoadingViewManager builder() {
            return CommonLoadingViewManager.getInstance(this);
        }
    }

}
