package com.example.byfastapp.weight.status;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Map;

/**
 * Created by admin on 2017/10/13.
 *
 */

public class StatusControllerFrameLayoutImp implements IStatusController {

    private View mTargetView;
    private ViewGroup mParent;
    private ViewGroup.LayoutParams mLayoutParams;

    private FrameLayout mFrameLayout;
    private StatusChangeListener mListener;
    private Map<String, View> mStatusViews;

    private int mCurrentShowViewIndex;
    private int mNextShowViewIndex;

    @Override
    public void bindTargetView(@NonNull View targetView,
                               Map<String, View> statusViews,
                               StatusChangeListener listener) {
        this.mTargetView = targetView;
        this.mStatusViews = statusViews;
        this.mListener = listener;
        //获取父容器
        mParent = (ViewGroup) mTargetView.getParent();
        mLayoutParams = mTargetView.getLayoutParams();
        //绑定监听
        mListener = listener;
        //获取索引
        int id = mTargetView.getId();
        if (id == View.NO_ID) {
            id = View.generateViewId();
            mTargetView.setId(id);
        }
        int index = 0;
        int childCount = mParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (mParent.getChildAt(i).getId() == id) {
                index = i;
                break;
            }
        }
        //往父容器中添加frameLayout
        if (mParent instanceof FrameLayout) {
            mFrameLayout = (FrameLayout) mParent;
        } else {
            mParent.removeViewAt(index);
            mFrameLayout = new FrameLayout(mParent.getContext());
            mParent.addView(mFrameLayout, index, mLayoutParams);
            mLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                         FrameLayout.LayoutParams.MATCH_PARENT);
            mTargetView.setTag("");
            mFrameLayout.addView(mTargetView, mLayoutParams);
        }
    }

    @Override
    public void showStatusView(String status) {
        View view = mStatusViews.get(status);
        if (view != null) {
            //检查父容器有木有这个
            if (!checkViewHaveAdd(status)) {
                addView(status, view);
            }
            showView();
        }
    }

    private void addView(String status, View view) {
        view.setTag(status);
        mFrameLayout.addView(view);
        mNextShowViewIndex = mFrameLayout.getChildCount() - 1;

    }

    private void showView() {
        if (mListener != null) {
            mListener.onBeginChangeStatus((String) mFrameLayout.getChildAt(mCurrentShowViewIndex).getTag(),
                                          (String) mFrameLayout.getChildAt(mNextShowViewIndex).getTag(),
                                          mFrameLayout.getChildAt(mCurrentShowViewIndex));
        }
        mFrameLayout.getChildAt(mCurrentShowViewIndex).setVisibility(View.GONE);
        mFrameLayout.getChildAt(mNextShowViewIndex).setVisibility(View.VISIBLE);
        String lastStatus = (String) mFrameLayout.getChildAt(mCurrentShowViewIndex).getTag();
        mCurrentShowViewIndex = mNextShowViewIndex;
        if (mListener != null) {
            mListener.onStatusViewShowing((String) mFrameLayout.getChildAt(mCurrentShowViewIndex).getTag(),
                                          mFrameLayout.getChildAt(mCurrentShowViewIndex));
        }

        if (mListener != null) {
            mListener.onAfterChangeStatus(lastStatus, (String) mFrameLayout.getChildAt(mCurrentShowViewIndex).getTag(),
                                          mFrameLayout.getChildAt(mCurrentShowViewIndex));
        }
    }

    private boolean checkViewHaveAdd(String status) {
        boolean haveAdd = false;
        for (int i = 0; i < mFrameLayout.getChildCount(); i++) {
            View childAt = mFrameLayout.getChildAt(i);
            Object tag = childAt.getTag();
            if (tag != null && tag instanceof String) {
                if (TextUtils.equals(status, (String) tag)) {
                    haveAdd = true;
                    mNextShowViewIndex = i;
                    break;
                }
            }
        }
        return haveAdd;
    }

    @Override
    public void showTargetView() {
        mNextShowViewIndex = 0;
        showView();
    }
}
