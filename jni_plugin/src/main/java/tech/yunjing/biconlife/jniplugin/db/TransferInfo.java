package tech.yunjing.biconlife.jniplugin.db;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * Created by lh on 2017/7/28 0028.
 * 转账订单信息表
 */
@Table("TransferInfo")
public class TransferInfo extends BaseEntityObj {

    /**
     * 数据库表自增ID，dbID
     */
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int dbID;

    /**
     * 转账订单id
     */
    @Column("TransferId")
    private String TransferId;
    /**
     * 转账订单状态
     */
    @Column("TransferState")
    private String TransferState;

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getTransferId() {
        return TransferId;
    }

    public void setTransferId(String transferId) {
        TransferId = transferId;
    }

    public String getTransferState() {
        return TransferState;
    }

    public void setTransferState(String transferState) {
        TransferState = transferState;
    }
}
