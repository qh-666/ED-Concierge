package com.example.edconcierge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ScrollView extends androidx.constraintlayout.widget.ConstraintLayout {
    //Scroller scroller;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();

    public ScrollView(Context context) {
        super(context);
        //init(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init(context);
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // init(context);
    }
//    private void init(Context context) {
//        scroller = new Scroller(context);
//    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //System.out.println(event.getAction());
        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                System.out.println("x:" + event.getX() + " y:" + event.getY());
//                System.out.println("ACTION_DOWN");
//                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("ACTION_MOVE");
                break;
//            case MotionEvent.ACTION_UP:
//                System.out.println("x:"+event.getX()+" y:"+event.getY());
//                System.out.println("ACTION_UP");
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("x:"+event.getX()+" y:"+event.getY());
//                System.out.println("ACTION_MOVE");
//                velocityTracker.addMovement(event);
//                velocityTracker.computeCurrentVelocity(1000);
//                //如果横向滑动
//                //或者当前有打开的Item,并且手指DOWN处仍为该Item,那么应该交给onTouchEvent()处理Item的滑动事件
//                System.out.println(velocityTracker.getYVelocity());
//                System.out.println(velocityTracker.getXVelocity());
//                if (Math.abs(velocityTracker.getYVelocity()) < Math.abs(velocityTracker.getXVelocity())) {
//                    System.out.println("here");
//                    return true;
//                }
//                break;//break
            //拦截
            //System.out.println("拦截");
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
