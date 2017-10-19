package com.example.admin.byfastapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.byfastapp.base.BaseFragment;


public class BlankFragment
        extends BaseFragment {

    private String mString;

    public static BlankFragment newInstance(String content){
        BlankFragment fragment = new BlankFragment();
        Bundle        bundle   = new Bundle();
        bundle.putString("test",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void getBundleData(Bundle arguments) {
        mString = arguments.getString("test");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void initView(View rootView) {
        TextView tv = (TextView) rootView.findViewById(R.id.tv_test);
        tv.setText(mString);
        tv.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(this).commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: "+mString+"////"+getUserVisibleHint() );
    }

}
