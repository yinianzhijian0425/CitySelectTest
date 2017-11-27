package tech.yunjing.biconlife.libbaselib.bean.Comments.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 新增评论-----请求实体
 * Created by Chen.qi on 2017/7/10.
 */

public class CommentsInfoAddRequestObj extends BaseRequestObj {

    /**
     * 客体ID，指被评论的对象ID
     */
    private String objectId;

    /**
     * 评论内容,必填
     */
    private String content;

    /**
     * 评论类型：0 = 新闻；1 = 商城；2 = 医疗；
     */
    private String type;

    /**
     * 获取客体ID，指被评论的对象ID
     */
    public String getObjectId() { return objectId; }

    /**
     * 设置客体ID，指被评论的对象ID
     */
    public void setObjectId(String objectId) { this.objectId = objectId; }

    /**
     * 获取评论内容,必填
     */
    public String getContent() { return content; }

    /**
     * 设置评论内容,必填
     */
    public void setContent(String content) { this.content = content; }

    /**
     * 获取评论类型：0 = 新闻；1 = 商城；2 = 医疗；
     */
    public String getType() { return type; }

    /**
     * 设置评论类型：0 = 新闻；1 = 商城；2 = 医疗；
     */
    public void setType(String type) { this.type = type; }
}
