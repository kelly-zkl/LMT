package com.wisec.scanner.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisec.scanner.R;

/**
 * 自定义进度条控件
 * <p>
 * 1、包含进度条功能
 * <p>
 * 2、数值可显示在进度条中
 * Created by qwe on 2018/4/13.
 */

public class ProgressView extends RelativeLayout {
    private View view;
    private TextView textView;
    private ProgressBar progressBar;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /*public ProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    //初始化
    @SuppressLint("NewApi")
    private void initView(Context context, AttributeSet attrs) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_progressview, this, true);
        progressBar = view.findViewById(R.id.progress);
        textView = view.findViewById(R.id.textview);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        if (attributes != null) {
            //设置文字颜色、大小
            int textColor = attributes.getColor(R.styleable.ProgressView_title_text_color, Color.WHITE);
            textView.setTextColor(textColor);
            //设置进度条的最小值
//            int progressMin = attributes.getResourceId(R.styleable.ProgressView_set_progress_min, 0);
//            progressBar.setMin(progressMin);
            //设置进度条的总长度
            int progress = attributes.getResourceId(R.styleable.ProgressView_set_progress_max, 100);
            if (progress > 0) {
                progressBar.setMax(progress);
            }
        }
    }


    public void setProgressBar(int progress, String text) {
        progressBar.setProgress(Math.abs(progress));
        textView.setText(text);
    }
}
