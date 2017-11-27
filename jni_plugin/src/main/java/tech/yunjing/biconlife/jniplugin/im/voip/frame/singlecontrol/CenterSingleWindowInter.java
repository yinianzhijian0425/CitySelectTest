package tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol;

/**
 * 回调、控制器回调的接口。由显示器继承。如：界面和小窗口
 * Created by Chen.qi on 2017/8/15
 */
public interface CenterSingleWindowInter {

    /**
     * 时间 回调
     *
     * @param time
     */
    void onTimerTick(String time);

    /**
     * 服务销毁
     */
    void onServiceDestroy();

    /**
     * 电话接听
     *
     * @param call
     */
    void onCallAnswered(String call);

    /**
     * 呼叫结束
     *
     * @param call
     */
    void onCallReleased(String call);

    /**
     * 对方拒绝接听
     */
    void onRefuseToAnswer(String call);

    /**
     * 时间超时未接听的回调
     */
    void timeOutNoAnswer();

    /**
     * 获取远端视频uid
     */
    void onGetRemoteVideo(int uid);

    /**
     * 离线成功
     */
    void onLeaveChannelSuccess();

    /**
     * 用户离线时的回调
     */
    void onUserOffline(int uid, int reason);

    /**
     * 加入频道成功
     */
    void onJoinChannelSuccess(String channel, int uid);

}
