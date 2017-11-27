package tech.yunjing.biconlife.liblkclass.common.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Emoji表情过滤
 * Created by nanPengFei on 2016/12/16 19:10.
 */
public class LKEmojiFilterUtil {

    private static LKEmojiFilterUtil mInstance;

    /**
     * 实例化对象
     *
     * @return
     */
    public static LKEmojiFilterUtil getInstance() {
        if (mInstance == null) {
            mInstance = new LKEmojiFilterUtil();
        }
        return mInstance;
    }

    private final Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    /**
     * 表情过滤
     *
     * @param emojiEdit
     */
    public void setEmojiFilter(EditText emojiEdit, int maxLength) {
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(maxLength);
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    LKToastUtil.showToastShort("不支持输入表情");
                    return "";
                }
                if (" ".equals(source)) {
                    return "";
                }
                return null;
            }
        };
        emojiEdit.setFilters(new InputFilter[]{lengthFilter, inputFilter});
    }
}
