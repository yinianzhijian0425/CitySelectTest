package tech.yunjing.biconlife.jniplugin.bean.request;

import android.text.TextUtils;

import java.io.Serializable;

import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;

/**
 * 接口请求基类对象
 * Created by chen.qi on 2017/6/27.
 */

public class BaseRequestObj implements Serializable {

    transient private static final long serialVersionUID = 5278145101338185500L;

    public BaseRequestObj() {
        setUserId(UserInfoManageUtil.getUserId());
        setToken(UserInfoManageUtil.getUserToken());
        setUuid(UserInfoManageUtil.getIMEI());
        // 假数据
        if (TextUtils.isEmpty(getUserId())) {
            setUserId("b24f7df5ec2a453a94926c77e416ec77");
        }
        if (TextUtils.isEmpty(getToken())) {
            setToken("21b96d9486f749138f5689f0");
        }
        if (TextUtils.isEmpty(getUuid())) {
            setUuid("cd4bf9f0d1351f605aeaa1e3767469ac");
        }
    }


    /**
     * 用户ID
     */
    private String userId;
    /**
     * Token
     */
    private String token;

    private String uuId;

    /**
     * 获取用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取Token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuId;
    }

    public void setUuid(String uuid) {
        this.uuId = uuid;
    }
}
