package tech.yunjing.biconlife.libbaselib.global;

/**
 * Intent传递的常量
 * Created by CQ on 2017/5/23.
 */

public class BCIntentKey {


     public  static final  String BOOLEAN ="BOOLEAN";

    public static final String IS_FORGET_PASSWORD = "IS_FORGET_PASSWORD";//判断是否是忘记密码


    public static final String HEALTH_MANAGER_DATA_LIST = "HEALTH_MANAGER_DATA_LIST";//健康数据管理传递的数据
    public static final String HEALTH_DATA_TITLE = "HEALTH_DATA_TITLE";//健康数据传递的标题
    public static final String HEALTH_DATA_TYPE = "HEALTH_DATA_TYPE";//健康数据类别
    public static final String HEALTH_DATA_TYPE_RANGE = "HEALTH_DATA_TYPE_RANGE";//健康数据类别范围

    public static final String HEALTH_DATA_VALEU = "HEALTH_DATA_VALEU";//健康数据传递的数值
    public static final String HEALTH_DATA_SELECT_SS_VALUE = "HEALTH_DATA_SELECT_SS_VALUE";//健康数据录入的舒缩数值
    public static final String HEALTH_DATA_SELECT_SZ_VALUE = "HEALTH_DATA_SELECT_SZ_VALUE";//健康数据录入的舒张数值
    public static final String HEALTH_DATA_RANGE = "HEALTH_DATA_RANGE";//健康数据传递范围
    public static final String HEALTH_DATA_OPTION = "HEALTH_DATA_OPTION";//健康数据传递单选操作
    public static final String HEALTH_DATA_UNIT = "HEALTH_DATA_UNIT";//健康数据传递单位操作
    public static final String HEALTH_DATA_CODE = "HEALTH_DATA_CODE";//健康数据传递CODE
    public static final String HEALTH_DATA_VALUE = "HEALTH_DATA_VALUE";//健康数据传递值
    public static final String HEALTH_DATA_OPTION_NAME = "HEALTH_DATA_OPTION_NAME";//健康数据选择饭前等值

    public static final String HEALTH_DATA_SELECT_TIME = "HEALTH_DATA_SELECT_TIME";//健康数据录入的时间
    public static final String HEALTH_DATA_SELECT_WEEK = "HEALTH_DATA_SELECT_WEEK";//健康数据录入的时间周几
    public static final String HEALTH_DATA_SELECT_SLEEP_TYPE = "HEALTH_DATA_SELECT_SLEEP_TYPE";//健康数据录入血糖饭前饭后类型

    public static final String HEALTH_DATA_OPTION_DATA_CODE_CHOSE = "HEALTH_DATA_OPTION_DATA_CODE_CHOSE";//健康数据手动录入数据选择CODE
    public static final String HEALTH_DATA_OPTION_DATA_NAME_CHOSE = "HEALTH_DATA_OPTION_DATA_NAME_CHOSE";//健康数据手动录入数据选择名字

    public static final String SHOP_DATA_PRICE = "SHOP_DATA_PRICE";//商品详情降价通知价格
    public static final String SHOP_DATA_PRODUCT_ID = "SHOP_DATA_PRODUCT_ID";//商品详情降价通知ID
    /**
     * 商城首页传递商品ID
     */
    public static String SHOP_PRODUCT_ID = "SHOP_PRODUCT_ID";

    public static final String HEARTH_REPORT_DATA = "HEARTH_REPORT_DATA";//健康报告数据


    /** 跳转选择地址界面标志*/
    public static final int IntentSelectAddressCode = 1002;

    /** 跳转添加地址界面标志*/
    public static final int IntentAddAddressCode = 1003;

    /** 商城跳转选择地址界面标志*/
    public static final int IntentSelectAddressShopCode = 1001;

    /**
     * 地址选择标志Key
     */
    public static final String AddressSelectLogoKey = "AddressSelectLogoKey";

    /**
     * 当前选中的地址对象ID
     */
    public static final String CurrentAddressSelectID = "AddressSelectLogoKey";


    /** 站外搜索关键字标志*/
    public static final String OutsideStationSearchKeyLogo = "OutsideStationSearchKeyLogo";

    /** 分享实体对象标志*/
    public static final String ShareEntityObjectLogo = "ShareEntityObjectLogo";

    /**
     * 社交模块的朋友id
     */
    public static final String SOCIAL_FRIENDID = "SOCIAL_FRIENDID";
    /**
     * 社交模块的动态id
     */
    public static final String SOCIAL_DYNAMICID = "SOCIAL_DYNAMICID";
    /**
     * 健身模块的发现选择城市
     */
    public static final int FITNESS_CHOOSE_CITY =1004;

}
