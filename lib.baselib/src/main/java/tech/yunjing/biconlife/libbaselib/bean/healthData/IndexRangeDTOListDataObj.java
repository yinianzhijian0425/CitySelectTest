package tech.yunjing.biconlife.libbaselib.bean.healthData;

import android.os.Parcel;
import android.os.Parcelable;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 健康数据范围
 * Created by CHP on 2017/7/26.
 */

public class IndexRangeDTOListDataObj extends BaseEntityObj implements Parcelable {
    /**
     * 范围id
     */
    private int id;
    /**
     * 范围名称
     */
    private String name;
    /**
     * 字典code
     */
    private String code;
    /**
     * 起始值
     */
    private double startValue;
    /**
     * 结束值
     */
    private double endValue;


    /**
     * 值
     */
    private double result;
    /**
     * 单位
     */
    private String unit;
    /**
     * 刻度

     */
    private double scaleValue;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getStartValue() {
        return startValue;
    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public double getEndValue() {
        return endValue;
    }

    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    public double getScaleValue() {
        return scaleValue;
    }

    public void setScaleValue(double scaleValue) {
        this.scaleValue = scaleValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeDouble(this.startValue);
        dest.writeDouble(this.endValue);
        dest.writeDouble(this.result);
        dest.writeString(this.unit);
        dest.writeDouble(this.scaleValue);
    }

    public IndexRangeDTOListDataObj() {
    }

    protected IndexRangeDTOListDataObj(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.code = in.readString();
        this.startValue = in.readDouble();
        this.endValue = in.readDouble();
        this.result = in.readDouble();
        this.unit = in.readString();
        this.scaleValue = in.readDouble();
    }

    public static final Parcelable.Creator<IndexRangeDTOListDataObj> CREATOR = new Parcelable.Creator<IndexRangeDTOListDataObj>() {
        @Override
        public IndexRangeDTOListDataObj createFromParcel(Parcel source) {
            return new IndexRangeDTOListDataObj(source);
        }

        @Override
        public IndexRangeDTOListDataObj[] newArray(int size) {
            return new IndexRangeDTOListDataObj[size];
        }
    };
}
