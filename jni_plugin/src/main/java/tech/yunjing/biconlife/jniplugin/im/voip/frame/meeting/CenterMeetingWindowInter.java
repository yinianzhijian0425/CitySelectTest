package tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting;


import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;

/**
 * Created by dup on 2017/6/9.
 * 回调、控制器回调的接口。由显示器继承。如：界面和小窗口
 */
public interface CenterMeetingWindowInter {

    /**
     * 时间 回调
     *
     * @param time
     */
    void onTimerTick(String time);


    void onServiceDestroy();

    /**
     * 电话接听
     *
     * @param callImObj
     */
    void onCallAnswered(IMObj callImObj);

    /**
     * 呼叫结束
     *
     * @param callImObj
     */
    void onCallReleased(IMObj callImObj);

    /**
     * 对方拒绝接听
     */
    void onRefuseToAnswer(IMObj callImObj);

    /**
     * 加入频道成功
     */
    void onJoinChannelSuccess(String channel, int uid);

    /**
     * 离线时的回调
     */
    void onUserOffline(int uid, int reason);

    /**
     * 打开关闭远端视频的回调
     *
     * @param uid
     * @param enabled
     */
    void onUserMuteVideo(int uid, boolean enabled);

    /**
     * 超时未接听
     */
    void timeOutNoAnswer();

    /**
     * 创建者取消群聊音视频会话
     */
    void onCreatorCancel(IMObj callImObj);

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
