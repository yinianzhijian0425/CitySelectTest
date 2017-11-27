package tech.yunjing.biconlife.liblkclass.common.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tech.yunjing.biconlife.liblkclass.global.LKApplication;

/**
 * 常用方法聚集类
 *
 * @author nanPengFei
 */
public class LKCommonUtil {

    public static void runOnUIThread(Runnable runnable) {
        LKApplication.getMainHandler().postDelayed(runnable, 0);
    }

    /**
     * 根据id获取dimens中定义的dimen
     *
     * @param resId 指向dimen的id
     * @return float
     */
    public static float getDimens(int resId) {
        return LKApplication.getContext().getResources().getDimension(resId);
    }

    /**
     * 根据id获取strings中定义的string
     *
     * @param resId 指向string的id
     * @return String
     */
    public static String getString(int resId) {
        return LKApplication.getContext().getResources().getString(resId);
    }

    /**
     * 获取图片资源
     *
     * @param resId
     * @return Drawable
     */
    public static Drawable getDrawable(int resId) {
        return LKApplication.getContext().getResources().getDrawable(resId);
    }

    /**
     * 获取字符串数组资源
     *
     * @param resId
     * @return String[]
     */
    public static String[] getStringArray(int resId) {
        return LKApplication.getContext().getResources().getStringArray(resId);
    }

    /**
     * 根据手机分辨率转dp为px
     *
     * @param dpValue dp值
     * @return int
     */
    public static int dip2px(float dpValue) {
        final float scale = LKApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率转px为 dp
     *
     * @param pxValue px值
     * @return int
     */
    public static int px2dip(float pxValue) {
        final float scale = LKApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return int
     */
    public static int px2sp(float pxValue) {
        final float fontScale = LKApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = LKApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取手机状态栏高度
     *
     * @return int
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = LKCommonUtil.dip2px(18);
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = LKApplication.getContext().getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 将指定childView从它的父View中移除
     *
     * @param child
     */
    public static void removeSelfFromParent(View child) {
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                // 将child从父view当中移除
                group.removeView(child);
            }
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        Resources resources = LKApplication.getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        Resources resources = LKApplication.getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕中控件顶部位置的高度--即控件顶部的Y点
     *
     * @return
     */
    public static int getScreenViewTopHeight(View view) {
        return view.getTop();
    }

    /**
     * 获取屏幕中控件底部位置的高度--即控件底部的Y点
     *
     * @return
     */
    public static int getScreenViewBottomHeight(View view) {
        return view.getBottom();
    }

    /**
     * 获取屏幕中控件左侧的位置--即控件左侧的X点
     *
     * @return
     */
    public static int getScreenViewLeftHeight(View view) {
        return view.getLeft();
    }

    /**
     * 获取屏幕中控件右侧的位置--即控件右侧的X点
     *
     * @return
     */
    public static int getScreenViewRightHeight(View view) {
        return view.getRight();
    }

    /**
     * 判断是否为手机号
     *
     * @param phoneStr
     * @return
     */
    public static boolean isPhone(String phoneStr) {
        if (TextUtils.isEmpty(phoneStr)) {
            return false;
        } else {
            final String str = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
            String replace = phoneStr.replace(" ", "");
            return replace.matches(str);
        }
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        final String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 特殊字符判断---
     *
     * @param str
     * @return
     */
    public static boolean checkPassword(String str) {
        final String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间
     *
     * @param str
     * @return
     */
    public static boolean checkPassword1(String str) {
        final String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 获取底部虚拟键盘的高度
     */
    public static int getBottomKeyboardHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return getScreenHeight() - dm.heightPixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static final Spanned fromHtml(String text) {
//        final int version = Build.VERSION.SDK_INT;
//        final int mineVersion = 19;
//        if (version >= mineVersion) {
//            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
//        } else {
//            return Html.fromHtml(text);
//        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }
}
