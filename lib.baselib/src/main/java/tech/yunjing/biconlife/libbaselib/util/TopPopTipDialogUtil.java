package tech.yunjing.biconlife.libbaselib.util;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.base.BaseApplication;
import tech.yunjing.biconlife.liblkclass.common.util.LKDialogUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;

/**
 * Created by xining on 2017/9/2 0002.
 * 顶部提示弹出dialog
 */

public class TopPopTipDialogUtil {
//    private static TopPopTipDialogUtil mInstance = null;
//    private final int WAIT_TIME = 1000;
//
//    private Context mContext;
//
//    private Dialog dialog;
//
//    private ImageView iv_dtpt_pic;
//
//    private TextView tv_dtpt_tip;
//
//    private boolean whetherShowing = true;
//
//    private TopPopTipDialogUtil() {
//    }
//
//    public static TopPopTipDialogUtil getInstance() {
//        if (null == mInstance) {
//            synchronized (TopPopTipDialogUtil.class) {
//                if (null == mInstance) {
//                    mInstance = new TopPopTipDialogUtil();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    public Dialog getDialog() {
//        Dialog dg = null;
//        try {
//            if (mContext == null) {
//                mContext = BaseApplication.getContext();
//            }
//            if (dg == null) {
//                View dropDownView = LayoutInflater.from(mContext).inflate(R.layout.dialog_top_poptip, null);
//                iv_dtpt_pic = (ImageView) dropDownView.findViewById(R.id.iv_dtpt_pic);
//                tv_dtpt_tip = (TextView) dropDownView.findViewById(R.id.tv_dtpt_tip);
//                dg = LKDialogUtil.getDialog(dropDownView, R.style.UserDetailsDialogStyle, 0, Gravity.TOP, false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            dg = null;
//        }
//        return dg;
//    }
//
//    /***
//     *顶部提示的Dialog
//     */
//    public void showTopTipDialog(Context context, int picRes, String tip) {
//        try {
//            mContext = context;
//            dialog = getDialog();
//            if(mContext == null || dialog == null){
//                return;
//            }
//            Window window = dialog.getWindow();
//            if (null != window) {
//                WindowManager.LayoutParams layoutParams = window.getAttributes();
//                dialog.onWindowAttributesChanged(layoutParams);
//                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                layoutParams.dimAmount = 0;
//            }
//            dialog.setCancelable(true);
//            if (iv_dtpt_pic != null) {
//                iv_dtpt_pic.setImageResource(picRes);
//            }
//            if (tv_dtpt_tip != null) {
//                tv_dtpt_tip.setText(tip);
//            }
//            doWaiteClosePage();
//            if (mContext != null && dialog != null && !dialog.isShowing() && isWhetherShowing()) {
//                dialog.show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /***
//     * 暂停2秒关闭页面
//     */
//    private void doWaiteClosePage() {
//        try {
//            new CountDownTimer(WAIT_TIME, WAIT_TIME) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                }
//
//                @Override
//                public void onFinish() {
//                    if (mContext != null && dialog!=null && dialog.isShowing() && isWhetherShowing()) {
//                        dialog.dismiss();
//                    }
//                }
//            }.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean isWhetherShowing() { return whetherShowing; }
//
//    public void setWhetherShowing(boolean whetherShowing) { this.whetherShowing = whetherShowing; }
}
