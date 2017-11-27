package tech.yunjing.biconlife.jniplugin.im.MyIm;

import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

/**
 * 聊天记录操作
 * Created by lh on 2017/8/12.
 */

public class ChatRecordOption {
    private static ChatRecordOption mInstance;

    private ChatRecordOption() {
    }

    public static ChatRecordOption getInstance() {
        if (null == mInstance) {
            synchronized (ChatRecordOption.class) {
                if (null == mInstance) {
                    mInstance = new ChatRecordOption();
                }
            }
        }
        return mInstance;
    }

    /**
     * 更新数据库中消息
     *
     * @param chatTag
     * @param emMessage
     * @return
     */
    public boolean updateMsgFromDB(String chatTag, EMMessage emMessage) {
        boolean isUpdateSuccess = false;
        if (!TextUtils.isEmpty(chatTag)) {
            try {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatTag);
                if (null != conversation) {
                    isUpdateSuccess = conversation.updateMessage(emMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isUpdateSuccess;
    }
}
