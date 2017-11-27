package tech.yunjing.biconlife.libbaselib.bean.healthData;

import android.os.Parcel;
import android.os.Parcelable;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 健康数据操作单选
 * Created by CHP on 2017/7/26.
 */

public class OptionListDataObj extends BaseEntityObj implements Parcelable {
    /**
     * 字典
     */
    private int id;
    /**
     * 名称
     */
    private String value;
    /**
     * 选项code
     */
    private String key;
    /**
     * 字典类别
     */
    private int type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.value);
        dest.writeString(this.key);
        dest.writeInt(this.type);
    }

    public OptionListDataObj() {
    }

    protected OptionListDataObj(Parcel in) {
        this.id = in.readInt();
        this.value = in.readString();
        this.key = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<OptionListDataObj> CREATOR = new Parcelable.Creator<OptionListDataObj>() {
        @Override
        public OptionListDataObj createFromParcel(Parcel source) {
            return new OptionListDataObj(source);
        }

        @Override
        public OptionListDataObj[] newArray(int size) {
            return new OptionListDataObj[size];
        }
    };
}
