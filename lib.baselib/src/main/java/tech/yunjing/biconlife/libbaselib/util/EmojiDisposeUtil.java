package tech.yunjing.biconlife.libbaselib.util;

import android.text.TextUtils;

/**
 * Created by sun.li on 2017/8/8.
 */

public class EmojiDisposeUtil {


    /**
     * 字符串转换unicode
     */
//    public static String string2Unicode(String string) {
//        if(TextUtils.isEmpty(string)){
//            return "";
//        }
//        StringBuffer unicode = null;
//        try {
//            unicode = new StringBuffer();
//
//            for (int i = 0; i < string.length(); i++) {
//
//                // 取出每一个字符
//                char c = string.charAt(i);
//
//                // 转换为unicode
//                unicode.append("\\u" + Integer.toHexString(c));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return unicode.toString();
//    }

    /**
     * unicode 转字符串
     */
//    public static String unicode2String(String unicode) {
//        if(TextUtils.isEmpty(unicode)){
//            return "";
//        }
//        StringBuffer string = null;
//        try {
//            string = new StringBuffer();
//
//            String[] hex = unicode.split("\\\\u");
//
//            for (int i = 1; i < hex.length; i++) {
//
//                // 转换出每一个代码点
//                int data = Integer.parseInt(hex[i], 16);
//
//                // 追加成string
//                string.append((char) data);
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return string.toString();
//    }




}
