package tech.yunjing.biconlife.liblkclass.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 */
public class LKLogUtil {

    public static String customTagPrefix = "lkLog";

    private LKLogUtil() {
    }

    @SuppressLint("DefaultLocale")
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();
        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.w(tag, tr);
    }


    public static void wtf(String content) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.wtf(tag, tr);
    }


    public static void m(String content) {
        /** isOffical : false :不打正式包，可打log true：正式包屏蔽log打印*/
        boolean isOffical = false;
        if (isOffical) {
            return;
        }
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.e(tag, content);
    }

    public static void m(String content, Throwable tr) {
        boolean isOffical = false;
        if (isOffical) {
            return;
        }
        if (!LK.isDebug()) {
            return;
        }
        String tag = generateTag();

        Log.e(tag, content, tr);
    }
}
