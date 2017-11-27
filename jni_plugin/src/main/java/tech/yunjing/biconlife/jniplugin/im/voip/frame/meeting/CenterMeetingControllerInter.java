package tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting;

import android.content.Context;
import android.content.Intent;

import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;

/**
 * Created by dup on 2017/6/8.
 * 会议音视频中控器 接口
 */

public interface CenterMeetingControllerInter {
    /**
     * 初始化
     *
     * @param intent
     */
    void init(Intent intent, Context mContext);

    void addChangeDataListener(CenterMeetingWindowInter listener);

    void removeChangeDataListener(CenterMeetingWindowInter listener);

    /**
     * service销毁
     */
    void onServiceDestroy();

    /**
     * 超时未接听
     */
    void timeOutNoAnswer();

    /**
     * 电话回调
     *
     * @param callImObj 数据对象
     */
    void onCallEventCallBack(IMObj callImObj);

    /**
     * 用户加入成功
     */
    void onJoinChannelSuccess(String channel, int uid);

    /**
     * 离线时的回调
     */
    void onUserOffline(int uid, int reason);

    /**
     * 关闭、打开远端摄像头回调
     */
    void onUserMuteVideo(int uid, boolean enabled);

    /**
     * 离线成功
     */
    void onLeaveChannelSuccess();

    /**
     * 有新人加入会议
     */
    void onJoinNewMembers(IMObj imObj);

    /**
     * 销毁房间
     */
    void onDestoryRoom(IMObj imObjDes);


}
