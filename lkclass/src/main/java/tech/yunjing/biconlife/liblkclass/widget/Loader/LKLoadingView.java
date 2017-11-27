package tech.yunjing.biconlife.liblkclass.widget.Loader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import tech.yunjing.biconlife.liblkclass.R;
import tech.yunjing.biconlife.liblkclass.widget.Loader.indicators.LKLoaderA;


/**
 * 自定义加载动画控件
 * <p/>
 * app:indicatorName="LKLoaderA"
 * app:indicatorName="LKLoaderB"
 * app:indicatorName="LKLoaderC"
 */
public class LKLoadingView extends View {
    private static final LKLoaderA DEFAULT_INDICATOR = new LKLoaderA();

    private static final int MIN_SHOW_TIME = 500;
    private static final int MIN_DELAY = 500;

    private long mStartTime = -1;

    private boolean mPostedHide = false;

    private boolean mPostedShow = false;

    private boolean mDismissed = false;

    private final Runnable mDelayedHide = new Runnable() {

        @Override
        public void run() {
            mPostedHide = false;
            mStartTime = -1;
            setVisibility(View.GONE);
        }
    };

    private final Runnable mDelayedShow = new Runnable() {

        @Override
        public void run() {
            mPostedShow = false;
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
            }
        }
    };

    int mMinWidth;
    int mMaxWidth;
    int mMinHeight;
    int mMaxHeight;

    private LKIndicator mLKIndicator;
    private int mIndicatorColor;

    private boolean mShouldStartAnimationDrawable;

    public LKLoadingView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public LKLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, R.style.LKLoadingView);
    }

    public LKLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, R.style.LKLoadingView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LKLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, R.style.LKLoadingView);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mMinWidth = 24;
        mMaxWidth = 48;
        mMinHeight = 24;
        mMaxHeight = 48;

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.LKLoadingView, defStyleAttr, defStyleRes);

        mMinWidth = a.getDimensionPixelSize(R.styleable.LKLoadingView_minWidth, mMinWidth);
        mMaxWidth = a.getDimensionPixelSize(R.styleable.LKLoadingView_maxWidth, mMaxWidth);
        mMinHeight = a.getDimensionPixelSize(R.styleable.LKLoadingView_minHeight, mMinHeight);
        mMaxHeight = a.getDimensionPixelSize(R.styleable.LKLoadingView_maxHeight, mMaxHeight);
        String indicatorName = a.getString(R.styleable.LKLoadingView_indicatorName);
        mIndicatorColor = a.getColor(R.styleable.LKLoadingView_indicatorColor, Color.parseColor("#F1686C"));
        setIndicator(indicatorName);
        if (mLKIndicator == null) {
            setIndicator(DEFAULT_INDICATOR);
        }
        a.recycle();
    }

    public LKIndicator getIndicator() {
        return mLKIndicator;
    }

    public void setIndicator(LKIndicator d) {
        if (mLKIndicator != d) {
            if (mLKIndicator != null) {
                mLKIndicator.setCallback(null);
                unscheduleDrawable(mLKIndicator);
            }

            mLKIndicator = d;
            setIndicatorColor(mIndicatorColor);
            if (d != null) {
                d.setCallback(this);
            }
            postInvalidate();
        }
    }


    /**
     * 设置指示器颜色
     *
     * @param color
     */
    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
        mLKIndicator.setColor(color);
    }


    /**
     * 设置指示器类名
     *
     * @param indicatorName
     */
    public void setIndicator(String indicatorName) {
        if (TextUtils.isEmpty(indicatorName)) {
            return;
        }
        StringBuilder drawableClassName = new StringBuilder();
        if (!".".contains(indicatorName)) {
            String defaultPackageName = getClass().getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(indicatorName);
        try {
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            LKIndicator lkIndicator = (LKIndicator) drawableClass.newInstance();
            setIndicator(lkIndicator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smoothToShow() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        setVisibility(VISIBLE);
    }

    public void smoothToHide() {
        startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        setVisibility(GONE);
    }

    public void hide() {
        mDismissed = true;
        removeCallbacks(mDelayedShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
            setVisibility(View.GONE);
        } else {
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff);
                mPostedHide = true;
            }
        }
    }

    public void show() {
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY);
            mPostedShow = true;
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mLKIndicator
                || super.verifyDrawable(who);
    }

    void startAnimation() {
        if (getVisibility() != VISIBLE) {
            return;
        }

        if (mLKIndicator instanceof Animatable) {
            mShouldStartAnimationDrawable = true;
        }
        postInvalidate();
    }

    void stopAnimation() {
        if (mLKIndicator instanceof Animatable) {
            mLKIndicator.stop();
            mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                stopAnimation();
            } else {
                startAnimation();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (verifyDrawable(dr)) {
            final Rect dirty = dr.getBounds();
            final int scrollX = getScrollX() + getPaddingLeft();
            final int scrollY = getScrollY() + getPaddingTop();

            invalidate(dirty.left + scrollX, dirty.top + scrollY,
                    dirty.right + scrollX, dirty.bottom + scrollY);
        } else {
            super.invalidateDrawable(dr);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateDrawableBounds(w, h);
    }

    private void updateDrawableBounds(int w, int h) {
        w -= getPaddingRight() + getPaddingLeft();
        h -= getPaddingTop() + getPaddingBottom();

        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;

        if (mLKIndicator != null) {
            final int intrinsicWidth = mLKIndicator.getIntrinsicWidth();
            final int intrinsicHeight = mLKIndicator.getIntrinsicHeight();
            final float intrinsicAspect = (float) intrinsicWidth / intrinsicHeight;
            final float boundAspect = (float) w / h;
            if (intrinsicAspect != boundAspect) {
                if (boundAspect > intrinsicAspect) {
                    final int width = (int) (h * intrinsicAspect);
                    left = (w - width) / 2;
                    right = left + width;
                } else {
                    final int height = (int) (w * (1 / intrinsicAspect));
                    top = (h - height) / 2;
                    bottom = top + height;
                }
            }
            mLKIndicator.setBounds(left, top, right, bottom);
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    void drawTrack(Canvas canvas) {
        final Drawable d = mLKIndicator;
        if (d != null) {
            final int saveCount = canvas.save();
            canvas.translate(getPaddingLeft(), getPaddingTop());
            d.draw(canvas);
            canvas.restoreToCount(saveCount);

            if (mShouldStartAnimationDrawable && d instanceof Animatable) {
                ((Animatable) d).start();
                mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = 0;
        int dh = 0;

        final Drawable d = mLKIndicator;
        if (d != null) {
            dw = Math.max(mMinWidth, Math.min(mMaxWidth, d.getIntrinsicWidth()));
            dh = Math.max(mMinHeight, Math.min(mMaxHeight, d.getIntrinsicHeight()));
        }

        updateDrawableState();

        dw += getPaddingLeft() + getPaddingRight();
        dh += getPaddingTop() + getPaddingBottom();

        final int measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0);
        final int measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        final int[] state = getDrawableState();
        if (mLKIndicator != null && mLKIndicator.isStateful()) {
            mLKIndicator.setState(state);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);

        if (mLKIndicator != null) {
            mLKIndicator.setHotspot(x, y);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
        removeCallbacks();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        removeCallbacks(mDelayedShow);
    }


}
