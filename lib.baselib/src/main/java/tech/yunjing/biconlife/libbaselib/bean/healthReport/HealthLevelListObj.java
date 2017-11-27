package tech.yunjing.biconlife.libbaselib.bean.healthReport;

import android.os.Parcel;
import android.os.Parcelable;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 首页获取用户健康等级及提示语list
 * Created by CHP on 2017/7/20.
 */

public class HealthLevelListObj extends BaseEntityObj implements Parcelable {

    /**
     * 指标名称
     */
    private String indexName;

    /**
     * 指标code
     */
    private String indexCode;
    /**
     * 指标值
     */
    private String indexValue;

    /**
     * 单位
     */
    private String indexUnit;
    /**
     *状态：1偏高，0正常，-1偏低
     */
    private int status;
    /**
     *来源：0录入，1设备
     */
    private int source;
    /**
     *偏高提示语
     */
    private String highContent;
    /**
     *偏低提示语
     */
    private String lowContent;
    /**
     * 图片
     */
    private int getIndexIcon;

    /**
     * 偏离数
     */
    private int  priority;

    /**
     *
     * 是否展开
     */
    private boolean isSpread;
    /**
     *
     * 是否展开
     */
    public boolean isSpread() {
        return isSpread;
    }
    /**
     *
     * 是否展开
     */
    public void setSpread(boolean spread) {
        isSpread = spread;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getGetIndexIcon() {
        return getIndexIcon;
    }

    public void setGetIndexIcon(int getIndexIcon) {
        this.getIndexIcon = getIndexIcon;
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

    public String getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(String indexValue) {
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

    public String getHighContent() {
        return highContent;
    }

    public void setHighContent(String highContent) {
        this.highContent = highContent;
    }

    public String getLowContent() {
        return lowContent;
    }

    public void setLowContent(String lowContent) {
        this.lowContent = lowContent;
    }

    public HealthLevelListObj() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.indexName);
        dest.writeString(this.indexCode);
        dest.writeString(this.indexValue);
        dest.writeString(this.indexUnit);
        dest.writeInt(this.status);
        dest.writeInt(this.source);
        dest.writeString(this.highContent);
        dest.writeString(this.lowContent);
        dest.writeInt(this.getIndexIcon);
        dest.writeInt(this.priority);
    }

    protected HealthLevelListObj(Parcel in) {
        this.indexName = in.readString();
        this.indexCode = in.readString();
        this.indexValue = in.readString();
        this.indexUnit = in.readString();
        this.status = in.readInt();
        this.source = in.readInt();
        this.highContent = in.readString();
        this.lowContent = in.readString();
        this.getIndexIcon = in.readInt();
        this.priority = in.readInt();
    }

    public static final Creator<HealthLevelListObj> CREATOR = new Creator<HealthLevelListObj>() {
        @Override
        public HealthLevelListObj createFromParcel(Parcel source) {
            return new HealthLevelListObj(source);
        }

        @Override
        public HealthLevelListObj[] newArray(int size) {
            return new HealthLevelListObj[size];
        }
    };
}
