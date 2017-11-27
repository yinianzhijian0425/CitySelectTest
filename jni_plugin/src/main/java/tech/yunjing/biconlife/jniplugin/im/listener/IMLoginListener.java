package tech.yunjing.biconlife.jniplugin.im.listener;

import android.content.Intent;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;

import tech.yunjing.biconlife.jniplugin.im.key.LKBroadcastKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.key.LKPrefKeyList;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 登录聊天服务器状态监听
 * Created by nanPengFei on 2016/10/14 15:21.
 */
public class IMLoginListener implements EMCallBack {
    private static IMLoginListener mInstance;

    private IMLoginListener() {
    }

    public static IMLoginListener getInstance() {
        if (null == mInstance) {
            synchronized (IMLoginListener.class) {
                if (null == mInstance) {
                    mInstance = new IMLoginListener();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onSuccess() {
        if (EMClient.getInstance() == null) {
            return;
        }
        //此方法传入一个字符串String类型的参数，返回成功或失败的一个Boolean类型的返回值
//        boolean aDouble = EMClient.getInstance().updateCurrentUserNick(LKPrefUtil.getString(LKPrefKeyList.USER_NICK, "U赚"));//失败昵称
        LKLogUtil.e("=========IM登录成功===========");
        loadMsgToMemory();
        LKPrefUtil.putInt(LKPrefKeyList.IM_LINK_STATUS, LKOthersFinalList.IM_LINK_SUCCESS);
        LKApplication.getContext().sendBroadcast(new Intent(LKBroadcastKeyList.BROADCAST_LINKSTATUS_CHANGE));//发送登录成功广播
    }

    @Override
    public void onProgress(int progress, String status) {
        LKLogUtil.e(progress + "==IM登录中==" + status);
    }

    @Override
    public void onError(int code, String message) {
        LKLogUtil.e(code + "==IM登录失败==" + message);
        if (EMError.USER_NOT_FOUND == code) {//=User dosn't exist
            LKApplication.getContext().sendBroadcast(new Intent(LKBroadcastKeyList.NOFINDUSER));
            LKLogUtil.e("==账号未注册到聊天服务器==");
//            IMLoginOption.getInstance().regist();
        } else if (EMError.SERVER_TIMEOUT == code) {
//            IMLoginOption.getInstance().login();
            LKLogUtil.e("==聊天服务器登录超时==");
        } else if (EMError.USER_AUTHENTICATION_FAILED == code) {
            LKLogUtil.e("==IM帐号或者密码错误==");
        } else {
//            LKToastUtil.showToastLong(LKApplication.getContext()+"网络异常");
        }
    }

    /**
     * 加载数据到缓存
     */
    public void loadMsgToMemory() {
        if (EMClient.getInstance() == null) {
            return;
        }
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
    }
}
