package tech.yunjing.biconlife.liblkclass.widget.Loader.indicators;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

import tech.yunjing.biconlife.liblkclass.widget.Loader.LKIndicator;

/**
 * 圆形往外扩散加载动画样式
 */
public class LKLoaderB extends LKIndicator {

    float[] scaleFloats = new float[]{1, 1, 1};
    int[] alphaInts = new int[]{255, 255, 255};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing = 4;
        final int maxValue = 3;
        for (int i = 0; i < maxValue; i++) {
            paint.setAlpha(alphaInts[i]);
            canvas.scale(scaleFloats[i], scaleFloats[i], getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - circleSpacing, paint);
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        long[] delays = new long[]{0, 200, 400};
        final int maxValue = 3;
        for (int i = 0; i < maxValue; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(0, 1);
            scaleAnim.setInterpolator(new LinearInterpolator());
            scaleAnim.setDuration(3500);
            scaleAnim.setRepeatCount(-1);
            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 0);
            alphaAnim.setInterpolator(new LinearInterpolator());
            alphaAnim.setDuration(3500);
            alphaAnim.setRepeatCount(-1);
            addUpdateListener(alphaAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphaInts[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);

            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }

}
