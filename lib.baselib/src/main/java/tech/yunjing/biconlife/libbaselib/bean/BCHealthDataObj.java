package tech.yunjing.biconlife.libbaselib.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页健康数据的对象
 * Created by CQ on 2017/6/7.
 */

public class BCHealthDataObj implements Parcelable {
    public String name;
    public String num;
    public String status;
    public int icon;
    public boolean isOpen;

    @Override
    public String toString() {
        return "BCHealthDataObj{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", status='" + status + '\'' +
                ", icon=" + icon +
                ", isOpen=" + isOpen +
                '}';
    }

    public BCHealthDataObj(String name, String num, String status,int icon, boolean isOpen) {
        this.name = name;
        this.num = num;
        this.status = status;
        this.isOpen = isOpen;
        this.icon = icon;
    }

    protected BCHealthDataObj(Parcel in) {
        name = in.readString();
        num = in.readString();
        status = in.readString();
        icon=in.readInt();
        isOpen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(num);
        dest.writeString(status);
        dest.writeInt(icon);
        dest.writeByte((byte) (isOpen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BCHealthDataObj> CREATOR = new Creator<BCHealthDataObj>() {
        @Override
        public BCHealthDataObj createFromParcel(Parcel in) {
            return new BCHealthDataObj(in);
        }

        @Override
        public BCHealthDataObj[] newArray(int size) {
            return new BCHealthDataObj[size];
        }
    };
}
