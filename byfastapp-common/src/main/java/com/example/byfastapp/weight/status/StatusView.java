package com.example.byfastapp.weight.status;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2017/10/13.
 *
 */

public class StatusView
        implements IStatusView {

    private IStatusController mController;

    private StatusView(@NonNull IStatusConfig config, @NonNull IStatusController controller) {
        mController = controller;
        mController.bindTargetView(config.getTargetView(),
                                   config.getStatusMap(),
                                   config.getStatusListener());
    }

    @Override
    public void showTargetView() {
        mController.showTargetView();
    }

    @Override
    public void showStatusView(String status) {
        mController.showStatusView(status);
    }

    public static class Builder
            implements IStatusConfig {
        private View                 mTargetView;
        private Map<String, View>    mStatusViews;
        private IStatusController    mController;
        private StatusChangeListener mListener;

        public Builder(@NonNull View targetView) {
            this.mTargetView = targetView;
            mStatusViews = new LinkedHashMap<>();
        }

        public IStatusConfig setController(IStatusController controller) {
            this.mController = controller;
            return this;
        }

        @Override
        public IStatusConfig setStatusView(View statusView, String status) {
            mStatusViews.put(status, statusView);
            return this;
        }

        @Override
        public IStatusConfig setStatusView(@LayoutRes int statusView, String status) {
            return setStatusView(LayoutInflater.from(mTargetView.getContext())
                                               .inflate(statusView, null), status);
        }

        @Override
        public IStatusConfig setOnStatusChangeListener(StatusChangeListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public View getTargetView() {
            return mTargetView;
        }

        @Override
        public View getStatusView(String status) {
            return mStatusViews.get(status);
        }


        @Override
        public StatusChangeListener getStatusListener() {
            return mListener;
        }

        @Override
        public IStatusView build() {
            if (mController == null) {
                mController = new StatusControllerViewsImp();
            }
            return new StatusView(this, mController);
        }

        @Override
        public Map<String, View> getStatusMap() {
            return mStatusViews;
        }

    }

}
