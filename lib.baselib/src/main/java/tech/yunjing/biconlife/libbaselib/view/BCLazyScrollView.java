package tech.yunjing.biconlife.libbaselib.view;

/**
 * Created by wsw on 2017/3/17 0017.
 * scrollview滑动
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 带滚动监听的scrollview
 */
public class BCLazyScrollView extends ScrollView {

    public interface ScrollViewListener {
        void onScrollChanged(BCLazyScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    private OnScrollToBottomListener mOnScrollToBottomListener;

    public BCLazyScrollView(Context context) {
        super(context);
    }

    public BCLazyScrollView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
    }

    public BCLazyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
        if(mOnScrollToBottomListener != null) {
            mOnScrollToBottomListener.onScroll(x, y, oldx, oldy);
        }
            // 滑动的距离加上本身的高度与子View的高度对比
        if(y + getHeight() >=  getChildAt(0).getMeasuredHeight()){
            // ScrollView滑动到底部
            if(mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onScrollToBottom();
            }
        } else {
            if(mOnScrollToBottomListener != null) {
                mOnScrollToBottomListener.onNotScrollToBottom();
            }
        }
    }

    public void setScrollToBottomListener(OnScrollToBottomListener listener) {
        this.mOnScrollToBottomListener = listener;
    }

    public interface OnScrollToBottomListener {
        /** ScrollView滑动动态监听*/
        void onScroll(int x, int y, int oldx, int oldy);
        /** ScrollView滑动到底部回调*/
        void onScrollToBottom();
        /** ScrollView未滑动到底部回调*/
        void onNotScrollToBottom();
    }

}