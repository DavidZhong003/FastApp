package com.example.admin.byfastapp;

import android.support.v4.app.Fragment;

import com.example.byfastapp.base.BaseHomeBottomActivity;
import com.qmuiteam.qmui.widget.QMUITabSegment;

/**
 * Created by admin on 2017/10/9.
 */

public class TextActivity
        extends BaseHomeBottomActivity {

    @Override
    protected Fragment[] initFragments() {
        Fragment[] fragments = {BlankFragment.newInstance("哈哈"),
                                BlankFragment.newInstance("呵呵"),
                                BlankFragment.newInstance("嘻嘻"),
                                BlankFragment.newInstance("嚯嚯")};
        return fragments;
    }

    @Override
    protected QMUITabSegment.Tab[] configTabs(QMUITabSegment qTabSegment) {
        QMUITabSegment.Tab[] tabs = new QMUITabSegment.Tab[4];
        tabs[0] = createNormalTab(R.mipmap.icon_tabbar_util,R.mipmap.icon_tabbar_util_selected,"哈哈");
        tabs[1] = createNormalTab(R.mipmap.icon_tabbar_util,R.mipmap.icon_tabbar_util_selected,"呵呵");
        tabs[2] = createNormalTab(R.mipmap.icon_tabbar_util,R.mipmap.icon_tabbar_util_selected,"嘻嘻");
        tabs[3] = createNormalTab(R.mipmap.icon_tabbar_util,R.mipmap.icon_tabbar_util_selected,"嚯嚯");
        return tabs;
    }


}
