package com.example.byfastapp.weight.status;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.Map;

/**
 * Created by admin on 2017/10/19.
 * 通过fragment 实现
 */

public class StatusControllerFragmentImp implements IStatusController {

    private FragmentManager mFragmentManager;

    public StatusControllerFragmentImp(FragmentActivity activity) {
        mFragmentManager = activity.getSupportFragmentManager();
    }

    public StatusControllerFragmentImp(Fragment fragment) {
        mFragmentManager = fragment.getChildFragmentManager();
    }

    @Override
    public void bindTargetView(@NonNull View targetView,
                               Map<String, View> statusViews,
                               StatusChangeListener listener)
    {

    }

    @Override
    public void showStatusView(String status) {

    }

    @Override
    public void showTargetView() {

    }

    public static class StatusFragment extends Fragment{

    }
}
