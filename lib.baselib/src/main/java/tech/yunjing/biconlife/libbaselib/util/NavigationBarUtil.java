package tech.yunjing.biconlife.libbaselib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * 手机系统底部通知栏工具类
 * Created by sun.li on 2017/10/10.
 */

public class NavigationBarUtil {

    /** 获取是否显示了手机系统底部通知栏*/
    public static boolean isNavigationBarShow(Context context){
        if(context == null){
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                return realSize.y!=size.y;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if(menu || back) {
                return false;
            }else {
                return true;
            }
        }
    }

    /** 获取系统NavigationBar的高度*/
    public static int getNavigationBarHeight(Activity activity) {
        try {
            if (!isNavigationBarShow(activity)){
                return 0;
            }
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 隐藏系统NavigationBar*/
    public static void hideNavKey(Context context) {
        try {
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
                View v = ((Activity) context).getWindow().getDecorView();
                v.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                //for new api versions.
                View decorView = ((Activity) context).getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 显示系统NavigationBar*/
    public static void showNavKey(Context context, int systemUiVisibility) {
        try {
            //getWindow().getDecorView().getSystemUiVisibility() 传入0也可以
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
