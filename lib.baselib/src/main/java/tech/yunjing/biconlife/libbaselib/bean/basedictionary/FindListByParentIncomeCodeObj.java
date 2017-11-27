package tech.yunjing.biconlife.libbaselib.bean.basedictionary;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 收入
 * Created by CHP on 2017/10/13.
 */
@Table("IncomeCode")
public class FindListByParentIncomeCodeObj extends BaseEntityObj {
    //指定自增，每个对象需要一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int Pid;
    /**
     * id
     */
    @Column("id")
    private String id;
    /**
     * 名称
     */
    @Column("dictionaryName")
    private String dictionaryName;
    /**
     * 字典code
     */
    @Column("dictionaryCode")
    private String dictionaryCode;
    /**
     * 内容
     */
    @Column("content")
    private String content;
    /**
     * id
     */
    public String getId() {
        return id;
    }
    /**
     * id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 名称
     */
    public String getDictionaryName() {
        return dictionaryName;
    }
    /**
     * 名称
     */
    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }
    /**
     * 字典code
     */
    public String getDictionaryCode() {
        return dictionaryCode;
    }
    /**
     * 字典code
     */
    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }
    /**
     * 内容
     */
    public String getContent() {
        return content;
    }
    /**
     * 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FindListByParentIncomeCodeObj{" +
                "Pid=" + Pid +
                ", id='" + id + '\'' +
                ", dictionaryName='" + dictionaryName + '\'' +
                ", dictionaryCode='" + dictionaryCode + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
