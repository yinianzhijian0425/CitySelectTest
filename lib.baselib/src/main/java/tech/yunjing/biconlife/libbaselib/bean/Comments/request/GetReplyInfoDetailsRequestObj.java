package tech.yunjing.biconlife.libbaselib.bean.Comments.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 评论的回复对象分页查询-----请求实体
 * Created by Chen.qi on 2017/7/10.
 */

public class GetReplyInfoDetailsRequestObj extends BaseRequestObj {

    /**
     * 当前页,必填
     */
    private int current;

    /**
     * 每页记录数,必填
     */
    private int size;

    /**
     * 评论ID
     */
    private String commentId;

    /**
     * 评论类型
     * 评论类型：0：新闻、1：商城、2：医生、3：圈子
     */
    private String type;



    /**
     * 获取当前页,必填
     */
    public int getCurrent() { return current; }

    /**
     * 设置当前页,必填
     */
    public void setCurrent(int current) { this.current = current; }

    /**
     * 获取每页记录数,必填
     */
    public int getSize() { return size; }

    /**
     * 设置每页记录数,必填
     */
    public void setSize(int size) { this.size = size; }

    /**
     * 获取评论ID
     */
    public String getCommentId() { return commentId; }

    /**
     * 设置评论ID
     */
    public void setCommentId(String commentId) { this.commentId = commentId; }

    /**
     * 返回评论类型
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * 设置评论类型
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
