package com.astir_trotter.atcustom.ui.layout.filter.widget;

import android.graphics.Color;
import android.widget.RelativeLayout;

import com.astir_trotter.atcustom.ui.layout.filter.listener.CollapseListener;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/22/17
 */

public class CollapsedFilterContainer extends RelativeLayout {

    CollapseListener listener = null;

    private float mStartX = 0f;
    private float mStartY = 0f;

    int containerBackground = Color.WHITE;
    set(value) {
        field = value
        relative_container.setBackgroundColor(value)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleRes: Int) : super(context, attrs, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.collapsed_container, this, true)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val isEmpty = collapsedFilter.childCount == 0
        val containsEvent = ev.x >= collapsedFilter.x && ev.x <= collapsedFilter.x + collapsedFilter.measuredWidth

        return isEmpty || !containsEvent
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event.x
                mStartY = event.y
            }
            MotionEvent.ACTION_UP -> {
                if (!collapsedFilter.isBusy && isClick(mStartX, mStartY, event.x, event.y)) {
                    listener?.toggle()
                    mStartX = 0f
                    mStartY = 0f
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!collapsedFilter.isBusy && Math.abs(mStartX - event.x) < 20 && event.y - mStartY > 20) {
                    listener?.expand()
                    mStartX = 0f
                    mStartY = 0f
                } else if (!collapsedFilter.isBusy && Math.abs(mStartX - event.x) < 20 && event.y - mStartY < -20) {
                    listener?.collapse()
                    mStartX = 0f
                    mStartY = 0f
                }
            }
        }

        return true
    }

}
