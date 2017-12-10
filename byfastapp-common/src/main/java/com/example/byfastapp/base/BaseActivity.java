package com.example.byfastapp.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.byfastapp.R;
import com.example.byfastapp.utils.ActivityUtils;
import com.example.byfastapp.utils.BarUtils;
import com.example.byfastapp.utils.ToastUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/9/11.
 *
 * 预留基类
 *
 *
 */

public abstract class BaseActivity
        extends AppCompatActivity {
    protected String TAG = this.getClass()
                               .getSimpleName();

    protected boolean mIsApplyButterKnife = true;// 是否使用butterknife
    protected Unbinder mUnbinder;

    protected void setEnterAnim(int enterAnim) {
        mEnterAnim = enterAnim;
    }

    protected void setExitAnim(int exitAnim) {
        mExitAnim = exitAnim;
    }
    /**
     * 进入下个界面的效果
     */
    protected int mEnterAnim = R.anim.slide_in_right;
    /**
    当前退出的动画效果
     */
    protected int mExitAnim  = R.anim.slide_out_left;

    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if (intent != null) {
            getIntentData(intent.getExtras());
        }
        //设置主界面
        mRootView = initRootView();
        setContentView(mRootView);
        //设置状态栏
        //透明处理
        if (isStatusBarTranslucent()) {
            QMUIStatusBarHelper.translucent(this);
        }
        //使用butterknife
        if (mIsApplyButterKnife) {
            mUnbinder = ButterKnife.bind(this);
        }
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化控件事件监听
     */
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mIsApplyButterKnife) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    /**
     *
     * @return 是否沉浸式 默认否
     */
    protected boolean isStatusBarTranslucent() {
        return false;
    }

    protected LayoutInflater getLayoutInflater_() {
        return LayoutInflater.from(this);
    }

    /**
     * 获取从Intent 的数据
     * @param extras
     */
    protected void getIntentData(Bundle extras) {

    }

    /**
     * 资源文件id
     * @return 资源文件id
     */
    protected abstract
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化布局文件
     */
    protected abstract void initView();

    /**
     *  初始化contentView (跟布局)
     * @return rootView
     */
    protected View initRootView() {
        return getLayoutInflater().inflate(getLayoutId(), null);
    }

    /**
     * 打开activity(有动画)
     * @param pClass 目标activity 类名
     */
    protected void openActivity(Class<Activity> pClass) {
        ActivityUtils.startActivity(this, pClass, mEnterAnim, mExitAnim);
    }

    /**
     * 打开activity(有动画),携带参数
     * @param pClass 目标activity 类名
     * @param extras 携带的参数
     */
    protected void openActivity(Class<Activity> pClass, Bundle extras) {
        ActivityUtils.startActivity(extras, this, pClass, mEnterAnim, mExitAnim);
    }

    /**
     * 打开activity(无动画)
     * @param pClass 目标activity
     */
    protected void openActivityWithOutAnima(Class<Activity> pClass) {
        ActivityUtils.startActivity(this, pClass);
    }

    /**
     * 打开activity(无动画)
     * @param pClass 目标activity 类名
     * @param extras 携带的参数
     */
    protected void openActivityWithOutAnima(Class<Activity> pClass, Bundle extras) {
        ActivityUtils.startActivity(extras, this, pClass);
    }

    /**
     * 设置状态栏颜色
     * @param statusBarColor    状态栏颜色值
     */
    protected void setStatusBarColor(@ColorInt int statusBarColor) {
        BarUtils.setStatusBarColor(this, statusBarColor);
    }

    /**
     * 设置状态栏颜色
     * @param statusBarColor    状态栏颜色值
     * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
     */
    protected void setStatusBarColor(@ColorInt int statusBarColor,
                                     @IntRange(from = 0,
                                               to = 255) int alpha)
    {
        BarUtils.setStatusBarColor(this, statusBarColor, alpha);
    }

    /**
     * 设置状态栏颜色
     * @param statusBarColor    状态栏颜色值
     * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
     * @param isDecor  {@code true}: 设置在DecorView中<br>
     *                 {@code false}: 设置在ContentView中
     */
    protected void setStatusBarColor(@ColorInt int statusBarColor,
                                     @IntRange(from = 0,
                                               to = 255) int alpha,
                                     final boolean isDecor)
    {
        BarUtils.setStatusBarColor(this, statusBarColor, alpha);
    }

    //======================Toast 区域 ===========================================

    protected void showShortToast(String msg) {
        ToastUtils.showShort(msg);
    }

    protected void showShortToast(@StringRes int msgId) {
        ToastUtils.showShort(msgId);
    }

    protected void showShortToast(@StringRes int msgId, Object... args) {
        ToastUtils.showShort(msgId, args);
    }

    protected void showLongToast(String msg) {
        ToastUtils.showLong(msg);
    }

    protected void showLongToast(@StringRes int msgId) {
        ToastUtils.showLong(msgId);
    }

    protected void showLongToast(@StringRes int msgId, Object... args) {
        ToastUtils.showLong(msgId, args);
    }

    //============================================================================

    protected BaseActivity setViewText(TextView textView, String str) {
        textView.setText(str);
        return this;
    }

    protected BaseActivity setViewText(TextView textView, @StringRes int strId) {
        textView.setText(strId);
        return this;
    }

    protected BaseActivity setViewVisible(View view, boolean isVisible) {
        view.setVisibility(isVisible
                           ? View.VISIBLE
                           : View.GONE);
        return this;
    }

    protected BaseActivity setViewImage(ImageView view, @DrawableRes int resId)
    {
        view.setImageResource(resId);
        return this;
    }

    protected BaseActivity setViewImage(ImageView view, Bitmap bm)
    {
        view.setImageBitmap(bm);
        return this;
    }

    protected BaseActivity setViewBackgroundColor(View view, int color) {
        view.setBackgroundColor(color);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected BaseActivity setViewBackgroundColor(View view, Drawable background) {
        view.setBackground(background);
        return this;
    }

    //======================事件==============================
    protected BaseActivity setViewOnClickListener(View view, View.OnClickListener l) {
        view.setOnClickListener(l);
        return this;
    }

    protected BaseActivity setViewOnLongClickListener(View view, View.OnLongClickListener listener)
    {
        view.setOnLongClickListener(listener);
        return this;
    }

    protected BaseActivity setViewOnCheckedChangeListener(CheckBox cb,
                                                          CompoundButton.OnCheckedChangeListener listener)
    {
        cb.setOnCheckedChangeListener(listener);
        return this;
    }

    //================================================================


}

