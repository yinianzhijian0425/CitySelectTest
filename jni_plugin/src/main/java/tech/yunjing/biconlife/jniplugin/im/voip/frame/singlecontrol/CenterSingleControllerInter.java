package tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol;

import android.content.Context;
import android.content.Intent;


/**
 * 单人音视频中控器 接口
 * Created by Chen.qi on 2017/8/15
 */

public interface CenterSingleControllerInter {
    /**
     * 初始化
     *
     * @param intent
     */
    void init(Intent intent, Context mContext);

    void addChangeDataListener(CenterSingleWindowInter listener);

    void removeChangeDataListener(CenterSingleWindowInter listener);


    /**
     * service销毁
     */
    void onServiceDestroy();

    /**
     * 电话回调
     *
     * @param voipCall 类型
     */
    void onCallEventCallBack(String voipCall);

    /**
     * 超时未接听
     */
    void timeOutNoAnswer();

    /**
     * 获取远程视屏uid
     */
    void onGetRemoteVideo(int uid);


    /**
     * 用户离线
     */
    void onUserOffline(int uid, int reason);

    /**
     * 用户加入成功
     */
    void onJoinChannelSuccess(String channel, int uid);

    /**
     * 离线成功
     */
    void onLeaveChannelSuccess();


//    /**
//     * 第一次
//     *
//     * @param uid
//     * @param width
//     * @param height
//     * @param elapse
//     */
//    void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapse);
//
//    /**
//     * onUserMuteVideo
//     *
//     * @param uid
//     * @param muted
//     */
//    void onUserMuteVideo(int uid, boolean muted);


}
