package tech.yunjing.biconlife.jniplugin.im.voip;

/**
 * 测试消息接收和注册
 * Created by Chen.qi on 2017/8/10 0010.
 */

public class HxTestKey {


    /**
     * 接通发送的广播
     */
    public static final String BROADCAST_SINGLE_RECEIVER = "BROADCAST_SINGLE_RECEIVER";


    /**
     * 对方拒绝接听来电
     */
    public static final String BROADCAST_SINGLE_REJECT = "BROADCAST_SINGLE_REJECT";

    /**
     * 对方未接听的广播
     */
    public static final String BROADCAST_SINGLE_VOICE_NO_ANSER = "BROADCAST_SINGLE_VOICE_NO_ANSER";


    /**
     * 挂断通话的广播
     */
    public static final String BROADCAST_SINGLE_HAND_UP = "BROADCAST_SINGLE_HAND_UP";

    /**
     * 收到音视频消息发送的广播用于开启音视频服务
     */
    public static final String BROADCASE_RECEIVER_VOIP = "BROADCASE_RECEIVER_VOIP";


    /**
     * 创建者取消或者其他成员推出后，销毁整个房间号,销毁这个会话
     */
    public static final String BROADCAST_CREATED_CANCEL = "BROADCAST_CREATED_CANCEL";

    /**
     * 会议已经开启了，但是又邀请了其他人，此时接收者的数据不会发生改变，特此发送广播将数据发送过去
     */
    public static final String BROADCAST_MEMBERS_JOIN_NEW = "BROADCAST_MEMBERS_JOIN_NEW";

    /**
     * 加人时候传递的对象数据
     */
    public static final String JOIN_MEMBERS_IMOBJ = "JOIN_MEMBERS_IMOBJ";

    /**
     * 邀请者正邀请人员的时候，别人挂断或者拒接了的广播
     */
    public static final String JOIN_REFUSE_OR_CANCEL = "JOIN_REFUSE_OR_CANCEL";

    /**
     * 销毁整个房间的广播
     */
    public static final String DESTORY_MEETING_ROOMS = "DESTORY_MEETING_ROOMS";


    /**
     * 邀请者正邀请人员的时候，别人挂断或者拒接了传递的数据对象
     */
    public static final String JOIN_CANCEL_IMOBJ = "JOIN_CANCEL_IMOBJ";

    /**
     * 接收到透传消息传递的数据对象
     */
    public static final String IMOBJ_TRANSMIT_MESSAGE = "IMOBJ_TRANSMIT_MESSAGE";

    /**
     * 接收到销毁回话传递的数据对象
     */
    public static final String IMOBJ_DESTORY_DATA = "IMOBJ_DESTORY_DATA";


    /**
     * 多人音视频聊天时候发起的自己拼装的对象数据
     */
    public static final String PIN_ZHUANG_GROUP_IMOBJ = "PIN_ZHUANG_GROUP_IMOBJ";

    /**
     * 首页显示未读消息的数量
     */
    public static final String UNREAD_MESSAGE_MEMBER = "UNREAD_MESSAGE_MEMBER";

}
