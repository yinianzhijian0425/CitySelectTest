package tech.yunjing.biconlife.jniplugin.util.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.text.ParseException;

import tech.yunjing.biconlife.jniplugin.db.daTable.RemindFunctionObj;
import tech.yunjing.biconlife.jniplugin.server.RemindFunctionRelatedServer;
import tech.yunjing.biconlife.jniplugin.util.BCNotification;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKTimeUtil;

/**
 * 闹钟响应广播
 * Created by sun.li on 2017/9/15.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null){
            return;
        }
        if ("tech.yunjing.biconlife.MyAlarm".equals(intent.getAction())) {
            String remindID = "";
            try {
                remindID = intent.getExtras().get("RemindID").toString();
                LKLogUtil.e("MyAlarm-" +"RemindID:"+ remindID.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            RemindFunctionObj remindFunctionObj = null;
            if(!TextUtils.isEmpty(remindID)){
                remindFunctionObj = RemindFunctionRelatedServer.getInstance().getRemindFunction(remindID);
            }
            if(remindFunctionObj == null){
                return;
            }
            String title = remindFunctionObj.getRemindTitle();
            String startTime = remindFunctionObj.getRemindStartTime();
            LKLogUtil.e("MyAlarm-" +"title:"+ title + "  startTime:" +startTime);
            if(!TextUtils.isEmpty(title)){
//                String title1 = "";
//
//                try {
////                startTime = intent.getExtras().get("StartTime").toString();
//                    title1 = title.substring(0,title.indexOf("_"));
//                    startTime = title.substring(title.indexOf("_")+1,title.length());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                showRemind(context, title, startTime);
            }
        }else{
            LKLogUtil.e("repeating alarm");
        }
    }

    /**
     * 显示提醒
     */
    private void showRemind(Context context, String title, String startTime) {
        if (!TextUtils.isEmpty(startTime)) {
            long start = 0;
            try {
                start = LKTimeUtil.getLongTime(startTime, "HH:MM");
            } catch (ParseException e) {
                e.printStackTrace();
                start = 0;
            }
            LKLogUtil.e("提醒开始时间：" + startTime + "-----" + start);
            if (start != 0) {
                String nowTime = LKTimeUtil.getNowDateString("HH:mm");
                long now = 0;
                try {
                    now = LKTimeUtil.getLongTime(nowTime, "HH:MM");
                } catch (ParseException e) {
                    e.printStackTrace();
                    now = 0;
                }
                LKLogUtil.e("接收到提醒时间：" + nowTime + "-----" + now);
                if (now - start >= 0) {
                    String content = "伯图全景提醒您，该" + title + "了";
                    if ("喝水提醒".equals(title)) {
                        content = "你与健康之间，还差一杯水，干了吧；";
                    } else if ("久坐提醒".equals(title)) {
                        content = "今天坐了太久啦，起身活动一下吧；";
                    }
                    new BCNotification(context.getApplicationContext()).postNotification(title, content);
                }
            }
        }
    }
}
