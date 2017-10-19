package com.example.byfastapp.weight.status;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Map;

/**
 * Created by admin on 2017/10/12.
 *
 */

public interface IStatusConfig {
    IStatusConfig setController(IStatusController controller);

    IStatusConfig setStatusView(View statusView,@NonNull String status);

    IStatusConfig setStatusView(@LayoutRes int statusView,@NonNull String status);

    IStatusConfig setOnStatusChangeListener(StatusChangeListener listener);

    View getTargetView();

    View getStatusView(String status);

    StatusChangeListener getStatusListener();

    IStatusView build();

    Map<String,View> getStatusMap();
}
