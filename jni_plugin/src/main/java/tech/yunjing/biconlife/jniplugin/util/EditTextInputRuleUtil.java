package tech.yunjing.biconlife.jniplugin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xining on 2017/9/28 0028.
 * EditText输入规则
 * 1.姓名类：汉字、数字、字母
 * 2、地址类、搜索框类：除系统表情外
 * 3、不限制
 */

public class EditTextInputRuleUtil {
    /***
     * 地址类、搜索框类：除系统表情外
     * @param s
     * @param start
     * @param count
     * @return
     */
    public static String banInputEmoji(CharSequence s, int start, int count) {
        String s_emoji = "";
        //输入的类容
        CharSequence input = s.subSequence(start, start + count);//input指的是当前位置开始，s从首位开始
        //如果 输入的类容包含有Emoji
        if (isEmojiCharacter(input)) {
            //那么就去掉
            s_emoji = removeEmoji(s);
        }
        return s_emoji;
    }

    /**
     * 判断一个字符串中是否包含有Emoji表情
     *
     * @param input
     * @return true 有Emoji
     */
    public static boolean isEmojiCharacter(CharSequence input) {
        for (int i = 0; i < input.length(); i++) {
            if (isEmojiCharacter(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是Emoji 表情
     *
     * @param codePoint
     * @return true 是Emoji表情
     */
    public static boolean isEmojiCharacter(char codePoint) {
        // Emoji 范围
        boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

        return !isScopeOf;
    }

    /**
     * 去除字符串中的Emoji表情
     *
     * @param source
     * @return
     */
    public static String removeEmoji(CharSequence source) {
        String result = "";
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (isEmojiCharacter(c)) {
                continue;
            }
            result += c;
        }
        return result;
    }

    /***
     * 只输入字母、数字、汉字
     * @param s
     * @return
     */
    public static String InputNumAndLetterAndChinese(String s) {
        String result = "";
        if (checkAccountMark(s)) {
            result = stringNameFilter(s);
            return result;
        }
        return result;
    }

    /**
     * 验证用户名只包含字母，数字，中文
     *
     * @param account
     * @return
     */
    public static boolean checkAccountMark(String account) {
        String all = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
//        String all = "^[a-zA-Z\\u4e00-\\u9fa5]+$";
        Pattern pattern = Pattern.compile(all);
        return Pattern.matches(all, account);
    }

    /***
     *  只允许字母、数字和汉字
     * @param str
     * @return
     */
    public static String stringNameFilter(String str) {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
