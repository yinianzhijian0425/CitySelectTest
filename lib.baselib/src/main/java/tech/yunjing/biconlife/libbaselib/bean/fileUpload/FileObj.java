package tech.yunjing.biconlife.libbaselib.bean.fileUpload;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 文件对象
 * Created by sun.li on 2017/9/8.
 */

public class FileObj extends BaseEntityObj{

    /** 应用id*/
    private String appId;

    /** 应用名称*/
    private String appName;

    /** 原图地址*/
    private String path;

    /** 缩略图地址*/
    private String zoomPath;

    /** 压缩后宽度*/
    private int width;

    /** 压缩后高度*/
    private int height;

    /** 原图宽度*/
    private int w;

    /** 原图高度*/
    private int h;

    /** 文件真实名称*/
    private String realName;

    /** 文件后缀*/
    private String fileExt;

    /** 转换后的大小*/
    private String fileSize;

    /** 原始大小*/
    private String realSize;

    /** 备注*/
    private String remark;

    public String getAppId() { return appId; }

    public void setAppId(String appId) { this.appId = appId; }

    public String getAppName() { return appName; }

    public void setAppName(String appName) { this.appName = appName; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getZoomPath() { return zoomPath; }

    public void setZoomPath(String zoomPath) { this.zoomPath = zoomPath; }

    public int getWidth() { return width; }

    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }

    public int getW() { return w; }

    public void setW(int w) { this.w = w; }

    public int getH() { return h; }

    public void setH(int h) { this.h = h; }

    public String getRealName() { return realName; }

    public void setRealName(String realName) { this.realName = realName; }

    public String getFileExt() { return fileExt; }

    public void setFileExt(String fileExt) { this.fileExt = fileExt; }

    public String getFileSize() { return fileSize; }

    public void setFileSize(String fileSize) { this.fileSize = fileSize; }

    public String getRealSize() { return realSize; }

    public void setRealSize(String realSize) { this.realSize = realSize; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }
}
