package com.android.magnet.togglebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ToggleButton extends android.support.v7.widget.AppCompatButton {
    Drawable[] drawables;
    boolean DEFAULT_STATE;

    int COLOR_SELECTED;
    int COLOR_UNSELECTED;

    boolean isSelected;
    TypedArray a;

    public ToggleButton(Context context) {
        super(context);
        a = context.getTheme().obtainStyledAttributes(R.styleable.ToggleButton);
        initialize();
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ToggleButton, 0, 0);
        initialize();
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ToggleButton, 0, 0);
        initialize();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            updateState();
        }

        return super.onTouchEvent(event);
    }

    /**
     * ALL THREE (DEFAULT_STATE, COLOR_SELECTED, COLOR_UNSELECTED) can be overridden in XML
     *
     * DEFAULT_STATE is Unselected, if not specified in XML
     * COLOR_SELECTED is the color to be used in the Button's Selected State. Shows original icon & text color, if not specified in XML
     * COLOR_UNSELECTED is the color to be used in the Button's Unselected State. Shows icon & text color with a light gray tint
     */
    public void initialize() {
        try {
            drawables = getCompoundDrawables();
            DEFAULT_STATE = a.getBoolean(R.styleable.ToggleButton_defaultState, false); //
            COLOR_SELECTED = a.getColor(R.styleable.ToggleButton_colorSelected, Color.BLACK); //Selected State will show original icon & text color, if not specified in XML
            COLOR_UNSELECTED = a.getColor(R.styleable.ToggleButton_colorUnselected, Color.LTGRAY); //Unselected state will show original icon & text color w/ a light gray tint, if not specified in XML

            isSelected = DEFAULT_STATE;
            updateColor(isSelected);
        } finally {
            a.recycle();
        }
    }

    public void updateState() {

        isSelected = !isSelected;
        updateColor(isSelected);
    }

    public void updateColor(boolean isSelected) {

        int currentColor;

        if (isSelected) {
            currentColor = COLOR_SELECTED;
        } else {
            currentColor = COLOR_UNSELECTED;
        }

        if (getText() != null)
            setTextColor(currentColor);

        for (int x = 0; x < drawables.length; x++) {
            if (drawables[x] != null)
                drawables[x].setColorFilter(currentColor, PorterDuff.Mode.SRC_ATOP);
        }

        invalidate();
    }

    public boolean isSelected(){
        return isSelected;
    }
}

