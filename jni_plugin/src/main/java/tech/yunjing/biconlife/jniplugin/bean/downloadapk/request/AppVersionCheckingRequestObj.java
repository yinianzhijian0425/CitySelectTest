package tech.yunjing.biconlife.jniplugin.bean.downloadapk.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 检查是否存在新版本请求实体类
 * @author huijitao
 */
public class AppVersionCheckingRequestObj extends BaseRequestObj {
    /**
     * 版本号码
     */
    private int versionCode;

    public int getCode() { return versionCode; }

    public void setCode(int code) { this.versionCode = code; }
}
