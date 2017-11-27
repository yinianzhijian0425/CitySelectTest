package tech.yunjing.biconlife.libbaselib.view.pulltorefresh;
/**
 * Created by Bys on 2017/8/7.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 作者：Bys on 2017/8/7.
 * 邮箱：baiyinshi@vv.cc
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context,
                                                 AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        return recyclerView;
    }

    @Override
    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onPtrRestoreInstanceState(savedInstanceState);
        }catch (Exception e) {}
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }





    /**
     * @return boolean:
     * @Description: 判断第一个条目是否完全可见
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                LKLogUtil.e(LOG_TAG + "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView
                        .getTop();
            }
        }

        return false;
    }

    /**
     * @return int: 位置
     * @Description: 获取第一个可见子View的位置下标
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }

    /**
     * @return boolean:
     * @Description: 判断最后一个条目是否完全可见
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private boolean isLastItemVisible() {
        final RecyclerView.Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                LKLogUtil.e(LOG_TAG + "isLastItemVisible. Empty View.");
            }
            return true;

        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount() - 1) {
                return mRefreshableView.getChildAt(
                        mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView
                        .getBottom();
            }
        }

        return false;
    }

    /**
     * @return int: 位置
     * @Description: 获取最后一个可见子View的位置下标
     * @version 1.0
     * @date 2015-9-23
     * @Author zhou.wenkai
     */
    private int getLastVisiblePosition() {
        View lastVisibleChild = mRefreshableView.getChildAt(mRefreshableView
                .getChildCount() - 1);
        return lastVisibleChild != null ? mRefreshableView
                .getChildAdapterPosition(lastVisibleChild) : -1;
    }
}
