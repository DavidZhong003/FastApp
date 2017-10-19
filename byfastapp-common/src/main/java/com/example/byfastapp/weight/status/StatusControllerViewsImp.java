package com.example.byfastapp.weight.status;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by admin on 2017/10/13.
 *
 */

public class StatusControllerViewsImp
        implements IStatusController {
    private View                   mTargetView;
    private ViewGroup              mParent;
    private ViewGroup.LayoutParams mLayoutParams;
    private int                    mIndex;
    private int                    mTargetViewId;

    private StatusChangeListener mChangeListener;
    private Map<String, View>    mViewMap;

    private String mCurrentStatus = "";
    private String mNextStatus    = "";


    private void initParentConfig() {
        mParent = (ViewGroup) mTargetView.getParent();
        mLayoutParams = mTargetView.getLayoutParams();
    }

    private void initTargetViewId() {
        mTargetViewId = mTargetView.getId();
        if (mTargetViewId == View.NO_ID) {
            mTargetView.setId(View.generateViewId());
            mTargetViewId = mTargetView.getId();
        }
    }

    private void initIndex() {//索引
        mIndex = 0;
        for (int i = 0; i < mParent.getChildCount(); i++) {
            View childAt = mParent.getChildAt(i);
            if (childAt.getId() == mTargetViewId) {
                mIndex = i;
                break;
            }
        }
    }

    @Override
    public void bindTargetView(@NonNull View targetView,
                               Map<String, View> statusViews,
                               StatusChangeListener listener)
    {
        mTargetView = targetView;
        mViewMap = statusViews;
        mChangeListener = listener;
        initParentConfig();
        initTargetViewId();
        initIndex();
    }

    @Override
    public void showStatusView(String status) {
        mNextStatus = status;
        replaceView(mViewMap.get(status));
    }

    private boolean isTarGetViewShowing() {
        View childAt = mParent.getChildAt(mIndex);
        return isTargetView(childAt);
    }

    private boolean isTargetView(View view) {
        return view.getId() == mTargetViewId;
    }

    @Override
    public void showTargetView() {
        if (!isTarGetViewShowing()) {
            mNextStatus = "";
            replaceView(mTargetView);
        }
    }

    private void replaceView(View view) {
        if (mChangeListener != null) {
            mChangeListener.onBeginChangeStatus(mCurrentStatus, mNextStatus,mParent.getChildAt(mIndex));
        }
        mParent.removeViewAt(mIndex);
        mParent.addView(view, mIndex, mLayoutParams);
        String lastStatus = mCurrentStatus;
        mCurrentStatus = mNextStatus;
        if (mChangeListener != null) {
            mChangeListener.onAfterChangeStatus(lastStatus, mCurrentStatus,view);
            mChangeListener.onStatusViewShowing(mCurrentStatus, view);
        }
    }

}
