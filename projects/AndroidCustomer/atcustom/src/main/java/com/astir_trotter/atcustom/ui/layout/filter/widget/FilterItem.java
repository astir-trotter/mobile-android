package com.astir_trotter.atcustom.ui.layout.filter.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.astir_trotter.atcustom.R;
import com.astir_trotter.atcustom.ui.layout.filter.listener.FilterItemListener;

import java.io.Serializable;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class FilterItem extends FrameLayout implements Serializable {

    private Context context;
    private View viewLeft;
    private View viewRight;
    private View topStroke;
    private View bottomStroke;
    private View textBackground;
    private TextView textView;
    private Button buttonCancel;

    boolean isFilterSelected = false;
    boolean isIncreased = false;
    float startX = 0f;
    float startY = 0f;
    @DrawableRes
    int cancelIcon = R.drawable.ic_cancel;
    @ColorInt
    Integer color = null;
    @ColorInt
    Integer checkedColor = null;
    @ColorInt
    Integer strokeColor = null;
    @ColorInt
    Integer checkedTextColor = null;
    @ColorInt
    Integer textColor = null;

    private float circlePosition() {
        return textBackground.getWidth() / 2 + 1;
    }

    private int collapsedSize() {
        return viewLeft.getWidth();
    }

    private int fullSize = 0;
    FilterItemListener listener = null;

    private int mStrokeWidth = 20; // TODO: dpToPx(1.25f)

    public FilterItem(@NonNull Context context) {
        this(context, null);
    }

    public FilterItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View container = LayoutInflater.from(context).inflate(R.layout.item_filter, this, true);
        viewLeft = container.findViewById(R.id.viewLeft);
        viewRight = container.findViewById(R.id.viewRight);
        topStroke = container.findViewById(R.id.topStroke);
        bottomStroke = container.findViewById(R.id.bottomStroke);
        textBackground = container.findViewById(R.id.textBackground);
        textView = container.findViewById(R.id.textView);
        buttonCancel = container.findViewById(R.id.buttonCancel);

        viewLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIncreased) {
                    if (isFilterSelected) {
                        deselect();
                    } else {
                        select();
                    }
                } else {
                    dismiss();
                }
            }
        });
        viewRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLeft.performClick();
            }
        });
        textBackground.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.performClick();
            }
        });
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIncreased) {
                    if (isFilterSelected) {
                        deselect();
                    } else {
                        select();
                    }
                }
            }
        });

        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isIncreased) {
                    dismiss();
                } else {
                    viewLeft.performClick();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            buttonCancel.setBackgroundTintList(ColorStateList.valueOf(getColor(android.R.color.white)));
        }
        isIncreased = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        buttonCancel.setBackgroundResource(cancelIcon);
        if (fullSize == 0) {
            fullSize = getMeasuredWidth();
        }
    }

    void select() {
        select(true);
    }

    void select(boolean notify) {
        isIncreased = true;
        isFilterSelected = true;
        updateView();

        if (notify && listener != null) {
            listener.onItemSelected(this);
        }
    }

    void deselect() {
        deselect(true);
    }

    void deselect(boolean notify) {
        isFilterSelected = false;
        updateView();

        if (notify && listener != null) {
            listener.onItemDeselected(this);
        }
    }

    void dismiss() {
        if (listener != null)
            listener.onItemRemoved(this);
    }

    void decrease(float ratio) {
        textView.setScaleX(1 - 0.2f * ratio);
        textView.setAlpha(1 - ratio);
        buttonCancel.setAlpha(ratio);
        textBackground.setScaleX(1 - ratio);
        viewLeft.setTranslationX(circlePosition() * ratio);
        viewRight.setTranslationX(-circlePosition() * ratio);

        if (ratio == 0f) {
            buttonCancel.setVisibility(View.VISIBLE);
            buttonCancel.setAlpha(0f);
        }

        if (ratio == 1f) {
            textView.setScaleX(0f);
        }

        isIncreased = false;
    }

    void increase(float ratio) {
        textView.setScaleX(1f);
        textView.setAlpha(ratio);
        buttonCancel.setAlpha(1 - buttonCancel.getAlpha());
        textBackground.setScaleX(ratio);
        viewLeft.setTranslationX(circlePosition() * (1 - ratio));
        viewRight.setTranslationX(-circlePosition() * (1 - ratio));

        if (ratio == 1f) {
            buttonCancel.setVisibility(View.GONE);
            fullSize = getMeasuredWidth();
        }

        isIncreased = true;
    }

    private void updateView() {
        updateTextColor();
        updateBackground();
    }

    private void updateTextColor() {
        @ColorInt Integer color = isFilterSelected ? checkedTextColor : textColor;

        if (color != null) {
            textView.setTextColor(color);
        }
    }

    private void updateBackground() {
        @ColorInt Integer color = isFilterSelected ? checkedColor : null;
        color = removeAlpha(color);
        Integer strokeColor = isFilterSelected ? color : removeAlpha(null);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(100);

        if (color != null) {
            drawable.setColor(color);
            textBackground.setBackgroundColor(color);
        } else {
            drawable.setColor(getColor(android.R.color.white));
            textBackground.setBackgroundColor(getColor(android.R.color.white));
        }

        if (strokeColor != null) {
            drawable.setStroke(mStrokeWidth, strokeColor);
            topStroke.setBackgroundColor(strokeColor);
            bottomStroke.setBackgroundColor(strokeColor);
        }

        viewLeft.setBackgroundDrawable(drawable);
        viewRight.setBackgroundDrawable(drawable);

    }

    private int getColor(@ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    private Integer removeAlpha(@ColorInt Integer color) {
        return color == null ? 0 : color | 0xff000000;
    }

    void removeFromParent() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (getParent() != null) {
            return !(getParent() instanceof CollapsedFilterView);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof FilterItem))
            return false;

        if (textView.getText() != ((FilterItem)other).textView.getText())
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return textView.getText() != null ? textView.getText().hashCode() : 0;
    }
}