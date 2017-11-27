package tech.yunjing.biconlife.jniplugin.im.listener;

import com.hyphenate.EMCallBack;

import cn.jpush.android.api.JPushInterface;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 登出监听
 * Created by nanPengFei on 2016/10/14 15:24.
 */
public class IMLogoutListener implements EMCallBack {
    private static IMLogoutListener mInstance;

    private IMLogoutListener() {
    }

    public static IMLogoutListener getInstance() {
        if (null == mInstance) {
            synchronized (IMLogoutListener.class) {
                if (null == mInstance) {
                    mInstance = new IMLogoutListener();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onSuccess() {
        LKLogUtil.e("==退出成功==");
        JPushInterface.stopPush(LKApplication.getContext());
//        LKApplication.getInstance().exitToLogin();//退出登录
//        boolean isRepetitionLogin = LKPrefUtils.getBoolean(LKPrefKeyList.LOGIN_REPETITION, false);
//        if (isRepetitionLogin) {//如果是重复登录的话，退出后再登录
//            LKPrefUtils.clearSP(LKPrefKeyList.LOGIN_REPETITION);
//            IMLoginOption.getInstance().login();
//        }
    }

    @Override
    public void onProgress(int progress, String status) {
        LKLogUtil.e(progress + "==退出进度==" + status);
    }

    @Override
    public void onError(int code, String message) {
        LKLogUtil.e(code + "==退出错误==" + message);
        JPushInterface.stopPush(LKApplication.getContext());
//        LKApplication.getInstance().exitToLogin();//退出登录
    }
}
