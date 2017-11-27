package tech.yunjing.biconlife.jniplugin.bean.personal;


import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;
import tech.yunjing.biconlife.jniplugin.util.StringProcessingUtil;

/**
 * 用户信息二维码内容信息对象
 * Created by sun.li on 2017/7/21.
 */

public class UserInfoQrCodeContentObj extends BaseEntityObj {

    /**
     * 二维码类型-“userInfo”
     */
    private String codeType = "userInfo";

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 电话
     */
    private String mobile;

    public String getCodeType() { return codeType; }

    public void setCodeType(String codeType) { this.codeType = codeType; }

    /** 获取用户ID*/
    public String getUserId() { return userId; }

    /** 设置用户ID*/
    public void setUserId(String userId) { this.userId = userId; }


    /** 获取电话*/
    public String getMobile() { return mobile; }

    /** 设置电话*/
    public void setMobile(String mobile) { this.mobile = StringProcessingUtil.stringRemoveBlankSpace(mobile); }

}
