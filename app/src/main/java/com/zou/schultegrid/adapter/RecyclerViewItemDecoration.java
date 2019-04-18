package com.zou.schultegrid.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.zou.schultegrid.R;

/**
 * ************************************************************
 * description:  <br>
 * packageName:com.zou.schultegrid.adapter mode:SchulteGrid <br>
 * created by 邹高原 on 2018/11/12 13:30<br>
 * last modified time：2018/11/12 13:30 <br>
 *
 * @author 邹高原
 * ************************************************************
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable drawable;
    /**
     * 网格布局
     */
    public static final int LAYOUT_STYLE_GRIDLAYOUT = 0;

    /**
     * 线性布局——垂直方向
     */
    public static final int LAYOUT_STYLE_LINEAR_ORIENTATION_VERTICAL = 1;

    /**
     * 线性布局——水平方向
     */
    public static final int LAYOUT_STYLE_LINEAR_ORIENTATION_HORIZONTAL = 2;

    private int layoutStyle = LAYOUT_STYLE_LINEAR_ORIENTATION_VERTICAL;

    public RecyclerViewItemDecoration(Context context, int style) {
        this(context, style, R.color.colorAccent);
    }

    public RecyclerViewItemDecoration(Context context, int style, int drawableID) {
        drawable = context.getResources().getDrawable(drawableID);
        layoutStyle = style;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        switch (layoutStyle) {
            case LAYOUT_STYLE_GRIDLAYOUT:
                drawGrid(c, parent);
                break;
            case LAYOUT_STYLE_LINEAR_ORIENTATION_HORIZONTAL:
                drawHorizontal(c, parent);
                break;
            case LAYOUT_STYLE_LINEAR_ORIENTATION_VERTICAL:
                drawVertical(c, parent);
                break;
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int right = left + drawable.getIntrinsicWidth();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    private void drawGrid(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = manager.getSpanCount();
        int itemWidth = (parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight()) / spanCount;
        int row = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
        int itemHeight = (parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom()) / row;
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            //竖线
            if (i == 0 || i % (spanCount - 1) != 0) {
                drawable.setBounds(childView.getRight(), childView.getTop(), childView.getRight() + drawable.getIntrinsicWidth(), childView.getTop() + itemHeight);
                drawable.draw(canvas);
            }
            //横线
            if (i < (row - 1) * spanCount) {
                drawable.setBounds(childView.getLeft(), childView.getBottom(), childView.getLeft() + itemWidth, childView.getBottom() + drawable.getIntrinsicHeight());
                drawable.draw(canvas);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        switch (layoutStyle) {
            case LAYOUT_STYLE_GRIDLAYOUT:
                outRect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                break;
            case LAYOUT_STYLE_LINEAR_ORIENTATION_HORIZONTAL:
                outRect.set(0, 0, drawable.getIntrinsicWidth(), 0);
                break;
            case LAYOUT_STYLE_LINEAR_ORIENTATION_VERTICAL:
                outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
                break;
        }
    }
}
