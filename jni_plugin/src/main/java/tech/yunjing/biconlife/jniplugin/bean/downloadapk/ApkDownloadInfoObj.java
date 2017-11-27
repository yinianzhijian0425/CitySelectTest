package tech.yunjing.biconlife.jniplugin.bean.downloadapk;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * apk下载信息对象
 * Created by sun.li on 2017/10/18.
 * @author sun.li
 */

public class ApkDownloadInfoObj extends BaseEntityObj{

    /** 标志（No：没有新版本, Yes：有新版本）*/
    private String update;

    /** 类型：ios-Android*/
    private String type;

    /** 版本名称*/
    private String versionName;

    /** 版本号*/
    private String versionCode;

    /** 下载地址*/
    private String url;

    /** 版本说明*/
    private String info;

    /** 文件大小*/
    private String size;

    /** 下载次数*/
    private String times;

    /** 状态:0未发布，1已发布*/
    private String state;

    public String getUpdate() { return update; }

    public void setUpdate(String update) { this.update = update; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getVersionName() { return versionName; }

    public void setVersionName(String versionName) { this.versionName = versionName; }

    public String getVersionCode() { return versionCode; }

    public void setVersionCode(String versionCode) { this.versionCode = versionCode; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size; }

    public String getTimes() { return times; }

    public void setTimes(String times) { this.times = times; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}
