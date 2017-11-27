package tech.yunjing.biconlife.jniplugin.db;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * Created by lh on 2017/7/28 0028.
 * 被删除的好友信息表
 */
@Table("DeleteFriendInfo")
public class DeleteFriendInfo extends BaseEntityObj {

    /**
     * 数据库表自增ID，dbID
     */
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int dbID;

    /**
     * 被删除的好友id
     */
    @Column("UserId")
    private String UserId;

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
