package tech.yunjing.biconlife.jniplugin.im.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 群成员信息
 * Created by Chen.qi on 2017/8/30.
 */

public class GroupMenberListdata implements Parcelable {
    public boolean isCheck;//是否勾选

    public boolean isMuteVideo;//是否打开了摄像头一遍对方看见自己

    /**
     * 是否接通了
     */
    public boolean isAccept;

    /**
     * 成员id
     */
    public String userId;
    /**
     * 成员id
     */
    public String smallImg;
    /**
     * 用户名字
     */
    public String name;
    /**
     * 成员昵称
     */
    public String nickName;
    /**
     * 成员群昵称
     */
    public String userGroupNickName;
    /**
     * 查询者对其他群成员的好友备注
     */
    public String petName;
    /**
     * 用户手机号
     */
    public String phone;

    public String UID;

    public String mobile;

    public String userID;

    public String avatar;


    public GroupMenberListdata() {

    }

    protected GroupMenberListdata(Parcel in) {
        isCheck = in.readByte() != 0;
        isMuteVideo = in.readByte() != 0;
        isAccept = in.readByte() != 0;
        userId = in.readString();
        smallImg = in.readString();
        name = in.readString();
        nickName = in.readString();
        userGroupNickName = in.readString();
        petName = in.readString();
        phone = in.readString();
        UID = in.readString();
        mobile = in.readString();
        userID = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeByte((byte) (isMuteVideo ? 1 : 0));
        dest.writeByte((byte) (isAccept ? 1 : 0));
        dest.writeString(userId);
        dest.writeString(smallImg);
        dest.writeString(name);
        dest.writeString(nickName);
        dest.writeString(userGroupNickName);
        dest.writeString(petName);
        dest.writeString(phone);
        dest.writeString(UID);
        dest.writeString(mobile);
        dest.writeString(userID);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GroupMenberListdata> CREATOR = new Creator<GroupMenberListdata>() {
        @Override
        public GroupMenberListdata createFromParcel(Parcel in) {
            return new GroupMenberListdata(in);
        }

        @Override
        public GroupMenberListdata[] newArray(int size) {
            return new GroupMenberListdata[size];
        }
    };

    @Override
    public String toString() {
        return "GroupMenberListdata{" +
                "isCheck=" + isCheck +
                ", isMuteVideo=" + isMuteVideo +
                ", isAccept=" + isAccept +
                ", userId='" + userId + '\'' +
                ", smallImg='" + smallImg + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userGroupNickName='" + userGroupNickName + '\'' +
                ", petName='" + petName + '\'' +
                ", phone='" + phone + '\'' +
                ", UID='" + UID + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userID='" + userID + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
