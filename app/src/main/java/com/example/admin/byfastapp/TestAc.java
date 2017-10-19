package com.example.admin.byfastapp;

import android.widget.TextView;

import com.example.byfastapp.base.BaseActivity;
import com.example.byfastapp.utils.LogUtils;

import butterknife.BindView;

/**
 * Created by admin on 2017/9/21.
 */

public class TestAc
        extends BaseActivity {
    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        LogUtils.e("initView: ////"+mTvTest);
    }

}
