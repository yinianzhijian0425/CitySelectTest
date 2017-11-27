package tech.yunjing.biconlife.jniplugin.util;

import android.text.TextUtils;

/**
 * String字符串处理工具类
 * Created by sun.li on 2017/7/18.
 */

public class StringProcessingUtil {

    /** 去除字符串中的空格
     * */
    public static String stringRemoveBlankSpace(String str){
        str = str.replace(" ","");
        return str;
    }
    /**
     * 格式化显示的电话号码
     *
     * @param num
     * @return
     */
    public static String setNumFormat(String num) {// &#160
        if(TextUtils.isEmpty(num)){
          return "";
        }
        if(FormatUtil.isMobileNO(num)){
            String maskNumber = num.substring(0, 3) + "****" + num.substring(7, 11);
            return maskNumber;
        }else{
           return num;
        }
    }

}
