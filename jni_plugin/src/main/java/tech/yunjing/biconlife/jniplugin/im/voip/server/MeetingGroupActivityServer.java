package tech.yunjing.biconlife.jniplugin.im.voip.server;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImSendOption;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKBroadcastKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKIntentKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;

/**
 * 音视频的数据拼装类
 * Created by chen.qi on 2017/9/13.
 */

public class MeetingGroupActivityServer {

    private static MeetingGroupActivityServer sInstance;

    /**
     * 实例化对象
     */
    public static MeetingGroupActivityServer getInstance() {
        if (sInstance == null) {
            sInstance = new MeetingGroupActivityServer();
        }
        return sInstance;
    }


    /**
     * 自己加入频道
     */
    public void joinChannelSuccess(CenterMeetingController mController) {
        IMObj saveObj = mController.imObj;
        IMObj imObjAccept = new IMObj();
        imObjAccept.creatorMobile = saveObj.creatorMobile;
        imObjAccept.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjAccept.roomNum = saveObj.roomNum;
        imObjAccept.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateReceived;
        imObjAccept.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
        imObjAccept.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjAccept.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjAccept.groupID = saveObj.groupID;
        imObjAccept.groupNick = saveObj.groupNick;
        imObjAccept.creatorUserID = saveObj.creatorUserID;
        imObjAccept.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjAccept.groupAvatar = saveObj.groupAvatar;
        imObjAccept.creatorName = saveObj.creatorName;
        imObjAccept.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateUNPutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjAccept);

    }

    /**
     * 挂断并销毁会话
     */
    public void cancelAndEnd(CenterMeetingController mController, Context context) {
        IMObj endObj = mController.imObj;
        IMObj imObjEnd = new IMObj();
        imObjEnd.creatorMobile = endObj.creatorMobile;
        imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjEnd.roomNum = endObj.roomNum;
        imObjEnd.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
        imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
        imObjEnd.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjEnd.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjEnd.groupID = endObj.groupID;
        imObjEnd.groupNick = endObj.groupNick;
        imObjEnd.creatorUserID = endObj.creatorUserID;
        imObjEnd.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjEnd.groupAvatar = endObj.groupAvatar;
        imObjEnd.creatorName = endObj.creatorName;
        imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjEnd);


        //销毁整个会话，别人收到也关闭页面
        IMObj endObjLeavel = mController.imObj;
        IMObj imObjLeavel = new IMObj();
        imObjLeavel.creatorMobile = endObjLeavel.creatorMobile;
        imObjLeavel.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjLeavel.roomNum = endObjLeavel.roomNum;
        imObjLeavel.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
        imObjLeavel.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
        imObjLeavel.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjLeavel.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjLeavel.groupID = endObjLeavel.groupID;
        imObjLeavel.groupNick = endObjLeavel.groupNick;
        imObjLeavel.creatorUserID = endObjLeavel.creatorUserID;
        imObjLeavel.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjLeavel.groupAvatar = endObjLeavel.groupAvatar;
        imObjLeavel.creatorName = endObjLeavel.creatorName;
        imObjLeavel.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjLeavel);


        EMMessage message = EMMessage.createTxtSendMessage("语音聊天已经结束", mController.imObj.groupID);
        message.setChatType(EMMessage.ChatType.GroupChat);
        message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_NOTIFY);//消息类型为通知消息
        message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_GROUP);//消息类型(0,单聊,1群聊)
        message.setAttribute("groupId", mController.imObj.groupID);//群聊id
        message.setAttribute("groupAvatar", mController.imObj.groupAvatar);//群头像
        message.setAttribute("groupNick", mController.imObj.groupNick);//群昵称
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mController.imObj.groupID);
        //将本条消息插入到会话中
        if (conversation!=null){
        conversation.insertMessage(message);}
        context.sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
        Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + mController.imObj.groupID);
        intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
        context.sendBroadcast(intent);


    }

    /**
     * 超时自动挂断
     */
    public void timeOutCancel(CenterMeetingController mController) {
        IMObj saveObj = mController.imObj;
        IMObj imObjReject = new IMObj();
        imObjReject.creatorMobile = saveObj.creatorMobile;
        imObjReject.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjReject.roomNum = saveObj.roomNum;
        imObjReject.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateRejected;
        imObjReject.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
        imObjReject.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjReject.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjReject.groupID = saveObj.groupID;
        imObjReject.groupNick = saveObj.groupNick;
        imObjReject.creatorUserID = saveObj.creatorUserID;
        imObjReject.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjReject.groupAvatar = saveObj.groupAvatar;
        imObjReject.creatorName = saveObj.creatorName;
        imObjReject.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateUNPutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjReject);
    }

    /**
     * 接听后挂断按钮，同事判断人数少于2人同事在线进行销毁会话
     */
    public void acceptAfterEnd(CenterMeetingController mController, Context context) {
        IMObj endObj = mController.imObj;
        IMObj imObjEnd = new IMObj();
        imObjEnd.creatorMobile = endObj.creatorMobile;
        imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjEnd.roomNum = endObj.roomNum;
        imObjEnd.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
        imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
        imObjEnd.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjEnd.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjEnd.groupID = endObj.groupID;
        imObjEnd.groupNick = endObj.groupNick;
        imObjEnd.creatorUserID = endObj.creatorUserID;
        imObjEnd.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjEnd.groupAvatar = endObj.groupAvatar;
        imObjEnd.creatorName = endObj.creatorName;
        imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjEnd);

        //销毁
        //销毁整个会话，别人收到也关闭页面
        IMObj endObjLeavel = mController.imObj;
        IMObj imObjLeavel = new IMObj();
        imObjLeavel.creatorMobile = endObjLeavel.creatorMobile;
        imObjLeavel.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjLeavel.roomNum = endObjLeavel.roomNum;
        imObjLeavel.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
        imObjLeavel.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
        imObjLeavel.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjLeavel.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjLeavel.groupID = endObjLeavel.groupID;
        imObjLeavel.groupNick = endObjLeavel.groupNick;
        imObjLeavel.creatorUserID = endObjLeavel.creatorUserID;
        imObjLeavel.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjLeavel.groupAvatar = endObjLeavel.groupAvatar;
        imObjLeavel.creatorName = endObjLeavel.creatorName;
        imObjLeavel.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjLeavel);


        EMMessage message = EMMessage.createTxtSendMessage("语音聊天已经结束", mController.imObj.groupID);
        message.setChatType(EMMessage.ChatType.GroupChat);
        message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_NOTIFY);//消息类型为通知消息
        message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_GROUP);//消息类型(0,单聊,1群聊)
        message.setAttribute("groupId", mController.imObj.groupID);//群聊id
        message.setAttribute("groupAvatar", mController.imObj.groupAvatar);//群头像
        message.setAttribute("groupNick", mController.imObj.groupNick);//群昵称
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mController.imObj.groupID);
        //将本条消息插入到会话中
        if (conversation!=null){
        conversation.insertMessage(message);}
        context.sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
        Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + mController.imObj.groupID);
        intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
        context.sendBroadcast(intent);
    }

    /**
     * 通话以后
     * 接听后挂断，不进行销毁操作
     */
    public void guaDuan(CenterMeetingController mController) {
        IMObj endObj = mController.imObj;
        IMObj imObjEnd = new IMObj();
        imObjEnd.creatorMobile = endObj.creatorMobile;
        imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjEnd.roomNum = endObj.roomNum;
        imObjEnd.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp;
        imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
        imObjEnd.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjEnd.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjEnd.groupID = endObj.groupID;
        imObjEnd.groupNick = endObj.groupNick;
        imObjEnd.creatorUserID = endObj.creatorUserID;
        imObjEnd.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjEnd.groupAvatar = endObj.groupAvatar;
        imObjEnd.creatorName = endObj.creatorName;
        imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjEnd);
    }

    /**
     * 来电后拒接--挂断
     */
    public void reJuctPhone(CenterMeetingController mController) {
        IMObj saveObj = mController.imObj;
        IMObj imObjReject = new IMObj();
        imObjReject.creatorMobile = saveObj.creatorMobile;
        imObjReject.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjReject.roomNum = saveObj.roomNum;
        imObjReject.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateRejected;
        imObjReject.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
        imObjReject.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjReject.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjReject.groupID = saveObj.groupID;
        imObjReject.groupNick = saveObj.groupNick;
        imObjReject.creatorUserID = saveObj.creatorUserID;
        imObjReject.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjReject.groupAvatar = saveObj.groupAvatar;
        imObjReject.creatorName = saveObj.creatorName;
        imObjReject.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateUNPutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjReject);

    }


    /**
     * 来电后拒绝，判断人数少于2人的时候销毁整个会话
     * 发送101
     */
    public void reJuceEndAll(CenterMeetingController mController) {
        //销毁整个会话，别人收到也关闭页面
        IMObj endObjLeavel = mController.imObj;
        IMObj imObjLeavel = new IMObj();
        imObjLeavel.creatorMobile = endObjLeavel.creatorMobile;
        imObjLeavel.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
        imObjLeavel.roomNum = endObjLeavel.roomNum;
        imObjLeavel.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
        imObjLeavel.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
        imObjLeavel.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        imObjLeavel.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
        imObjLeavel.groupID = endObjLeavel.groupID;
        imObjLeavel.groupNick = endObjLeavel.groupNick;
        imObjLeavel.creatorUserID = endObjLeavel.creatorUserID;
        imObjLeavel.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        imObjLeavel.groupAvatar = endObjLeavel.groupAvatar;
        imObjLeavel.creatorName = endObjLeavel.creatorName;
        imObjLeavel.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateUNPutThough;
        MyImSendOption.getInstance().sendGroupTC(mController.imObj.groupID, imObjLeavel);
    }

}
