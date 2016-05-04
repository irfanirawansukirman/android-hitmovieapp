package com.example.irfan.hitmovieapp.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.irfan.hitmovieapp.R;

/**
 * Created by irfan on 05/05/16.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        applyFont(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyFont(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyFont(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyFont(attrs);
    }

    private void applyFont(AttributeSet attrs){
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String mFontName = a.getString(R.styleable.CustomTextView_font);
            try {
                if (mFontName != null) {
                    Typeface mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + mFontName);
                    setTypeface(mTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.recycle();
        }
    }
}
