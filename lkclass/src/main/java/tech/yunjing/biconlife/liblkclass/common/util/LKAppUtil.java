package tech.yunjing.biconlife.liblkclass.common.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tech.yunjing.biconlife.liblkclass.global.LKApplication;

/**
 * 关于APK详情的一些操作
 * Created by nanPengFei on 2016/4/7 12:01.
 */
public class LKAppUtil {
    private static LKAppUtil sInstance;

    /**
     * 实例化对象
     *
     * @return
     */
    public static LKAppUtil getInstance() {
        if (sInstance == null) {
            sInstance = new LKAppUtil();
        }
        return sInstance;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) LKApplication.getContext().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = LKApplication.getContext().getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断程序是否正在后台运行
     *
     * @return
     */
    public boolean isAppOnBackground() {
        ActivityManager activityManager = (ActivityManager) LKApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = LKApplication.getContext().getPackageName();
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> processList = activityManager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : processList) {
            if (info.topActivity.getPackageName().startsWith(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测APK是否存在
     *
     * @return
     */
    public boolean checkPackage() {
        boolean flag = false;
        try {
            PackageManager packageManager = LKApplication.getContext().getPackageManager();
            String packageName = LKApplication.getContext().getApplicationContext().getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            if (null != packageInfo) {
                flag = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 打开App
     */
    public void openApp() {
        try {
            String packageName = LKApplication.getContext().getApplicationContext().getPackageName();
            PackageManager packageManager = LKApplication.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageInfo.packageName);
            List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                LKApplication.getContext().startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * 获取版本号
     */
    public int checkVersionCode() {
        PackageManager packageManager = LKApplication.getContext().getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(LKApplication.getContext().getPackageName(), 0);
            int version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取版本信息
     */
    public String checkVersionInfo() {
        String vsersionName = "";
        PackageManager packageManager = LKApplication.getContext().getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(LKApplication.getContext().getPackageName(), 0);
            if (packInfo != null) {
                if (!TextUtils.isEmpty(packInfo.versionName)) {
                    return vsersionName;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vsersionName;
    }

    /**
     * 获取安装包中logo
     *
     * @param apkPath
     * @return
     */
    public Drawable getApkIcon(String apkPath) {
        PackageManager pm = LKApplication.getContext().getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (null != info) {
            ApplicationInfo applicationInfo = info.applicationInfo;
            applicationInfo.sourceDir = apkPath;
            applicationInfo.publicSourceDir = apkPath;
            try {
                return pm.getApplicationIcon(applicationInfo);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取APP包名
     *
     * @return
     */
    public String getAppName() {
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) LKApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return processName;
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 自动默认弹起键盘弹起键盘
     */
    public void openInputLogin(final EditText ed, Handler mHandler) {
        ed.setFocusable(true);
        ed.setFocusableInTouchMode(true);
        ed.requestFocus();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) ed.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(ed, 0);
            }
        }, 300);
    }


    /**
     * 弹起键盘
     */
    public void openInput(final EditText ed) {
        InputMethodManager inputManager =
                (InputMethodManager) ed.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ed, 0);
    }


    /**
     * 关闭输入法
     */
    public void closeInput(Context context, EditText ed) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = inputManager.isActive();
        if (isOpen) {
            inputManager.hideSoftInputFromWindow(ed.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭输入法
     */
    public void closeInput(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = inputManager.isActive();
        if (isOpen) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断字符串是否为纯数字
     */
    public boolean isIntegerNum(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String regex = "^[0-9]+(.[0-9]+)?$";
        if (str.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    private final Pattern pattern = Pattern.compile("[0-9]*");

    /**
     * 判断字符串是否为纯数字
     */
    public boolean isNumeric(String str) {

        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            //如果不能匹配,则该字符是Emoji表情
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {

        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
