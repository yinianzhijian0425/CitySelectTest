package tech.yunjing.biconlife.libbaselib.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import net.wequick.small.Small;

import cn.jpush.android.api.JPushInterface;
import tech.yunjing.biconlife.jniplugin.base.AppBaseActivity;
import tech.yunjing.biconlife.jniplugin.bean.personal.UserInfoObj;
import tech.yunjing.biconlife.jniplugin.bean.response.BaseResponseObj;
import tech.yunjing.biconlife.jniplugin.global.BCSharePreKey;
import tech.yunjing.biconlife.jniplugin.global.JNIBroadCastKey;
import tech.yunjing.biconlife.jniplugin.im.MyIm.IMLoginOption;
import tech.yunjing.biconlife.jniplugin.im.voip.CheckVoiceServiceIsStartVoip;
import tech.yunjing.biconlife.jniplugin.im.voip.StartVidioAndVoiceService;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.global.BCBundleJsonKey;
import tech.yunjing.biconlife.libbaselib.util.ForeAndBackUtil;
import tech.yunjing.biconlife.libbaselib.view.BCSingleButtonDialog;
import tech.yunjing.biconlife.liblkclass.common.util.LKAppUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;

/**
 * 基类，处理数据交互，以及页面交互
 * <p/>
 * Created by CQ on 2016/3/2 12:02
 */
public abstract class BaseActivity extends AppBaseActivity {

    /**
     * 界面上下文
     */
    protected Context mContext;

    /**
     * 社交异地登陆提醒广播接收器对象
     */
    private IMDistanceLoginBroadcast imDistanceLoginBroadcast;

    @Override
    protected void initView() {
        BaseApplication.getInstance().addActivity(this);
        mContext = this;
        imDistanceLoginBroadcast = new IMDistanceLoginBroadcast();
//        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JNIBroadCastKey.IM_DISTANCE_LOGIN);
        registerReceiver(imDistanceLoginBroadcast, intentFilter);
    }


    @Override
    protected void initViewEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            JPushInterface.onResume(this);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (!ForeAndBackUtil.isActive) {
            ForeAndBackUtil.isActive = true;
            whetherYourPrivacyPassword();
        }

        if (!CheckVoiceServiceIsStartVoip.serviceIsRunningVoip(this)) {
            Intent intent = new Intent(this, StartVidioAndVoiceService.class);
            startService(intent);
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        /** 屏幕旋转监听*/
//        try {
//            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                LKLogUtil.e("当前为横屏");
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else {
//                LKLogUtil.e("当前为竖屏");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        // 隐藏键盘
        try {
            LKAppUtil.getInstance().closeInput(this, getWindow().getDecorView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!ForeAndBackUtil.isForeground(this)) {
            ForeAndBackUtil.isActive = false;
        }
        LKPrefUtil.putLong(BCSharePreKey.APP_TO_BACKGROUND_TIME_FLAG, System.currentTimeMillis());
    }

    /**
     * 判断隐私密码是否检测
     */
    private void whetherYourPrivacyPassword() {
        String privacy_password = LKPrefUtil.getString(BCSharePreKey.PRIVACY_PASSWORD, "");
        //判断是否设置了隐私密码
        if (privacy_password.length() == 4) {
            long currentTime = System.currentTimeMillis();
            long oldTime = -1;
            try {
                oldTime = LKPrefUtil.getLong(BCSharePreKey.APP_TO_BACKGROUND_TIME_FLAG, (long) 0);
            } catch (Exception e) {
                e.printStackTrace();
                oldTime = -1;
            }
            if (oldTime > 0) {
                if (currentTime - oldTime >= (5 * 60 * 1000)) {
                    Log.e("Activity", "超过5分钟需要启动屏保");
                    Small.openUri(BCBundleJsonKey.Mine_InputPasswordActivity, this);
                    LKPrefUtil.putLong(BCSharePreKey.APP_TO_BACKGROUND_TIME_FLAG,System.currentTimeMillis());
                } else {
                    Log.e("Activity", "未超过5分钟不用启动屏保");
                }
            } else {
                Log.e("Activity", "初始化进入");
                Small.openUri(BCBundleJsonKey.Mine_InputPasswordActivity, this);
                LKPrefUtil.putLong(BCSharePreKey.APP_TO_BACKGROUND_TIME_FLAG, System.currentTimeMillis());
            }
        } else {
            Log.e("Activity", "未设置隐私密码");
        }
    }

    @Override
    protected void getData(Message msg) {
        super.getData(msg);
        closeLoader();
        if (msg != null && msg.obj instanceof BaseResponseObj) {
            BaseResponseObj baseResponseObj;
            try {
                baseResponseObj = (BaseResponseObj) msg.obj;
            } catch (Exception e) {
                e.printStackTrace();
                baseResponseObj = null;
            }
            if (baseResponseObj == null) {
                return;
            }
            if (baseResponseObj.code == 200) {
                getData(baseResponseObj);
            } else if (baseResponseObj.code == 300) {
                UserInfoObj userInfoObj = new UserInfoObj();
                userInfoObj.setUserId("");
                UserInfoManageUtil.saveUserInfo(userInfoObj);
                showDistanceLoginDialog(baseResponseObj.code);
            } else {
                getRequestAbnormal(baseResponseObj.code);
                getRequestAbnormal(baseResponseObj, baseResponseObj.code);
                if (!TextUtils.isEmpty(baseResponseObj.message)) {
                    LKToastUtil.showToastShort(baseResponseObj.message);
                }
            }
        }
    }

    @Override
    protected void requestFail(Message msg) {
        super.requestFail(msg);
        getData(new BaseResponseObj());//此行设置为了解决网络请求失败时，下拉或上滑加载提示不消失问题。
        LKToastUtil.showToastShort(getResources().getString(R.string.string_network_error));
    }

    private void intentLogin(int code) {
        try {
            UserInfoObj userInfoObj = new UserInfoObj();
            userInfoObj.setUserId("");
            UserInfoManageUtil.saveUserInfo(userInfoObj);
            IMLoginOption.getInstance().loginOut(true);//退出环信服务器
            BaseApplication.getInstance().exitApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Intent intent = Small.getIntentOfUri(BCBundleJsonKey.Login_RegisterAndLoginActivity, BaseApplication.getContext());
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("RequestCode", code);
                startActivity(intent);
            }
            LKPrefUtil.putBoolean(BCSharePreKey.ISLOGIN, false);
            LKPrefUtil.putString(BCSharePreKey.TOKEN, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    /**
     * 接口请求成功回调
     */
    protected void getData(BaseResponseObj obj) {
    }

    /**
     * 接口请求异常回调
     *
     * @param code 异常代码
     */
    protected void getRequestAbnormal(int code) {
        getData(new BaseResponseObj());//此行设置为了解决网络请求异常时，下拉或上滑加载提示不消失问题。
    }

    /**
     * 接口请求异常回调
     *
     * @param code 异常代码
     */
    protected void getRequestAbnormal(BaseResponseObj obj, int code) {
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Class跳转界面并处理返回数据
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面并处理返回数据
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onDestroy() {
//        TopPopTipDialogUtil.getInstance().setWhetherShowing(false);
        try {
            /*释放资源，避免OOM*/
            setContentView(R.layout.activity_base_destruction_dedicated_null_layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
        if (imDistanceLoginBroadcast != null) {
            unregisterReceiver(imDistanceLoginBroadcast);
        }
    }

    private void showDistanceLoginDialog(final int code) {
        try {
            final BCSingleButtonDialog dialog = new BCSingleButtonDialog(this);
            dialog.setTitle("下线通知");
            dialog.setContent("你的账号在另一台设备登录，如非本人操作，则密码可能已泄露，请你修改密码。");
            dialog.setConfirmBtnText("重新登录");
            dialog.setEventInterface(new BCSingleButtonDialog.EventInterface() {
                @Override
                public void confirmOnClick() {
                    intentLogin(code);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 社交异地登陆提醒广播接收器
     */
    private class IMDistanceLoginBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            LKLogUtil.e(intent.getAction());
            if (intent.getAction().equals(JNIBroadCastKey.IM_DISTANCE_LOGIN)) {
                showDistanceLoginDialog(300);
            }
        }
    }
}
