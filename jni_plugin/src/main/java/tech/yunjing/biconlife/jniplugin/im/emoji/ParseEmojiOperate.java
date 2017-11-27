package tech.yunjing.biconlife.jniplugin.im.emoji;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.hyphenate.util.ImageUtils;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.util.ImageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * Emoji解析
 * Created by NPF on 2017/4/21 10:29.
 */
public class ParseEmojiOperate {

    private static ParseEmojiOperate mInstance;
    private static StringBuffer emojiStrBuffer = new StringBuffer();//emoji字符串转Unicoe使用

    private ParseEmojiOperate() {
    }

    public static ParseEmojiOperate getInstance() {
        if (null == mInstance) {
            synchronized (ParseEmojiOperate.class) {
                if (null == mInstance) {
                    mInstance = new ParseEmojiOperate();

                    initEmojiRes();
                }
            }
        }
        return mInstance;
    }

    private static ArrayList<String> mEmojiDess = new ArrayList<>();
    private static ArrayList<Integer> mEmojiIds = new ArrayList<>();

    /**
     * 初始化Emoji资源
     */
    private static void initEmojiRes() {
        mEmojiDess.clear();
        mEmojiIds.clear();
        for (int i = 0; i < EmojiRes.emojides.length; i++) {
            mEmojiDess.add(EmojiRes.emojides[i]);
            mEmojiIds.add(EmojiRes.emojires[i]);
        }
    }

    /**
     * 解析字符串中指定内容为emoji图片
     *
     * @param msgStr
     * @param emojiWH
     * @return
     */
    public SpannableString parseEmoji(String msgStr, int emojiWH) {
        if (TextUtils.isEmpty(msgStr)) {
            return new SpannableString("");
        }
        String emojiStr = EmojiParser.getInstance().parseEmoji(msgStr);
        if (emojiStr.contains("[e]") && emojiStr.contains("[/e]")) {
            int enojiWH = LKCommonUtil.dip2px(emojiWH);
            SpannableString spanStr = new SpannableString(emojiStr);
            int startIndex = emojiStr.indexOf("[e]");
            int endIndex = emojiStr.indexOf("[/e]", startIndex);

            if (startIndex != -1 && endIndex != -1) {
                if (emojiStr.substring(startIndex + 3, endIndex).contains("[e]")) {
                    startIndex = emojiStr.indexOf("[e]", startIndex + 3);
                }
                parseEmoji(emojiStr, spanStr, startIndex, endIndex, enojiWH);
            }
            return spanStr;
        } else {
            return new SpannableString(emojiStr);
        }
    }

    /**
     * 具体替换成emoji图片操作
     *
     * @param spanStr
     * @param startIndex
     * @param endIndex
     */
    private void parseEmoji(String emojiStr, SpannableString spanStr, int startIndex, int endIndex, int enojiWH) {
        int index = mEmojiDess.indexOf(emojiStr.substring(startIndex, endIndex + 4));
//        LKLogUtil.e("表情位置===" + index);
        if (index >= 0 && index < mEmojiIds.size()) {
            Drawable drawable;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                drawable = LKApplication.getContext().getResources().getDrawable(mEmojiIds.get(index), null);
            } else {
                drawable = ImageUtil.setBCDrawable(mEmojiIds.get(index));
            }
            drawable.setBounds(0, 0, enojiWH, enojiWH);

            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            spanStr.setSpan(span, startIndex, endIndex + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (emojiStr.length() > endIndex + 4) {
            startIndex = emojiStr.indexOf("[e]", endIndex + 4);
            endIndex = emojiStr.indexOf("[/e]", startIndex + 3);
            if (startIndex != -1 && endIndex != -1) {


                String subEmojiStr = emojiStr.substring(startIndex + 3, endIndex);
                if (subEmojiStr.contains("[e]")) {
                    startIndex = emojiStr.indexOf("[e]", startIndex + 3);
                }
                parseEmoji(emojiStr, spanStr, startIndex, endIndex, enojiWH);
            }
        }
    }


    /**
     * emojiStr解析成为unicode
     *
     * @return
     */
    public String emoji2Unicode(String emojiStr) {
        emojiStrBuffer.delete(0, emojiStrBuffer.length());
        if (!TextUtils.isEmpty(emojiStr) && emojiStr.contains("[e]") && emojiStr.contains("[/e]")) {
            int startIndex = emojiStr.indexOf("[e]");
            if (startIndex != 0) {
                emojiStrBuffer.append(emojiStr.substring(0, startIndex));
            }
            int endIndex = emojiStr.indexOf("[/e]", startIndex);
            if (endIndex != -1) {
                String subEmojiStr = emojiStr.substring(startIndex + 3, endIndex);
                if (subEmojiStr.contains("[e]")) {
                    emojiStrBuffer.append(emojiStr.substring(startIndex, startIndex + 3));
                    startIndex = emojiStr.indexOf("[e]", startIndex + 3);
                    subEmojiStr = emojiStr.substring(startIndex + 3, endIndex);
                }
                emojiStrBuffer.append(new String(Character.toChars(Integer.parseInt(subEmojiStr, 16))));
                while ((endIndex + 4) < emojiStr.length()) {
                    emojiStr = emojiStr.substring(endIndex + 4);
                    if (emojiStr.contains("[e]") && emojiStr.contains("[/e]")) {
                        startIndex = emojiStr.indexOf("[e]");
                        if (startIndex != 0) {
                            emojiStrBuffer.append(emojiStr.substring(0, startIndex));
                        }
                        endIndex = emojiStr.indexOf("[/e]", startIndex);
                        if (endIndex != -1) {
                            subEmojiStr = emojiStr.substring(startIndex + 3, endIndex);
                            if (subEmojiStr.contains("[e]")) {
                                emojiStrBuffer.append(emojiStr.substring(startIndex, startIndex + 3));
                                startIndex = emojiStr.indexOf("[e]", startIndex + 3);
                                subEmojiStr = emojiStr.substring(startIndex + 3, endIndex);
                            }
                            emojiStrBuffer.append(new String(Character.toChars(Integer.parseInt(subEmojiStr, 16))));
                        } else {
                            emojiStrBuffer.append(emojiStr);
                            break;
                        }
                    } else {
                        emojiStrBuffer.append(emojiStr);
                        break;
                    }
                }
            } else {
                emojiStrBuffer.append(emojiStr);
            }
        } else {
            emojiStrBuffer.append(emojiStr);
        }
        return emojiStrBuffer.toString();
    }
}
