package tech.yunjing.biconlife.jniplugin.im.key;


import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;

/**
 * 所有SP传值Key
 * Created by nanPengFei on 2016/9/22 09:05.
 */
public class LKPrefKeyList {
    public static final String USER_ID = "USER_ID";//用户ID

    //---------------------IM相关开始-----------------------------
    public static final String IM_LINK_STATUS = "IM_LINK_STATUS";//连接聊天服务器状态,0,连接中；1、成功；2、失败

    public static final String STREAM_VOICE_CALL = LKPrefUtil.getString(USER_ID, "") + "STREAM_VOICE_CALL";//语音是否听筒播放

}