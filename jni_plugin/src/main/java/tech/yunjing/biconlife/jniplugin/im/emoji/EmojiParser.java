package tech.yunjing.biconlife.jniplugin.im.emoji;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;

public class EmojiParser {
    private static EmojiParser mInstance;
    private HashMap<List<Integer>, String> convertMap = new HashMap<>();
    private HashMap<String, ArrayList<String>> emoMap = new HashMap<>();

    private EmojiParser() {
        readMap();
    }

    public static EmojiParser getInstance() {
        if (null == mInstance) {
            synchronized (EmojiParser.class) {
                if (null == mInstance) {
                    mInstance = new EmojiParser();
                }
            }
        }
        return mInstance;
    }


    public HashMap<List<Integer>, String> readMap() {
        if (convertMap == null || convertMap.size() == 0) {
            convertMap = new HashMap<>();
            XmlPullParser xmlpull = null;
            String fromAttr = null;
            String key = null;
            ArrayList<String> emos = null;
            try {
                XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
                xmlpull = xppf.newPullParser();
                InputStream stream = LKApplication.getContext().getClass().getClassLoader().getResourceAsStream("assets/emoji.xml");
                xmlpull.setInput(stream, "UTF-8");
                int eventCode = xmlpull.getEventType();
                while (eventCode != XmlPullParser.END_DOCUMENT) {
                    switch (eventCode) {
                        case XmlPullParser.START_DOCUMENT: {
                            break;
                        }
                        case XmlPullParser.START_TAG: {
                            if ("key".equals(xmlpull.getName())) {
                                emos = new ArrayList<>();
                                key = xmlpull.nextText();
                            }
                            if ("e".equals(xmlpull.getName())) {
                                fromAttr = xmlpull.nextText();
                                emos.add(fromAttr);
                                List<Integer> fromCodePoints = new ArrayList<>();
                                if (fromAttr.length() > 6) {
                                    String[] froms = fromAttr.split("\\_");
                                    for (String part : froms) {
                                        fromCodePoints.add(Integer.parseInt(part, 16));
                                    }
                                } else {
                                    fromCodePoints.add(Integer.parseInt(fromAttr, 16));
                                }
                                convertMap.put(fromCodePoints, fromAttr);
                            }
                            break;
                        }
                        case XmlPullParser.END_TAG: {
                            if ("dict".equals(xmlpull.getName())) {
                                emoMap.put(key, emos);
                            }
                            break;
                        }
                        case XmlPullParser.END_DOCUMENT: {
                            LKLogUtil.e("===Emoji解析==XmlPullParser.END_DOCUMENT");
                            break;
                        }
                        default:
                            break;
                    }
                    eventCode = xmlpull.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertMap;
    }

    public String parseEmoji(String input) {
        if (input == null || input.length() <= 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int[] codePoints = toCodePointArray(input);
        List<Integer> key = null;
        for (int i = 0; i < codePoints.length; i++) {
            key = new ArrayList<>();
            if (i + 1 < codePoints.length) {
                key.add(codePoints[i]);
                key.add(codePoints[i + 1]);
                if (convertMap.containsKey(key)) {
                    String value = convertMap.get(key);
                    if (value != null) {
                        result.append("[e]" + value + "[/e]");
                    }
                    i++;
                    continue;
                }
            }
            key.clear();
            key.add(codePoints[i]);
            if (convertMap.containsKey(key)) {
                String value = convertMap.get(key);
                if (value != null) {
                    result.append("[e]" + value + "[/e]");
                }
                continue;
            }
            result.append(Character.toChars(codePoints[i]));
        }
        return result.toString();
    }

    private int[] toCodePointArray(String str) {
        char[] ach = str.toCharArray();
        int len = ach.length;
        int[] acp = new int[Character.codePointCount(ach, 0, len)];
        int j = 0;
        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(ach, i);
            acp[j++] = cp;
        }
        return acp;
    }


}
