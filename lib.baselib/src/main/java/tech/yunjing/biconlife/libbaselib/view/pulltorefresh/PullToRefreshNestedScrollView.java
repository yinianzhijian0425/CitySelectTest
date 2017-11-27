package tech.yunjing.biconlife.libbaselib.view.pulltorefresh;
/**
 * Created by Bys on 2017/8/7.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import tech.yunjing.biconlife.libbaselib.R;

/**
 * 作者：Bys on 2017/8/7.
 * 邮箱：baiyinshi@vv.cc
 */

public class PullToRefreshNestedScrollView extends PullToRefreshBase<NestedScrollView>{

public PullToRefreshNestedScrollView(Context context) {
        super(context);
        }

public PullToRefreshNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        }

public PullToRefreshNestedScrollView(Context context, Mode mode) {
        super(context, mode);
        }

public PullToRefreshNestedScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
        }

@Override
public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
        }

@Override
protected NestedScrollView createRefreshableView(Context context, AttributeSet attrs) {
    NestedScrollView scrollView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
        scrollView = new InternalScrollViewSDK9(context, attrs);
        } else {
        scrollView = new NestedScrollView(context, attrs);
        }

        scrollView.setId(R.id.scrollview);
        return scrollView;
        }

@Override
protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
        }

@Override
protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
        return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
        }

@TargetApi(9)
final class InternalScrollViewSDK9 extends NestedScrollView {

    public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

        // Does all of the hard work...
        OverscrollHelper.overScrollBy(PullToRefreshNestedScrollView.this, deltaX, scrollX, deltaY, scrollY,
                getScrollRange(), isTouchEvent);

        return returnValue;
    }

    /**
     * Taken from the AOSP ScrollView source
     */
    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
        }
        return scrollRange;
    }
}
}
