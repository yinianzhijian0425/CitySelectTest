package tech.yunjing.biconlife.jniplugin.bean.personal;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;
import tech.yunjing.biconlife.jniplugin.util.StringProcessingUtil;

/**
 * 更新用户信息请求类
 * Created by CHP on 2017/7/21.
 */

public class SaveUserInfoRequest extends BaseRequestObj {
    /**
     * id
     */
    private String id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 名字
     */
    private String name;
    /**
     * 原图
     */
    private String largeImg;
    /**
     * 小图
     */
    private String smallImg;
    /**
     * 电话
     */
    private String phone;
    /**
     * 身高
     */
    private double height;
    /**
     * 体重
     */
    private double weight;
    /**
     * 省ID
     */
    private String provinceId;
    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 区域ID
     */
    private String areaId;
    /**
     * 婚姻状况
     */
    private String marriage;
    /**
     * 用户地址
     */
    private String address;

    /**
     * 国籍
     */
    private String nationality;
    /**
     * 职业
     */
    private String profession;
    /**
     * 收入
     */
    private String income;


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLargeImg() {
        return largeImg;
    }

    public void setLargeImg(String largeImg) {
        this.largeImg = largeImg;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = StringProcessingUtil.stringRemoveBlankSpace(phone);
    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }
}
