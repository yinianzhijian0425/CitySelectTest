package tech.yunjing.biconlife.libbaselib.global;

/**
 * Small模块间跳转所有常量类
 * Created by CQ on 2017/6/23.
 */

public class BCBundleJsonKey {
    /**
     * 登录模块
     */
    private static final String LOGIN_URL = "login";
    /**
     * 登录模块-登录注册
     */
    public static final String Login_RegisterAndLoginActivity = LOGIN_URL + "/RegisterAndLoginActivity";
    /**
     * 登录模块-登录
     */
    public static final String Login_LoginActivity = LOGIN_URL + "/LoginActivity";
    /**
     * 登录模块-修改密码界面
     */
    public static final String Login_InputPhoneNumberActivity = LOGIN_URL + "/InputPhoneNumberActivity";

    /**
     * 健康模块
     */
    private static final String HEALTH_URL = "health";
    /**
     * 健康模块-录入体征界面
     */
    public static final String Health_IntoSignActivity = HEALTH_URL + "/IntoSignActivity";

    /**
     * 我的模块
     */
    private static final String MINE_URL = "mine";
    /**
     * 我的模块-输入隐私密码
     */
    public static final String Mine_InputPasswordActivity = MINE_URL + "/InputPasswordActivity";

    /**
     * 我的模块-选择收货地址
     */
    public static final String Mine_SelectDeliveryAddressActivity = MINE_URL + "/SelectDeliveryAddressActivity";
    /**
     * 我的模块-添加或编辑收货地址
     */
    public static final String Mine_AddOrEditorDeliveryAddressActivity = MINE_URL + "/AddOrEditorDeliveryAddressActivity";
    /**
     * 我的模块-设置界面
     */
    public static final String Mine_MySettingActivity= MINE_URL + "/MySettingActivity";


    /**
     * 商城模块
     */
    private static final String SHOP_URL = "shop";
    /**
     * 商城模块-商城首页界面
     */
    public static final String Shop_ShopMainActivity = SHOP_URL + "/ShopMainActivity";
    /**
     * 商城模块-我的优惠券界面
     */
    public static final String Shop_ShopCouponActivity = SHOP_URL + "/ShopCouponActivity";
    /**
     * 商城模块-我的关注
     */
    public static final String Shop_FollowGoodsActivity = SHOP_URL + "/FollowGoodsActivity";
    /**
     * 商城模块-商品详情界面
     */
    public static final String Shop_GoodsDetailActivity = SHOP_URL + "/GoodsDetailActivity";


    /**
     * 新闻模块
     */
    private static final String NEWS_URL = "news";
    /**
     * 新闻模块-新闻首页界面
     */
    public static final String News_SelectInterestedChannelActivity = NEWS_URL + "/SelectInterestedChannelActivity";
    /**
     * 新闻模块-新闻详情界面
     */
    public static final String NewsDetailsActivity = NEWS_URL + "/NewsDetailsActivity";

    /**
     * 医疗模块
     */
    private static final String MEDICAL_URL = "medicalfield";
    /**
     * 医疗模块-医疗首页界面
     */
    public static final String Medical_MedicalMainActivity = MEDICAL_URL + "/MedicalFieldMainActivity";

    /**
     * 娱乐模块
     */
    private static final String RECREATION_URL = "recreation";
    /**
     * 娱乐模块-娱乐首页
     */
    public static final String Recreation_RecreationMainActivity = RECREATION_URL + "/RecreationMainActivity";

    /**
     * 出行模块
     */
    private static final String GOING_URL = "going";
    /**
     * 出行模块-首页
     */
    public static final String Going_TravelHomeActivity = GOING_URL + "/TravelHomeActivity";
    /****
     * 出行模块---城市选择
     */
    public static final String Going_CityPickerActivity = GOING_URL + "/CityPickerActivity";

    /**
     * 激励模块
     */
    private static final String STIMULATE_URL = "stimulate";
    /**
     * 激励模块-首页
     */
    public static final String Stimulate_ActionCenterActivity = STIMULATE_URL + "/ActionCenterActivity";

    /**
     * 饮食推荐模块
     */
    private static final String FOOD_URL = "food";
    /**
     * 饮食推荐模块-首页
     */
    public static final String Food_HealthDietActivity = FOOD_URL + "/HealthDietActivity";

    public static final String Food_RestaurantActivity = FOOD_URL + "/RestaurantActivity";

    /**
     * 休闲模块
     */
    private static final String RELATION_URL = "relaxation";

    /**
     * 休闲模块音乐
     */
    public static final String Relaxation_RelaxationMusicActivity = RELATION_URL + "/RelaxationMusicActivity";


    /**
     * 休闲模块游戏
     */
    public static final String Relaxation_RelaxationGameActivity = RELATION_URL + "/RelaxationGameActivity";
    /**
     * e
     */
    public static final String Relaxation_RelaxationVideoActivity = RELATION_URL + "/RelaxationVideoActivity";

    /**
     * 金融模块
     */
    private static final String FINANCE_URL = "finance";
    /**
     * 金融模块-首页
     */
    public static final String Finance_FinanceHomeActivity = FINANCE_URL + "/FinanceHomeActivity";
    /**
     * 金融模块-设置密码
     */
    public static final String Finance_SetPasswordActivity = FINANCE_URL + "/SetPasswordActivity";

    /**
     * 工具箱模块
     */
    private static final String TOOLS_BOX_URL = "toolsbox";
    /**
     * 工具箱模块-首页
     */
    public static final String ToolsBox_ToolBoxMainActivity = TOOLS_BOX_URL + "/ToolBoxMainActivity";

    /**
     * 社交模块
     */
    private static final String SOCIAL_URL = "social";
    /**
     * 社交模块-相片选择入口界面
     */
    public static final String Social_PhotoAlbumActivity = SOCIAL_URL + "/PhotoAlbumActivity";
    /**
     * 社交模块-照片拍摄/视频录制界面
     */
    public static final String Social_ShootActivity = SOCIAL_URL + "/ShootActivity";
    /**
     * 社交模块- 视频播放页面（不包含下载视频的方法）
     */
    public static final String Social_VideoPlayerActivity = SOCIAL_URL + "/VideoPlayerActivity";
    /**
     * 社交模块- 视频播放页面（包含下载视频的方法）
     */
    public static final String Social_ZoneVideoPlayerActivity = SOCIAL_URL + "/ZoneVideoPlayerActivity";
    /**
     * 社交模块- 我的二维码页面
     */
    public static final String Social_MyTwoDimensionalCodeActivity = SOCIAL_URL + "/MyTwoDimensionalCodeActivity";
    /**
     * 社交模块- 好友详情页面
     */
    public static final String Social_FriendDetailsActivity = SOCIAL_URL + "/FriendDetailsActivity";
    /**
     * 社交模块- 伯图好友分享选择好友页面
     */
    public static final String Social_BoTuFriendShareActivity = SOCIAL_URL + "/BoTuFriendShareActivity";
    /**
     * 社交模块- 发布好友圈页面
     */
    public static final String Social_CreateNewZoneActivity = SOCIAL_URL + "/CreateNewZoneActivity";
    /**
     * 社交模块扫一扫页面
     */
    public static final String Social_ScanActivity = SOCIAL_URL + "/ScanActivity";
    /***
     * 朋友圈动态详情
     */
    public static final String Social_FriendZoneDetailsActivity = SOCIAL_URL + "/FriendZoneDetailsActivity";

    /**
     * 健身模块
     */
    private static final String FITNESS_URL = "fitness";

    /**
     * 健身模块-首页
     */
    public static final String Fitness_FitnessHomeActivity = FITNESS_URL + "/FitnessHomeActivity";

    /**
     * 主模块
     */
    private static final String MAIN_URL = "main";
    /**
     * 主模块-首站外搜索界面
     */
    public static final String MAIN_SearchOutsidePublicStationActivity = MAIN_URL + "/SearchOutsidePublicStationActivity";

    /**
     * 登录跳转到主页面
     */
    public static final String Main_Jump_to_main = MAIN_URL + "/MainTableActivity";

    /**
     * 公共模块
     */
    private static final String BASELIB_URL = "lib.baselib";
    /**
     * 公共模块-公共图片浏览Activity界面
     */
    public static final String BASELIB_BCImageDisplayActivity = BASELIB_URL + "/BCImageDisplayActivity";

}
