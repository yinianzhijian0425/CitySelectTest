package tech.yunjing.biconlife.jniplugin.im.MyIm;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tech.yunjing.biconlife.jniplugin.db.DeleteFriendInfo;
import tech.yunjing.biconlife.jniplugin.db.GeneralDb;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.util.Bean2JsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 消息发送帮助类
 * Created by lh on 2017/8/11.
 */

public class MyImSendOption {

    private static MyImSendOption mInstance;

    private MyImSendOption() {
    }

    public static MyImSendOption getInstance() {
        if (null == mInstance) {
            synchronized (MyImSendOption.class) {
                if (null == mInstance) {
                    mInstance = new MyImSendOption();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送文本消息
     *
     * @param imObj
     * @param chatContent
     * @param handler
     */
    public void sendText(String chatID, IMObj imObj, String chatContent, Handler handler) {
        if (EMClient.getInstance() == null) {
            return;
        }
        EMMessage.ChatType chatType = EMMessage.ChatType.Chat;
        if ("1".equals(imObj.conversationType)) {//群聊
            chatType = EMMessage.ChatType.GroupChat;
        }
        EMMessage emMessage = EMMessage.createTxtSendMessage(chatContent, chatID);
        setEMMsgInfo(emMessage, imObj, chatType);
        addMsgStatusListener(emMessage, handler, chatID);
    }


    /**
     * 获得的对象
     */
    public IMObj getVoiceIMObj(EMMessage emMessage) {
        if (emMessage == null) {
            return null;
        }
        int callState = emMessage.getIntAttribute("callState", 0);
        String creatorMobile = emMessage.getStringAttribute("creatorMobile", "");
        String creatorName = emMessage.getStringAttribute("creatorName", "");
        String creatorUID = emMessage.getStringAttribute("creatorUID", "");
        String creatorUserID = emMessage.getStringAttribute("creatorUserID", "");
        String fromAvatar = emMessage.getStringAttribute("fromAvatar", "");
        String fromMobile = emMessage.getStringAttribute("fromMobile", "");
        String fromNick = emMessage.getStringAttribute("fromNick", "");
        String fromUID = emMessage.getStringAttribute("fromUID", "");
        String groupAvatar = emMessage.getStringAttribute("groupAvatar", "");
        String groupID = emMessage.getStringAttribute("groupID", "");
        String groupNick = emMessage.getStringAttribute("groupNick", "");
        String messageType = emMessage.getStringAttribute("messageType", "");
        int multimediaMessageType = emMessage.getIntAttribute("multimediaMessageType", 0);
        int multimediaState = emMessage.getIntAttribute("multimediaState", 0);
        int multimediaType = emMessage.getIntAttribute("multimediaType", 0);
        String roomNum = emMessage.getStringAttribute("roomNum", "");
        String members = emMessage.getStringAttribute("members", "");
        IMObj imObj1;
        if (TextUtils.isEmpty(members)) {
            imObj1 = new IMObj(callState, creatorMobile, creatorName, creatorUID,
                    creatorUserID, fromAvatar, fromMobile, fromNick, fromUID, groupAvatar,
                    groupID, groupNick, messageType, multimediaMessageType, multimediaState, multimediaType, roomNum);
        } else {
            imObj1 = new IMObj(callState, creatorMobile, creatorName, creatorUID,
                    creatorUserID, fromAvatar, fromMobile, fromNick, fromUID, groupAvatar,
                    groupID, groupNick, members, messageType, multimediaMessageType, multimediaState, multimediaType, roomNum);
        }
        return imObj1;
    }


    /**
     * 发送语音
     *
     * @param imObj
     * @param voicePath
     * @param voiceLength
     * @param handler
     */
    public void sendVoice(String chatID, IMObj imObj, String voicePath, int voiceLength, final Handler handler) {
        EMMessage.ChatType chatType = EMMessage.ChatType.Chat;
        if ("1".equals(imObj.conversationType)) {//群聊
            chatType = EMMessage.ChatType.GroupChat;
        }
        EMMessage emMessage = EMMessage.createVoiceSendMessage(voicePath, voiceLength, chatID);
        setEMMsgInfo(emMessage, imObj, chatType);
        addMsgStatusListener(emMessage, handler, chatID);
    }

    /**
     * 发送图片
     *
     * @param imObj
     * @param photoPath
     * @param isBig
     * @param handler
     */
    public void sendImage(String chatID, IMObj imObj, String photoPath, boolean isBig, final Handler handler) {
        File file = new File(photoPath);
        int length = (int) (file.length() / 1024);//大于10M的图片禁止发送原图
        if (length >= 10240) {
            isBig = false;
        }
        EMMessage.ChatType chatType = EMMessage.ChatType.Chat;
        if ("1".equals(imObj.conversationType)) {//群聊
            chatType = EMMessage.ChatType.GroupChat;
        }
        EMMessage emMessage = EMMessage.createImageSendMessage(photoPath, isBig, chatID);
        setEMMsgInfo(emMessage, imObj, chatType);
        addMsgStatusListener(emMessage, handler, chatID);
    }

    /**
     * 发送链接
     *
     * @param imObj
     * @param chatContent
     * @param handler
     */
    public void sendLink(String chatID, IMObj imObj, String chatContent, Handler handler) {
        if (EMClient.getInstance() == null) {
            return;
        }
        EMMessage.ChatType chatType = EMMessage.ChatType.Chat;
        if ("1".equals(imObj.conversationType)) {//群聊
            chatType = EMMessage.ChatType.GroupChat;
        }
        EMMessage emMessage = EMMessage.createTxtSendMessage(chatContent, chatID);
        setEMMsgInfo(emMessage, imObj, chatType);
        addMsgStatusListener(emMessage, handler, chatID);
    }

    /**
     * 发送转账消息
     *
     * @param imObj
     * @param chatContent
     * @param handler
     */
    public void sendTransfer(String chatID, IMObj imObj, String chatContent, Handler handler) {
        if (EMClient.getInstance() == null) {
            return;
        }
        EMMessage.ChatType chatType = EMMessage.ChatType.Chat;
        if ("1".equals(imObj.conversationType)) {//群聊
            chatType = EMMessage.ChatType.GroupChat;
        }
        EMMessage emMessage = EMMessage.createTxtSendMessage(chatContent, chatID);
        setEMMsgInfo(emMessage, imObj, chatType);
        addMsgStatusListener(emMessage, handler, chatID);
    }

    /**
     * 发送视频
     *
     * @param chatID
     * @param imObj
     * @param videoPath
     * @param thumbPath
     * @param videoLength
     * @param handler
     */
    public void sendVideo(String chatID, IMObj imObj, String videoPath, String thumbPath, int videoLength, final Handler handler) {
        EMMessage.ChatType chatType = EMMessage.ChatType.Chat;
        if ("1".equals(imObj.conversationType)) {//群聊
            chatType = EMMessage.ChatType.GroupChat;
        }
        EMMessage emMessage = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, chatID);
        setEMMsgInfo(emMessage, imObj, chatType);
        addMsgStatusListener(emMessage, handler, chatID);
    }


    /**
     * 设置发送消息需要的扩展数据
     *
     * @param emMessage
     * @param imObj
     */
    private void setEMMsgInfo(EMMessage emMessage, IMObj imObj, EMMessage.ChatType chatType) {
        emMessage.setChatType(chatType);
        emMessage.setAttribute("messageType", imObj.messageType + "");//消息类型
        emMessage.setAttribute("conversationType", imObj.conversationType);//消息类型(0,单聊,1群聊)
        emMessage.setAttribute("userID", imObj.userID);//用户id
        emMessage.setAttribute("avatar", imObj.avatar);//用户头像
        emMessage.setAttribute("nick", imObj.nick);//用户昵称
        if ("0".equals(imObj.conversationType)) {
            emMessage.setAttribute("chatAvatar", imObj.chatAvatar);//聊天人头像
            emMessage.setAttribute("chatNick", imObj.chatNick);//聊天人昵称
        } else if ("1".equals(imObj.conversationType)) {
            emMessage.setAttribute("groupId", imObj.chatID);//群聊id
            emMessage.setAttribute("groupAvatar", imObj.chatAvatar);//群头像
            emMessage.setAttribute("groupNick", imObj.chatNick);//群昵称
        }
        switch (imObj.messageType) {
            case LKOthersFinalList.MSGTYPE_NOTIFY://通知
                break;
            case LKOthersFinalList.MSGTYPE_TXT://文本
                break;
            case LKOthersFinalList.MSGTYPE_VOICE://语音
                break;
            case LKOthersFinalList.MSGTYPE_IMAGE://图片
            case LKOthersFinalList.MSGTYPE_CUSTOM_EMOJI://自定义表情图片
                emMessage.setAttribute("imageWidth", imObj.imageWidth);
                emMessage.setAttribute("imageHeight", imObj.imageHeight);
                break;
            case LKOthersFinalList.MSGTYPE_REDPACKET_AD://链接
                emMessage.setAttribute("linkTitle", imObj.linkTitle);
                emMessage.setAttribute("linkContent", imObj.linkContent);
                emMessage.setAttribute("linkIcon", imObj.linkIcon);
                emMessage.setAttribute("linkSource", imObj.linkSource);
                emMessage.setAttribute("linkUrl", imObj.linkUrl);
                break;
            case LKOthersFinalList.MSGTYPE_CARD://转账
                emMessage.setAttribute("transferMoney", imObj.transferMoney);
                emMessage.setAttribute("transferContent", imObj.transferContent);
                emMessage.setAttribute("transferSource", imObj.transferSource);
                emMessage.setAttribute("transferId", imObj.transferId);
                break;
            case LKOthersFinalList.MSGTYPE_VIDEO://视频

                break;
            default:
                break;
        }
    }


    /**
     * 添加消息状态监听，发送，添加最后记录
     *
     * @param message
     */
    private void addMsgStatusListener(final EMMessage message, final Handler handler, String chatId) {
        List<DeleteFriendInfo> infos = GeneralDb.getQueryByWhere(DeleteFriendInfo.class, "UserId", new String[]{chatId});
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                LKLogUtil.e("------成功-------");
                handler.sendEmptyMessage(7201);
            }

            @Override
            public void onError(int i, String s) {
                LKLogUtil.e("------失败-------" + s);
                message.setStatus(EMMessage.Status.FAIL);
                handler.sendEmptyMessage(7201);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
        Message hmessage = new Message();
        hmessage.what = 101;
        hmessage.obj = message;
        handler.sendMessage(hmessage);
        if (infos != null && infos.size() > 0) {//如果好友被删除，则主动插入消息
            message.setStatus(EMMessage.Status.FAIL);
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatId);
            if (conversation != null) {
                conversation.insertMessage(message);
            }
        } else {
            EMClient.getInstance().chatManager().sendMessage(message);//发送消息
        }
    }

    /**
     * 发送透传消息--修改群昵称
     */
    public void sendCMD(String GroupId, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        cmdMsg.setChatType(EMMessage.ChatType.GroupChat);
        String action = Bean2JsonUtil.getGroupNickJson(imObj);//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(GroupId);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 发送透传消息--确认转账
     */
    public void sendTransferCMD(String chatId, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = Bean2JsonUtil.getTransferJson(imObj);//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(chatId);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 发送透传消息--删除好友--
     */
    public void sendDeleteFriendCMD(String chatId, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = Bean2JsonUtil.getDeleteJson(imObj);//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(chatId);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 发送透传消息--新的好友请求和朋友圈未读消息
     */
    public void sendNewFriendRequest(String chatId, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = Bean2JsonUtil.getNewFriendRequest(imObj);//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(chatId);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 发送透传消息--同意好友请求
     */
    public void sendAgreeFriendCMD(String chatId, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = Bean2JsonUtil.getAgreeJson(imObj);//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(chatId);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 发送透传消息--创建和添加群成员
     */
    public void sendCreateAddCMD(String groupId, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        cmdMsg.setChatType(EMMessage.ChatType.GroupChat);
        String action = Bean2JsonUtil.getCreateAddJson(imObj);//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(groupId);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 音视频发送透传消息(单人所发)
     */
    public void sendVoiceAndVideoTrans(String chatID, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = Bean2JsonUtil.getChatJson(imObj);//action可以自定义
        LKLogUtil.e("发起透传消息的数据==" + action);
        LKLogUtil.e("----发送人chatID==" + chatID);
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(chatID);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 音视频发送透传消息(单人所发)
     */
    public void sendSingleEnd(String chatID, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = Bean2JsonUtil.getChatEnd(chatID, imObj);//action可以自定义
        LKLogUtil.e("发起透传消息的数据这是结束的时候发起的==" + action);
        LKLogUtil.e("----发送人chatID==" + chatID);
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(chatID);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }


    /**
     * 音视频发送透传消息(群发透传消息)
     */
    public void sendGroupTC(String groupID, IMObj imObj) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
        cmdMsg.setChatType(EMMessage.ChatType.GroupChat);
        String action = Bean2JsonUtil.getChatGroupJson(imObj);//action可以自定义
        LKLogUtil.e("发起透传消息的数据==" + action);
        LKLogUtil.e("----发送人chatID==" + groupID);
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.setTo(groupID);//发送给某个人
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * 发送文本消息--100音视频开启（单人）
     */
    public void sendTextVoico(final String chatID, IMObj imObj) {
        if (EMClient.getInstance() == null) {
            return;
        }
        final EMMessage emMessage = EMMessage.createTxtSendMessage("开启音视频", chatID);
        emMessage.setAttribute("callState", imObj.callState);
        emMessage.setAttribute("creatorMobile", imObj.creatorMobile);
        emMessage.setAttribute("creatorName", imObj.creatorName);
        emMessage.setAttribute("creatorUID", imObj.creatorUID);
        emMessage.setAttribute("creatorUserID", imObj.creatorUserID);
        emMessage.setAttribute("fromAvatar", imObj.fromAvatar);
        emMessage.setAttribute("fromMobile", imObj.fromMobile);
        emMessage.setAttribute("fromNick", imObj.fromNick);
        emMessage.setAttribute("fromUID", imObj.fromUID);
        emMessage.setAttribute("groupAvatar", imObj.groupAvatar);
        emMessage.setAttribute("groupID", imObj.groupID);
        emMessage.setAttribute("groupNick", imObj.groupNick);
        emMessage.setAttribute("messageType", imObj.messageType);
        emMessage.setAttribute("multimediaMessageType", imObj.multimediaMessageType);
        emMessage.setAttribute("multimediaState", imObj.multimediaState);
        emMessage.setAttribute("multimediaType", imObj.multimediaType);
        emMessage.setAttribute("roomNum", imObj.roomNum);

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatID);
                            if (conversation != null) {
                                conversation.removeMessage(emMessage.getMsgId());
                                LKLogUtil.e("=======成功后删除======" + conversation.getAllMsgCount());
                            }
                        }

                    }
                }, 100);
            }

            @Override
            public void onError(int i, String s) {
                LKLogUtil.e("=====发送100音视频消息失败=====");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        EMClient.getInstance().chatManager().sendMessage(emMessage);//发送消息
    }


    /**
     * 发送文本消息--100音视频开启（单人）
     * 加人的时候ios发送的是单人（循环发送）
     */
    public void sendTextJoinOther(final String chatID, IMObj imObj) {
        if (EMClient.getInstance() == null) {
            return;
        }
        final EMMessage emMessage = EMMessage.createTxtSendMessage("开启音视频", chatID);
        emMessage.setAttribute("callState", imObj.callState);
        emMessage.setAttribute("creatorMobile", imObj.creatorMobile);
        emMessage.setAttribute("creatorName", imObj.creatorName);
        emMessage.setAttribute("creatorUID", imObj.creatorUID);
        emMessage.setAttribute("creatorUserID", imObj.creatorUserID);
        emMessage.setAttribute("fromAvatar", imObj.fromAvatar);
        emMessage.setAttribute("fromMobile", imObj.fromMobile);
        emMessage.setAttribute("fromNick", imObj.fromNick);
        emMessage.setAttribute("fromUID", imObj.fromUID);
        emMessage.setAttribute("groupAvatar", imObj.groupAvatar);
        emMessage.setAttribute("groupID", imObj.groupID);
        emMessage.setAttribute("groupNick", imObj.groupNick);
        ArrayList<GroupMenberListdata> groupMenberList
                = LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);
        JSONArray jsonArray = new JSONArray();
        if (groupMenberList != null) {
            for (int i = 0; i < groupMenberList.size(); i++) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UID", groupMenberList.get(i).UID);
                    jsonObject.put("mobile", groupMenberList.get(i).mobile);
                    jsonObject.put("userID", groupMenberList.get(i).userID);
                    jsonObject.put("avatar", groupMenberList.get(i).avatar);
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        emMessage.setAttribute("members", jsonArray);
        emMessage.setAttribute("messageType", imObj.messageType);
        emMessage.setAttribute("multimediaMessageType", imObj.multimediaMessageType);
        emMessage.setAttribute("multimediaState", imObj.multimediaState);
        emMessage.setAttribute("multimediaType", imObj.multimediaType);
        emMessage.setAttribute("roomNum", imObj.roomNum);
        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatID);
                            if (conversation != null) {
                                conversation.removeMessage(emMessage.getMsgId());
                            }
                            LKLogUtil.e("=======成功后删除======" + conversation.getAllMsgCount());
                        }
                    }
                }, 100);
            }

            @Override
            public void onError(int i, String s) {
                LKLogUtil.e("=====发送100音视频消息失败=====");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        EMClient.getInstance().chatManager().sendMessage(emMessage);//发送消息
    }


    /**
     * 发送文本消息--100音视频开启（群组）
     */
    public void sendTextGroupVideoOrVoice(final String groupId, IMObj imObj) {
        if (EMClient.getInstance() == null) {
            return;
        }
        EMMessage.ChatType chatType = EMMessage.ChatType.GroupChat;
        final EMMessage emMessage = EMMessage.createTxtSendMessage("开启音视频", groupId);
        emMessage.setChatType(chatType);
        emMessage.setAttribute("callState", imObj.callState);
        emMessage.setAttribute("creatorMobile", imObj.creatorMobile);
        emMessage.setAttribute("creatorName", imObj.creatorName);
        emMessage.setAttribute("creatorUID", imObj.creatorUID);
        emMessage.setAttribute("creatorUserID", imObj.creatorUserID);
        emMessage.setAttribute("fromAvatar", imObj.fromAvatar);
        emMessage.setAttribute("fromMobile", imObj.fromMobile);
        emMessage.setAttribute("fromNick", imObj.fromNick);
        emMessage.setAttribute("fromUID", imObj.fromUID);
        emMessage.setAttribute("groupAvatar", imObj.groupAvatar);
        emMessage.setAttribute("groupID", imObj.groupID);
        emMessage.setAttribute("groupNick", imObj.groupNick);
        ArrayList<GroupMenberListdata> groupMenberList = LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);
        JSONArray jsonArray = new JSONArray();
        if (groupMenberList != null) {
            for (int i = 0; i < groupMenberList.size(); i++) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("UID", groupMenberList.get(i).UID);
                    jsonObject.put("mobile", groupMenberList.get(i).phone);
                    jsonObject.put("userID", groupMenberList.get(i).userId);
                    jsonObject.put("avatar", groupMenberList.get(i).smallImg);
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        emMessage.setAttribute("members", jsonArray);
        emMessage.setAttribute("messageType", imObj.messageType);
        emMessage.setAttribute("multimediaMessageType", imObj.multimediaMessageType);
        emMessage.setAttribute("multimediaState", imObj.multimediaState);
        emMessage.setAttribute("multimediaType", imObj.multimediaType);
        emMessage.setAttribute("roomNum", imObj.roomNum);
        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupId);
                            if (conversation != null) {
                                conversation.removeMessage(emMessage.getMsgId());
                            }
                            LKLogUtil.e("=======成功后删除======" + conversation.getAllMsgCount());
                        }

                    }
                }, 100);
            }

            @Override
            public void onError(int i, String s) {
                LKLogUtil.e("=====发送100音视频消息失败=====");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        EMClient.getInstance().chatManager().sendMessage(emMessage);//发送消息
    }

}
