package tech.yunjing.biconlife.jniplugin.db.daTable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 全国城市信息对象
 * Created by sun.li on 2017/9/12.
 */
@Table("NationalUrbanInfoObj")
public class NationalUrbanInfoObj extends BaseEntityObj{

    /** 城市数据库ID*/
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int urbanID;

    /** 城市ID*/
    @Column("id")
    private int id;

    /** 城市名称*/
    @Column("name")
    private String name;

    /** 城市对应父ID*/
    @Column("parent_id")
    private int parent_id;

    /** 城市级别
     * @comment 1、省级；2、市级；3、县级；
     * */
    private int level;

    public int getUrbanID() { return urbanID; }

    public void setUrbanID(int urbanID) { this.urbanID = urbanID; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getParent_id() { return parent_id; }

    public void setParent_id(int parent_id) { this.parent_id = parent_id; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public enum EnumClass {
        urbanID,id, name, parent_id, level
    }
}
