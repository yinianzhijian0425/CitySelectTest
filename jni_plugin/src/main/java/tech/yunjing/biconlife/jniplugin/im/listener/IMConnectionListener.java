package tech.yunjing.biconlife.jniplugin.im.listener;

import android.app.Activity;
import android.content.Intent;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;

import tech.yunjing.biconlife.jniplugin.global.JNIBroadCastKey;
import tech.yunjing.biconlife.jniplugin.im.key.LKBroadcastKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.key.LKPrefKeyList;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 连接状态监听
 * Created by nanPengFei on 2016/10/14 15:16.
 */
public class IMConnectionListener implements EMConnectionListener {
    private static IMConnectionListener mInstance;

    private IMConnectionListener() {
    }

    public static IMConnectionListener getInstance() {
        if (null == mInstance) {
            synchronized (IMConnectionListener.class) {
                if (null == mInstance) {
                    mInstance = new IMConnectionListener();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onConnected() {
        LKLogUtil.e("=====已经连接=====");
        LKPrefUtil.putInt(LKPrefKeyList.IM_LINK_STATUS, LKOthersFinalList.IM_LINK_SUCCESS);
        LKApplication.getContext().sendBroadcast(new Intent(LKBroadcastKeyList.BROADCAST_LINKSTATUS_CHANGE));
    }

    @Override
    public void onDisconnected(final int error) {
        try {
            if (error == EMError.USER_REMOVED) {
                LKLogUtil.e("=====帐号已经被移除=====");
            } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                LKLogUtil.e("=====帐号在其他设备登录=====");
                LKApplication.getContext().sendBroadcast(new Intent(JNIBroadCastKey.IM_DISTANCE_LOGIN));
            } else {
                if (NetUtils.hasNetwork(LKApplication.getContext())) {
                    LKLogUtil.e("=====连接不到聊天服务器=====");
                    LKPrefUtil.putInt(LKPrefKeyList.IM_LINK_STATUS, LKOthersFinalList.IM_LINK_FAIL);
                } else {
                    LKLogUtil.e("=====当前网络不可用=====");
                    LKPrefUtil.putInt(LKPrefKeyList.IM_LINK_STATUS, LKOthersFinalList.IM_LINK_FAIL);
                }
                LKApplication.getContext().sendBroadcast(new Intent(LKBroadcastKeyList.BROADCAST_LINKSTATUS_CHANGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Activity mActivity;

    /**
     * 设置main Activity
     *
     * @param activity
     */
    public void initActivity(Activity activity) {
        this.mActivity = activity;
    }
}