package tech.yunjing.biconlife.jniplugin.im.voip.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import tech.yunjing.biconlife.jniplugin.R;


/**
 * Created by dup on 2017/7/15.
 * 三个小点 闪动view。视频加载控件
 */

public class VoIPWaitingView extends View {


    private int colorStart; //颜色开始
    private int colorEnd; //颜色结束
    private float alphaRange;//透明图变化最小
    private float scaleRange; //大小变化最小
    private float circleRadius = 12; //小圆半径
    private int duration = 800;//动画时长

    private ArgbEvaluator argbEvaluator;
    private Paint paint;
    private ValueAnimator valueAnimator;
    private float fraction;
    private ValueAnimator valueAnimator2;
    private float fraction2;
    private ValueAnimator valueAnimator3;
    private float fraction3;
    private AnimatorSet as;
    private int centerX;
    private int circleY;
    private float[] circleX;

    public VoIPWaitingView(Context context) {
        this(context, null);
    }

    public VoIPWaitingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoIPWaitingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VoIPWaitingView);
//        colorStart = a.getColor(R.styleable.VoIPWaitingView_circle_color_start, Color.WHITE);
//        colorEnd = a.getColor(R.styleable.VoIPWaitingView_circle_color_end, Color.WHITE);
        colorStart = a.getColor(R.styleable.VoIPWaitingView_circle_color_start, Color.GREEN);
        colorEnd = a.getColor(R.styleable.VoIPWaitingView_circle_color_end, Color.GREEN);
        alphaRange = a.getFloat(R.styleable.VoIPWaitingView_alpha_range, 0.4f);
        scaleRange = a.getFloat(R.styleable.VoIPWaitingView_scale_range, 0.8f);
        duration = a.getInt(R.styleable.VoIPWaitingView_anim_duration, 800);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));

        argbEvaluator = new ArgbEvaluator();
        valueAnimator = new ObjectAnimator();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        if (modeW == MeasureSpec.UNSPECIFIED || modeW == MeasureSpec.AT_MOST) {
            w = 120;
        }

        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        if (modeH == MeasureSpec.UNSPECIFIED || modeH == MeasureSpec.AT_MOST) {
            h = 120;
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleRadius = w / 10f;
        int min = (int) Math.min(circleRadius, h / 2);
        circleRadius = min;
        centerX = getWidth() / 2;
        circleY = getHeight() / 2;
        circleX = new float[3];
        circleX[0] = centerX - 4 * circleRadius;
        circleX[1] = centerX;
        circleX[2] = centerX + 4 * circleRadius;

        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
            }
        });

        valueAnimator2 = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator2.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator2.setDuration(duration);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction2 = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator2.setStartDelay(duration / 2);

        valueAnimator3 = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator3.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator3.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator3.setDuration(duration);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction3 = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator3.setStartDelay(duration);

        as = new AnimatorSet();
        as.playTogether(valueAnimator, valueAnimator2, valueAnimator3);
        as.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <= 2; i++) {
            drawCircle(canvas, i);
        }
        postInvalidateDelayed(100);
    }

    private void drawCircle(Canvas canvas, int i) {
        paint.setColor((int) argbEvaluator.evaluate(calcFraction(i), colorStart, colorEnd));
        paint.setAlpha((int) ((alphaRange + calcFraction(i) * (1 - alphaRange)) * 255));
        canvas.drawCircle(circleX[i], circleY, circleRadius * scaleRange + (circleRadius - scaleRange * circleRadius) * calcFraction(i), paint);
    }

    private float calcFraction(int i) {
        switch (i) {
            case 0:
                return fraction;
            case 1:
                return fraction2;
            case 2:
                return fraction3;
            default:
                break;
        }
        return fraction;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            if (as != null) {
                as.cancel();
            }
        } else if (visibility == View.VISIBLE) {
            if (as != null && !as.isRunning()) {
                as.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (as != null) {
            as.cancel();
        }
        as = null;
    }
}
