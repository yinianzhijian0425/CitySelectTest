package tech.yunjing.biconlife.jniplugin.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 级联数据基类实体
 * Created by sun.li on 2017/7/10.
 */

public class BaseCascadeDataObj<T> extends BaseEntityObj {

    /**
     * 数据库表自增ID，dbID
     */
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int dbID;

    /***
     * id
     */
    @Column("id")
    private String id;

    /***
     * 对应值
     */
    @Column("value")
    private String value;

    /***
     * 父id
     */
    @Column("parentId")
    private String parentId;

    /***
     * 对象属性（数据库）
     */
    @Column("objectProperty")
    private String objectProperty;
    /***
     * 对象属性（接口）
     */
    private T object;

    /***
     *最后更新时间
     */
    @Column("updateDate")
    private long updateDate;
    /***
     * 症状是否被选择

     */

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
        setObjectProperty(object.toString());
    }

    public String getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(String objectProperty) {
        this.objectProperty = objectProperty;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

}
