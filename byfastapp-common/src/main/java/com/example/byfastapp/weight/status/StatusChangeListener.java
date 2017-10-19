package com.example.byfastapp.weight.status;

import android.view.View;

/**
 * Created by admin on 2017/10/12.
 *
 */

public interface StatusChangeListener {
    void onAfterChangeStatus(String lastStatus, String nowStatus ,View currentView);
    void onStatusViewShowing(String status, View statusView);
    void onBeginChangeStatus(String nowStatus ,String afterStatus,View currentView);
}
