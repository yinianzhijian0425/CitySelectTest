package tech.yunjing.biconlife.jniplugin.im.bean;

import java.util.ArrayList;

/**
 * 群聊群组信息-----包含群成员信息
 * Created by lh on 2017/8/30.
 */

public class GroupMembersAllObj {
    public String message;
    public int code;
    public GroupMembersObj data;

    public class GroupMembersObj {
        /**
         * 群聊id
         */
        public String groupId;
        /**
         * 群聊名称
         */
        public String groupName;
        /**
         * 群聊头像
         */
        public String groupImage;
        /**
         * 群公告
         */
        public String groupAnnouncement;
        /**
         * 查询者id
         */
        public String userId;
        /**
         * 查询者昵称
         */
        public String userNickname;
        /**
         * 是否保存至通讯录 0保存 1不保存
         */
        public String saveState;
        /**
         * 查询者群昵称
         */
        public String userGroupNickName;
        /**
         * 查询者头像缩略图
         */
        public String userSmallImg;
        /**
         * 查询者权限 "1":群主 "0":普通成员
         */
        public String userPermission;
        /**
         * 群类型 "1"：私有群 "0"：公共群
         */
        public String groupPublic;
        /**
         * 群聊总人数
         */
        public String groupCount;
        /**
         * 群聊最大人数
         */
        public String groupMax;
        /**
         * 创建者id
         */
        public String creatorId;
        /**
         * 创建者头像
         */
        public String creatorImage;

        /**
         * 群成员信息
         */
        public ArrayList<GroupMenberListdata> userInfoList;


    }
}
