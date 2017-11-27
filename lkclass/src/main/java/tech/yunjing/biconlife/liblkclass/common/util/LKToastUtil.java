package tech.yunjing.biconlife.liblkclass.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import tech.yunjing.biconlife.liblkclass.R;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;

/**
 * 高效率显示Toast的方法，不需要等待已经弹出的Toast消失，即可显示新的Toast
 *
 * @author nanPengFei
 */
public class LKToastUtil {
    /** 普通的Toast框*/
    public static Toast mToast;
    @SuppressLint("StaticFieldLeak")
    private static TextView tv_toastMsg;

    /** 错误信息的Toast框*/
    public static Toast mErrorToast;

    /** Toast距离屏幕底部高度，单位DP*/
    private static final int ALIGN_PARENT_BOTTOM_DP = 50;

    /**
     * 实例化普通的Toast对象
     *
     * @return
     */
    public static Toast getInstance(int time,String text) {
        if (mToast == null) {
            mToast = Toast.makeText(LKApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        mToast.setDuration(time);
        return mToast;
    }

    /**
     * 实例化错误信息的Toast对象
     *
     * @return
     */
    public static Toast getErrorToastInstance(int time,String text) {
        if (mErrorToast == null) {
            mErrorToast = Toast.makeText(LKApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        mErrorToast.setDuration(time);
        return mErrorToast;
    }

    /**
     * 快速连续弹Toast
     *
     * @param msg 需要显示的内容
     */
    public static void showToastLong(final String msg) {
        try {
            LKCommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (null == mToast || null == tv_toastMsg) {
                        mToast = getInstance(Toast.LENGTH_LONG, "");
                        @SuppressLint("InflateParams") View view = LayoutInflater.from(LKApplication.getContext()).inflate(R.layout.view_toast, null);
                        tv_toastMsg = (TextView) view.findViewById(R.id.tv_toastMsg);
                        mToast.setView(view);
                        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, LKCommonUtil.dip2px(ALIGN_PARENT_BOTTOM_DP));
                    }
                    tv_toastMsg.setText(msg);
                    mToast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 快速连续弹Toast
     *
     * @param msg 需要显示的内容
     */
    public static void showToastShort(final String msg) {
        try {
            LKCommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = getInstance(Toast.LENGTH_SHORT, "");
                        @SuppressLint("InflateParams") View view = LayoutInflater.from(LKApplication.getContext()).inflate(R.layout.view_toast, null);
                        tv_toastMsg = (TextView) view.findViewById(R.id.
                                tv_toastMsg);
                        mToast.setView(view);
                        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, LKCommonUtil.dip2px(ALIGN_PARENT_BOTTOM_DP));
                    }
                    tv_toastMsg.setText(msg);
                    mToast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /** 显示网络异常时的Toast*/
//    public static void showRequestFailToast(String content){
//        try {
//            Context mContext = LKApplication.getContext();
//            mErrorToast = getErrorToastInstance(Toast.LENGTH_SHORT,content);
//            mErrorToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
//            LinearLayout toastView = (LinearLayout) mErrorToast.getView();
//            toastView.removeAllViews();
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LKCommonUtil.getScreenWidth(),LKCommonUtil.dip2px(48));
//            toastView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            toastView.setLayoutParams(lp);
//            LinearLayout linearLayout = new LinearLayout(mContext);
//            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//            linearLayout.setGravity(Gravity.CENTER);
//            linearLayout.setLayoutParams(lp);
//            LinearLayout.LayoutParams imageLp = new LinearLayout.LayoutParams(LKCommonUtil.dip2px(17),LKCommonUtil.dip2px(17));
//            ImageView iv = new ImageView(mContext);
//            iv.setImageResource(R.mipmap.icon_request_fail_image);
//            iv.setLayoutParams(imageLp);
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            linearLayout.addView(iv);
//            TextView tv = new TextView(mContext);
//            tv.setText(content);
//            tv.setTextColor(Color.parseColor("#EA4C24"));
//            tv.setTextSize(16);
//            tv.setPadding(LKCommonUtil.dip2px(4),0,0,0);
//            linearLayout.addView(tv);
//            toastView.addView(linearLayout, 0);
//            mErrorToast.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
