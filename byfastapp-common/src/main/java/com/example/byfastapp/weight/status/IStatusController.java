package com.example.byfastapp.weight.status;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.Map;

/**
 * Created by admin on 2017/10/12.
 * 控制器,实际工作
 */

public interface IStatusController {

    void bindTargetView(@NonNull View targetView, Map<String,View> statusViews,StatusChangeListener listener);

    void showStatusView(String status);

    void showTargetView();
}
