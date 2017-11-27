package tech.yunjing.biconlife.jniplugin.im.bean;

import java.io.Serializable;

/**
 * 消息发送时需要的所有数据
 * Created by lh on 2016/11/8 15:02.
 */
public class IMObj implements Serializable {
    public String userID;//发送者id
    public String avatar;//用户头像
    public String nick;//用户昵称
    public String groupId;//透传群id
    public String groupNick;// 透传群昵称
    public String groupAvatar;//透传群头像
    public String imageWidth;//图片宽度
    public String imageHeight;//图片高度
    public String linkTitle;//链接标题
    public String linkContent;//链接描述内容
    public String linkIcon;// 链接logo
    public String linkSource;//链接来源
    public String linkUrl;//链接地址
    public String transferMoney;//转账金额
    public String transferContent;//转账附带描述
    public String transferSource;// 转账来源，默认为伯图全景转账，以备后续有其他形式接入
    public String transferId;//转账ID
    public String conversationType;//字符串: 0.单聊, 1.群聊
    public String messageType;//字符串: 0.通知 1.文字 2.语音 3.图片 4.链接 5.转账 6.视频
    public String chatID;//聊天人id
    public String chatAvatar;//聊天人头像
    public String chatNick;//聊天人昵称


    public IMObj() {

    }

    /**
     * 基础信息
     *
     * @param conversationType
     * @param messageType
     */
    public IMObj(String userID, String userAvatar, String userNick, String chatID, String chatAvatar, String chatNick,
                 String conversationType, String messageType) {
        this.userID = userID;
        this.avatar = userAvatar;
        this.nick = userNick;
        this.chatID = chatID;
        this.chatAvatar = chatAvatar;
        this.chatNick = chatNick;
        this.conversationType = conversationType;
        this.messageType = messageType;
    }


    //修改群昵称透传消息
    public String userNick;
    public String groupName;

    public IMObj(String userNick, String groupName, String groupID, String messageType, String groupAvatar) {
        this.groupAvatar = groupAvatar;
        this.messageType = messageType;
        this.groupID = groupID;
        this.userNick = userNick;
        this.groupName = groupName;
    }

    public String orderNo;//转账订单id

    //确认转账透传消息
    public IMObj(String messageType, String orderNo) {
        this.messageType = messageType;
        this.orderNo = orderNo;
    }

    public String nickName;
    public String userId;

    //删除好友透传消息和同意好友请求
    public IMObj(String messageType, String avatar, String nickName, String userId) {
        this.avatar = avatar;
        this.messageType = messageType;
        this.nickName = nickName;
        this.userId = userId;
    }

    public String inviterId;
    public String beInvitedIds;

    //创建群聊或者添加群成员透传消息
    public IMObj(String groupId, String groupNick, String groupAvatar, String messageType, String inviterId, String beInvitedIds) {
        this.groupId = groupId;
        this.groupNick = groupNick;
        this.groupAvatar = groupAvatar;
        this.messageType = messageType;
        this.inviterId = inviterId;
        this.beInvitedIds = beInvitedIds;
    }


    //新的好友提醒透传消息
    public IMObj(String messageType) {
        this.messageType = messageType;
    }


    /**
     * 音视频所用
     *
     * @param messageType           消息类型
     * @param callState             当前通话状态 0：未接通 1：已接通 2：已取消 3：已拒绝
     * @param creatorMobile         创建者的手机号
     * @param creatorName           创建者的昵称
     * @param creatorUID            创建者的uid（自己生成多人需要）
     * @param creatorUserID         创建者的userId
     * @param fromAvatar            创建者的头像
     * @param fromMobile            创建者的手机号
     * @param fromNick              创建者的昵称
     * @param fromUID               创建者的uid（自己生成多人需要）
     * @param groupAvatar           群头像
     * @param groupID               群id
     * @param groupNick             群昵称
     * @param multimediaMessageType 接听状态 0:创建 1：邀请  2：接听状态 3:有成员加入
     * @param multimediaState
     * @param multimediaType
     * @param roomNum
     */
    public IMObj(int callState, String creatorMobile, String creatorName, String creatorUID,
                 String creatorUserID, String fromAvatar, String fromMobile, String fromNick,
                 String fromUID, String groupAvatar, String groupID, String groupNick, String messageType,
                 int multimediaMessageType, int multimediaState, int multimediaType, String roomNum) {
        this.callState = callState;
        this.creatorMobile = creatorMobile;
        this.creatorName = creatorName;
        this.creatorUID = creatorUID;
        this.creatorUserID = creatorUserID;
        this.fromAvatar = fromAvatar;
        this.fromMobile = fromMobile;
        this.fromNick = fromNick;
        this.fromUID = fromUID;
        this.groupAvatar = groupAvatar;
        this.groupID = groupID;
        this.groupNick = groupNick;
        this.messageType = messageType;
        this.multimediaMessageType = multimediaMessageType;
        this.multimediaState = multimediaState;
        this.multimediaType = multimediaType;
        this.roomNum = roomNum;
    }

    /**
     * 音视频所用(群组所用)
     *
     * @param messageType           消息类型
     * @param callState             当前通话状态 0：未接通 1：已接通 2：已取消 3：已拒绝
     * @param creatorMobile         创建者的手机号
     * @param creatorName           创建者的昵称
     * @param creatorUID            创建者的uid（自己生成多人需要）
     * @param creatorUserID         创建者的userId
     * @param fromAvatar            创建者的头像
     * @param fromMobile            创建者的手机号
     * @param fromNick              创建者的昵称
     * @param fromUID               创建者的uid（自己生成多人需要）
     * @param groupAvatar           群头像
     * @param groupID               群id
     * @param groupNick             群昵称
     * @param multimediaMessageType 接听状态 0:创建 1：邀请  2：接听状态 3:有成员加入
     * @param multimediaState
     * @param multimediaType
     * @param roomNum
     */
    public IMObj(int callState, String creatorMobile, String creatorName, String creatorUID,
                 String creatorUserID, String fromAvatar, String fromMobile, String fromNick,
                 String fromUID, String groupAvatar, String groupID, String groupNick, String members, String messageType,
                 int multimediaMessageType, int multimediaState, int multimediaType, String roomNum) {
        this.callState = callState;
        this.creatorMobile = creatorMobile;
        this.creatorName = creatorName;
        this.creatorUID = creatorUID;
        this.creatorUserID = creatorUserID;
        this.fromAvatar = fromAvatar;
        this.fromMobile = fromMobile;
        this.fromNick = fromNick;
        this.fromUID = fromUID;
        this.groupAvatar = groupAvatar;
        this.groupID = groupID;
        this.groupNick = groupNick;
        this.members = members;
        this.messageType = messageType;
        this.multimediaMessageType = multimediaMessageType;
        this.multimediaState = multimediaState;
        this.multimediaType = multimediaType;
        this.roomNum = roomNum;

    }


    /////透传消息
    public int callState;
    public String creatorMobile;
    public String creatorName;
    public String creatorUID;
    public String creatorUserID;
    public String fromAvatar;
    public String fromMobile;
    public String fromNick;
    public String fromUID;
    public String groupID;
    public String members;
    public int multimediaMessageType;
    public int multimediaState;
    public int multimediaType;
    public String roomNum;
    public String tipMessage;
    public String duration;

    @Override
    public String toString() {
        return "IMObj{" +
                "userID='" + userID + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupNick='" + groupNick + '\'' +
                ", groupAvatar='" + groupAvatar + '\'' +
                ", imageWidth='" + imageWidth + '\'' +
                ", imageHeight='" + imageHeight + '\'' +
                ", linkTitle='" + linkTitle + '\'' +
                ", linkContent='" + linkContent + '\'' +
                ", linkIcon='" + linkIcon + '\'' +
                ", linkSource='" + linkSource + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", transferMoney='" + transferMoney + '\'' +
                ", transferContent='" + transferContent + '\'' +
                ", transferSource='" + transferSource + '\'' +
                ", transferId='" + transferId + '\'' +
                ", conversationType='" + conversationType + '\'' +
                ", messageType='" + messageType + '\'' +
                ", chatID='" + chatID + '\'' +
                ", chatAvatar='" + chatAvatar + '\'' +
                ", chatNick='" + chatNick + '\'' +
                ", userNick='" + userNick + '\'' +
                ", groupName='" + groupName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userId='" + userId + '\'' +
                ", inviterId='" + inviterId + '\'' +
                ", beInvitedIds='" + beInvitedIds + '\'' +
                ", callState=" + callState +
                ", creatorMobile='" + creatorMobile + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", creatorUID='" + creatorUID + '\'' +
                ", creatorUserID='" + creatorUserID + '\'' +
                ", fromAvatar='" + fromAvatar + '\'' +
                ", fromMobile='" + fromMobile + '\'' +
                ", fromNick='" + fromNick + '\'' +
                ", fromUID='" + fromUID + '\'' +
                ", groupID='" + groupID + '\'' +
                ", members='" + members + '\'' +
                ", multimediaMessageType=" + multimediaMessageType +
                ", multimediaState=" + multimediaState +
                ", multimediaType=" + multimediaType +
                ", roomNum='" + roomNum + '\'' +
                ", tipMessage='" + tipMessage + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
