package tech.yunjing.biconlife.libbaselib.bean.BannerAdvertising;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 轮播广告对象实体
 * Created by sun.li on 2017/8/15.
 */

public class BannerAdvertisingObj extends BaseEntityObj {

    /**
     * 轮播广告id
     */
    private String id;

    /**
     * 状态标识 0：正常1：删除
     */
    private String logicDelete;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 修改时间
     */
    private String updateDate;

    /**
     * 轮播图名称
     */
    private String bannerName;

    /**
     * 轮播图链接地址
     */
    private String bannerUrl;

    /**
     * 轮播图片
     */
    private String bannerImage;

    /**
     * 轮播序号
     */
    private String bannerSort;

    /**
     * 响应类型：1.链接，2.商品
     */
    private double responseType;

    /**
     * 商品Id
     */
    private String goodsIds;

    /**
     * 轮播图开关 轮播开关:0.开，1.关
     */
    private String bannerFlag;

    /**
     * 轮播图位置
     */
    private String bannerPosition;

    /**
     * 轮播图文字内容
     */
    private String bannerContext;

    /**
     * 获取轮播广告id
     */
    public String getId() { return id; }

    /**
     * 设置轮播广告id
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
    public String getCreateDate() { return createDate; }

    /**
     * 设置创建时间
     */
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    /**
     * 获取修改时间
     */
    public String getUpdateDate() { return updateDate; }

    /**
     * 设置修改时间
     */
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }

    public String getBannerName() { return bannerName; }

    public void setBannerName(String bannerName) { this.bannerName = bannerName; }

    public String getBannerUrl() { return bannerUrl; }

    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }

    public String getBannerImage() { return bannerImage; }

    public void setBannerImage(String bannerImage) { this.bannerImage = bannerImage; }

    public String getBannerSort() { return bannerSort; }

    public void setBannerSort(String bannerSort) { this.bannerSort = bannerSort; }

    public double getResponseType() {
        return responseType;
    }

    public void setResponseType(double responseType) {
        this.responseType = responseType;
    }

    public String getGoodsIds() { return goodsIds; }

    public void setGoodsIds(String goodsIds) { this.goodsIds = goodsIds; }

    public String getBannerFlag() { return bannerFlag; }

    public void setBannerFlag(String bannerFlag) { this.bannerFlag = bannerFlag; }

    public String getBannerPosition() { return bannerPosition; }

    public void setBannerPosition(String bannerPosition) { this.bannerPosition = bannerPosition; }

    public String getBannerContext() { return bannerContext; }

    public void setBannerContext(String bannerContext) { this.bannerContext = bannerContext; }
}
