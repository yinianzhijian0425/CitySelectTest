package tech.yunjing.biconlife.jniplugin.im.voip.http;

import tech.yunjing.biconlife.jniplugin.http.BCUrl;

/**
 * 公共接口请求Url地址
 * 获取好友列表地址
 * 注：音视频专用
 * Created by CQ on 2017/5/23.
 */

public class JniNetworkInterfaceGetFriend {
    private static final String PREFIX_Social = "/V1.0.0/api";

    /**
     * 社交模块IP
     */
    private static final String Social_IP = BCUrl.getBaseUrl_Social()+PREFIX_Social;

    /**
     * 社交模块使用IP获取群好友列表接口
     */
    public static final String SocialHttpGetGroupMember = Social_IP + "/groupChat/selectByGroupIdAndUserId";
}
