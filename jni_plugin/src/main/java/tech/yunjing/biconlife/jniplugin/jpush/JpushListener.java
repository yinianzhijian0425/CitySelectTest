package tech.yunjing.biconlife.jniplugin.jpush;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import tech.yunjing.biconlife.jniplugin.global.JNIBroadCastKey;

/**
 * 极光通知监听处理
 * Created by CQ on 2017/02/28 10:01.
 */
public class JpushListener {

    private static JpushListener mInstance = null;

    private JpushListener() {
    }

    public static JpushListener getInstance() {
        if (null == mInstance) {
            synchronized (JpushListener.class) {
                if (null == mInstance) {
                    mInstance = new JpushListener();
                }
            }
        }
        return mInstance;
    }

    /**
     * 通知类型消息
     *
     * @param context
     * @param bundle
     */
    public void notifyMsg(Context context, Bundle bundle) {
        int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
    }

    /**
     * 极光推送的自定义消息处理
     *
     * @param context
     * @param bundle
     */
    public void customMsg(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//标题
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);//消息内容
        Log.e("chenqi", "极光推送==收到自定义消息:title=" + title + "-----message=" + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent intent = new Intent("000");
        context.sendBroadcast(intent);
        showNotification(context, title, message);
    }

    /**
     * 富媒体处理
     *
     * @param context
     * @param bundle
     */
    public void DeputyText(Context context, Bundle bundle) {


    }

    /**
     * 通知栏点击打开操作
     *
     * @param context
     * @param bundle
     */
    public void onClickOpen(Context context, Bundle bundle) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        //大于=5.0下面方法无效
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        //app在后台的情况下
        if (!cn.getPackageName().equals(context.getPackageName())) {//
            Intent intent = context.getPackageManager().getLaunchIntentForPackage("tech.yunjing.biconlife");
            context.startActivity(intent );
        }else {
            //app不在后台的情况下
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setAction(JNIBroadCastKey.SCAN_JPUSH_CODE);
            context.sendBroadcast(intent);
        }
//        if (LKPrefUtils.getBoolean(LKOthersFinalList.ISFRISTLOGIN, false)) {
//            String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);
////            LKLogUtils.e("==极光推送==点击操作==" + jsonString);
//            if (!TextUtils.isEmpty(jsonString)) {
//                JpushExtra jpushExtra = LKJsonUtil.parseJsonToBean(jsonString, JpushExtra.class);
//                LKLogUtils.e("==极光推送=解析=" + jpushExtra.toString());
//                Intent onClickIntent = new Intent(context, RedPacketActivity.class);
//                onClickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                onClickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                onClickIntent.putExtra(LKIntentKeyList.JUMP_SOURCE, jpushExtra.jumpType);
//                if (LKOthersFinalList.JPUSHTYPE_SYSTEMMSG.equals(jpushExtra.jumpType)) {//跳转到系统聊天
//                     onClickIntent.putExtra(LKIntentKeyList.SYSTEM_NICK, jpushExtra.nick);//系统帐号昵称
//                    onClickIntent.putExtra(LKIntentKeyList.SYSTEM_AVATAR, jpushExtra.avatar);//系统帐号头像
//                    onClickIntent.putExtra(LKIntentKeyList.SYSTEM_ID, jpushExtra.imTag);//系统帐号id
//                } else if (LKOthersFinalList.JPUSHTYPE_RP.equals(jpushExtra.jumpType)) {//跳转到抢红包
//
//                }
//                context.startActivity(onClickIntent);
//            }
//        } else {
//            Intent onClickIntent = new Intent(context, LoginActivity.class);
//            onClickIntent.putExtras(bundle);
//            onClickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            onClickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(onClickIntent);
//        }
    }

    /**
     * 极光连接状态
     *
     * @param context
     * @param intent
     */
    public void connectionState(Context context, Intent intent) {
        boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
    }


    /**
     * 透传信息
     */
    public class JpushExtra {
        public String imTag;//系统账号ID
        public String jumpType;//需要跳转的页面
        public String nick;//系统帐号昵称
        public String avatar;//系统帐号头像
    }

    /**
     * 在service运行时，显示通知信息
     */
    public void showNotification(Context context, String title, String content) {
        // 标志位的设置：应设置为可以自动取消，这样用户就可以取消他，如果设置为Intent.FLAG_ACTIVITY_CLEAR_TOP | Notification.FLAG_ONGOING_EVENT;则会一直显示通知<br>//        notification.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP | Notification.FLAG_ONGOING_EVENT;
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        Intent intent = Small.getIntentOfUri("health/sub", Small.getContext());
////        Small.openUri("health/sub", Small.getContext());
////        Intent intent = new Intent(context, BaseActivity.class);//将要跳转的界面
//        builder.setAutoCancel(true);//点击后消失
////        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
//        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
//        //builder.setTicker("你收到一条推送");
//        builder.setContentText(content);//通知内容
//        builder.setContentTitle(title);
//        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
//        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(intentPend);
//        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
    }
}
