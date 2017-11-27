package tech.yunjing.biconlife.jniplugin.im.key;

import android.os.Environment;

import tech.yunjing.biconlife.jniplugin.base.AppBaseApplication;

/**
 * 其他常量
 * Created by nanPengFei on 2016/9/20 16:35.
 */
public class LKOthersFinalList {
    /**
     * 作用: 数据文件夹
     */
    public static final String PATH_WRITE_RES = Environment.getExternalStorageDirectory().getPath()
            + "/Android/data/" + AppBaseApplication.getContext().getPackageName() + "/";//缓存根目录
    /**
     * 消息类型
     */
    public static final String CONVERSATIONTYPE_SINGLE = "0";//单聊
    public static final String CONVERSATIONTYPE_GROUP = "1";//群聊
    public static final String MSGTYPE_NOTIFY = "0";//通知类型
    public static final String MSGTYPE_TXT = "1";//文本
    public static final String MSGTYPE_VOICE = "2";//语音
    public static final String MSGTYPE_IMAGE = "3";//图片
    public static final String MSGTYPE_REDPACKET_AD = "4";//链接
    public static final String MSGTYPE_CARD = "5";//转账
    public static final String MSGTYPE_VIDEO = "6";//视频
    public static final String MSGTYPE_CUSTOM_EMOJI = "7";//自定义表情
    public static final String MSGTYPE_TEXT_VOICEANDVIDEO = "11";//音视频接通挂断灯文本消息体
    public static final String CHANGE_GROUP_NICK = "50";//透传修改群昵称
    public static final String CHANGE_CREATE_GROUP = "51";//创建群聊和添加群成员透传类型
    public static final String CHANGE_TRANSFER = "60";//确认转账透传类型
    public static final String CHANGE_FRIEND_REMIND = "70";//新的好友提醒
    public static final String CHANGE_AGREE_FRIEND = "71";//同意好友请求
    public static final String CHANGE_DELETE_FRIEND = "72";//删除好友关系透传消息类型
    public static final String FRIEND_UNREAD_MESSAGE = "80";//好友圈未读消息透传类型
    public static final String MSGTYPE_CMD_TEXT = "100";//音视频所用
    public static final String MSGTYPE_CMD_TEXT_END_101 = "101";//音视频所用结束的时候必须调用

    /**
     * Hanlder 相关
     */
    public static final int MSG_DELETE = 10012;//删除某条消息
    public static final int SHOOT_BACK = 10026;//拍摄照片或视频返回
    public static final int SHOOT_IMAGE_BACK = 10027;//拍摄照片返回
    public static final int SHOOT_VIDEO_BACK = 10028;//拍摄视频返回
    public static final int SELECTED_PHOTO_CHANGE = 10024;//选择的相册列表发生变化

    /**
     * IM连接状态
     */
    public static final int IM_LINK_SUCCESS = 1;//连接成功
    public static final int IM_LINK_FAIL = 2;//连接失败

}