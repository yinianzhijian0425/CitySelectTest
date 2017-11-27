package tech.yunjing.biconlife.jniplugin.im.option;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import tech.yunjing.biconlife.jniplugin.im.bean.ImSetObj;


/**
 * 最近聊天记录列表操作
 * Created by nanPengFei on 2016/10/20 10:51.
 */
public class LastMsgRecordOption {
    private static LastMsgRecordOption mInstance;

    private LastMsgRecordOption() {
    }

    public static LastMsgRecordOption getInstance() {
        if (null == mInstance) {
            synchronized (LastMsgRecordOption.class) {
                if (null == mInstance) {
                    mInstance = new LastMsgRecordOption();
                }
            }
        }
        return mInstance;
    }


    /**
     * 获取消息列表
     */
    public ArrayList<EMMessage> getLastMsgList() {
        ArrayList<EMMessage> emMessages = new ArrayList<>();
        emMessages.clear();
        try {
            EMChatManager emChatManager = EMClient.getInstance().chatManager();
            Map<String, EMConversation> allConversations = emChatManager.getAllConversations();
            if (null != allConversations) {
                for (Map.Entry<String, EMConversation> entry : allConversations.entrySet()) {
                    String imTag = entry.getKey();
                    EMConversation value = entry.getValue();
                    EMMessage lastMessage = value.getLastMessage();
//                LKLogUtils.e("==会话列表数据ID==" + imTag);
                    if (null != lastMessage) {
                        EMConversation conversation = emChatManager.getConversation(imTag);
                        if (null != conversation) {
                            int unreadMsgCount = conversation.getUnreadMsgCount();
                            lastMessage.setAttribute("unreadMsgCount", unreadMsgCount);
                            lastMessage.setAttribute("imTag", imTag);
                            ImSetObj imSetObj = getConversationF(conversation);
                            if (imSetObj.stick) {
                                lastMessage.setAttribute("isUp", 1);
                            } else {
                                lastMessage.setAttribute("isUp", 0);
                            }
                            if (imSetObj.noDisturb) {
                                lastMessage.setAttribute("isSilence", 1);
                            } else {
                                lastMessage.setAttribute("isSilence", 0);
                            }
                        }
                        emMessages.add(lastMessage);
                    }
                }
            }
            emMessagesSort(emMessages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emMessages;
    }

    /**
     * 获取会话扩展字段，没有则初始化
     *
     * @param conversation
     */
    private ImSetObj getConversationF(EMConversation conversation) {
        String extField = conversation.getExtField();
        if (TextUtils.isEmpty(extField)) {
            extField = "{\"stick\":false,\"noDisturb\":false}";
        }
        Gson gson = new Gson();
        ImSetObj imSetObj = gson.fromJson(extField, ImSetObj.class);
        return imSetObj;
    }

    /**
     * 会话列表排序
     *
     * @param emMessages
     */
    public void emMessagesSort(ArrayList<EMMessage> emMessages) {
        Collections.sort(emMessages, new Comparator<EMMessage>() {
            @Override
            public int compare(EMMessage lhs, EMMessage rhs) {
                int isUp = 0;
                try {
                    int anotherIsUp = rhs.getIntAttribute("isUp");
                    int thisIsUp = lhs.getIntAttribute("isUp");
                    if (anotherIsUp != thisIsUp) {
                        isUp = anotherIsUp - thisIsUp;
                    } else {
                        long gap = rhs.getMsgTime() - lhs.getMsgTime();
                        if (gap > 0) {
                            isUp = 1;
                        } else if (gap < 0) {
                            isUp = -1;
                        } else {
                            isUp = 0;
                        }
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                return isUp;
            }
        });
    }

    /**
     * 删除和某个user会话，如果需要保留聊天记录，传false
     *
     * @param tag
     * @return
     */
    public boolean delete(String tag, boolean isClearLog) {
        boolean deleteConversation = false;
        if (TextUtils.isEmpty(tag)) {
            deleteConversation = false;
        } else {
            try {
                deleteConversation = EMClient.getInstance().chatManager().deleteConversation(tag, isClearLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deleteConversation;
    }

    /**
     * 获取所有消息未读数量
     *
     * @return
     */
    public int getAllUnreadMsgCount() {
        int unreadMsgsCount = 0;
        try {
            unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unreadMsgsCount;
    }

}
