package tech.yunjing.biconlife.libbaselib.bean.Comments;

import java.util.List;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 评论详细信息对象
 * Created by sun.li on 2017/7/19.
 */

public class CommentsInfo extends BaseEntityObj {

    /**
     * 评论id
     */
    private String id;

    /**
     * 创建时间
     */
    private long createDate;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 内容
     */
    private String content;

    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 点赞状态；0未点赞，1已点赞，默认为0
     */
    private int dotPraiseState;
    /**
     * 点赞数
     */
    private String dotPraiseCount;
    /**
     * 回复总数
     */
    private int replyCount;

    /**
     * 评论的回复列表（返回3条）
     */
    private List<ReplyToCommentsInfo> replyInfos;

    /**
     * 获取评论id
     */
    public String getId() { return id; }

    /**
     * 设置评论id
     */
    public void setId(String id) { this.id = id; }

    /**
     * 获取创建时间
     */
    public long getCreateDate() { return createDate; }

    /**
     * 设置创建时间
     */
    public void setCreateDate(long createDate) { this.createDate = createDate; }

    /**
     * 获取用户ID
     */
    public String getUserId() { return userId; }

    /**
     * 设置用户ID
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * 获取用户姓名
     */
    public String getUserName() { return userName; }

    /**
     * 设置用户姓名
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     * 获取内容
     */
    public String getContent() { return content; }

    /**
     * 设置内容
     */
    public void setContent(String content) { this.content = content; }

    /**
     * 获取用户头像
     */
    public String getUserAvatar() { return userAvatar; }

    /**
     * 设置用户头像
     */
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }

    /**
     * 获取点赞状态；0未点赞，1已点赞，默认为0
     */
    public int getDotPraiseState() { return dotPraiseState; }

    /**
     * 设置点赞状态；0未点赞，1已点赞，默认为0
     */
    public void setDotPraiseState(int dotPraiseState) { this.dotPraiseState = dotPraiseState; }

    /**
     * 获取点赞数
     */
    public String getDotPraiseCount() { return dotPraiseCount; }

    /**
     * 设置点赞数
     */
    public void setDotPraiseCount(String dotPraiseCount) { this.dotPraiseCount = dotPraiseCount; }

    /**
     * 获取回复总数
     */
    public int getReplyCount() { return replyCount; }

    /**
     * 设置回复总数
     */
    public void setReplyCount(int replyCount) { this.replyCount = replyCount; }

    /**
     * 获取评论的回复列表（返回3条）
     */
    public List<ReplyToCommentsInfo> getReplyInfos() { return replyInfos; }

    /**
     * 设置评论的回复列表（返回3条）
     */
    public void setReplyInfos(List<ReplyToCommentsInfo> replyInfos) { this.replyInfos = replyInfos; }
}
