package tech.yunjing.biconlife.jniplugin.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 极光接收类
 * Created by CQ on 2017/02/28 10:46.
 */
public class JpushReceiver extends BroadcastReceiver {
    /**
     * 类别
     */
    private String type;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LKLogUtil.e("极光推送==Action===" + intent.getAction() + "===extras===: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            LKLogUtils.e("极光推送==收到注册ID==" + regId);//可以将注册的ID上传到服务器进行维护
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            JpushListener.getInstance().customMsg(context, bundle);//自定义消息处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
          //JpushListener.getInstance().notifyMsg(context, bundle);//通知类型消息处理
            Log.d("asdaf", "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d("asfasf", "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            if (bundle != null) {
                receiverJpush(bundle);
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            JpushListener.getInstance().onClickOpen(context, bundle);//点击通知栏消息处理

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            JpushListener.getInstance().DeputyText(context, bundle);//富媒体处理
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            JpushListener.getInstance().connectionState(context, intent);//极光连接状态
        } else {
            LKLogUtil.e("极光推送==未处理的Action" + intent.getAction());
        }
    }

    /**
     * 根据key处理通知
     */
    private void receiverJpush(Bundle bundle) {

        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    continue;
                }
                JSONObject json;
                try {
                    json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                } catch (JSONException e) {
                    json = null;
                    e.printStackTrace();
                }
                if (json != null) {
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        type = json.optString("type");
                    }
                    if (TextUtils.isEmpty(type)) {
                        return;
                    }
                    //推送返回类别如降价通知等
                    //降价通知
                    if ("1".equals(type)) {

                    }
                }
            }
        }
    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
