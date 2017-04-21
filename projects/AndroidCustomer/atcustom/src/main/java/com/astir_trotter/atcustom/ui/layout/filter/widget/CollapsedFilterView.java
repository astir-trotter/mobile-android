package com.astir_trotter.atcustom.ui.layout.filter.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.astir_trotter.atcustom.ui.layout.filter.listener.CollapseListener;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class CollapsedFilterView extends ViewGroup {

    private static final long ANIMATION_DURATION = 400;

    private int margin = 20; //TODO dpToPx(getDimen(R.dimen.margin))
    private boolean isBusy = false;
    CollapseListener scrollListener = null;

    private float mStartX = 0f;
    private float mStartY = 0f;
    private int mRealMargin = margin;

    public CollapsedFilterView(Context context) {
        this(context, null);
    }

    public CollapsedFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsedFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            FilterItem child = (FilterItem) getChildAt(i);
            child.layout(0, 0, child.collapsedSize / 2 + child.getMeasuredWidth() / 2 + 1, child.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 0) {
            FilterItem child = (FilterItem) getChildAt(0);
            child.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mRealMargin = calculateMargin(((ViewGroup) getParent()).getMeasuredWidth(), child.collapsedSize, margin);

            int width = getChildCount() * child.collapsedSize + getChildCount() * mRealMargin + mRealMargin;

            setMeasuredDimension(width, calculateSize(margin * 2 + child.collapsedSize, LayoutParams.MATCH_PARENT));
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    boolean removeItem(final FilterItem child) {
        if (isBusy) {
            return false;
        }

        final int index = indexOfChild(child);
        isBusy = true;

        ValueAnimator animator = ValueAnimator.ofFloat(0f, ANIMATION_DURATION / 2f).setDuration(ANIMATION_DURATION / 2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float ratio = ((float) animation.getAnimatedValue()) / (ANIMATION_DURATION / 2);
                for (int i = index + 1; i < getChildCount(); i++) {
                    FilterItem item = (FilterItem) getChildAt(i);

                    if (ratio == 0f) {
                        item.startX = item.getX();
                    }

                    item.setTranslationX(item.startX + (-child.collapsedSize - mRealMargin) * ratio);
                    child.setAlpha(1 - ratio);
                }

                if (ratio == 1f) {
                    child.setTranslationX(child.startX + (-child.collapsedSize - mRealMargin) * ratio);
                    isBusy = false;
                }
            }
        });
        animator.start();
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return getChildCount() > 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                if (!isBusy && isClick(mStartX, mStartY, event.getX(), event.getY())) {
                    FilterItem item = findViewByCoord(event.getX());
                    if (item != null)
                        item.dismiss();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mStartX - event.getX()) < 20 && event.getY() - mStartY > 20) {
                    if (!isBusy && scrollListener != null) {
                        scrollListener.expand();
                    }
                    mStartX = 0f;
                    mStartY = 0f;
                }
                break;
        }

        return true;
    }


    private FilterItem findViewByCoord(float x) {
        for (int i = 0; i < getChildCount(); i++) {
            FilterItem item = (FilterItem) getChildAt(i);

            if (containsCoord(item, x)) {
                return item;
            }
        }

        return null;
    }

    private boolean containsCoord(FilterItem item, float x) {
        return item.getX() + item.getWidth() / 2 - item.collapsedSize / 2 <= x &&
                x <= item.getX() + item.getWidth()/ 2 + item.collapsedSize / 2;
    }

}
