package tech.yunjing.biconlife.jniplugin.db;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * Created by lh on 2017/7/28 0028.
 * 好友备注名表
 */
@Table("FriendPetName")
public class FriendPetName extends BaseEntityObj {

    /**
     * 数据库表自增ID，dbID
     */
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int dbID;

    /**
     * 有备注名的用户id
     */
    @Column("petUserId")
    private String petUserId;

    /**
     * 备注名
     */
    @Column("petUserName")
    private String petUserName;

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getPetUserId() {
        return petUserId;
    }

    public void setPetUserId(String petUserId) {
        this.petUserId = petUserId;
    }

    public String getPetUserName() {
        return petUserName;
    }

    public void setPetUserName(String petUserName) {
        this.petUserName = petUserName;
    }
}
