package tech.yunjing.biconlife.liblkclass.common.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 字符串系列工具
 * Created by nanPengFei on 2016/9/29 13:48.
 */
public class LKStrUtil {

    private static LKStrUtil mInstance = null;

    public static LKStrUtil getInstance() {
        if (null == mInstance) {
            synchronized (LKStrUtil.class) {
                if (mInstance == null) {
                    mInstance = new LKStrUtil();
                }
            }
        }
        return mInstance;
    }

    private final Pattern pattern = Pattern.compile("^[A-Za-z]+$");

    /**
     * 获取汉语拼音首字母
     *
     * @param str
     * @return
     */
    public String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }


    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /**
     * 截取小数点后两位
     *
     * @param money
     * @return
     */
    public static String cutOutDoubleMoney(double money) {
        String moneyStr = String.valueOf(money);
        String newMoney = moneyStr;
        if (!TextUtils.isEmpty(moneyStr)) {
            String containStr = ".";
            if (moneyStr.contains(containStr)) {
                int index = moneyStr.indexOf('.');
                String substring = moneyStr.substring(index, moneyStr.length());
                boolean judge1 = substring.length() > 3;
                boolean judge2 = substring.length() == 2;
                boolean judge3 = substring.length() == 1;
                if (judge1) {
                    newMoney = moneyStr.substring(0, index) + substring.substring(0, 3);
                } else if (judge2) {
                    newMoney = moneyStr.substring(0, index) + substring + "0";
                } else if (judge3) {
                    newMoney = moneyStr.substring(0, index) + substring + "00";
                }
            } else {
                newMoney = moneyStr + ".00";
            }
        }
        return newMoney;
    }

    /**
     * 截取小数点后两位
     *
     * @param money
     * @return
     */
    public static String cutOutDoubleMoney(String money) {
        String newMoney = money;
        if (!TextUtils.isEmpty(money)) {
            String containsStr = ".";
            if (money.contains(containsStr)) {
                int index = money.indexOf('.');
                String substring = money.substring(index, money.length());
                boolean judge1 = substring.length() > 3;
                boolean judge2 = substring.length() == 2;
                boolean judge3 = substring.length() == 1;
                if (judge1) {
                    newMoney = money.substring(0, index) + substring.substring(0, 3);
                } else if (judge2) {
                    newMoney = money.substring(0, index) + substring + "0";
                } else if (judge3) {
                    newMoney = money.substring(0, index) + substring + "00";
                }
            } else {
                newMoney = money + ".00";
            }
        }
        return newMoney;
    }


    /**
     * 转字符串为数值型字符串
     *
     * @param input
     * @return
     */
    public static String str2Num(String input) {
        final String reg1 = "[a-zA-Z]";
        final String reg2 = "[0-9]";
        StringBuffer strBuf = new StringBuffer();
        if (null != input && !"".equals(input)) {
            String replace = input.replace(",", "");
            String inputStr = LKStrParserUtil.getInstance().getSelling(replace);
            String str = inputStr.toLowerCase();
            for (char c : str.toCharArray()) {
                if (String.valueOf(c).matches(reg1)) {
                    strBuf.append(c - 96);
                } else if (String.valueOf(c).matches(reg2)) {
                    strBuf.append(c);
                }
            }
            return strBuf.toString();
        } else {
            return "88888888";
        }
    }
}
