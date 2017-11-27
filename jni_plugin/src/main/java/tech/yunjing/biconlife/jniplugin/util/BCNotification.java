package tech.yunjing.biconlife.jniplugin.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * Created by sun.li on 2017/9/15.
 */

public class BCNotification {

    private Context context;

    private static NotificationManager notificationManager = null;


    public BCNotification(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 普通的Notification
     */
    public void postNotification(String title,String content) {

        try {
            final int NOTIFY_ID_CHAT = (title+content).length();
            Intent intent = new Intent();
            notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFY_ID_CHAT, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(context)
                    .setContentTitle(title)//标题
                    .setContentText(content)//显示文本
                    .setContentIntent(pendingIntent)//需要跳转的
                    .setSmallIcon(R.mipmap.ic_logo)//设置小图标
                    .setWhen(System.currentTimeMillis())//通知时间
                    .setPriority(Notification.PRIORITY_DEFAULT)//设置高优先级
                    .setOngoing(true)//设置为ture，表示它为一个正在进行的通知。
                    .setAutoCancel(true);//点击消失
            Notification notification = mBuilder.build();
//            notification.defaults = Notification.DEFAULT_VIBRATE;    //添加默认震动提醒  需要 VIBRATE permission
//            notification.defaults = Notification.DEFAULT_SOUND;    // 添加默认声音提醒
//            notification.defaults = Notification.DEFAULT_LIGHTS;// 添加默认三色灯提醒
            notification.defaults = Notification.DEFAULT_ALL;//默认系统所有效果（声音、闪灯、震动---）
            notification.flags = Notification.FLAG_SHOW_LIGHTS;             //三色灯提醒，在使用三色灯提醒时候必须加该标志符
//            notification.flags = Notification.FLAG_ONGOING_EVENT;          //发起正在运行事件（活动中）
//            notification.flags = Notification.FLAG_INSISTENT;   //让声音、振动无限循环，直到用户响应 （取消或者打开）
//            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;  //发起Notification后，铃声和震动均只执行一次
            notification.flags = Notification.FLAG_AUTO_CANCEL;      //用户单击通知后自动消失
//            notification.flags = Notification.FLAG_NO_CLEAR;          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
//            notification.flags = Notification.FLAG_FOREGROUND_SERVICE;    //表示正在运行的服务
            notificationManager.notify(NOTIFY_ID_CHAT, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
