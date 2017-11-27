package tech.yunjing.biconlife.libbaselib.util;

import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.base.BaseApplication;

/**
 * Generate thumb and background color state list use tintColor
 * Created by kyle on 15/11/4.
 */
public class ColorUtil {
    private static final int ENABLE_ATTR = android.R.attr.state_enabled;
    private static final int CHECKED_ATTR = android.R.attr.state_checked;
    private static final int PRESSED_ATTR = android.R.attr.state_pressed;

    public static ColorStateList generateThumbColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {PRESSED_ATTR, -CHECKED_ATTR},
                {PRESSED_ATTR, CHECKED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xAA000000,
                0xFFBABABA,
                tintColor - 0x99000000,
                tintColor - 0x99000000,
                tintColor | 0xFF000000,
                0xFFEEEEEE
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList generateBackColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {CHECKED_ATTR, PRESSED_ATTR},
                {-CHECKED_ATTR, PRESSED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xE1000000,
                0x10000000,
                tintColor - 0xD0000000,
                0x20000000,
                tintColor - 0xD0000000,
                0x20000000
        };
        return new ColorStateList(states, colors);
    }

    /**
     * 颜色设置
     *
     * @param color
     */
    public static int setBCColor(int color) {
        int colorBC = 0;
        try {
            colorBC = ContextCompat.getColor(BaseApplication.getContext(), color);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return colorBC;
    }

    /***
     * 颜色设置
     * @param color
     * @return
     */
    public static ColorStateList setBCColorStateList(int color) {
        ColorStateList colorBCList = null;
        try {
            colorBCList = ContextCompat.getColorStateList(BaseApplication.getContext(), color);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorBCList;
    }

}
