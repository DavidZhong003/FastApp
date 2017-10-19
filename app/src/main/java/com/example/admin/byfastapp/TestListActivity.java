package com.example.admin.byfastapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.example.byfastapp.base.BaseSampleListActivity;
import com.example.byfastapp_common_baserecyclerviewadapter.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2017/10/10.
 *
 */

public class TestListActivity extends BaseSampleListActivity<String> {
    @Override
    protected CharSequence setTitleContentText() {
        return "列表标题";
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_test_list;
    }

    @Override
    protected List<String> getAdapterData() {
        List<String> list = Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15");
        return list;
    }

    @Override
    protected void bindItemData(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_test,item);
    }

    @Override
    protected RecyclerView.ItemDecoration setItemDecoration() {
        return createSimpleDecoration(2, Color.GRAY);
    }

}
