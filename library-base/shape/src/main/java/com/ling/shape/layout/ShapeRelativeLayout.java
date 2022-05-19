package com.ling.shape.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ling.shape.R;
import com.ling.shape.builder.ShapeDrawableBuilder;
import com.ling.shape.styleable.ShapeRelativeLayoutStyleable;

/**
 * author : wangchengzhen
 * github : https://github.com/getActivity/ShapeView
 * time   : 2022/5/19
 * desc   : 支持直接定义 Shape 背景的 RelativeLayout
 */
public class ShapeRelativeLayout extends RelativeLayout {

    private static final ShapeRelativeLayoutStyleable STYLEABLE = new ShapeRelativeLayoutStyleable();

    private final ShapeDrawableBuilder mShapeDrawableBuilder;

    public ShapeRelativeLayout(Context context) {
        this(context, null);
    }

    public ShapeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeRelativeLayout);
        mShapeDrawableBuilder = new ShapeDrawableBuilder(this, typedArray, STYLEABLE);
        typedArray.recycle();

        mShapeDrawableBuilder.intoBackground();
    }

    public ShapeDrawableBuilder getShapeDrawableBuilder() {
        return mShapeDrawableBuilder;
    }
}
