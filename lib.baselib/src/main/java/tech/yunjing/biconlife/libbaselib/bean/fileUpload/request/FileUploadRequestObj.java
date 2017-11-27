package tech.yunjing.biconlife.libbaselib.bean.fileUpload.request;

import java.io.File;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 文件上传-----请求实体
 * Created by sun.li on 2017/7/10.
 */

public class FileUploadRequestObj extends BaseRequestObj {

    /**
     * 调用的服务id
     */
    private String appId;

    /**
     * 调用的服务名
     */
    private String appName;

    /**
     * 压缩宽度,非必填
     */
    private String width;

    /**
     * 压缩高度,非必填
     */
    private String height;

    /**
     * 上传的文件
     */
    private File file;


    //多图片上传
    /**
     * 上传的文件1
     */
    private File file1;
    /**
     * 上传的文件2
     */
    private File file2;
    /**
     * 上传的文件3
     */
    private File file3;
    /**
     * 上传的文件4
     */
    private File file4;
    /**
     * 上传的文件5
     */
    private File file5;
    /**
     * 上传的文件6
     */
    private File file6;
    /**
     * 上传的文件7
     */
    private File file7;
    /**
     * 上传的文件8
     */
    private File file8;
    /**
     * 上传的文件9
     */
    private File file9;



    /** 获取调用的服务id*/
    public String getAppId() { return appId; }

    /** 设置调用的服务id*/
    public void setAppId(String appId) { this.appId = appId; }

    /** 获取调用的服务名*/
    public String getAppName() { return appName; }

    /** 设置调用的服务名*/
    public void setAppName(String appName) { this.appName = appName; }

    /** 获取压缩宽度,非必填*/
    public String getWidth() { return width; }

    /** 设置压缩宽度,非必填*/
    public void setWidth(String width) { this.width = width; }

    /** 获取压缩高度,非必填*/
    public String getHeight() { return height; }

    /** 设置压缩高度,非必填*/
    public void setHeight(String height) { this.height = height; }

    /** 获取上传的文件*/
    public File getFile() { return file; }

    /** 设置上传的文件*/
    public void setFile(File file) { this.file = file; }

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }

    public File getFile3() {
        return file3;
    }

    public void setFile3(File file3) {
        this.file3 = file3;
    }

    public File getFile4() {
        return file4;
    }

    public void setFile4(File file4) {
        this.file4 = file4;
    }

    public File getFile5() {
        return file5;
    }

    public void setFile5(File file5) {
        this.file5 = file5;
    }

    public File getFile6() {
        return file6;
    }

    public void setFile6(File file6) {
        this.file6 = file6;
    }

    public File getFile7() {
        return file7;
    }

    public void setFile7(File file7) {
        this.file7 = file7;
    }

    public File getFile8() {
        return file8;
    }

    public void setFile8(File file8) {
        this.file8 = file8;
    }

    public File getFile9() {
        return file9;
    }

    public void setFile9(File file9) {
        this.file9 = file9;
    }
}
