package com.example.edconcierge;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

public class PayAnimatorView extends View {

    /**
     * 动画状态：加载中、成功、失败
     */
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_SUCCESS = 2;
    public static final int STATUS_FAIL = 3;

    /**
     * 当前动画的状态
     */
    private int curStatus;

    /**
     * loading 动画变量
     */
    private PathMeasure mPathMeasure;
    private Path mDstPath;
    private int mCurRotate = 0;
    private float mProgress;
    private boolean hasCanvasSaved = false;
    private boolean hasCanvasRestored = false;

    /**
     * success / Fail 动画变量
     */
    private PathMeasure mSuccessPathMeasure;
    private Path mSuccessDstPath;
    private PathMeasure mFailPathMeasure;
    private Path mFailDstPath;

    /**
     * 动画
     */
    private ValueAnimator mLoadingAnimator;
    private ValueAnimator mSuccessAnimator;
    private ValueAnimator mFailAnimator;

    private Paint mBluePaint;
    private Paint mRedPaint;

    private int mCenterX, mCenterY;

    public PayAnimatorView(Context context) {
        super(context);
        init();
    }

    public PayAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PayAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        int radius = (int) (Math.min(w, h) * 0.3);
        // 在获取宽高之后设置加载框的位置和大小
        Path circlePath = new Path();
        circlePath.addCircle(mCenterX, mCenterY, radius, Path.Direction.CW);
        mPathMeasure = new PathMeasure(circlePath, true);
        mDstPath = new Path();
        // 设置 success 动画的 path
        Path successPath = new Path();
        successPath.addCircle(mCenterX, mCenterY, radius, Path.Direction.CW);
        successPath.moveTo(mCenterX - radius * 0.5f, mCenterY - radius * 0.2f);
        successPath.lineTo(mCenterX - radius * 0.1f, mCenterY + radius * 0.4f);
        successPath.lineTo(mCenterX + radius * 0.6f, mCenterY - radius * 0.5f);
        mSuccessPathMeasure = new PathMeasure(successPath, false);
        mSuccessDstPath = new Path();
        // 设置 fail 动画的 path
        Path failPath = new Path();
        failPath.addCircle(mCenterX, mCenterY, radius, Path.Direction.CW);
        failPath.moveTo(mCenterX - radius / 3, mCenterY - radius / 3);
        failPath.lineTo(mCenterX + radius / 3, mCenterY + radius / 3);
        failPath.moveTo(mCenterX + radius / 3, mCenterY - radius / 3);
        failPath.lineTo(mCenterX - radius / 3, mCenterY + radius / 3);
        mFailPathMeasure = new PathMeasure(failPath, false);
        mFailDstPath = new Path();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        // 取消硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 初始化画笔
        mBluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBluePaint.setColor(R.color.colorEDTheme);
        mBluePaint.setStyle(Paint.Style.STROKE);
        mBluePaint.setStrokeCap(Paint.Cap.ROUND);
        mBluePaint.setStrokeWidth(10);

        mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedPaint.setColor(Color.RED);
        mRedPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setStrokeCap(Paint.Cap.ROUND);
        mRedPaint.setStrokeWidth(10);

        // 初始化时, 动画为加载状态
        curStatus = STATUS_LOADING;

        // 新建 Loading 动画并 start
        mLoadingAnimator = ValueAnimator.ofFloat(0, 1);
        mLoadingAnimator.setDuration(2000);
        mLoadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mLoadingAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (curStatus == STATUS_SUCCESS) {
                    mSuccessAnimator.start();
                } else if (curStatus == STATUS_FAIL) {
                    mFailAnimator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        mLoadingAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mLoadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mLoadingAnimator.setRepeatMode(ValueAnimator.RESTART);
        mLoadingAnimator.start();
        // 新建 success 动画
        mSuccessAnimator = ValueAnimator.ofFloat(0, 2);
        mSuccessAnimator.setDuration(1600);
        mSuccessAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        // 新建 fail 动画
        mFailAnimator = ValueAnimator.ofFloat(0, 3);
        mFailAnimator.setDuration(2100);
        mFailAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    /**
     * 将动画的状态从 Loading 变为 success 或 fail
     */
    public void setStatus(int status) {
        if (curStatus == STATUS_LOADING && status != STATUS_LOADING) {
            curStatus = status;
            mLoadingAnimator.end();
        }
    }

    private int mSuccessIndex = 1;
    private int mFailIndex = 1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 在 Loading 状态下 Canvas 会被旋转, 需要在第一次进入时保存
        if (!hasCanvasSaved) {
            canvas.save();
            hasCanvasSaved = true;
        }
        // 判断当前动画的状态并绘制相应动画
        if (curStatus == STATUS_LOADING) {
            mDstPath.reset();
            float length = mPathMeasure.getLength();
            float stop = mProgress * length;
            float start = (float) (stop - (0.5 - Math.abs(mProgress - 0.5)) * length);
            mPathMeasure.getSegment(start, stop, mDstPath, true);
            // 旋转画布
            mCurRotate = (mCurRotate + 2) % 360;
            canvas.rotate(mCurRotate, mCenterX, mCenterY);
            canvas.drawPath(mDstPath, mBluePaint);
        } else if (curStatus == STATUS_SUCCESS) {
            if (!hasCanvasRestored) {
                canvas.restore();
                hasCanvasRestored = true;
            }
            if (mProgress < 1) {
                float stop = mSuccessPathMeasure.getLength() * mProgress;
                mSuccessPathMeasure.getSegment(0, stop, mSuccessDstPath, true);
            } else {
                if (mSuccessIndex == 1) {
                    mSuccessIndex = 2;
                    mSuccessPathMeasure.getSegment(0, mSuccessPathMeasure.getLength(),
                            mSuccessDstPath, true);
                    mSuccessPathMeasure.nextContour();
                }
                float stop = mSuccessPathMeasure.getLength() * (mProgress - 1);
                mSuccessPathMeasure.getSegment(0, stop, mSuccessDstPath, true);
            }
            canvas.drawPath(mSuccessDstPath, mBluePaint);
        } else if (curStatus == STATUS_FAIL) {
            if (!hasCanvasRestored) {
                canvas.restore();
                hasCanvasRestored = true;
            }
            if (mProgress < 1) {
                float stop = mFailPathMeasure.getLength() * mProgress;
                mFailPathMeasure.getSegment(0, stop, mFailDstPath, true);
            } else if (mProgress < 2) {
                if (mFailIndex == 1) {
                    mFailIndex = 2;
                    mFailPathMeasure.getSegment(0, mFailPathMeasure.getLength(),
                            mFailDstPath, true);
                    mFailPathMeasure.nextContour();
                }
                float stop = mFailPathMeasure.getLength() * (mProgress - 1);
                mFailPathMeasure.getSegment(0, stop, mFailDstPath, true);
            } else {
                if (mFailIndex == 2) {
                    mFailIndex = 3;
                    mFailPathMeasure.getSegment(0, mFailPathMeasure.getLength(),
                            mFailDstPath, true);
                    mFailPathMeasure.nextContour();
                }
                float stop = mFailPathMeasure.getLength() * (mProgress - 2);
                mFailPathMeasure.getSegment(0, stop, mFailDstPath, true);
            }
            canvas.drawPath(mFailDstPath, mRedPaint);
        }
    }
}
