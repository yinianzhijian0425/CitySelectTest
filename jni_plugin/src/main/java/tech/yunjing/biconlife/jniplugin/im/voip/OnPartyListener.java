package tech.yunjing.biconlife.jniplugin.im.voip;

/**
 * 音视频相关回调
 * Created by Chen.qi on 2017/8/10 0010.
 */

public interface OnPartyListener {
    /**
     * 加入频道成功的回调
     */
    void onJoinChannelSuccess(String channel, int uid);

    /**
     * 当获取用户uid的远程视频的回调
     */
    void onGetRemoteVideo(int uid);

    /**
     * 退出频道
     */
    void onLeaveChannelSuccess();

    /**
     * 用户uid离线时的回调
     */
    void onUserOffline(int uid, int reason);

    /**
     * 关闭、打开远端摄像头回调
     */
    void onUserMuteVideo(int uid, boolean muted);

//    /**
//     * 远端视频相关
//     *
//     * @param uid
//     * @param muted
//     */
//    void onUserMuteVideo(int uid, boolean muted);
//
//    /**
//     * 第一次接通后视频（demo是这样进行的）
//     *
//     * @param uid
//     * @param width
//     * @param height
//     * @param elapsed
//     */
//    void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed);

}
