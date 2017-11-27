package tech.yunjing.biconlife.liblkclass.common.util;


import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import tech.yunjing.biconlife.liblkclass.view.WindowBottomDialog;

public class LKDialogUtil {
    /**
     * Dialog显示工具
     *
     * @param view
     * @param style
     * @param anim
     * @param gravity
     * @param isTouchCancel
     * @return
     */
    public static Dialog getDialog(View view, int style, int anim, int gravity, boolean isTouchCancel) {
        Dialog dialog = new Dialog(view.getContext(), style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        if (null != window) {
            window.setWindowAnimations(anim);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.x = 0;
            layoutParams.gravity = gravity;
            dialog.onWindowAttributesChanged(layoutParams);
            if (gravity == Gravity.BOTTOM) {
                layoutParams.width = LayoutParams.MATCH_PARENT;
            }
        }
        dialog.setCanceledOnTouchOutside(isTouchCancel);
        return dialog;
    }

    /**
     * 点击非dialog区域，dialog不消失
     *
     * @param activity
     * @param view
     * @param style
     * @param anim
     * @param centerY  是否从中间弹出
     * @return dialog
     */
    public static Dialog getDialog(Activity activity, View view, int style, int anim, boolean centerY) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        if (centerY) {
            wl.gravity = Gravity.CENTER;
        } else {
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        }
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 点击非dialog区域，dialog消失
     *
     * @param activity
     * @param view
     * @param style
     * @param anim
     * @param centerY  是否从中间弹出
     * @return dialog
     */
    public static Dialog getDialogOutside(Activity activity, View view, int style, int anim, boolean centerY) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        if (centerY) {
            wl.gravity = Gravity.CENTER;
        } else {
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        }
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog getDialogPOp(Activity activity, View view, int style, int anim, boolean centerY, boolean isTouchDiss) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(1);
        dialog.setContentView(view, new LayoutParams(-1, -2));
        Window window = dialog.getWindow();
        window.setWindowAnimations(anim);
        android.view.WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        if (centerY) {
            wl.gravity = 17;
        } else {
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        }

        wl.width = -1;
        wl.height = -2;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(isTouchDiss);
        return dialog;
    }


    /**
     * 点击非dialog区域，dialog消失
     *
     * @param activity
     * @param view
     * @param style
     * @param anim
     * @param centerY  是否从中间弹出
     * @return dialog
     */
    public static Dialog getDialogOutSide(Activity activity, View view, int style, int anim, boolean centerY) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        Window window = dialog.getWindow();
        if (anim != 0) {
            Animation animation = AnimationUtils.loadAnimation(activity, anim);
            view.setAnimation(animation);
        }
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        if (centerY) {
            wl.gravity = Gravity.CENTER;
        } else {
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        }
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    /**
     * 点击非dialog区域，dialog消失
     * 宽度铺满全屏 高度
     *
     * @param activity
     * @param view
     * @param style
     * @param anim
     * @return dialog
     */
    public static Dialog getDialogWidth(Activity activity, View view, int style, int anim) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        if (anim != 0) {
            Animation animation = AnimationUtils.loadAnimation(activity, anim);
            view.setAnimation(animation);
        }
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.width = LayoutParams.MATCH_PARENT;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        wl.height = activity.getWindowManager().getDefaultDisplay().getHeight() / 2;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    /**
     * 点击非dialog区域，dialog消失
     * 宽度铺满全屏 高度为屏幕三分之二
     *
     * @param activity
     * @param view
     * @param style
     * @param anim
     * @param centerY  是否从中间弹出
     * @return dialog
     */
    public static Dialog getDialogWidth(Activity activity, View view, int style, int anim, boolean centerY) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        if (anim != 0) {
            Animation animation = AnimationUtils.loadAnimation(activity, anim);
            view.setAnimation(animation);
        }
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.width = LayoutParams.MATCH_PARENT;
//        wl.height = LayoutParams.WRAP_CONTENT;
        wl.height = activity.getWindowManager().getDefaultDisplay().getHeight() / 3 * 2;
        if (centerY) {
            wl.gravity = Gravity.CENTER;
        } else {
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        }
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    /**
     * 点击非dialog区域，dialog消失
     * 宽度铺满全屏 高度二分之一
     *
     * @param activity
     * @param view
     * @param style
     * @param anim
     * @param centerY    是否从中间弹出
     * @param viewHeight 距离底部高度
     * @return dialog
     */
    public static Dialog getDialogHeight(Activity activity, View view, int style, int anim, boolean centerY, int viewHeight) {
        Dialog dialog = new Dialog(activity, style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        if (anim != 0) {
            Animation animation = AnimationUtils.loadAnimation(activity, anim);
            view.setAnimation(animation);
        }
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.width = LayoutParams.MATCH_PARENT;
//        wl.height = LayoutParams.MATCH_PARENT;
        wl.height = activity.getWindowManager().getDefaultDisplay().getHeight() - DensityUtil.dip2px(75);
        if (centerY) {
            wl.gravity = Gravity.CENTER;
        } else {
            wl.gravity = Gravity.TOP;
        }
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    /**
     * 从界面底部弹出的填充屏幕宽度的Dialog
     *
     * @param activity 上下文
     * @param view     Dialog布局
     * @param style    Dialog布局样式
     * @return dialog
     */
    public static Dialog getBottomDialog(Activity activity, View view, int style) {
        WindowBottomDialog windowBottomDialog = new WindowBottomDialog(activity, 0);
        windowBottomDialog.setBottomView(view);
        return windowBottomDialog;
//        Dialog dialog = new Dialog(activity, style);
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        attributes.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(attributes);
//        return dialog;
    }
    /**
     * 从界面底部弹出的填充屏幕宽度的Dialog
     *
     * @param activity 上下文
     * @param view     Dialog布局
     * @param style    Dialog布局样式
     * @return dialog
     */
    public static Dialog getBottomDialog1(Activity activity, View view, int style) {
        Dialog dialog = new Dialog(activity, style);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = LinearLayout.LayoutParams.MATCH_PARENT;
        attributes.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
        return dialog;
    }
}
