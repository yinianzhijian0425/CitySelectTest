package tech.yunjing.biconlife.jniplugin.im.voip.frame;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImSendOption;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;


/**
 * 提供调用sdk方法的工具类
 * Created by Chen.qi on 2017/8/15
 */
public class VoIPHelper {

    /**
     * 单人音频类型
     */
    public static final String EXTRA_SINGLE_CALL_VOICE = "EXTRA_SINGLE_CALL_VOICE";

    /**
     * 单人视频类型
     */
    public static final String EXTRA_SINGLE_CALL_VIDEO = "EXTRA_SINGLE_CALL_VIDEO";

    /**
     * 昵称
     */
    public static final String EXTRA_CALL_NAME = "EXTRA_CALL_NAME";
    /**
     * 头像
     */
    public static final String EXTRA_CALL_AVATAR = "EXTRA_CALL_AVATAR";

    /**
     * 通话号码
     */
    public static final String EXTRA_CALL_NUMBER = "EXTRA_CALL_NUMBER";
    /**
     * 呼入方或者呼出方
     */
    public static final String EXTRA_OUTGOING_CALL = "EXTRA_OUTGOING_CALL";
    /**
     * 单人还是会议
     */
    public static final String EXTRA_SINGLE_CALL = "EXTRA_SINGLE_CALL";

    /**
     * 群id
     */
    public static final String EXTRA_MEETING_GROUP_ID = "EXTRA_MEETING_GROUP_ID";

    /**
     * 接收人id
     */
    public static final String RECEIVER_ID = "RECEIVER_ID";

    /**
     * 透传的对象
     */
    public static final String TRANST_MSG_OBJ = "TRANST_MSG_OBJ";


    /**
     * 判断service是否正在进行
     *
     * @param context
     * @return
     */
    public static boolean serviceIsRunning(Context context) {
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            //再次进行确认
            boolean serviceRunning = false;
            try {
                serviceRunning = isServiceRunning(context.getApplicationContext(), "tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService");
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
     * * 单人呼叫
     *
     * @param chatID   接收人的id
     * @param imObj    扩展字段即所有数据
     * @param activity 上下文
     * @param type     类型0：单人语音  1:单人视频
     */
    public static void callSingleAction(String chatID, String nickName, String avatar, IMObj imObj, Activity activity, int type) {
        if (serviceIsRunning(activity)) {
            LKToastUtil.showToastShort("正在通话中，无法创建!");
            return;
        }

        Intent callAction = new Intent(activity, CenterService.class);
        if (type == 0) {
            callAction.putExtra(VoIPHelper.EXTRA_SINGLE_CALL_VOICE, true);//单人音频呼叫类型
        } else if (type == 1) {
            callAction.putExtra(VoIPHelper.EXTRA_SINGLE_CALL_VIDEO, true);//单人视频呼叫类型
        }


        callAction.putExtra(VoIPHelper.EXTRA_CALL_AVATAR, avatar);

        callAction.putExtra(VoIPHelper.EXTRA_CALL_NAME, nickName);
        callAction.putExtra(VoIPHelper.EXTRA_OUTGOING_CALL, true); // 设置为呼出
        callAction.putExtra(VoIPHelper.EXTRA_SINGLE_CALL, true);//设置为单人
        callAction.putExtra(VoIPHelper.RECEIVER_ID, chatID);//接收人的Id号码
        callAction.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);//传递的拼装的对象
        activity.startService(callAction);
    }


    /**
     * 群中启动会议呼叫
     *
     * @param activity
     */
    public static void callMeetingAction(String groupId, IMObj imObj, Activity activity) {
        if (serviceIsRunning(activity)) {
            LKToastUtil.showToastShort("正在通话中，无法创建!");
            return;
        }
        Intent intent = new Intent(activity, CenterService.class);
        intent.putExtra(VoIPHelper.EXTRA_OUTGOING_CALL, true);
        intent.putExtra(VoIPHelper.EXTRA_SINGLE_CALL, false);//设置为会议
        intent.putExtra(VoIPHelper.RECEIVER_ID, groupId);
        intent.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);//传递的拼装的对象
        activity.startService(intent);
    }


    /**
     * 异常结束
     *
     * @param activity
     */
    public static void errorFinish(Activity activity) {
        LKToastUtil.showToastShort("通话结束!");
        activity.finish();
    }

    /**
     * 判断service是否在运行
     *
     * @param mContext
     * @param className
     * @return
     */
    private static boolean isServiceRunning(Context mContext, String className) throws Exception {
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
