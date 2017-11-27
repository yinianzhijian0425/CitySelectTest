package tech.yunjing.biconlife.jniplugin.jpush;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * 极光设置相关
 * Created by CQ on 2017/02/28 11:46.
 */
public class JpushOption {

    private static JpushOption mInstance = null;

    private JpushOption() {
    }

    public static JpushOption getInstance() {
        if (null == mInstance) {
            synchronized (JpushOption.class) {
                if (null == mInstance) {
                    mInstance = new JpushOption();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置用户注册极光别名
     *
     * @param context
     * @param jpushAlias
     */
    public void setAlias(final Context context, final String jpushAlias, final Handler handler) {
        //调用JPush API设置Alias
        JPushInterface.setAliasAndTags(context, jpushAlias, null, new TagAliasCallback() {
            @Override
            public void gotResult(int code, final String alias, Set<String> tags) {
                switch (code) {
                    case 0:
                        break;
                    case 6002:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setAlias(context, jpushAlias, handler);
                            }
                        }, 1000 * 60);
                        break;
                    default:
                        LKLogUtil.e("极光别名设置==失败errorCode = " + code);
                        break;
                }
            }
        });
    }

    /**
     * 设置Tag
     *
     * @param context
     * @param tag
     * @param handler
     */
    public void setTag(final Context context, final String tag, final Handler handler) {
        String[] sArray = tag.split(",");// ","隔开的多个 转换成 Set
        Set<String> tagSet = new LinkedHashSet<>();
        for (String sTagItme : sArray) {
            tagSet.add(sTagItme + "double");
        }
        JPushInterface.setAliasAndTags(context, null, tagSet, new TagAliasCallback() {
            @Override
            public void gotResult(int code, final String alias, Set<String> tags) {
                switch (code) {
                    case 0:
                        break;
                    case 6002:
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setAlias(context, tag, handler);
                            }
                        }, 1000 * 60);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}