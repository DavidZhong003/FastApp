package com.example.byfastapp.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.byfastapp.R;
import com.example.byfastapp.utils.LogUtils;
import com.example.byfastapp.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/9/13.
 *
 */

public abstract class BaseFragment
        extends Fragment {
    protected final String TAG = this.getClass()
                                     .getSimpleName();

    //============================= UI ================================

    protected Context  mContext;
    private   View     mBaseView;
    protected boolean  mIsApplyButterKnife;
    private   Unbinder mUnbinder;
    protected boolean  mOpenLogInThisClass;

    public BaseFragment() {
        super();
    }


    public final BaseFragmentActivity getBaseFragmentActivity() {
        return (BaseFragmentActivity) getActivity();
    }

    public boolean isAttachedToActivity() {
        return !isRemoving() && mBaseView != null;
    }

    protected boolean isCurrentFragment() {
        return TextUtils.equals(this.getClass()
                                    .getSimpleName(),
                                getBaseFragmentActivity().getCurrentFragment()
                                                         .getClass()
                                                         .getSimpleName());
    }


    protected void startFragment(BaseFragment fragment) {
        BaseFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
        if (baseFragmentActivity != null) {
            if (this.isAttachedToActivity()) {
                baseFragmentActivity.startFragment(fragment);
            } else {
                LogUtils.e("fragment not attached:" + this);
            }
        } else {
            LogUtils.e("startFragment null:" + this);
        }
    }

    protected void startActivity(Class<Activity> aClass) {
        getBaseFragmentActivity().openActivity(aClass);
    }

    protected void startActivity(Class<Activity> aClass, Bundle extral) {
        getBaseFragmentActivity().openActivity(aClass, extral);
    }

    ////////界面跳转动画
    public static final class TransitionConfig {
        public final int enter;
        public final int exit;
        public final int popenter;
        public final int popout;

        public TransitionConfig(int enter, int popout) {
            this(enter, 0, 0, popout);
        }

        public TransitionConfig(int enter, int exit, int popenter, int popout) {
            this.enter = enter;
            this.exit = exit;
            this.popenter = popenter;
            this.popout = popout;
        }
    }

    // 资源，放在业务初始化，会在业务层
    protected static final TransitionConfig SLIDE_TRANSITION_CONFIG = new TransitionConfig(R.anim.slide_in_right,
                                                                                           R.anim.slide_out_left,
                                                                                           R.anim.slide_in_left,
                                                                                           R.anim.slide_out_right);
    protected static final TransitionConfig SCALE_TRANSITION_CONFIG = new TransitionConfig(R.anim.scale_enter,
                                                                                           R.anim.slide_still,
                                                                                           R.anim.slide_still,
                                                                                           R.anim.scale_exit);


    //============================= 生命周期 ================================


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            getBundleData(arguments);
        }
    }

    /**
     * 获取Bundle数据
     * @param arguments
     */
    protected void getBundleData(Bundle arguments) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        mBaseView = inflater.inflate(getContentViewId(), container, false);
        return mBaseView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //使用butterknife
        if (mIsApplyButterKnife) {
            mUnbinder = ButterKnife.bind(view);
        }
        initView(view);
        initData();
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }


    protected void initView(View rootView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mIsApplyButterKnife) {
            mUnbinder.unbind();
        }
    }

    protected abstract
    @LayoutRes
    int getContentViewId();

    protected void popBackStack() {
        getBaseFragmentActivity().popBackStack();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        // bugfix: 使用scale enter时看不到效果， 因为两个fragment的动画在同一个层级，被退出动画遮挡了
        // http://stackoverflow.com/questions/13005961/fragmenttransaction-animation-to-slide-in-over-top#33816251
        if (nextAnim != R.anim.scale_enter || !enter) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
        try {
            Animation nextAnimation = AnimationUtils.loadAnimation(getContext(), nextAnim);
            nextAnimation.setAnimationListener(new Animation.AnimationListener() {

                private float mOldTranslationZ;

                @Override
                public void onAnimationStart(Animation animation) {
                    if (getView() != null) {
                        mOldTranslationZ = ViewCompat.getTranslationZ(getView());
                        ViewCompat.setTranslationZ(getView(), 100.f);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (getView() != null) {
                        getView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //延迟回复z-index,如果退出动画更长，这里可能会失效
                                ViewCompat.setTranslationZ(getView(), mOldTranslationZ);
                            }
                        }, 100);

                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            return nextAnimation;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            onResumeWithUserVisible();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()){
            onPauseWithUserVisible();
        }
    }


    //============================= 新流程 ================================

    /**
     * onCreateView
     *
     * @return
     */
//    protected abstract View onCreateView();

    /**
     * onResume
     */
    protected void onResumeWithUserVisible() {

    }

    /**
     * onPause
     */
    protected void onPauseWithUserVisible() {

    }


    /**
     * 如果是最后一个Fragment,finish后执行的方法
     *
     * @return
     */
    public Object onLastFragmentFinish() {
        return null;
    }

    /**
     * 转场动画控制
     *
     * @return
     */
    public TransitionConfig onFetchTransitionConfig() {
        return SLIDE_TRANSITION_CONFIG;
    }

    protected BaseFragment setViewText(TextView textView, String str) {
        textView.setText(str);
        return this;
    }

    protected BaseFragment setViewText(TextView textView, @StringRes int strId) {
        textView.setText(strId);
        return this;
    }

    protected BaseFragment setViewVisible(View view, boolean isVisible) {
        view.setVisibility(isVisible
                           ? View.VISIBLE
                           : View.GONE);
        return this;
    }

    protected BaseFragment setViewImage(ImageView view, @DrawableRes int resId)
    {
        view.setImageResource(resId);
        return this;
    }

    protected BaseFragment setViewImage(ImageView view, Bitmap bm)
    {
        view.setImageBitmap(bm);
        return this;
    }

    protected BaseFragment setViewBackgroundColor(View view, int color) {
        view.setBackgroundColor(color);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected BaseFragment setViewBackgroundColor(View view, Drawable background) {
        view.setBackground(background);
        return this;
    }

    //======================事件==============================
    protected BaseFragment setViewOnClickListener(View view, View.OnClickListener l) {
        view.setOnClickListener(l);
        return this;
    }

    protected BaseFragment setViewOnLongClickListener(View view, View.OnLongClickListener listener)
    {
        view.setOnLongClickListener(listener);
        return this;
    }

    protected BaseFragment setViewOnCheckedChangeListener(CheckBox cb,
                                                          CompoundButton.OnCheckedChangeListener listener)
    {
        cb.setOnCheckedChangeListener(listener);
        return this;
    }


    protected void printLoge(Object content){
        if (mOpenLogInThisClass){
            LogUtils.e(content);
        }
    }

    protected void printLogi(Object content){
        if (mOpenLogInThisClass){
            LogUtils.i(content);
        }
    }

    protected void showShortToast(String msg){
        ToastUtils.showShort(msg);
    }

    protected void showShortToast(@StringRes int msgId){
        ToastUtils.showShort(msgId);
    }

    protected void showShortToast(@StringRes int msgId,Object...args){
        ToastUtils.showShort(msgId,args);
    }

    protected void showLongToast(String msg){
        ToastUtils.showLong(msg);
    }

    protected void showLongToast(@StringRes int msgId){
        ToastUtils.showLong(msgId);
    }

    protected void showLongToast(@StringRes int msgId,Object...args){
        ToastUtils.showLong(msgId,args);
    }

}
