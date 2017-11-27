package tech.yunjing.biconlife.jniplugin.global;

import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;

/**
 * 保存的SharePreference相关值
 * Created by CQ on 2017/5/19.
 */

public class BCSharePreKey {

    public static final String FILENAME_SP = "biConLife";//sp存储
    public static final String ISLOGIN = "ISLOGIN";//是否登陆过
    public static final String SYSTEM_REMIND = "SYSTEM_REMIND";//开启系统提醒模式
    public static final String LONG_REMIND = "LONG_REMIND";//开启久坐提醒模式
    public static final String WATER_REMIND = "WATER_REMIND";//开启喝水提醒模式

    public static final String PREVIOUSSTEP_TIME = "PREVIOUSSTEP_TIME";//先前的天


    /**
     * token
     */
    public static final String TOKEN = "TOKEN";//登录失效
    /**
     * 隐私密码
     */
    public static final String PRIVACY_PASSWORD = UserInfoManageUtil.getUserId() + "_PRIVACY_PASSWORD";
    /**
     * 点击的是请输入密码1   设置隐私密码2
     */
    public static final String PASSWORD_FROM = "PASSWORD_FROM";
    /**
     * 设置中屏幕保护
     */
    public static final String SAFE_SCREEN_PROTECT = "SAFE_SCREEN_PROTECT";
    /**
     * 设置安全保护
     */
    public static final String SAFE_CODE_PROTECT = "SAFE_CODE_PROTECT";
    /**
     * 修改用户名
     */
//    public static final String USER_NAME = "USER_NAME";
    /**
     * 用户收货地址-Label
     */
    public static final String USER_DELIVERY_ADDRESS_Label = "USER_DELIVERY_ADDRESS_Label";

    /**
     * 隐私密码忘记密码标志_login
     */
    public static String FORGET_CODE_PROTECT_LOGIN = "FORGET_CODE_PROTECT_LOGIN";

    /**
     * 隐私密码忘记密码标志_main
     */
    public static String FORGET_CODE_PROTECT_Main = "FORGET_CODE_PROTECT_Main";


    //用于发送消息
    public static final int MSG_FROM_CLIENT = 12;
    public static final int MSG_FROM_SERVER = 32;
    public static final int REQUEST_SERVER = 33;


    public static String STEP_SWITCH_BTN_STATE = "SWITCH_BTN_STATE";//运动步数获取开关状态
    public static String READ_PERMISS_SWITCH_BTN_STATE = "READ_PERMISS_SWITCH_BTN_STATE";//读取设备权限开关状态


    public static String USERINFO_DATA = "USERINFO_DATA";//用户信息
    public static String USER_ID = "USER_ID";//用户ID
    public static String CHOOSE_AIRPLANE_ADULT = "CHOOSE_AIRPLANE_ADULT";//选择成人乘机人的数量
    public static String CHOOSE_AIRPLANE_CHILDREN = "CHOOSE_AIRPLANE_CHILDREN";//选择儿童乘机人的数量
    public static String CHOOSE_AIRPLANE_LIST = "CHOOSE_AIRPLANE_LIST";//选择乘机人的列表

    /**
     * 应用进入后台时间标志
     */
    public static String APP_TO_BACKGROUND_TIME_FLAG = "APP_TO_BACKGROUND_TIME_FLAG";

    /**
     * 全名，用户名
     */
    public static String FULL_NAME = "FULL_NAME";
    /**
     * 用户手机号
     */
    public static String USER_PHONE = "USER_PHONE";
    /**
     * 个人头图路径
     */
    public static String USER_HEADIMG_PATH = "USER_HEADIMG_PATH";
    /**
     * 健康档案界面数据
     */
    public static final String HEALTH_DATA = "HEALTH_DATA";
    /**
     * 录入体征界面数据
     */
    public static final String HEALTH_ENTRY_SIGN_DATA = "HEALTH_ENTRY_SIGN_DATA";

    /**
     * 当天是否打开过活动中心，包含open为打开
     */
    public static final String STIMULUS_CENTER_IS_OPEN = "Stimulus_Center_Is_Open";

    /**
     * 钱包余额
     */
    public static final String WALLET_BALANCE = "WALLET_BALANCE";
    /**
     * 好友请求数量
     */
    public static String FRIEND_SUM = "FRIEND_SUM";
    /**
     * 社交朋友圈未读消息数量
     */
    public static String FRIEND_RING_UNMESSAGE = "FRIEND_RING_UNMESSAGE";
}
