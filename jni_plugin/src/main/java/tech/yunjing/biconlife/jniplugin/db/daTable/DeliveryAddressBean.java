package tech.yunjing.biconlife.jniplugin.db.daTable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;
import tech.yunjing.biconlife.jniplugin.util.StringProcessingUtil;

/**
 * 收货地址对象
 * Created by sun.li on 2017/7/11.
 */
@Table("DeliveryAddressBean")
public class DeliveryAddressBean extends BaseEntityObj{
    @Ignore
    transient private static final long serialVersionUID = -978887391555044151L;

    /** 收货人ID*/
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int userID;

    /** 收货地址id 收货地址的唯一标识*/
    @Column("id")
    private String id = "";

    /** 收货人名称*/
    @Column("userName")
    private String userName = "";

    /** 收货人电话*/
    @Column("phone")
    private String phone = "";

    /** 城市 含省市区*/
    @Column("city")
    private String city = "";

    /** 收货人地址  详细地址*/
    @Column("address")
    private String address = "";

    /**
     * 是否为默认地址
     */
    @Column("isDefaultAddress")
    private boolean isDefaultAddress = false;

    /** 更新时间
     * @comment 格式 yyyy-MM-dd HH:mm:ss
     * */
    @Column("updatedDate")
    private String updatedDate;

    /** 收货地址标签*/
    @Column("label")
    private String label = "";

    /** 地址对应用户ID*/
    @Column("addressUserID")
    private String addressUserID = "";

    /**
     * 是否选中
     */
    private boolean isSelected = false;

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = StringProcessingUtil.stringRemoveBlankSpace(phone); }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public boolean isDefaultAddress() { return isDefaultAddress; }

    public void setDefaultAddress(boolean defaultAddress) { isDefaultAddress = defaultAddress; }

    public String getUpdatedDate() { return updatedDate; }

    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }

    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddressUserID() { return addressUserID; }

    public void setAddressUserID(String addressUserID) { this.addressUserID = addressUserID; }

    public enum EnumClass {
        userID,userName, phone, city, address, isDefaultAddress, updatedDate, label,addressUserID
    }

    @Override
    public String toString() {
        return "DeliveryAddressBean{" +
                "userID=" + userID +
                ", id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", isDefaultAddress=" + isDefaultAddress +
                ", updatedDate='" + updatedDate + '\'' +
                ", label='" + label + '\'' +
                ", addressUserID='" + addressUserID + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
