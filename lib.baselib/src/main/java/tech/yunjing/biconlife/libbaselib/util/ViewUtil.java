package tech.yunjing.biconlife.libbaselib.util;
/**
 * Created by Bys on 2017/8/3.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：Bys on 2017/8/3.
 * 邮箱：baiyinshi@vv.cc
 */

public class ViewUtil {

    /**
     * 设置TextView颜色
     * @param strResId
     */
    public static  void setTextViewColor(Context context, TextView textView, @ColorRes int strResId){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            textView.setTextColor(context.getResources().getColor(strResId, null));
        }else {
            textView.setTextColor(context.getResources().getColor(strResId));
        }
    }


    /**
     * 重置View的宽高
     *
     * @param view
     * @param width
     * @param height
     */
    public static void resetViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams lp;
        lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }


    /**
     * 按比例 重置View的宽高
     *
     * @param context
     * @param view
     * @param width
     * @param height
     */
    public static void resetViewSize(Context context, View view, int width, int height) {
        int w = DisplayUtil.getScreenWidth(context);
        ViewGroup.LayoutParams lp;
        lp = view.getLayoutParams();
        lp.width = w;
        lp.height = w * height / width;
        view.setLayoutParams(lp);
    }
}
