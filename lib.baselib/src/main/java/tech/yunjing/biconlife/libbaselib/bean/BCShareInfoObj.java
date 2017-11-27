package tech.yunjing.biconlife.libbaselib.bean;

import java.util.List;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;
import tech.yunjing.biconlife.libbaselib.bean.Comments.ReplyToCommentsInfo;

/**
 * 公共分享信息实体对象
 * Created by sun.li on 2017/7/19.
 */

public class BCShareInfoObj extends BaseEntityObj {

    /**
     * 分享的标题
     */
    private String title = "";

    /**
     * 分享内容描述
     */
    private String description = "";

    /**
     * 网页地址
     */
    private String webUrl = "";

    /**
     * 图片地址
     */
    private String imgUrl = "";

    /**
     * 分享来源，默认“伯图全景”
     */
    private String shareSource = "伯图全景";

    /** 获取分享的标题*/
    public String getTitle() { return title; }

    /** 设置分享的标题*/
    public void setTitle(String title) { this.title = title; }

    /** 获取分享内容描述*/
    public String getDescription() { return description; }

    /** 设置分享内容描述*/
    public void setDescription(String description) { this.description = description; }

    /** 获取网页地址*/
    public String getWebUrl() { return webUrl; }

    /** 设置网页地址*/
    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }

    /** 获取图片地址*/
    public String getImgUrl() { return imgUrl; }

    /** 设置图片地址*/
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    /** 获取分享来源，默认“伯图全景”*/
    public String getShareSource() { return shareSource; }

    /** 设置分享来源，默认“伯图全景”*/
    public void setShareSource(String shareSource) { this.shareSource = shareSource; }
}
