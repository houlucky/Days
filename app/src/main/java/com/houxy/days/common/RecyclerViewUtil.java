package com.houxy.days.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Houxy on 2016/9/2.
 */
public class RecyclerViewUtil {


    /**
     * 设置item间距
     * 使用方法：mRecyclerView.addItemDecoration(new RecyclerViewUtil.SpaceItemDecoration(
     * getResources().getDimensionPixelSize(R.dimen.recyclerview_item)));
     */
    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;


        public SpaceItemDecoration(int space) {
            this.space = space;
        }


        @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = space;
            }
        }
    }

    /**
     * Item装饰器，使用方法：
     * RecyclerViewUtil.DividerLine dividerLine = new RecyclerViewUtil.DividerLine(RecyclerViewUtil.DividerLine.VERTICAL);
     * dividerLine.setSize(40);//px
     * dividerLine.setColor(getResources().getColor(R.color.userListbackgroud));
     * mRecyclerView.addItemDecoration(dividerLine);
     */
    public static class DividerLine extends RecyclerView.ItemDecoration {
        /**
         * 水平方向
         */
        public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

        /**
         * 垂直方向
         */
        public static final int VERTICAL = LinearLayoutManager.VERTICAL;

        // 画笔
        private Paint paint;

        // 布局方向
        private int orientation;
        // 分割线颜色
        private int color;
        // 分割线尺寸
        private int size;
        // 左边的margin
        private int marginStart;
        // 右边的margin
        private int marginEnd;
        // top margin
        private int marginTop;
        //Bottom margin
        private int marginBottom;

        public DividerLine() {
            this(VERTICAL);
        }


        public DividerLine(int orientation) {
            this.orientation = orientation;

            paint = new Paint();
        }


        @Override public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            if (orientation == VERTICAL) {
                drawHorizontal(c, parent);
            }
            else {
                drawVertical(c, parent);
            }
        }


        /**
         * 设置分割线颜色
         *
         * @param color 颜色
         */
        public void setColor(int color) {
            this.color = color;
            paint.setColor(color);
        }


        /**
         * 设置分割线尺寸
         *
         * @param size 尺寸
         */
        public void setSize(int size) {
            this.size = size;
        }


        /**
         * 左边margin
         * @param marginStart
         */
        public void setMarginStart(int marginStart){
            this.marginStart = marginStart;
        }

        /**
         *右边margin
         * @param marginEnd
         */
        public void setMarginEnd(int marginEnd) {
            this.marginEnd = marginEnd;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }

        // 绘制垂直分割线
        protected void drawVertical(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop() + marginTop;
            final int bottom = parent.getHeight() - parent.getPaddingBottom() - marginBottom;

            //默认最后一个item不添加分割线
            final int childCount = parent.getChildCount()-1;
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + size;

                c.drawRect(left, top, right, bottom, paint);
            }
        }


        // 绘制水平分割线
        protected void drawHorizontal(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft() + marginStart;
            final int right = parent.getWidth() - parent.getPaddingRight() - marginEnd;

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + size;

                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }
}
