package tech.yunjing.biconlife.jniplugin.im.MyIm;

import android.content.Intent;
import android.text.TextUtils;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.db.DeleteFriendInfo;
import tech.yunjing.biconlife.jniplugin.db.FriendPetName;
import tech.yunjing.biconlife.jniplugin.db.GeneralDb;
import tech.yunjing.biconlife.jniplugin.db.TransferInfo;
import tech.yunjing.biconlife.jniplugin.global.BCSharePreKey;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKBroadcastKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKIntentKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.HxTestKey;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * Created by lh on 2017/8/7.
 */

public class MyImMessageListener implements EMMessageListener {
    private static MyImMessageListener mInstance;

    /**
     * 收到消息时的房间号(两个房间号判断是否是一样的从而判断是否是一方收不到消息后另一方挂断所用)
     */
    private ArrayList<String> noReceiver = new ArrayList<>();


    private MyImMessageListener() {
    }

    public static MyImMessageListener getInstance() {
        if (null == mInstance) {
            synchronized (MyImMessageListener.class) {
                if (null == mInstance) {
                    mInstance = new MyImMessageListener();
                }
            }
        }
        return mInstance;
    }

    /**
     * 收到消息监听
     *
     * @param messages
     */
    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        synchronized (MyImMessageListener.class) {
            EMMessage emMessage = null;
            for (int i = 0; i < messages.size(); i++) {  //收到消息
                emMessage = messages.get(i);
                String messageType = emMessage.getStringAttribute("messageType", "");
                String imTag = "";
                if ((LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(messageType))) {//100音视频开启
                    int multimediaType = emMessage.getIntAttribute("multimediaType", 0);
                    imTag = emMessage.getStringAttribute("groupID", "");

                    int multimediaMessageType = emMessage.getIntAttribute("multimediaMessageType", 0);
                    if (0 == multimediaType || 1 == multimediaType) {
                        imTag = emMessage.getStringAttribute("creatorUserID", "");
                    } else if (2 == multimediaType || 3 == multimediaType) {
                        if (1 == multimediaMessageType || 3 == multimediaMessageType) {
                            //群聊加入新的人员,单个邀请
                            imTag = emMessage.getStringAttribute("creatorUserID", "");
                        }
                    }

                    openVodicOrVideo(emMessage, imTag);//开启音视频

                    String nick = emMessage.getStringAttribute("creatorName", "");
                    String groupAvatar = emMessage.getStringAttribute("groupAvatar", "");
                    String groupNick = emMessage.getStringAttribute("groupNick", "");

                    EMConversation conversation = EMClient.getInstance().chatManager().getConversation(imTag);
                    if (conversation != null) {
                        conversation.removeMessage(emMessage.getMsgId());
                    }

                    if (2 == multimediaType || 3 == multimediaType) {
                        if (1 == multimediaMessageType || 3 == multimediaMessageType) {
                            continue;
                        }
                        emMessage = EMMessage.createTxtSendMessage(nick + "邀请你加入语音聊天", imTag);
                        emMessage.setChatType(EMMessage.ChatType.GroupChat);
                        emMessage.setAttribute("messageType", LKOthersFinalList.MSGTYPE_NOTIFY);//消息类型为通知消息
                        emMessage.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_GROUP);//消息类型(0,单聊,1群聊)
                        emMessage.setAttribute("groupId", imTag);//群聊id
                        emMessage.setAttribute("groupAvatar", groupAvatar);//群头像
                        emMessage.setAttribute("groupNick", groupNick);//群昵称
                        //将本条消息插入到会话中
                        conversation.insertMessage(emMessage);
                    } else {
                        continue;
                    }
                } else {//普通消息
                    if (LKOthersFinalList.MSGTYPE_CARD.equals(messageType)) {//转账
                        String transferId = emMessage.getStringAttribute("transferId", "");
                        TransferInfo transferInfo = new TransferInfo();
                        transferInfo.setTransferId(transferId);
                        transferInfo.setTransferState("0");
                        GeneralDb.insert(transferInfo);
                    }
                    String conversationType = emMessage.getStringAttribute("conversationType", "0");
                    if ("1".equals(conversationType)) {
                        imTag = emMessage.getTo();
                    } else {
                        imTag = emMessage.getUserName();
                    }
                    LKLogUtil.e("=====聊天对象=====" + imTag);
                    Intent reminderIntent = new Intent("REMINDER_NOTIFICATION");
                    reminderIntent.putExtra("message", emMessage);
                    LKApplication.getContext().sendBroadcast(reminderIntent);//收到新消息提示音和通知栏
                }
                if (emMessage == null) {
                    continue;
                }
                LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.UNREAD_MESSAGE_MEMBER));//收到新消息  发送广播刷新首页消息未读条数
                LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//收到新消息  发送广播刷新消息列表
                Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + imTag);
                intent.putExtra(LKIntentKeyList.MSG_HASNEW, emMessage);
                LKApplication.getContext().sendBroadcast(intent);
            }
        }
    }

    /**
     * 开启音视频
     */
    private void openVodicOrVideo(EMMessage emMessage, String imTag) {
        try {
            IMObj imObj = MyImSendOption.getInstance().getVoiceIMObj(emMessage);
            LKLogUtil.e("result==" + "收到的消息----" + imObj.toString());
            if (imObj == null || TextUtils.isEmpty(imObj.roomNum)) {
                return;
            }
            boolean isStart = true;
            /**未收到消息时对方挂断，下次计入是否重新启动*/
            if (imObj.multimediaType == 0 || imObj.multimediaType == 1 || imObj.multimediaType == 2) {
                for (int j = 0; j < noReceiver.size(); j++) {
                    if (imObj.roomNum.equals(noReceiver.get(j))) {
                        noReceiver.remove(imObj.roomNum);
                        isStart = false;
                    }
                }
            }


            if (LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType) &&
                    VoIPHelper.serviceIsRunning(LKApplication.getContext()) &&
                    SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice == imObj.multimediaType
                    && SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeMemberJoined == imObj.multimediaMessageType) {
                //会议已经开启了，但是又邀请了其他人，此时接收者的数据不会发生改变，特此发送广播将数据发送过去
                Intent intent = new Intent(HxTestKey.BROADCAST_MEMBERS_JOIN_NEW);
                intent.putExtra(HxTestKey.JOIN_MEMBERS_IMOBJ, imObj);
                LKApplication.getContext().sendBroadcast(intent);
            }


            if (imObj != null && LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType)
                    && !VoIPHelper.serviceIsRunning(LKApplication.getContext()) && isStart) {
                Intent intent = new Intent(HxTestKey.BROADCASE_RECEIVER_VOIP);
                intent.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);
                intent.putExtra(VoIPHelper.RECEIVER_ID, imTag);
                noReceiver.clear();
                LKApplication.getContext().sendBroadcast(intent);
            }
        } catch (Exception e) {
            LKLogUtil.e("result==" + "出现异常Lee -----------");
        }
    }


//    private static ArrayList<String> mBeInvitedIds = new ArrayList<>();

    /**
     * 收到透传消息监听
     *
     * @param list
     */
    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                EMMessage emMessage = list.get(i);
                EMCmdMessageBody body = (EMCmdMessageBody) emMessage.getBody();
                String action = body.action();
                LKLogUtil.e("result==" + "收到透传消息==" + action);
                if (!TextUtils.isEmpty(action)) {
                    try {
                        IMObj imObj = LKJsonUtil.parseJsonToBean(action, IMObj.class);
                        if (imObj != null && LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType)) {
                            //单人音视频messageType为100
                            if (SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange == imObj.multimediaMessageType
                                    && SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice == imObj.multimediaType) {
                                if (SocialChatCallStat.BKL_SocialChatMultimediaStateReceived == imObj.multimediaState) {//单人音频呼入接听
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_RECEIVER));
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateRejected == imObj.multimediaState) {//单人音频呼入拒绝
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_REJECT));
                                    if (!TextUtils.isEmpty(imObj.roomNum)) {
                                        noReceiver.add(imObj.roomNum);
                                    }
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp == imObj.multimediaState) {//创建者创建后立即挂断电话
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_HAND_UP));
                                    if (!TextUtils.isEmpty(imObj.roomNum)) {
                                        noReceiver.add(imObj.roomNum);
                                    }
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateEnd == imObj.multimediaState) {//接通后挂断
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_HAND_UP));
                                    if (!TextUtils.isEmpty(imObj.roomNum)) {
                                        noReceiver.add(imObj.roomNum);
                                    }
                                }
                            } else if (SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange == imObj.multimediaMessageType
                                    && SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo == imObj.multimediaType) {
                                if (SocialChatCallStat.BKL_SocialChatMultimediaStateReceived == imObj.multimediaState) {//单人视频频呼入接听
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_RECEIVER));
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateRejected == imObj.multimediaState) {//单人视频呼入拒绝
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_REJECT));
                                    if (!TextUtils.isEmpty(imObj.roomNum)) {
                                        noReceiver.add(imObj.roomNum);
                                    }
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp == imObj.multimediaState) {//单人视频呼入挂断
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_HAND_UP));
                                    if (!TextUtils.isEmpty(imObj.roomNum)) {
                                        noReceiver.add(imObj.roomNum);
                                    }
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateEnd == imObj.multimediaState) {//接通后挂断
                                    LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.BROADCAST_SINGLE_HAND_UP));
                                    if (!TextUtils.isEmpty(imObj.roomNum)) {
                                        noReceiver.add(imObj.roomNum);
                                    }
                                }
                            } else if (SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange == imObj.multimediaMessageType
                                    && SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice == imObj.multimediaType) {//多人音视频呼入
                                Intent intent = new Intent();
                                if (SocialChatCallStat.BKL_SocialChatMultimediaStateReceived == imObj.multimediaState) {//多人接听
                                    intent.putExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE, imObj);
                                    intent.setAction(HxTestKey.BROADCAST_SINGLE_RECEIVER);
                                    LKApplication.getContext().sendBroadcast(intent);
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateRejected == imObj.multimediaState
                                        || SocialChatCallStat.BKL_SocialChatMultimediaStateNotResponding == imObj.multimediaState) {//多人拒绝或者是超时未接听
                                    intent.putExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE, imObj);
                                    intent.setAction(HxTestKey.BROADCAST_SINGLE_REJECT);
                                    LKApplication.getContext().sendBroadcast(intent);
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp == imObj.multimediaState) {//多人挂断
                                    intent.putExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE, imObj);
                                    intent.setAction(HxTestKey.BROADCAST_SINGLE_HAND_UP);
                                    LKApplication.getContext().sendBroadcast(intent);
                                } else if (SocialChatCallStat.BKL_SocialChatMultimediaStateEnd == imObj.multimediaState) {//创建者取消
                                    intent.putExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE, imObj);
                                    intent.setAction(HxTestKey.BROADCAST_CREATED_CANCEL);
                                    LKApplication.getContext().sendBroadcast(intent);
                                }
                            }
                        } else if (imObj != null && LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101.equals(imObj.messageType)) {
                            //收到房间销毁的透传消息，添加销毁的房间号，避免再次接到消息后加入已经销毁的房间
                            LKLogUtil.e("result==" + "添加销毁的房间号" + imObj.roomNum);

                            if (!TextUtils.isEmpty(imObj.roomNum)) {
                                noReceiver.add(imObj.roomNum);
                            }
                            if (0 == imObj.multimediaType || 1 == imObj.multimediaType) {//单人音视频
                                String to = emMessage.getTo();
                                String from = emMessage.getFrom();
                                switch (imObj.callState) {
                                    //对方拒绝
                                    case SocialChatCallStat.BKL_SocialChatMultimediaCallStateReject:
                                        insertTextMessage(to, imObj, "对方已拒绝", from);
                                        break;
                                    //对方挂断(代表已经进行过通话，有时间)
                                    case SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough:
                                        String recordTime = getRecordTime(Integer.parseInt(imObj.duration));
                                        insertTextMessage(to, imObj, "通话时长 " + recordTime, from);
                                        break;
                                    //发起方取消（对方还未接听）
                                    case SocialChatCallStat.BKL_SocialChatMultimediaCallStateCancel:
                                        insertTextMessage(to, imObj, "对方已取消", from);
                                        break;
                                    default:
                                        break;
                                }
                            } else if (2 == imObj.multimediaType) {//多人音视频
                                EMMessage message = EMMessage.createTxtSendMessage("语音聊天已经结束", imObj.groupID);
                                message.setChatType(EMMessage.ChatType.GroupChat);
                                message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_NOTIFY);//消息类型为通知消息
                                message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_GROUP);//消息类型(0,单聊,1群聊)
                                message.setAttribute("groupId", imObj.groupID);//群聊id
                                message.setAttribute("groupAvatar", imObj.groupAvatar);//群头像
                                message.setAttribute("groupNick", imObj.groupNick);//群昵称
                                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(imObj.groupID);
                                //将本条消息插入到会话中
                                if (conversation != null) {
                                    conversation.insertMessage(message);
                                }
                                LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
                                Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + imObj.groupID);
                                intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
                                LKApplication.getContext().sendBroadcast(intent);
                            }
                        } else if (imObj != null && LKOthersFinalList.CHANGE_GROUP_NICK.equals(imObj.messageType)) {//收到修改群昵称透传消息
                            String groupID = imObj.groupID;
                            //收到消息插入一条通知消息
                            String userNick = imObj.userNick;
                            String userId = emMessage.getFrom();
                            List<FriendPetName> petUserId = GeneralDb.getQueryByWhere(FriendPetName.class, "petUserId", new String[]{userId});
                            if (petUserId != null && petUserId.size() > 0) {
                                userNick = petUserId.get(0).getPetUserName();
                            }
                            EMMessage message = EMMessage.createTxtSendMessage(userNick + "修改群昵称为" + imObj.groupName, groupID);
                            message.setChatType(EMMessage.ChatType.GroupChat);
                            message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_NOTIFY);//消息类型为通知消息
                            message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_GROUP);//消息类型(0,单聊,1群聊)
                            message.setAttribute("groupId", groupID);//群聊id
                            message.setAttribute("groupAvatar", imObj.groupAvatar);//群头像
                            message.setAttribute("groupNick", imObj.groupName);//群昵称
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupID);
                            //将本条消息插入到会话中
                            if (conversation != null) {
                                conversation.insertMessage(message);
                            }
                            LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
                            Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + groupID);
                            intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
                            LKApplication.getContext().sendBroadcast(intent);
                        } else if (imObj != null && LKOthersFinalList.CHANGE_TRANSFER.equals(imObj.messageType)) {//收到确认转账的透传消息
                            List<TransferInfo> transferId = GeneralDb.getQueryByWhere(TransferInfo.class, "TransferId", new String[]{imObj.orderNo});
                            TransferInfo transferInfo = new TransferInfo();
                            transferInfo.setTransferId(imObj.orderNo);
                            transferInfo.setTransferState("1");
                            if (transferId != null && transferId.size() > 0) {
                                transferInfo.setDbID(transferId.get(0).getDbID());
                                GeneralDb.update(transferInfo);
                            } else {
                                GeneralDb.insert(transferInfo);
                            }
                            LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
                        } else if (imObj != null && LKOthersFinalList.CHANGE_DELETE_FRIEND.equals(imObj.messageType)) {//收到被好友删除的透传
                            LKApplication.getContext().sendBroadcast(new Intent("BE_DELETE"));//发送广播刷新好友列表
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(imObj.userId);
                            if (conversation != null) {
                                EMMessage message = EMMessage.createTxtSendMessage("该好友已与你解除好友关系", imObj.userId);
                                message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_NOTIFY);//消息类型为通知类型
                                message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_SINGLE);//消息类型(0,单聊,1群聊)
                                message.setAttribute("chatId", imObj.userId);
                                message.setAttribute("chatAvatar", imObj.avatar);
                                message.setAttribute("chatNick", imObj.nickName);
                                conversation.insertMessage(message);

                                //将这个好友id保存数据库
                                DeleteFriendInfo deleteFriendInfo = new DeleteFriendInfo();
                                deleteFriendInfo.setUserId(imObj.userId);
                                GeneralDb.insert(deleteFriendInfo);

                                LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
                                Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + imObj.userId);
                                intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
                                LKApplication.getContext().sendBroadcast(intent);
                            }
                        } else if (imObj != null && LKOthersFinalList.CHANGE_CREATE_GROUP.equals(imObj.messageType)) {//收到创建群聊和添加群成员的透传消息
//                            if (i == 0) {
//                                mBeInvitedIds.add(imObj.beInvitedIds);
                            Intent intent = new Intent("ADD_GROUP_CHAT");
                            intent.putExtra("imObj", imObj);
                            LKApplication.getContext().sendBroadcast(intent);//收到消息发送广播
                            LKApplication.getContext().sendBroadcast(new Intent("GROUP_REFRESH"));//发送广播刷新群组信息
//                            } else if (!mBeInvitedIds.contains(imObj.beInvitedIds)) {
//                                mBeInvitedIds.add(imObj.beInvitedIds);
//                                Intent intent = new Intent("ADD_GROUP_CHAT");
//                                intent.putExtra("imObj", imObj);
//                                LKApplication.getContext().sendBroadcast(intent);//收到消息发送广播
//                                LKApplication.getContext().sendBroadcast(new Intent("GROUP_REFRESH"));//发送广播刷新群组信息
//                            }
//                            if (i == list.size() - 1) {
//                                mBeInvitedIds.clear();
//                            }
                        } else if (imObj != null && LKOthersFinalList.CHANGE_FRIEND_REMIND.equals(imObj.messageType)) {//收到新的好友请求
                            int friendNum = LKPrefUtil.getInt(BCSharePreKey.FRIEND_SUM + UserInfoManageUtil.getUserId(), 0);
                            LKPrefUtil.putInt(BCSharePreKey.FRIEND_SUM + UserInfoManageUtil.getUserId(), friendNum + 1);
                            LKApplication.getContext().sendBroadcast(new Intent("FRIEND_REQUEST"));//发送广播
                            LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.UNREAD_MESSAGE_MEMBER));//收到新消息  发送广播刷新首页消息未读条数
                        } else if (imObj != null && LKOthersFinalList.CHANGE_AGREE_FRIEND.equals(imObj.messageType)) {//收到同意好友请求
                            String chatID = imObj.userId;
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatID, EMConversation.EMConversationType.Chat, true);
                            EMMessage message = EMMessage.createTxtSendMessage("我通过了你的好友验证请求，现在我们可以开始聊天了", chatID);
                            message.setDirection(EMMessage.Direct.RECEIVE);
                            message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_TXT);//消息类型为通知消息
                            message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_SINGLE);//消息类型(0,单聊,1群聊)
                            message.setAttribute("userID", chatID);//id
                            message.setAttribute("avatar", imObj.avatar);//头像
                            message.setAttribute("nick", imObj.nickName);//昵称
                            message.setAttribute("chatAvatar", UserInfoManageUtil.getUserInfo().getLargeImg());
                            message.setAttribute("chatNick", UserInfoManageUtil.getUserInfo().getNickName());
                            if (conversation != null) {
                                conversation.insertMessage(message);
                            }
                            //如果该好友以前被删除过，从本地数据表中将其移除
                            if (!TextUtils.isEmpty(chatID)) {
                                List<DeleteFriendInfo> infos = GeneralDb.getQueryByWhere(DeleteFriendInfo.class, "UserId", new String[]{chatID});
                                if (infos != null && infos.size() > 0) {
                                    DeleteFriendInfo deleteFriendInfo = infos.get(0);
                                    GeneralDb.delete(deleteFriendInfo);
                                }
                            }
                            LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
                            Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + chatID);
                            intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
                            LKApplication.getContext().sendBroadcast(intent);
                            LKApplication.getContext().sendBroadcast(new Intent("AGREE_REQUEST"));//发送广播
                        } else if (imObj != null && LKOthersFinalList.FRIEND_UNREAD_MESSAGE.equals(imObj.messageType)) {//收到朋友圈有未读消息的透传消息
                            int friendRingNum = LKPrefUtil.getInt(BCSharePreKey.FRIEND_RING_UNMESSAGE + UserInfoManageUtil.getUserId(), 0);
                            LKPrefUtil.putInt(BCSharePreKey.FRIEND_RING_UNMESSAGE + UserInfoManageUtil.getUserId(), friendRingNum + 1);
                            LKApplication.getContext().sendBroadcast(new Intent("FRIEND_RING_UNMESSAGE"));//发送广播
                            LKApplication.getContext().sendBroadcast(new Intent(HxTestKey.UNREAD_MESSAGE_MEMBER));//收到新消息  发送广播刷新首页消息未读条数
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 插入消息到数据库
     */
    private void insertTextMessage(String to, IMObj imObj, String content, String from) {
        String smallImg = UserInfoManageUtil.getUserInfo().getLargeImg();
        String nickName = UserInfoManageUtil.getUserInfo().getNickName();
        EMMessage message = EMMessage.createTxtSendMessage(content, to);
        message.setFrom(from);
        message.setDirection(EMMessage.Direct.RECEIVE);
        message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_TEXT_VOICEANDVIDEO);//消息类型为音视频消息
        message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_SINGLE);//消息类型(0,单聊,1群聊)
        message.setAttribute("userID", from);//用户id
        message.setAttribute("avatar", imObj.avatar);//用户头像
        message.setAttribute("nick", imObj.nick);//用户昵称
        message.setAttribute("chatAvatar", smallImg);
        message.setAttribute("chatNick", nickName);
        message.setAttribute("multimediaType", imObj.multimediaType);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(from);
        if (conversation != null) {
            conversation.insertMessage(message);
        }
        LKApplication.getContext().sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
        Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + from);
        intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
        LKApplication.getContext().sendBroadcast(intent);
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }


    /**
     * 时间格式 mm:ss
     */
    private String getRecordTime(int time) {
        Date dates = new Date(time * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(dates);
    }
}
