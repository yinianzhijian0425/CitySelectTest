package tech.yunjing.biconlife.libbaselib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 自定义listView
 * 
 * @author 
 *
 */
public class BCNoScrollListView extends ListView {

	public BCNoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public BCNoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BCNoScrollListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * 设置istView高度使其适应ScrollView
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public float distance = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			distance = event.getY();
			LKLogUtil.e("===distance===" + distance);
		}
		return super.onTouchEvent(event);
	}
}
