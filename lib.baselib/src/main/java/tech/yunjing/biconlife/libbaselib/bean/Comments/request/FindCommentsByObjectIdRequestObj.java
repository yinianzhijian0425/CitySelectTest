package tech.yunjing.biconlife.libbaselib.bean.Comments.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 评论对象分页查询-----请求实体
 * Created by Chen.qi on 2017/7/10.
 */

public class FindCommentsByObjectIdRequestObj extends BaseRequestObj {

    /**
     * 客体ID，指被评论的对象ID；
     */
    private String objectId;

    /**
     * 当前页,必填
     */
    private int current;

    /**
     * 每页记录数,必填
     */
    private int size;

    /**
     * 评论的回复列表条数,服务器默认传3条
     */
    private int limit;

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
     * 获取客体ID，指被评论的对象ID；
     */
    public String getObjectId() { return objectId; }

    /**
     * 设置客体ID，指被评论的对象ID；
     */
    public void setObjectId(String objectId) { this.objectId = objectId; }

    /**
     * 获取评论的回复列表条数,服务器默认传3条
     */
    public int getLimit() { return limit; }

    /**
     * 设置评论的回复列表条数,服务器默认传3条
     */
    public void setLimit(int limit) { this.limit = limit; }
}
