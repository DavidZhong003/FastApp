package com.example.byfastapp.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/*
 *  @项目名：  android
 *  @包名：    com.shenzhen.vanke.vankeyi.ui.personal.invoice
 *  @文件名:   RecyclerItemDecoration
 *  @创建者:   zhong
 *  @创建时间:  2017/7/10 14:25
 *  @描述：    recyclerview的分割线
 */
public class SampleItemDecoration
        extends RecyclerView.ItemDecoration {
    private final float dividerSize;
    private final Paint paint;
    private       int   mStartPosition;

    /**
     *
     * @param dividerSize  分割线大小 dp
     * @param dividerColor 分割线颜色
     */
    public SampleItemDecoration(float dividerSize, @ColorInt int dividerColor) {
        this(dividerSize,dividerColor,0);
    }

    /**
     *
     * @param dividerSize  分割线大小
     * @param dividerColor 分割线颜色
     * @param startPosition 分割线起始位置
     */
    public SampleItemDecoration(float dividerSize, @ColorInt int dividerColor, int startPosition) {
        this.dividerSize = dividerSize;
        paint = new Paint();
        paint.setColor(dividerColor);
        paint.setStyle(Paint.Style.FILL);
        mStartPosition=startPosition;
    }

    /**
     *
     * @param context 上下文
     * @param dividerSizeResId 分割线资源id
     * @param dividerColorResId 分割线颜色资源id
     */
    public SampleItemDecoration(@NonNull Context context,
                                @DimenRes int dividerSizeResId,
                                @ColorRes int dividerColorResId)
    {
        this(context.getResources()
                    .getDimensionPixelSize(dividerSizeResId),
             ContextCompat.getColor(context, dividerColorResId));
    }

    /**
     * 绘制后执行
     * @param canvas 画布
     * @param parent recyclerView本身
     * @param state recycler 状态
     */
    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (!parent.isAnimating()) {
            final int                        childCount = parent.getChildCount();
            final RecyclerView.LayoutManager lm         = parent.getLayoutManager();
            for (int i = mStartPosition; i < childCount - 1; i++) {
                View child     = parent.getChildAt(i);
                View nextChild = parent.getChildAt(i + 1);
                //这里要考虑margin值
                ViewGroup.LayoutParams cLp          = child.getLayoutParams();
                ViewGroup.LayoutParams nLp          = nextChild.getLayoutParams();
                int                    bottomMargin = 0;
                int                    topMargin    = 0;
                if (cLp instanceof RelativeLayout.LayoutParams) {
                    bottomMargin = ((RelativeLayout.LayoutParams) cLp).bottomMargin;
                }
                if (cLp instanceof LinearLayout.LayoutParams) {
                    bottomMargin = ((LinearLayout.LayoutParams) cLp).bottomMargin;
                }

                if (nLp instanceof RelativeLayout.LayoutParams) {
                    topMargin = ((RelativeLayout.LayoutParams) cLp).bottomMargin;
                }
                if (nLp instanceof LinearLayout.LayoutParams) {
                    topMargin = ((LinearLayout.LayoutParams) cLp).bottomMargin;
                }
                //两个View的间距
                int i1 = lm.getDecoratedTop(nextChild) + topMargin - (lm.getDecoratedBottom(child) - bottomMargin);
                float bottom = lm.getDecoratedBottom(child) - bottomMargin + i1 / 2 + dividerSize / 2;
                float top    = bottom - dividerSize;
                canvas.drawRect(parent.getPaddingLeft(), top, parent.getRight(), bottom, paint);

            }
        }
    }

    /**
     * 绘制前执行
     * @param c canvas
     * @param parent recyclerView
     * @param state state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
    }


}
