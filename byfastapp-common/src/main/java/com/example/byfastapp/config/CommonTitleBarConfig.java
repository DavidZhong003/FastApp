package com.example.byfastapp.config;

import android.graphics.Color;
import android.support.annotation.DrawableRes;

/**
 * Created by admin on 2017/0/10.
 * 标题栏通用配置
 */

public interface CommonTitleBarConfig {
    int   TITLE_COLOR  = Color.WHITE;
    float TITLE_SIZE = 18;//单位sp
    @DrawableRes
    int LEFT_IMAGE_RESOURCE = com.qmuiteam.qmui.R.drawable.qmui_icon_topbar_back;
    boolean LEFT_VISIBLE = true;//左边按钮可见
}
