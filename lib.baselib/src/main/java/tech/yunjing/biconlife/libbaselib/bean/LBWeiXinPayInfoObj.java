package tech.yunjing.biconlife.libbaselib.bean;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 微信支付信息实体类
 *
 * @author huijitao
 */
public class LBWeiXinPayInfoObj extends BaseEntityObj {
    /**
     * 交易订单号
     * 20170831110825993316991841
     */
    private String orderNumber;
    /**
     * 应用ID
     * wx5b6536575f22fe33
     */
    private String appid;
    /**
     * 签名
     * 18F54DBB35EF1AF19CC18F01CA4FDD04
     */
    private String sign;
    /**
     * 商户号
     * 1487411462
     */
    private String partnerid;
    /**
     * 预支付交易会话ID
     * wx20170831110821c7ad529e210147191101
     */
    private String prepayid;
    /**
     * 随机字符串
     * BGuCApgLazk3ETZ9
     */
    private String noncestr;
    /**
     * 时间戳
     * 1504148905
     */
    private String timestamp;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WeiXinPayInfoObj{" +
                "orderNumber='" + orderNumber + '\'' +
                ", appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
