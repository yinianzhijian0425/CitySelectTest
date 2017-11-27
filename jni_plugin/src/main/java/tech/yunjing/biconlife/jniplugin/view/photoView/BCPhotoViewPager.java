package tech.yunjing.biconlife.jniplugin.view.photoView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 公共图片展示ViewPager
 * Created by sun.li on 2017/9/16.
 */

public class BCPhotoViewPager extends ViewPager{

    public BCPhotoViewPager(Context context) {
        super(context);
    }

    public BCPhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
