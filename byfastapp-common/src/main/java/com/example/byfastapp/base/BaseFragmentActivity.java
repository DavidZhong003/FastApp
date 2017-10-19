package com.example.byfastapp.base;

import android.content.Intent;

/**
 * Created by admin on 2017/9/12.
 * 管理fragment 的activity
 *
 */

public abstract class BaseFragmentActivity
        extends BaseActivity {
    /**
     *
     * @return 替换view 的id
     */
    protected abstract int getContextViewId();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 获取当前fragment
     *
     * @return
     */
    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
    }

    /**
     * 启动一个fragment
     * @param fragment
     */
    public void startFragment(BaseFragment fragment) {
        //转场动画控制
        BaseFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
        String tagName = fragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .replace(getContextViewId(), fragment, tagName)
                .addToBackStack(tagName)
                .commit();
    }

    /**
     * 返回
     */
    public void popBackStack() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            BaseFragment fragment = getCurrentFragment();
            BaseFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
            Object toExec = fragment.onLastFragmentFinish();
            if (toExec != null) {
                if (toExec instanceof BaseFragment) {
                    BaseFragment mFragment = (BaseFragment) toExec;
                    startFragment(mFragment);
                } else if (toExec instanceof Intent) {
                    Intent intent = (Intent) toExec;
                    finish();
                    startActivity(intent);
                    overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
                } else {
                    throw new Error("can not handle the result in onLastFragmentFinish");
                }
            } else {
                finish();
                overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * <pre>
     * 返回到clazz类型的Fragment，
     * 如 Home --> List --> Detail，
     * popBackStack(Home.class)之后，就是Home
     *
     * 如果堆栈没有clazz或者就是当前的clazz（如上例的popBackStack(Detail.class)），就相当于popBackStack()
     * </pre>
     *
     */
    public void popBackStack(Class<? extends BaseFragment> clazz) {
        //弹出该fragment以上的Fragment
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), 0);
    }

    /**
     * <pre>
     * 返回到非clazz类型的Fragment
     *
     * 如果上一个是目标clazz，则会继续pop，直到上一个不是clazz。
     * </pre>
     *
     */
    public void popBackStackInclusive(Class<? extends BaseFragment> clazz) {
        //弹出该fragment（包括该fragment）以的fragment。
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
    }
}
