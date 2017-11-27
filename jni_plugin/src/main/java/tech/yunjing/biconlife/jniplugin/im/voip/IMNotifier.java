
package tech.yunjing.biconlife.jniplugin.im.voip;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 通知栏操作类
 * Created by Chen.qi on 2017/8/15
 */
public class IMNotifier {

    public static final int CCP_NOTIFICATOIN_ID_CALLING = 0x1;
    public static final int NOTIFY_ID_PUSHCONTENT = 35;


    private static NotificationManager mNotificationManager = null;
    private HashMap<String, Integer> mUserAndId = new HashMap<>();//消息发送者,和其对应的提示ID
    private HashMap<String, Integer> mUserAndMsgNum = new HashMap<>();//消息发送者,和其对应的消息条数
    private int mNotificationNum = 0;//通知数量


    private static IMNotifier mInstance;


    private static Context mContext;


    private IMNotifier(Context mContext) {
        IMNotifier.mContext = mContext;
    }

    /**
     * 初始化通知栏操作工具
     *
     * @return
     */
    public static IMNotifier getInstance(Context mContext) {
        if (null == mInstance || null == mNotificationManager) {
            synchronized (IMNotifier.class) {
                if (null == mInstance || null == mNotificationManager) {
                    mInstance = new IMNotifier(mContext.getApplicationContext());
                    mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                }
            }
        }
        return mInstance;
    }

    /**
     * 重置通知栏通知提示
     */
    public void reset() {
        if (mNotificationManager != null) {
            Set<Map.Entry<String, Integer>> entries = mUserAndId.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> next = iterator.next();
                Integer notifyId = next.getValue();
                if (notifyId != CCP_NOTIFICATOIN_ID_CALLING) {
                    mNotificationManager.cancel(notifyId);
                }
            }
        }
        mNotificationNum = 0;
        mUserAndId.clear();
        mUserAndMsgNum.clear();
    }


    MediaPlayer mediaPlayer = null;

    public void playNotificationMusic(String voicePath) throws IOException {
        //paly music ...
        AssetFileDescriptor fileDescriptor = mContext.getAssets().openFd(voicePath);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
        mediaPlayer.prepare();
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }


    /**
     * 后台呈现音视频呼叫Notification
     */
    public void showCallingNotification(Intent intent, String noficeStr) {
        try {
            getInstance(mContext).checkNotification();
            String topic = "正在通话中，轻击以继续";
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(mContext.getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext
                    , CCP_NOTIFICATOIN_ID_CALLING, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentTitle(noficeStr);//标题
            mBuilder.setContentText(topic);//显示文本
            mBuilder.setContentIntent(pendingIntent);//需要跳转的
            mBuilder.setOngoing(true);
            Notification notification = mBuilder.build();
            mNotificationManager.notify(CCP_NOTIFICATOIN_ID_CALLING, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkNotification() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }

    }

    public void cancelCCPNotification(int id) {
        getInstance(mContext).checkNotification();
        mNotificationManager.cancel(id);
    }


}