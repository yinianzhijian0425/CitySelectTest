package tech.yunjing.biconlife.libbaselib.bean.Comments;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 评论的回复详细信息对象
 * Created by sun.li on 2017/7/19.
 */

public class ReplyToCommentsInfo extends BaseEntityObj {

    /**
     * 回复id
     */
    private String id;
    /**
     * 状态标识 0：正常1：删除
     */
    private String logicDelete;
    /**
     * 创建时间
     */
    private long createDate;
    /**
     * 修改时间
     */
    private long updateDate;
    /**
     * 父评论id
     */
    private String parentId;
    /**
     * 回复人ID
     */
    private String fromUserId;
    /**
     * 回复人姓名
     */
    private String fromUserName;
    /**
     * 被回复人ID
     */
    private String toUserId;
    /**
     * 被回复人姓名
     */
    private String toUserName;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 评论类型：0：新闻、1：商城、2：医生、3：圈子
     */
    private String type;

    //接口少的字段，暂用
    /**
     * 当前用户点赞状态：0-未点赞，1-已点赞
     */
    private String dotPraiseState;
    /**
     * 点赞数量
     */
    private String dotPraiseCount;
    /**
     * 回复人头像
     */
    private String fromUserAvatar;
    /**
     * 被回复人头像
     */
    private String toUserAvatar;


    /**
     * 获取回复id
     */
    public String getId() { return id; }

    /**
     * 设置回复id
     */
    public void setId(String id) { this.id = id; }

    /**
     * 获取状态标识 0：正常1：删除
     */
    public String getLogicDelete() { return logicDelete; }

    /**
     * 设置状态标识 0：正常1：删除
     */
    public void setLogicDelete(String logicDelete) { this.logicDelete = logicDelete; }

    /**
     * 获取创建时间
     */
    public long getCreateDate() { return createDate; }

    /**
     * 设置创建时间
     */
    public void setCreateDate(long createDate) { this.createDate = createDate; }

    /**
     * 获取修改时间
     */
    public long getUpdateDate() { return updateDate; }

    /**
     * 设置修改时间
     */
    public void setUpdateDate(long updateDate) { this.updateDate = updateDate; }

    /**
     * 获取回复人ID
     */
    public String getFromUserId() { return fromUserId; }

    /**
     * 设置回复人ID
     */
    public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }

    /**
     * 获取回复人姓名
     */
    public String getFromUserName() { return fromUserName; }

    /**
     * 设置回复人姓名
     */
    public void setFromUserName(String fromUserName) { this.fromUserName = fromUserName; }

    /**
     * 获取被回复人ID
     */
    public String getToUserId() { return toUserId; }

    /**
     * 设置被回复人ID
     */
    public void setToUserId(String toUserId) { this.toUserId = toUserId; }

    /**
     * 获取被回复人姓名
     */
    public String getToUserName() { return toUserName; }

    /**
     * 设置被回复人姓名
     */
    public void setToUserName(String toUserName) { this.toUserName = toUserName; }

    /**
     * 获取内容
     */
    public String getContent() { return content; }

    /**
     * 设置内容
     */
    public void setContent(String content) { this.content = content; }

    /**
     * 获取父评论id
     */
    public String getParentId() { return parentId; }

    /**
     * 设置父评论id
     */
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDotPraiseState() {
        return dotPraiseState;
    }

    public void setDotPraiseState(String dotPraiseState) {
        this.dotPraiseState = dotPraiseState;
    }

    public String getDotPraiseCount() {
        return dotPraiseCount;
    }

    public void setDotPraiseCount(String dotPraiseCount) {
        this.dotPraiseCount = dotPraiseCount;
    }

    public String getFromUserAvatar() {
        return fromUserAvatar;
    }

    public void setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
    }

    public String getToUserAvatar() {
        return toUserAvatar;
    }

    public void setToUserAvatar(String toUserAvatar) {
        this.toUserAvatar = toUserAvatar;
    }

    @Override
    public String toString() {
        return "ReplyToCommentsInfo{" +
                "id='" + id + '\'' +
                ", logicDelete='" + logicDelete + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", parentId='" + parentId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
