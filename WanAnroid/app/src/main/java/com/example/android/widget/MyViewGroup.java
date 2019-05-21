package com.example.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup {

    //子控件与子控件的距离
    private int margin_left = 0;//距离左面的距离
    private int margin_top = 0;//与上面的距离
    private int margin_right = 0;//与右边的距离
    private int margin_bottom = 0;//与下边的距离
    //子控件与子控件内容的距离
    private int right=10;
    private int left=10;
    private int top=10;
    private int bottom=10;

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int x = 0;//横坐标
        int y = 0;//纵坐标
        int rows = 1;//总行数
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int actualWidth = specWidth - margin_right - margin_left;//实际宽度
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
            child.setPadding(left, top, right, bottom);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            x += width;
            if (x > actualWidth) {//换行
                x = width;
                rows++;
            }
            y = rows * (height+margin_top+margin_bottom);
        }
        setMeasuredDimension(actualWidth, y+100);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int mViewGroupWidth = getMeasuredWidth(); //当前ViewGroup的总宽度
        int mPainterPosX = l;//当前绘制光标X坐标
        int mPainterPosY = 5;//当前绘制光标Y坐标
        int childCount = getChildCount();//子控件的数量

        //绘制子控件
        for (int i=0;i<childCount;i++){
            View childView=getChildAt(i);
            //子控件的宽和高
            int width=childView.getMeasuredWidth();
            int height=childView.getMeasuredHeight();
            //如果剩余控件不够，则移到下一行开始位置
            if(mPainterPosX+width>mViewGroupWidth){
                mPainterPosX=l;
                mPainterPosY+=height+margin_bottom+margin_top;
            }
            //执行childView的绘制
            childView.layout(mPainterPosX+margin_left,mPainterPosY+margin_top,
                    mPainterPosX+width+margin_right,mPainterPosY+height+margin_bottom);
            //下一次绘制的X坐标
            mPainterPosX+=width+margin_left+margin_right;
        }

    }

    public void setSpacing(int margin_left,int margin_top,int margin_right,int margin_bottom){
        this.margin_left=margin_left;
        this.margin_top=margin_top;
        this.margin_right=margin_right;
        this.margin_bottom=margin_bottom;

    }

    public void setViewPadding(int left ,int top,int right,int bottom ){
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
    }
}
