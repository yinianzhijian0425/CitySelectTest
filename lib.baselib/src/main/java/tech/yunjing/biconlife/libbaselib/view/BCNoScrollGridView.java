package tech.yunjing.biconlife.libbaselib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 可嵌套GridView
 *
 * @author nanPengFei
 */
public class BCNoScrollGridView extends GridView {

    public BCNoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BCNoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BCNoScrollGridView(Context context) {
        super(context);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = Integer.MAX_VALUE >> 2;
        LKLogUtil.e("i===="+i);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
