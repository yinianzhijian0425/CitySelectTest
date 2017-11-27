package tech.yunjing.biconlife.liblkclass.common.util;

import android.graphics.Color;
import android.text.TextUtils;

import java.util.Random;

/**
 * 色值相关工具
 * Created by NPF on 2017/3/25 14:08.
 */
public class LKColorUtil {
    /**
     * 根据字符串生成色值
     *
     * @param tag
     * @return
     */
    public static int getRandomColor(String tag, long subjoinNumber) {
        final int defRGB = 50;
        int rgb = 50;
        if (!TextUtils.isEmpty(tag)) {
            String convert = LKStrUtil.str2Num(tag) + subjoinNumber;
            Double aLong = Double.valueOf(convert);
            rgb = (int) (aLong % 255);
            if (rgb < defRGB) {
                rgb = defRGB;
            }
        }
        Random mRandom = new Random(rgb);
        int red = mRandom.nextInt(rgb);
        int green = mRandom.nextInt(rgb);
        int blue = mRandom.nextInt(rgb);
        return Color.rgb(red, green, blue);
    }


}
