package tech.yunjing.biconlife.jniplugin.bean.personal;


import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;
import tech.yunjing.biconlife.jniplugin.util.StringProcessingUtil;

/**
 * 用户信息
 * Created by sun.li on 2017/7/21.
 */

public class UserInfoObj extends BaseEntityObj {

    /**
     * iD
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户头像
     */
    private String largeImg;
    /**
     * 头像缩略图
     */
    private String smallImg;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别 （0：未设置,1:男，2：女）
     */
    private int gender;
    /**
     * 生日
     */
    private long birthday;
    /**
     * 省id
     */
    private String provinceId;
    /**
     * 市id
     */
    private String cityId;

    /**
     * 区域id
     */
    private String areaId;
    /**
     * 身高
     */
    private double height;
    /**
     * 体重
     */
    private double weight;
    /**
     * 婚姻状况
     */
    private String marriage;
    /**
     * 隐私密码
     */
    private String privacyPwd;
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
    /**
     * 国籍
     */
    public String getNationality() {
        return nationality;
    }
    /**
     * 国籍
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    /**
     * 职业
     */
    public String getProfession() {
        return profession;
    }
    /**
     * 职业
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }
    /**
     * 收入
     */
    public String getIncome() {
        return income;
    }
    /**
     * 收入
     */
    public void setIncome(String income) {
        this.income = income;
    }

    /** 获取用户地址*/
    public String getAddress() {
        return address;
    }

    /** 设置用户地址*/
    public void setAddress(String address) {
        this.address = address;
    }

    /** 获取区域id*/
    public String getAreaId() {
        return areaId;
    }

    /** 设置区域id*/
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /** 获取身高*/
    public double getHeight() {
        return height;
    }

    /** 设置身高*/
    public void setHeight(double height) {
        this.height = height;
    }

    /** 获取体重*/
    public double getWeight() {
        return weight;
    }

    /** 设置体重*/
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /** 获取iD*/
    public String getId() { return id; }

    /** 设置iD*/
    public void setId(String id) { this.id = id; }

    /** 获取用户ID*/
    public String getUserId() { return userId; }

    /** 设置用户ID*/
    public void setUserId(String userId) { this.userId = userId; }

    /** 获取昵称*/
    public String getNickName() { return nickName; }

    /** 设置昵称*/
    public void setNickName(String nickName) { this.nickName = nickName; }

    /** 获取用户名称*/
    public String getName() { return name; }

    /** 设置用户名称*/
    public void setName(String name) { this.name = name; }

    /** 获取用户头像*/
    public String getLargeImg() { return largeImg; }

    /** 设置用户头像*/
    public void setLargeImg(String largeImg) { this.largeImg = largeImg; }

    /** 获取头像缩略图*/
    public String getSmallImg() { return smallImg; }

    /** 设置头像缩略图*/
    public void setSmallImg(String smallImg) { this.smallImg = smallImg; }

    /** 获取电话*/
    public String getPhone() { return phone; }

    /** 设置电话*/
    public void setPhone(String phone) { this.phone = StringProcessingUtil.stringRemoveBlankSpace(phone); }

    /** 获取性别 （0：未设置,1:男，2：女）*/
    public int getGender() { return gender; }

    /** 设置性别 （0：未设置,1:男，2：女）*/
    public void setGender(int gender) { this.gender = gender; }

    /** 获取生日*/
    public long getBirthday() { return birthday; }

    /** 设置生日*/
    public void setBirthday(long birthday) { this.birthday = birthday; }

    /** 获取省id*/
    public String getProvinceId() { return provinceId; }

    /** 设置省id*/
    public void setProvinceId(String provinceId) { this.provinceId = provinceId; }

    /** 获取市id*/
    public String getCityId() { return cityId; }

    /** 设置市id*/
    public void setCityId(String cityId) { this.cityId = cityId; }

    /** 获取婚姻状况*/
    public String getMarriage() { return marriage; }

    /** 设置婚姻状况*/
    public void setMarriage(String marriage) { this.marriage = marriage; }

    /** 获取隐私密码*/
    public String getPrivacyPwd() { return privacyPwd; }

    /** 设置隐私密码*/
    public void setPrivacyPwd(String privacyPwd) { this.privacyPwd = privacyPwd; }

    @Override
    public String toString() {
        return "UserInfoObj{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", largeImg='" + largeImg + '\'' +
                ", smallImg='" + smallImg + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", marriage='" + marriage + '\'' +
                ", privacyPwd='" + privacyPwd + '\'' +
                '}';
    }
}
