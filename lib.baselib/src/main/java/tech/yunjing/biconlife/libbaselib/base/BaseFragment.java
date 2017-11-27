package tech.yunjing.biconlife.libbaselib.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import net.wequick.small.Small;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.jniplugin.bean.personal.UserInfoObj;
import tech.yunjing.biconlife.jniplugin.bean.response.BaseResponseObj;
import tech.yunjing.biconlife.libbaselib.global.BCBundleJsonKey;
import tech.yunjing.biconlife.jniplugin.global.BCSharePreKey;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.fragment.LKBaseFragment;


/**
 * Created by nanPengFei on 2016/3/2 16:09.
 */
public abstract class BaseFragment extends LKBaseFragment {
    @Override
    protected void initView() {
        setTextView(Color.parseColor("#DD5725"), 12, "加载中...");
        setLoadView(Color.parseColor("#DD5725"), 30, "LKLoaderA");
        setLoadRootView(R.drawable.shape_loading, 100);
    }

    @Override
    protected void initViewEvent() {

    }

    @Override
    protected void getData(Message msg) {
        super.getData(msg);
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
            closeLoader();
            if (baseResponseObj.code == 200) {
                getData(baseResponseObj);
            } else if (baseResponseObj.code == 300) {
                UserInfoObj userInfoObj = new UserInfoObj();
                userInfoObj.setUserId("");
                UserInfoManageUtil.saveUserInfo(userInfoObj);
//                final int code = baseResponseObj.code;
//                final BCSingleButtonDialog dialog = BCSingleButtonDialog.getInstance(getActivity());
//                dialog.setTitle("下线通知");
//                dialog.setContent("你的账号在另一台设备登录，如非本人操作，则密码可能已泄露，请你修改密码。");
//                dialog.setConfirmBtnText("重新登录");
//                dialog.setEventInterface(new BCSingleButtonDialog.EventInterface(){
//                    @Override
//                    public void confirmOnClick() {
//                        intentLogin(code);
//                    }
//                });
//                if (isAdded()){
//                    dialog.show();
//                }

            } else {
                getRequestAbnormal(baseResponseObj.code);
                if (!TextUtils.isEmpty(baseResponseObj.message)) {
                    LKToastUtil.showToastShort(baseResponseObj.message);
                }
            }
        }
    }

    @Override
    protected void requestFail(Message msg) {
        super.requestFail(msg);
        if (isAdded()) {
            LKToastUtil.showToastShort(getResources().getString(R.string.string_network_error));
        }

    }

    private void intentLogin(int code) {
        UserInfoObj userInfoObj = new UserInfoObj();
        userInfoObj.setUserId("");
        UserInfoManageUtil.saveUserInfo(userInfoObj);
        BaseApplication.getInstance().exitApp();
        try {
            Intent intent = Small.getIntentOfUri(BCBundleJsonKey.Login_RegisterAndLoginActivity, getActivity());
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
        try {
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接口请求回调
     */
    protected void getData(BaseResponseObj obj) {
    }

    /**
     * 接口请求异常回调
     *
     * @param code 异常代码
     */
    protected void getRequestAbnormal(int code) {
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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}