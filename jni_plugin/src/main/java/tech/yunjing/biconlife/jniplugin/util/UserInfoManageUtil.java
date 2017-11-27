package tech.yunjing.biconlife.jniplugin.util;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import tech.yunjing.biconlife.jniplugin.base.AppBaseApplication;
import tech.yunjing.biconlife.jniplugin.bean.personal.SaveUserInfoRequest;
import tech.yunjing.biconlife.jniplugin.bean.personal.UserInfoObj;
import tech.yunjing.biconlife.jniplugin.bean.personal.UserInfoQrCodeContentObj;
import tech.yunjing.biconlife.jniplugin.global.BCSharePreKey;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.common.util.MD5;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 用户信息管理操作类
 * Created by sun.li on 2017/7/27.
 */

public class UserInfoManageUtil {
    /*用户账号类型:手机号 2；*/
    public static final String UserLoginType_PhoneCode = "2";

    /*用户账号类型:邮箱 1；第三方微信3*/
    public static final String UserLoginType_MailCode = "1";

    /*用户账号类型:第三方微信 3*/
    public static final String UserLoginType_WeChatCode = "3";


    /**
     * 获取用户ID
     */
    public static String getUserId() {
        String userId = "";
        UserInfoObj userInfoObj = UserInfoManageUtil.getUserInfo();
        if (userInfoObj != null) {
            try {
                userId = UserInfoManageUtil.getUserInfo().getUserId();
            } catch (Exception e) {
                e.printStackTrace();
                userId = "";
            }
        }
        return userId;
    }

    /**
     * 获取用户信息
     */
    public static UserInfoObj getUserInfo() {
        UserInfoObj userInfoObj;
        try {
            userInfoObj = LKPrefUtil.getObject(BCSharePreKey.USERINFO_DATA, new UserInfoObj());
        } catch (Exception e) {
            e.printStackTrace();
            userInfoObj = null;
        }
        return userInfoObj;
    }

    public static void saveUserInfo(UserInfoObj userInfoObj) {
        if (userInfoObj != null) {
            String userName = LKPrefUtil.getString(BCSharePreKey.FULL_NAME, "");
            if (!TextUtils.isEmpty(userName)) {
                userInfoObj.setName(userName);
            }
            String phone = LKPrefUtil.getString(BCSharePreKey.USER_PHONE, "");
            if (!TextUtils.isEmpty(phone)) {
                userInfoObj.setPhone(phone);
            }
            LKPrefUtil.putString(BCSharePreKey.USER_ID, userInfoObj.getUserId());
            LKPrefUtil.putObject(BCSharePreKey.USERINFO_DATA, userInfoObj);
        }
    }

    /**
     * 获取用户Token
     */
    public static String getUserToken() {
        return LKPrefUtil.getString(BCSharePreKey.TOKEN, "");
    }

    /**
     * 获取设备唯一标识IMEI
     */
    public static String getIMEI() {
        if (AppBaseApplication.getContext() != null) {
            String szImei = "";
            try {
                TelephonyManager TelephonyMgr = (TelephonyManager) AppBaseApplication.getContext().getSystemService
                        (TELEPHONY_SERVICE);
                szImei = TelephonyMgr.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(szImei)) {
                return MD5.md5(szImei);
            }
        }
        return "";
    }

    /**
     * 设置用户隐私密码
     */
    public static void setUserPrivacyPassword(String psd) {
        LKLogUtil.e("当前设置的PrivacyPassword:" + psd);
        LKPrefUtil.putString(BCSharePreKey.PRIVACY_PASSWORD, psd);
    }

    /**
     * 获取用户隐私密码
     */
    public static String getUserPrivacyPassword() {
        String privacy_password;
        try {
            privacy_password = LKPrefUtil.getString(BCSharePreKey.PRIVACY_PASSWORD, "");
        } catch (Exception e) {
            e.printStackTrace();
            privacy_password = "";
        }
        return privacy_password;
    }

    /**
     * 获取用户是否设置隐私密码
     */
    public static boolean getUserWhetherSetPrivacyPassword() {
        if (getUserPrivacyPassword().length() == 4) {
            Log.e("PrivacyPassword:", "用户隐私密码已设置");
            return true;
        } else {
            Log.e("PrivacyPassword:", "用户为设置隐私密码");
            return false;
        }
    }

    /**
     * 获取用户信息二维码图片
     *
     * @param context    上下文；
     * @param isShowIcon 是否显示项目Logo；
     * @param qrCodeDp   二维码宽高（正方形），小于50默认为50；
     */
    public static Bitmap getUserInfoQrCodeImage(Context context, boolean isShowIcon, float qrCodeDp) {
        if (context == null) {
            context = LKApplication.getContext();
        }
        if (qrCodeDp < 50) {
            qrCodeDp = 50;
        }
        Bitmap bitmap = null;
        bitmap = LKQrCodeUtil.Create2DCode(context, getUserInfoQrCodeContent(), qrCodeDp);
        return bitmap;
    }

    /**
     * 获取用户信息二维码内容
     */
    public static String getUserInfoQrCodeContent() {
        UserInfoQrCodeContentObj userInfoQrCodeContentObj = new UserInfoQrCodeContentObj();
        UserInfoObj userInfoObj = getUserInfo();
        if (userInfoObj != null) {
            if (!TextUtils.isEmpty(userInfoObj.getUserId())) {
                userInfoQrCodeContentObj.setUserId(userInfoObj.getUserId());
            } else {
            }
            if (!TextUtils.isEmpty(userInfoObj.getPhone())) {
                userInfoQrCodeContentObj.setMobile(userInfoObj.getPhone());
            } else {
            }
        }
        return LKJsonUtil.objConversionJsonString(userInfoQrCodeContentObj);
    }

    /**
     * 处理用户信息二维码内容
     */
    public static UserInfoQrCodeContentObj disposeUserInfoQrCodeContent(String qrCodeContent) {
        UserInfoQrCodeContentObj userInfoQrCodeContentObj = null;
        if (!TextUtils.isEmpty(qrCodeContent)) {
            userInfoQrCodeContentObj = LKJsonUtil.parseJsonToBean(qrCodeContent, UserInfoQrCodeContentObj.class);
        }
        return userInfoQrCodeContentObj;
    }

    /**
     * 初始化用户提交保存信息
     */
    public static SaveUserInfoRequest initSaveUserInfoRequest(UserInfoObj userInfoObj) {
        SaveUserInfoRequest saveUserInfoRequest = new SaveUserInfoRequest();
        if (userInfoObj != null) {
            if (!TextUtils.isEmpty(userInfoObj.getId())) {
                saveUserInfoRequest.setId(userInfoObj.getId());
            }
            if (!TextUtils.isEmpty(userInfoObj.getUserId())) {
                saveUserInfoRequest.setUserId(userInfoObj.getUserId());
            }
            if (!TextUtils.isEmpty(userInfoObj.getName())) {
                saveUserInfoRequest.setName(userInfoObj.getName());
            }
            if (!TextUtils.isEmpty(userInfoObj.getNickName())) {
                saveUserInfoRequest.setNickName(userInfoObj.getNickName());
            }
            if (!TextUtils.isEmpty(userInfoObj.getLargeImg())) {
                saveUserInfoRequest.setLargeImg(userInfoObj.getLargeImg());
            }
            if (!TextUtils.isEmpty(userInfoObj.getSmallImg())) {
                saveUserInfoRequest.setSmallImg(userInfoObj.getSmallImg());
            }
            if (!TextUtils.isEmpty(userInfoObj.getPhone())) {
                saveUserInfoRequest.setPhone(StringProcessingUtil.stringRemoveBlankSpace(userInfoObj.getPhone()));
            }
            if (userInfoObj.getHeight() >= 0) {
                saveUserInfoRequest.setHeight(userInfoObj.getHeight());
            }
            if (userInfoObj.getWeight() >= 0) {
                saveUserInfoRequest.setWeight(userInfoObj.getWeight());
            }
            if (!TextUtils.isEmpty(userInfoObj.getProvinceId())) {
                saveUserInfoRequest.setProvinceId(userInfoObj.getProvinceId());
            }
            if (!TextUtils.isEmpty(userInfoObj.getCityId())) {
                saveUserInfoRequest.setCityId(userInfoObj.getCityId());
            }
            if (!TextUtils.isEmpty(userInfoObj.getProvinceId())) {
                saveUserInfoRequest.setProvinceId(userInfoObj.getProvinceId());
            }
            if (!TextUtils.isEmpty(userInfoObj.getMarriage())) {
                saveUserInfoRequest.setMarriage(userInfoObj.getMarriage());
            }
        }
        return saveUserInfoRequest;
    }

    /**
     * 获取应用包名
     * @return
     */
    private static String getPackageName(Context context){
        String packageName = "tech.yunjing.biconlife";
        if(context!=null){
            try {
                packageName = context.getApplicationContext().getPackageName();
            } catch (Exception e) {
                e.printStackTrace();
                packageName = "tech.yunjing.biconlife";
            }
        }
        LKLogUtil.e("PackageName：" + packageName);
        return packageName;
    }

    /**
     * 获取版本号
     * @return
     */
    public static int getVersionCode(Context context){
        if(context == null){
            return 0;
        }
        int code = 0;
        try {
            //获取包管理器
            PackageManager manager = context.getPackageManager();
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(getPackageName(context),0);
            if(info!=null){
                code = info.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LKLogUtil.e("VersionCode：" + code);
        return code;
    }

    /**
     * 获取坂本明
     * @return
     */
    public static String getVersionName(Context context){
        if(context == null){
            return "1.0.0";
        }
        String versionName = "1.0.0";
        try {
            PackageManager manager = context.getPackageManager();
            //第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(context), PackageManager.GET_ACTIVITIES);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LKLogUtil.e("VersionName：" + versionName);
        return versionName;
    }

}
