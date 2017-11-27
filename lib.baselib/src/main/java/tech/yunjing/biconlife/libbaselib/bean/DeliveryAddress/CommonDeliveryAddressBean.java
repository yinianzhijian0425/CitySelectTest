package tech.yunjing.biconlife.libbaselib.bean.DeliveryAddress;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 公用收货地址Bean（用于接口调试）
 * @author huijitao
 */
public class CommonDeliveryAddressBean extends BaseEntityObj {

    /**
     * 收货地址id
     * "577214b6cbb6435f8bff299c5da007cd"
     */
    private String id;
    /**
     * 逻辑删除  0正常，1删除
     * "0"
     */
    private String logicDelete;
    /**
     * 创建时间
     * 1501573256000
     */
    private long createDate;
    /**
     * 更新时间
     * 1501573256000
     */
    private long updateDate;
    /**
     * 用户id
     * "ed0af49cff1d49df9411574fb6e1c086"
     */
    private String userId;
    /**
     * 收货人
     * "张瓜"
     */
    private String addressName;
    /**
     * 收货手机号码
     * "18722476698"
     */
    private String addressPhone;
    /**
     * 省份
     * "陕西省"
     */
    private String addressProvince;
    /**
     * 城市
     * "西安市"
     */
    private String addressCity;
    /**
     * 区县
     * "碑林区"
     */
    private String addressArea;
    /**
     * 街道
     * "长安路"
     */
    private String addressStreet;
    /**
     * 邮编
     * "718100"
     */
    private String addressZip;
    /**
     * 详细地址
     * "华东小区"
     */
    private String addressDetailed;
    /**
     * 标签
     * "家"
     */
    private String addressTag;
    /**
     * 默认地址
     * 0默认，1非默认
     * "0"
     */
    private String addressDefaultChoose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogicDelete() {
        return logicDelete;
    }

    public void setLogicDelete(String logicDelete) {
        this.logicDelete = logicDelete;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressDetailed() {
        return addressDetailed;
    }

    public void setAddressDetailed(String addressDetailed) {
        this.addressDetailed = addressDetailed;
    }

    public String getAddressTag() {
        return addressTag;
    }

    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }

    public String getAddressDefaultChoose() {
        return addressDefaultChoose;
    }

    public void setAddressDefaultChoose(String addressDefaultChoose) {
        this.addressDefaultChoose = addressDefaultChoose;
    }

    @Override
    public String toString() {
        return "CommonDeliveryAddressBean{" +
                "id='" + id + '\'' +
                ", logicDelete='" + logicDelete + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", userId='" + userId + '\'' +
                ", addressName='" + addressName + '\'' +
                ", addressPhone='" + addressPhone + '\'' +
                ", addressProvince='" + addressProvince + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressArea='" + addressArea + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", addressZip='" + addressZip + '\'' +
                ", addressDetailed='" + addressDetailed + '\'' +
                ", addressTag='" + addressTag + '\'' +
                ", addressDefaultChoose='" + addressDefaultChoose + '\'' +
                '}';
    }
}
