package tech.yunjing.biconlife.jniplugin.http;

/**
 * 公共接口请求Url地址
 * Created by CQ on 2017/5/23.
 * 外网IP：180.104.112.254
 * 内网IP：172.17.0.168
 */
public class BCUrl {

    /**
     * 公共模块IP，公共【测试】192.168.1.201【正式】180.104.112.254
     */
    private static final String Base_IP = "192.168.1.201";

    /**
     * 获取H5公共IP（外网）
     * 外网："http://180.104.112.254:7001";
     */
    private static final String getBaseIP_H5() {
        return "http://" + Base_IP + ":7001";
    }

    /**
     * 获取H5公共地址
     *
     * @param sort 1:新闻H5; 20:单个商品分享（商品详情）;21多个商品分享（商品详情）;3:商城详情H5;
     *             4:健身周报H5;5:激励bannerH5;60:隐私条款;61:服务条款H5;7:搜索;
     */
    public static final String getBaseUrl_H5(int sort) {
        String url = getBaseIP_H5();
        switch (sort) {
            case 1:
                url += "/#/?sort=1&id=";
                break;
            case 20:
                url += "/#/?sort=2&path=2&id=";
                break;
            case 21:
                url += "/#/?sort=2&path=1&ids=";
                break;
            case 3:
                url += "/#/?sort=3&id=";
                break;
            case 4:
                url += "/#/?sort=4";
                break;
            case 5:
                url += "/#/?sort=5&code=";
                break;
            case 60:
                url += "/#/?sort=6&code=termsOfService";
                break;
            case 61:
                url += "/#/?sort=6&code=privacyPolicy";
                break;
            case 7:
                url += "/#/?sort=7&text=";
                break;
            default:
                break;
        }
        return url;
    }

    /**
     * 公共模块端口号：30110
     */
    private static final String PORT_30100 = "8000";
    /**
     * 公共模块（Application-name）
     */
    private static final String Base_Name = "/life-component-base";

    /**
     * 公共模块使用IP
     */
    public static String getBaseUrl_Base() {
        return "http://" + Base_IP + ":" + PORT_30100 + Base_Name;
    }

    /**
     * 文件上传IP，公共172.17.0.168----180.104.112.254
     */
    private static final String UpLoad_IP = Base_IP;
    /**
     * 文件上传端口号：20040---8000/zuul
     */
    private static final String PORT_20040 = "8000/zuul";
    /**
     * 文件上传模块（Application-name）/fileUploadApi
     */
    private static final String UpLoad_Name = "/fileUploadApi";

    /**
     * 文件上传使用IP
     */
    public static String getBaseUrl_UpLoad() {
        return "http://" + UpLoad_IP + ":" + PORT_20040 + UpLoad_Name;
    }

    /**
     * 权限账户模块IP，公共172.17.0.168
     */
    private static final String Account_IP = Base_IP;
    /**
     * 权限账户端口号：30110
     */
    private static final String PORT_30110 = "8000";
    /**
     * 权限账户模块（Application-name）
     */
    private static final String Account_Name = "/life-authority-account";

    /**
     * 权限账户模块使用IP
     */
    public static String getBaseUrl_Login() {
        return "http://" + Account_IP + ":" + PORT_30110 + Account_Name;
    }

    /**
     * 用户资料模块IP，公共172.17.0.168
     */
    private static final String UserInfo_IP = Base_IP;
    /**
     * 用户资料端口号：30120
     */
    private static final String PORT_30120 = "8000";
    /**
     * 用户资料模块（Application-name）
     */
    private static final String UserInfo_Name = "/life-authority-userinfo";

    /**
     * 用户信息模块使用IP
     */
    public static String getBaseUrl_UserInfo() {
        return "http://" + UserInfo_IP + ":" + PORT_30120 + UserInfo_Name;
    }

    /**
     * 评论服务IP,172.17.0.247
     */
    private static final String Comments_IP = Base_IP;
    /**
     * 评论服务端口号：30102
     */
    private static final String PORT_30102 = "8000";
    /**
     * 评论服务模块（Application-name）
     */
    private static final String Comments_Name = "/life-component-comment";

    /**
     * 评论信息模块使用IP
     */
    public static String getBaseUrl_Comments() {
        return "http://" + Comments_IP + ":" + PORT_30102 + Comments_Name;
    }

    /**
     * 健康模块IP，172.17.1.37
     */
    private static final String Health_IP = Base_IP;
    /**
     * 健康模块：30210
     */
    private static final String PORT_30210 = "8000";
    /**
     * 健康模块（Application-name）
     */
    private static final String Health_Name = "/life-health-health";

    /**
     * 健康模块IP地址
     */
    public static String getBaseUrl_Health() {
        return "http://" + Health_IP + ":" + PORT_30210 + Health_Name;
    }

    /**
     * 医疗模块IP,192.168.16.227
     */
    private static final String Medical_IP = Base_IP;
    /**
     * 医疗模块端口号：30310
     */
    private static final String PORT_30310 = "8000";
    /**
     * 医疗模块（Application-name）
     */
    private static final String Medical_Name = "/life-medical-medical";

    /**
     * 医疗模块使用IP
     */
    public static String getBaseUrl_Medical() {
        return "http://" + Medical_IP + ":" + PORT_30310 + Medical_Name;
    }

    /**
     * 社交模块IP,172.17.1.136
     */
    private static final String Social_IP = Base_IP;
    /**
     * 社交模块端口号：30510
     */
    private static final String PORT_30510 = "8000";
    /**
     * 社交模块（Application-name）
     */
    private static final String Social_Name = "/life-social-social";

    /**
     * 社交模块使用IP
     */
    public static String getBaseUrl_Social() {
        return "http://" + Social_IP + ":" + PORT_30510 + Social_Name;
    }

    /**
     * 新闻模块IP,172.17.0.247
     */
    private static final String News_IP = Base_IP;
    /**
     * 新闻模块端口号：30610
     */
    private static final String PORT_30610 = "8000";
    /**
     * 新闻模块（Application-name）
     */
    private static final String News_Name = "/life-news-news";

    /**
     * 新闻模块使用IP
     */
    public static String getBaseUrl_News() {
        return "http://" + News_IP + ":" + PORT_30610 + News_Name;
    }

    /**
     * 激励模块IP,172.17.1.37
     */
    private static final String Stimulate_IP = Base_IP;
    /**
     * 激励模块端口号：30710
     */
    private static final String PORT_30710 = "8000";
    /**
     * 激励模块（Application-name）
     */
    private static final String Stimulate_Name = "/life-health-encourage";

    /**
     * 激励模块使用IP
     */
    public static String getBaseUrl_Stimulate() {
        return "http://" + Stimulate_IP + ":" + PORT_30710 + Stimulate_Name;
    }

    /**
     * 商城IP,172.17.1.136
     */
    private static final String Shop_IP = Base_IP;
    /**
     * 商城模块基础模块
     */
    private static final String PORT_30400 = "8000";
    /**
     * 商城模块-30400端口对应（Application-name）
     */
    private static final String Shop_30400_Name = "/life-mall-base";
    /**
     * 商城模块会员模块
     */
    private static final String PORT_30410 = "8000";
    /**
     * 商城模块-30410端口对应（Application-name）
     */
    private static final String Shop_30410_Name = "/life-mall-member";
    /**
     * 商城模块商品模块
     */
    private static final String PORT_30420 = "8000";
    /**
     * 商城模块-30420端口对应（Application-name）
     */
    private static final String Shop_30420_Name = "/life-mall-goods";
    /**
     * 商城模块优惠券模块
     */
    private static final String PORT_30440 = "8000";
    /**
     * 商城模块-30440端口对应（Application-name）
     */
    private static final String Shop_30440_Name = "/life-mall-coupon";
    /**
     * 商城订单模块
     */
    private static final String PORT_30450 = "8000";
    /**
     * 商城模块-30450端口对应（Application-name）
     */
    private static final String Shop_30450_Name = "/life-mall-order";

    /**
     * 商城提交订单
     */
    public static String getBaseUrl_Shop_30450() {
        return "http://" + Shop_IP + ":" + BCUrl.PORT_30450 + Shop_30450_Name;
    }

    /**
     * 商城模块使用IP
     */
    public static String getBaseUrl_Shop_30410() {
        return "http://" + Shop_IP + ":" + BCUrl.PORT_30410 + Shop_30410_Name;
    }

    /**
     * 商城模块使用IP2
     */
    public static String getBaseUrl_Shop_30440() {
        return "http://" + Shop_IP + ":" + BCUrl.PORT_30440 + Shop_30440_Name;
    }


    /**
     * 商城模块使用IP2
     */
    public static String getBaseUrl_Shop_30400() {
        return "http://" + Shop_IP + ":" + BCUrl.PORT_30400 + Shop_30400_Name;
    }

    /**
     * 商城模块使用保存元素
     */
    public static String getBaseUrl_Shop_30420() {
        return "http://" + Shop_IP + ":" + BCUrl.PORT_30420 + Shop_30420_Name;
    }

    /**
     * 饮食IP,172.17.1.126
     */
    private static final String Food_IP = Base_IP;

    /***
     * 饮食使用的ip
     */
    public static String getBaseUrl_Food() {
        return "http://" + Food_IP + ":" + PORT_30210 + Health_Name;
    }

    /**
     * 金融模块IP,172.17.1.37
     */
    private static final String Finance_IP = Base_IP;
    /**
     * 金融模块-钱包模块端口号：30810
     */
    private static final String PORT_30810 = "8000";
    /**
     * 金融模块（Application-name）
     */
    private static final String Finance_Name = "/life-finance-wallet";

    /**
     * 金融模块-钱包模块使用基路径
     */
    public static String getBaseUrl_Finance() {
        return "http://" + Finance_IP + ":" + PORT_30810 + Finance_Name;
    }

    /**
     * 收货地址IP（暂用）,172.17.1.136
     */
    private static final String Address_IP = Base_IP;

    /**
     * 收货地址使用基路径
     */
    public static String getBaseUrl_Address() {
        return "http://" + Address_IP + ":" + PORT_30410 + Shop_30410_Name;
    }

    /**
     * 全局搜索ip,172.17.1.37
     */
    private static final String AllSearch_IP = Base_IP;

    public static String getBaseUrl_AllSearch() {
        return "http://" + AllSearch_IP + ":" + PORT_30100 + Base_Name;
    }

    /**
     * 健康的ip
     */
    private static final String FITNESS_IP = Base_IP;
    /**
     * 8000的端口号
     */
    private static final String PORT_8000 = "8000";

    /**
     * 获取基本的地址
     */
    public static String getBaseUrl_Fitness() {
        return "http://" + FITNESS_IP + ":" + PORT_8000 + "/life-fitness-fitness";
    }

//    商城-商城首页：172.17.1.136
//    商城-优惠券：172.17.1.136
//    商城-商品：172.17.1.136
//    商城-会员（购物车，关注，收货地址）：172.17.1.136
//    商城-订单：172.17.1.136
//    社交-好友管理，群聊管理，好友圈：172.17.1.136
//    新闻-评论：172.17.1.143
//    饮食-食物：172.17.1.126
//    金融-支付中心：172.17.1.37
//    金融-钱包：172.17.1.37
//    激励-全部172.17.1.37
//    全局-轮播图：172.17.1.37
//    全局-浏览历史：172.17.1.37
//    全局-热搜：172.17.1.37
//    全局-单页管理：172.17.1.37
//    后台接口文档：http://172.17.0.99:4999/index.php?s=/3&page_id=5
//    移动端接口文档：http://172.17.0.99:4999/index.php?s=/4&page_id=81
}
