package tech.yunjing.biconlife.libbaselib.bean.healthData;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 用户健康数据集合
 * Created by CHP on 2017/7/20.
 */

public class UerAllIndexDTOListObj extends BaseEntityObj{
    /**
     * 数据id
     */
     private String id;
    /**
     * 指标名称
     */
    private String indexName;
    /**
     * 指标code
     */
    private String indexCode;
    /**
     * 数值
     */
    private double indexValue;
    /**
     * 单位
     */
    private String indexUnit;
    /**
     * 状态：-1偏低,0正常,1偏高,99无法比较,100不用比较
     */
    private int status;
    /**
     * 来源 0：用户录入，1：设备
     */
    private int source;

    /**
     * 创建时间
     */
    private long createDate;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public double getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(double indexValue) {
        this.indexValue = indexValue;
    }

    public String getIndexUnit() {
        return indexUnit;
    }

    public void setIndexUnit(String indexUnit) {
        this.indexUnit = indexUnit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
