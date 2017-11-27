package tech.yunjing.biconlife.jniplugin.im.listener;

import com.hyphenate.EMContactListener;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * 通讯好友变化监听
 * Created by nanPengFei on 2016/10/14 17:27.
 */
public class IMContactListener implements EMContactListener {
    private static IMContactListener mInstance;

    private IMContactListener() {
    }

    public static IMContactListener getInstance() {
        if (null == mInstance) {
            synchronized (IMContactListener.class) {
                if (null == mInstance) {
                    mInstance = new IMContactListener();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onContactAdded(String username) {
        // save contact
        LKLogUtil.e(username + "==================1111111111111111111111111111111");
    }

    @Override
    public void onContactDeleted(String username) {
        LKLogUtil.e(username + "==================2222222222222222222222222222222222222");
    }

    @Override
    public void onContactInvited(String username, String reason) {
        LKLogUtil.e(username + "================apply to be your friend,reason: ===============" + reason);
    }

    @Override
    public void onFriendRequestAccepted(String s) {
        LKLogUtil.e(s + "======================accept your request===================");
    }

    @Override
    public void onFriendRequestDeclined(String s) {
        LKLogUtil.e(s + "==================333333333333333333333333333333333333");
    }

}
