package tech.yunjing.biconlife.jniplugin.http;

/**
 * 公共网络请求接口地址
 * Created by CQ on 2017/7/3.
 */

public class BCNetworkInterface {

    /**
     * 公共接口版本
     */
    private static final String PREFIX_Base = "/V1.0.0/api";
    /**
     * 公共接口地址
     */
    private static final String BaseIP = BCUrl.getBaseUrl_Base()+PREFIX_Base;

    /** 获取对应模块轮播图接口*/
    public static final String GetBannerDetailFromRedisInterface = BaseIP + "/base/banner/getBannerDetailFromRedis";

    /** 检查是否存在新版本接口*/
    public static final String AppVersionCheckingInterface = BaseIP + "/base/appVersion/checkingver";

    /**
     * 用户资料接口版本
     */
    private static final String PREFIX_UserInfo = "/V1.0.0/api";
    /**
     * 用户资料接口
     */
    private static final String UserInterface = BCUrl.getBaseUrl_UserInfo() + PREFIX_UserInfo + "/userinfo";

    /**
     * 保存用户信息
     */
    public static final String SAVE_PERSONAL_INFO = UserInterface + "/save";

    /**
     * 文件上传接口版本
     */
    private static final String PREFIX_UpLoad = "/V1.0.0/api";
    /**
     * 公共接口地址
     */
    private static final String UpLoadIP = BCUrl.getBaseUrl_UpLoad()+PREFIX_UpLoad;

    /** 上传文件接口*/
    public static final String FileUploadInterface = UpLoadIP + "/file/upload";

    /**
     * 评论服务接口版本
     */
    private static final String PREFIX_Comments = "/V1.0.0/api";
    /**
     * 评论服务接口地址
     */
    private static final String CommentsIP = BCUrl.getBaseUrl_Comments()+PREFIX_Comments;

    /** 新增点赞接口*/
    public static final String DotPraiseAddInterface = CommentsIP + "/dotPraise/add";

    /** 新增评论接口*/
    public static final String CommentsInfoAddInterface = CommentsIP + "/commentsInfo/add";

    /** 新增子评论（回复）接口*/
    public static final String ReplyInfoAddInterface = CommentsIP + "/replyInfo/add";

    /** 分页获取评论列表接口*/
    public static final String FindCommentsByObjectIdInterface = CommentsIP + "/commentsInfo/findCommentsByObjectId";

    /** 分页获取评论的回复列表接口*/
    public static final String GetReplyInfoDetailsInterface = CommentsIP + "/replyInfo/getReplyInfoDetails";

}
