package com.example.byfastapp.base;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.example.byfastapp.R;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2017/10/3.
 * 底部有导航栏的Activity
 * todo : 1,配置fragment
 *        2,配置选中图标
 *        3,未选中图标
 *        4,选中文字/未选中  字体颜色/大小
 *        5,文字图标的相对位置
 */

public abstract class BaseHomeBottomActivity
        extends BaseActivity {
    protected ViewPager      mPager;
    protected QMUITabSegment mTabs;
    private   List<Fragment> mFragmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottombar;
    }

    @Override
    protected void initView() {
        mPager = (ViewPager) findViewById(R.id.vp_bottom_activity);
        mTabs = (QMUITabSegment) findViewById(R.id.q_ts_bottom_activity);
        initTabSegment(mTabs);
        initViewPager(mPager);
        //关联viewPager
        //设置true时候跟随viewpager的适配器的getTitle取值
        mTabs.setupWithViewPager(mPager, false);
    }

    protected void initTabSegment(QMUITabSegment qtabsegment) {
        QMUITabSegment.Tab[] tabs = configTabs(qtabsegment);
        //添加
        for (QMUITabSegment.Tab tab : tabs) {
            qtabsegment.addTab(tab);
        }
    }

    protected void initViewPager(ViewPager pager) {
        mFragmentList = Arrays.asList(initFragments());//初始化fragment
        pager.setAdapter(new HomeAdapter(getSupportFragmentManager(), mFragmentList));
    }


    protected QMUITabSegment.Tab createNormalTab(@DrawableRes int normalId,
                                                 @DrawableRes int selectedId,
                                                 String content)
    {
        return new QMUITabSegment.Tab(ContextCompat.getDrawable(this, normalId),
                                      ContextCompat.getDrawable(this, selectedId),
                                      content,
                                      false);
    }

    protected QMUITabSegment.Tab createNormalTab(@DrawableRes int normalId, String content)
    {
        //设置true selecterId失效
        return new QMUITabSegment.Tab(ContextCompat.getDrawable(this, normalId),
                                      ContextCompat.getDrawable(this, normalId),
                                      content,
                                      true);
    }

    protected abstract Fragment[] initFragments();

    protected abstract QMUITabSegment.Tab[] configTabs(QMUITabSegment qTabSegment);

    /**
     * 适用页数量较少的情况
     */
    private static class HomeAdapter
            extends FragmentPagerAdapter {

        private List<Fragment> mArrays;

        HomeAdapter(FragmentManager fm, List<Fragment> arrays) {
            super(fm);
            this.mArrays = arrays;
        }

        @Override
        public Fragment getItem(int position) {
            return (mArrays != null && mArrays.size() > position)
                   ? mArrays.get(position)
                   : null;
        }

        @Override
        public int getCount() {
            return mArrays != null
                   ? mArrays.size()
                   : 0;
        }
    }

    /**
     * 适用页数量较多的情况
     */
    private static class HomeStateAdapter
            extends FragmentStatePagerAdapter {

        private List<Fragment> mArrays;

        HomeStateAdapter(FragmentManager fm, List<Fragment> arrays) {
            super(fm);
            this.mArrays = arrays;
        }

        @Override
        public Fragment getItem(int position) {
            return (mArrays != null && mArrays.size() > position)
                   ? mArrays.get(position)
                   : null;
        }

        @Override
        public int getCount() {
            return mArrays != null
                   ? mArrays.size()
                   : 0;
        }
    }


}
