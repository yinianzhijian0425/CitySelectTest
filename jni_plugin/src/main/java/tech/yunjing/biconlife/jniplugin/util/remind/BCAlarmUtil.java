package tech.yunjing.biconlife.jniplugin.util.remind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tech.yunjing.biconlife.jniplugin.util.broadcast.MyAlarmReceiver;
import tech.yunjing.biconlife.jniplugin.db.daTable.RemindFunctionObj;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKTimeUtil;

/**
 * 系统闹钟工具类
 * Created by sun.li on 2017/9/15.
 */

public class BCAlarmUtil {

    /**
     * 设置系统闹钟
     */
    public static void setSystemAlarm(Context mContext, RemindFunctionObj obj) {
        if (mContext != null) {
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && obj != null) {
                //操作：发送一个广播，广播接收后Toast提示定时操作完成
                Intent intent = new Intent(mContext, MyAlarmReceiver.class);
                intent.setAction("tech.yunjing.biconlife.MyAlarm");
                /*提醒唯一标志ID*/
                String remindUUID = obj.getAlarmID();
                if (remindUUID == null || TextUtils.isEmpty(remindUUID)) {
                    remindUUID = obj.getRemindID() + obj.getRemindTitle() + obj.getRemindStartTime() + obj.getRemindTitle() + obj.getRemindRepetitionDate();
                }
                String uri = "content://calendar/calendar_alerts/" + remindUUID;
                LKLogUtil.e(uri);
                intent.setData(Uri.parse(uri));//此处设置用于区分不同的Intent，区别闹钟；
                intent.putExtra("RemindID", obj.getRemindID());
//                intent.putExtra("Title", obj.getRemindTitle()+"_"+obj.getRemindStartTime());
//                intent.putExtra("StartTime", obj.getRemindStartTime());
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(mContext, 0, intent, 0);

                //设定一个五秒后的时间
//                Calendar calendar=Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.add(Calendar.SECOND, 5);
                long startTime = 0;
                long repetitionTime = 60 * 1000;
                String time = "08:00";
                if (!TextUtils.isEmpty(obj.getRemindStartTime())) {
                    time = obj.getRemindStartTime();
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date date = sdf.parse(time);
                    startTime = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (obj.getRemindRepetitionTime().contains("120分")) {
                    repetitionTime = 120 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("60分")) {
                    repetitionTime = 60 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("30分")) {
                    repetitionTime = 30 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("25分")) {
                    repetitionTime = 25 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("20分")) {
                    repetitionTime = 20 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("15分")) {
                    repetitionTime = 15 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("10分")) {
                    repetitionTime = 10 * 60 * 1000;
                } else if (obj.getRemindRepetitionTime().contains("5分")) {
                    repetitionTime = 5 * 60 * 1000;
                }

                //测试数据
//                    repetitionTime = 60*1000;
                String nowTime = LKTimeUtil.getNowDateString("HH:mm");
                long now = 0;
                try {
                    now = LKTimeUtil.getLongTime(nowTime, "HH:mm");
                } catch (ParseException e) {
                    e.printStackTrace();
                    now = 0;
                }
                LKLogUtil.e("当前开启提醒开始时间：" + time + "-----" + startTime);
                LKLogUtil.e("开启时间：" + nowTime + "-----" + now);
                if (now - startTime < 0) {
                    LKLogUtil.e("当前开启提醒的延时时间：" + repetitionTime / (60 * 1000) + "分钟");
                    if ("永不".equals(obj.getRemindRepetitionDate())) {
                    /* 无重复模式*/
                        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
                    } else {
                    /*
                     * AlarmManager.RTC，硬件闹钟，不唤醒手机（也可能是其它设备）休眠；当手机休眠时不发射闹钟。
                     * AlarmManager.RTC_WAKEUP，硬件闹钟，当闹钟发射时唤醒手机休眠；
                     * AlarmManager.ELAPSED_REALTIME，真实时间流逝闹钟，不唤醒手机休眠；当手机休眠时不发射闹钟。
                     * AlarmManager.ELAPSED_REALTIME_WAKEUP，真实时间流逝闹钟，当闹钟发射时唤醒手机休眠；
                     */
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, repetitionTime, pendingIntent);
                    }
                }
            }
        }
    }

    /**
     * 删除系统闹钟
     */
    public static void cancelSystemAlarm(Context mContext, RemindFunctionObj obj) {
        if (mContext != null) {
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && obj != null) {
                //操作：发送一个广播，广播接收后Toast提示定时操作完成
                Intent intent = new Intent(mContext, MyAlarmReceiver.class);
                intent.setAction("tech.yunjing.biconlife.MyAlarm");
                /*提醒唯一标志ID*/
                String remindUUID = obj.getAlarmID();
                if (remindUUID == null || TextUtils.isEmpty(remindUUID)) {
                    remindUUID = obj.getRemindID() + obj.getRemindTitle() + obj.getRemindStartTime() + obj.getRemindTitle() + obj.getRemindRepetitionDate();
                }
                String uri = "content://calendar/calendar_alerts/" + remindUUID;
                LKLogUtil.e(uri);
                intent.setData(Uri.parse(uri));//此处设置用于区分不同的Intent，区别闹钟；
                intent.putExtra("RemindID", obj.getRemindID());
//                intent.putExtra("Title", obj.getRemindTitle()+"_"+obj.getRemindStartTime());
//                intent.putExtra("StartTime", obj.getRemindStartTime());
                intent.putExtra(String.valueOf(obj.getRemindID()), obj.getRemindID());
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(mContext, 0, intent, 0);
                alarmManager.cancel(pendingIntent);
            }
        }
    }
}
