package com.example.byfastapp.base;

import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.byfastapp.R;
import com.example.byfastapp.R2;
import com.example.byfastapp.config.CommonTitleBarConfig;
import com.example.byfastapp.recyclerview.SampleItemDecoration;
import com.example.byfastapp.weight.TitleBar;
import com.example.byfastapp_common_baserecyclerviewadapter.BaseQuickAdapter;
import com.example.byfastapp_common_baserecyclerviewadapter.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/9/13.
 * 简单列表页的activity
 *
 */

public abstract class BaseSampleListActivity<T>
        extends BaseActivity {


    @BindView(R2.id.title_bar)
    TitleBar     mTitleBar;
    @BindView(R2.id.rv_list_activity)
    RecyclerView mRvListActivity;

    protected BaseQuickAdapter<T,BaseViewHolder> mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sample_list;
    }

    @Override
    protected void initView() {
        initTitleBar(mTitleBar);
        initRecyclerView(mRvListActivity);
    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(setRvLayoutManager());
        if (setItemDecoration()!=null){
            recyclerView.addItemDecoration(setItemDecoration());
        }
        initAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {
        mAdapter = new InnerAdapter<T>(getItemLayoutId(),getAdapterData()) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                bindItemData(helper,item);
            }
        };
    }
    //设置标题栏
    protected abstract CharSequence setTitleContentText();
    protected abstract int getItemLayoutId();
    protected abstract List<T> getAdapterData();
    protected abstract void bindItemData(BaseViewHolder helper, T item);

    protected abstract static  class InnerAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder>{

        public InnerAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
            super(layoutResId, data);
        }
    }

    /**
     * 设置item 的间距
     * 可参考
     * @see com.example.byfastapp.recyclerview.SampleItemDecoration
     * @return RecyclerView.ItemDecoration
     */
    protected RecyclerView.ItemDecoration setItemDecoration() {
        return null;
    }

    /**
     * 设置recyclerview的布局管理器
     * @return  默认线性布局
     */
    protected RecyclerView.LayoutManager setRvLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected void initTitleBar(TitleBar tb) {
        tb.setTitle(setTitleContentText())
                 .setTitleColor(CommonTitleBarConfig.TITLE_COLOR)
                 .setTitleSize(CommonTitleBarConfig.TITLE_SIZE)
                 .setLeftImageResource(CommonTitleBarConfig.LEFT_IMAGE_RESOURCE)
                 .setLeftClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         finish();
                     }
                 })
                 .setLeftVisible(CommonTitleBarConfig.LEFT_VISIBLE)
                 .setImmersive(isStatusBarTranslucent());
    }

    protected RecyclerView.ItemDecoration createSimpleDecoration(float size,@ColorInt int color){
        return new SampleItemDecoration(size,color);
    }


}
