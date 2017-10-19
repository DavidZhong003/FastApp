package com.example.byfastapp.weight.status;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Map;

/**
 * Created by admin on 2017/10/13.
 */

public class StatusControllerFramlayoutImp implements IStatusController {

    private View mTargetView;
    private ViewGroup mParent;
    private ViewGroup.LayoutParams mLayoutParams;

    private FrameLayout mFrameLayout;
    private StatusChangeListener mListener;
    private Map<String, View> mStatusViews;

    @Override
    public void bindTargetView(@NonNull View targetView,
                               Map<String, View> statusViews,
                               StatusChangeListener listener)
    {
        this.mTargetView = targetView;
        this.mStatusViews = statusViews;
        this.mListener = listener;
        //获取父容器
        mParent = (ViewGroup) mTargetView.getParent();
        mLayoutParams = mTargetView.getLayoutParams();
        //获取索引
        int id = mTargetView.getId();
        if (id==View.NO_ID){
            id=View.generateViewId();
            mTargetView.setId(id);
        }
        int index=0;
        int childCount = mParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (mParent.getChildAt(i).getId()== id){
                index =i;
                break;
            }
        }
        if (mParent instanceof FrameLayout){
            mFrameLayout = (FrameLayout) mParent;
        }else {
            mParent.removeViewAt(index);
            mFrameLayout = new FrameLayout(mParent.getContext());
            mParent.addView(mFrameLayout,index,mLayoutParams);
            mLayoutParams =  new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                               FrameLayout.LayoutParams.MATCH_PARENT);
            mFrameLayout.addView(mTargetView, mLayoutParams);
        }
    }

    @Override
    public void showStatusView(String status) {
        View view = mStatusViews.get(status);
        if (view!=null){

        }
    }

    @Override
    public void showTargetView() {

    }
}
