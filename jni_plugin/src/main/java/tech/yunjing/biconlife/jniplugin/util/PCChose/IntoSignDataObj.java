package tech.yunjing.biconlife.jniplugin.util.PCChose;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页健康数据的对象
 * Created by CQ on 2017/6/7.
 */

public class IntoSignDataObj implements Parcelable {
    public int pos;
    public String name;
    public String num;
    public String status;
    public boolean isOpen;



    @Override
    public String toString() {
        return "IntoSignDataObj{" +
                "pos=" + pos +
                ", name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", status='" + status + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }

    public IntoSignDataObj(int pos, String name, String num, String status, boolean isOpen) {
        this.pos = pos;
        this.name = name;
        this.num = num;
        this.status = status;
        this.isOpen = isOpen;
    }

    protected IntoSignDataObj(Parcel in) {
        pos = in.readInt();
        name = in.readString();
        num = in.readString();
        status = in.readString();
        isOpen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pos);
        dest.writeString(name);
        dest.writeString(num);
        dest.writeString(status);
        dest.writeByte((byte) (isOpen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IntoSignDataObj> CREATOR = new Creator<IntoSignDataObj>() {
        @Override
        public IntoSignDataObj createFromParcel(Parcel in) {
            return new IntoSignDataObj(in);
        }

        @Override
        public IntoSignDataObj[] newArray(int size) {
            return new IntoSignDataObj[size];
        }
    };
}