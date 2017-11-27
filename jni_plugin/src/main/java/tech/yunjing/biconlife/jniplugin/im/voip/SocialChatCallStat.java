package tech.yunjing.biconlife.jniplugin.im.voip;

/**
 * 音视频的各种状态
 * Created by Chen.qi on 2017/8/30 0030.
 */

public class SocialChatCallStat {

    /**
     * 单人语音
     */
    public static final int BKL_SocialChatMultimediaTypeSingleVoice = 0;
    /**
     * 单人视频
     */
    public static final int BKL_SocialChatMultimediaTypeSingleVideo = 1;
    /**
     * 多人语音
     */
    public static final int BKL_SocialChatMultimediaTypeMutilVoice = 2;
    /**
     * 多人视频
     */
    public static final int BKL_SocialChatMultimediaTypeMutilVideo = 3;


    /**
     * 接受
     */
    public static final int BKL_SocialChatMultimediaStateReceived = 0;
    /**
     * 拒绝
     */
    public static final int BKL_SocialChatMultimediaStateRejected = 1;
    /**
     * 挂断
     */
    public static final int BKL_SocialChatMultimediaStateHangUp = 2;
    /**
     * 创建者取消 开会人员少于2人即通话结束
     * 销毁整个会话
     */
    public static final int BKL_SocialChatMultimediaStateEnd = 3;

    /**
     * 未响应
     */
    public static final int BKL_SocialChatMultimediaStateNotResponding = 4;



    /**
     * 创建
     */
    public static final int BKL_SocialChatMultimediaMessageTypeCreate = 0;
    /**
     * 邀请
     */
    public static final int BKL_SocialChatMultimediaMessageTypeInvite = 1;
    /**
     * 接听状态
     */
    public static final int BKL_SocialChatMultimediaMessageTypeStateChange = 2;
    /**
     * 有成员加入
     */
    public static final int BKL_SocialChatMultimediaMessageTypeMemberJoined = 3;



    /**
     * 未接通
     */
    public static final int BKL_SocialChatMultimediaCallStateUNPutThough = 0;
    /**
     * 已接通
     */
    public static final int BKL_SocialChatMultimediaCallStatePutThough = 1;
    /**
     * 已取消
     */
    public static final int BKL_SocialChatMultimediaCallStateCancel = 2;
    /**
     * 已拒绝
     */
    public static final int BKL_SocialChatMultimediaCallStateReject = 3;


}
