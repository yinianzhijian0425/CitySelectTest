package tech.yunjing.biconlife.jniplugin.im.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.DrawableUtils;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.hyphenate.util.ImageUtils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.util.ImageUtil;
import tech.yunjing.biconlife.jniplugin.view.CenterAlignImageSpan;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * Emoji消息解析工具类
 */
public class ParseEmojiMsgUtil {
    private static final String TAG = ParseEmojiMsgUtil.class.getSimpleName();
    private static final String REGEX_STR = "\\[e\\](.*?)\\[/e\\]";

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     */
    public static void dealExpression(Context context, SpannableString spannableString, Pattern patten, int start, int fontSize) {
        try {
            Matcher matcher = patten.matcher(spannableString);
            int end = -1;
            while (matcher.find()) {
                String key = matcher.group();
                if (matcher.start() < start) {
                    continue;
                }
                end = matcher.start() + key.length();
                Field field = R.drawable.class.getDeclaredField("emoji_"
                        + key.substring(key.indexOf("]") + 1, key.lastIndexOf("[")));
                int resId = Integer.parseInt(field.get(null).toString());
                if (resId != 0) {
                    Drawable drawable = ImageUtil.setBCDrawable(resId);
                    if (fontSize <= 10) {
                        fontSize = 10;
                    }
                    int WAndH = LKCommonUtil.sp2px(fontSize);
                    drawable.setBounds(0, 0, WAndH, WAndH);//指定大小25dp
                    CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
                    spannableString.setSpan(imageSpan, matcher.start(), end, ImageSpan.ALIGN_BASELINE);
//                    ImageSpan imageSpan = new ImageSpan(drawable);
//                    spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
            if (end != -1 && end < spannableString.length()) {
                dealExpression(context, spannableString, patten, end, fontSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param str
     * @return
     * @desc <pre>
     * 解析字符串中的表情字符串替换成表情图片
     * </pre>
     * @author Weiliang Hu
     * @date 2013-12-17
     */
    public static SpannableString getExpressionString(Context context, String str, int fontSize) {
        SpannableString spannableString = new SpannableString(str);
        Pattern sinaPatten = Pattern.compile(REGEX_STR, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0, fontSize);
        } catch (Exception e) {
            LKLogUtil.e(e.getMessage());
            return new SpannableString("");
        }
        return spannableString;
    }

    /**
     * @param cs
     * @param mContext
     * @return
     * @desc <pre>表情解析,转成unicode字符</pre>
     * @author Weiliang Hu
     * @date 2013-12-17
     */
    public static String convertToMsg(CharSequence cs, Context mContext) {

        SpannableStringBuilder ssb = new SpannableStringBuilder(cs);


        ImageSpan[] spans = ssb.getSpans(0, cs.length(), ImageSpan.class);
        for (int i = 0; i < spans.length; i++) {
            ImageSpan span = spans[i];
            String c = span.getSource();
            int a = ssb.getSpanStart(span);
            int b = ssb.getSpanEnd(span);
            if (!TextUtils.isEmpty(c) && c.contains("[")) {
                ssb.replace(a, b, convertUnicode(c));
            }
        }
        ssb.clearSpans();
        return ssb.toString();
    }

    private static String convertUnicode(String emo) {
        emo = emo.substring(1, emo.length() - 1);
        if (emo.length() < 6) {
            return new String(Character.toChars(Integer.parseInt(emo, 16)));
        }
        String[] emos = emo.split("_");
        char[] char0 = Character.toChars(Integer.parseInt(emos[0], 16));
        char[] char1 = Character.toChars(Integer.parseInt(emos[1], 16));
        char[] emoji = new char[char0.length + char1.length];
        for (int i = 0; i < char0.length; i++) {
            emoji[i] = char0[i];
        }
        for (int i = char0.length; i < emoji.length; i++) {
            emoji[i] = char1[i - char0.length];
        }
        return new String(emoji);
    }

}
