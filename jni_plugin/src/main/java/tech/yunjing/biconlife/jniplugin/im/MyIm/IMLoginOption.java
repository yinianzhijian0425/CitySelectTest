package tech.yunjing.biconlife.jniplugin.im.MyIm;

import android.content.Intent;
import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import tech.yunjing.biconlife.jniplugin.im.key.LKBroadcastKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.key.LKPrefKeyList;
import tech.yunjing.biconlife.jniplugin.im.listener.IMLoginListener;
import tech.yunjing.biconlife.jniplugin.im.listener.IMLogoutListener;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * IM登录注册操作
 * Created by nanPengFei on 2016/10/21 11:15.
 */
public class IMLoginOption {
    private static IMLoginOption mInstance;

    private IMLoginOption() {
    }

    public static IMLoginOption getInstance() {
        if (null == mInstance) {
            synchronized (IMLoginOption.class) {
                if (null == mInstance) {
                    mInstance = new IMLoginOption();
                }
            }
        }
        return mInstance;
    }

    /**
     * 注册帐号，开放注册
     */
    public void regist(final String userId, final String passwork) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userId + "", passwork);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    LKLogUtil.e("==本地注册失败==" + e.toString());
                    LKLogUtil.e(LKApplication.getContext() + "测试提示(本地注册IM失败),不必理会,亲!");
                }
                login(userId, passwork);
            }
        }).start();
    }

    /**
     * 判断是否已经登录
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 登录聊天服务器
     */
    public void login(String userId, String passwork) {
        LKLogUtil.e("用户登录帐号====" + userId);
        if (!TextUtils.isEmpty(userId)) {
            if (EMClient.getInstance() == null) {
                return;
            }
            EMClient.getInstance().login(userId, passwork, IMLoginListener.getInstance());
        } else {
            LKLogUtil.e("==帐号为空==");
            LKPrefUtil.putInt(LKPrefKeyList.IM_LINK_STATUS, LKOthersFinalList.IM_LINK_FAIL);
            LKApplication.getContext().sendBroadcast(new Intent(LKBroadcastKeyList.BROADCAST_LINKSTATUS_CHANGE));
        }
    }

    /**
     * 登出聊天服务器
     * 如果集成了GCM、小米推送、华为推送，方法里第一个参数需要设为true，这样退出的时候会解绑设备token，否则可能会出现退出了，还能收到消息的现象。
     * 有时候可能会碰到网络问题而解绑失败，app处理时可以弹出提示框让用户选择，是否继续退出(弹出框提示继续退出能收到消息的风险)，如果用户选择继续退出，传false再调用logout方法退出成功；
     * 当然也可以失败后还是返回退出成功，然后在后台起个线程不断调用logout方法直到成功，这样有个风险是：用户kill了app，然后网络恢复，用户还是会继续收到消息。
     * <p/>
     * 还有一个需要注意的是：如果调用异步退出方法，在收到onsucess的回调后才去调用IM相关的方法，比如login
     */
    public void loginOut(boolean isUnbundling) {
        if (EMClient.getInstance() == null) {
            return;
        }
        EMClient.getInstance().logout(isUnbundling, IMLogoutListener.getInstance());
    }
}
