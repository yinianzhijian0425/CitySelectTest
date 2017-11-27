package tech.yunjing.biconlife.jniplugin.im.voip;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;

/**
 * Created by 检查服务是否是启动的 on 2017/9/1 0001.
 */

public class CheckVoiceServiceIsStartVoip {
    /**
     * 判断service是否正在进行
     *
     * @param context
     * @return
     */
    public static boolean serviceIsRunningVoip(Context context) {
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            //再次进行确认
            boolean serviceRunning = false;
            try {
                serviceRunning = isServiceRunningVoip(context.getApplicationContext(), "tech.yunjing.biconlife.jniplugin.im.voip.StartVidioAndVoiceService");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (serviceRunning) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断service是否在运行
     *
     * @param mContext
     * @param className
     * @return
     */
    private static boolean isServiceRunningVoip(Context mContext, String className) throws Exception {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (serviceList == null || !(serviceList.size() > 0)) {
            return false;
        }

        for (int i = serviceList.size() - 1; i >= 0; i--) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}
