package tech.yunjing.biconlife.libbaselib.bean.Comments.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 新增子评论（回复）-----请求实体
 * Created by Chen.qi on 2017/7/10.
 */

public class ReplyInfoAddRequestObj extends BaseRequestObj {

    /**
     * 回复人id,必填
     */
    private String fromUserId;

    /**
     * 被回复人id,必填
     */
    private String toUserId;


    /**
     * 父评论ID,必填
     */
    private String parentId;

    /**
     * 回复内容,必填
     */
    private String content;
    /**
     * 获取回复人id,必填
     */
    public String getFromUserId() { return fromUserId; }
    /**
     * 设置回复人id,必填
     */
    public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }
    /**
     * 获取被回复人id,必填
     */
    public String getToUserId() { return toUserId; }
    /**
     * 设置被回复人id,必填
     */
    public void setToUserId(String toUserId) { this.toUserId = toUserId; }
    /**
     * 获取父评论ID,必填
     */
    public String getParentId() { return parentId; }
    /**
     * 设置父评论ID,必填
     */
    public void setParentId(String parentId) { this.parentId = parentId; }

    /**
     * 获取回复内容,必填
     */
    public String getContent() { return content; }

    /**
     * 设置回复内容,必填
     */
    public void setContent(String content) { this.content = content; }

}
