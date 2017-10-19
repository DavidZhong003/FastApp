package com.example.byfastapp_common_baserecyclerviewadapter.animation;

import android.animation.Animator;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
