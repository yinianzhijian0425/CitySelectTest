package tech.yunjing.biconlife.jniplugin.im.voip.group;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImSendOption;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMembersAllObj;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.http.JniNetworkInterfaceGetFriend;
import tech.yunjing.biconlife.jniplugin.im.voip.HxTestKey;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPermissionUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.http.lkhttp.LKPostRequest;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.activity.LKBaseActivity;

/**
 * 选择群聊音视频群成员界面
 * Created by Chen.qi on 2017/8/31.
 */
public class ChooseGroupMemberListActivity extends LKBaseActivity implements View.OnClickListener {


    private ChooseGroupMemberListAdapter adapter;

    /**
     * ListView控件
     */
    private ListView lv_choose_members_groupList;

    /**
     * 确定按钮
     */
    private TextView tv_choose_grConfirm;
    /**
     * 返回
     */
    private LinearLayout ll_group_chooseList_back;

    /**
     * 所有群成员列表
     */
    private ArrayList<GroupMenberListdata> mGroupMembers;


    /**
     * 传递过来的集合数据
     */
    private ArrayList<GroupMenberListdata> inputMemberList;

    /**
     * 邀请者正邀请人员的时候，别人挂断或者拒接了传递的数据对象的广播用于更新数据
     */
    private JoinMemberBrocadCast mBrocadCast;

    /**
     * 收到数据改变的广播的数据对象
     */
    private ArrayList<IMObj> cancelList;

    /**
     * 选择的数量
     */
    private int isCheckCount = 0;

    private IMObj imObj;

    private boolean videoPermiss;


    @Override
    protected void initView() {
        setContentView(R.layout.activtiy_check_list_group_member);
        lv_choose_members_groupList = (ListView) findViewById(R.id.lv_choose_members_groupList);
        ll_group_chooseList_back = (LinearLayout) findViewById(R.id.ll_group_chooseList_back);
        tv_choose_grConfirm = (TextView) findViewById(R.id.tv_choose_grConfirm);
        ll_group_chooseList_back.setOnClickListener(this);
        tv_choose_grConfirm.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        initBrocadCast();
        mGroupMembers = new ArrayList<>();
        inputMemberList = new ArrayList<>();
        cancelList = new ArrayList<>();
        cancelList.clear();

        try {
            imObj = (IMObj) getIntent().getSerializableExtra(HxTestKey.PIN_ZHUANG_GROUP_IMOBJ);
            if (imObj != null && !TextUtils.isEmpty(imObj.members)) {
                ArrayList<GroupMenberListdata> groupMenberListdatas =
                        LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);

                if (groupMenberListdatas != null) {
                    inputMemberList.clear();
                    inputMemberList.addAll(groupMenberListdatas);//已经选择的成员
                    for (int i = 0; i < inputMemberList.size(); i++) {
                        if (TextUtils.isEmpty(inputMemberList.get(i).UID)) {
                            inputMemberList.get(i).UID = inputMemberList.get(i).phone.substring(3, inputMemberList.get(i).phone.length());
                        }
                        if (TextUtils.isEmpty(inputMemberList.get(i).mobile)) {
                            inputMemberList.get(i).mobile = inputMemberList.get(i).phone;
                        }
                        if (TextUtils.isEmpty(inputMemberList.get(i).avatar)) {
                            inputMemberList.get(i).avatar = inputMemberList.get(i).smallImg;
                        }
                        if (TextUtils.isEmpty(inputMemberList.get(i).userID)) {
                            inputMemberList.get(i).userID = inputMemberList.get(i).userId;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new ChooseGroupMemberListAdapter(mGroupMembers, this);
        lv_choose_members_groupList.setAdapter(adapter);
    }


    @Override
    protected void initViewEvent() {
        requestAllMembers();
        tv_choose_grConfirm.setClickable(false);
        lv_choose_members_groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupMenberListdata item = (GroupMenberListdata) adapter.getItem(position);
                if (item.isCheck) {
                    item.isCheck = false;
                } else {
                    item.isCheck = true;
                }
                adapter.notifyDataSetChanged();
                isCheckCount = 0;
                for (int i = 0; i < mGroupMembers.size(); i++) {
                    if (mGroupMembers.get(i).isCheck) {
                        isCheckCount++;
                    }
                }
                boolean isMaxMembers = false;
                if (isCheckCount > 6 - inputMemberList.size()) {
                    LKToastUtil.showToastShort("最多选择6个人同时进行音视频");
                    item.isCheck = false;
                    isMaxMembers = true;
                    adapter.notifyDataSetChanged();
                }

                boolean isHavaSelectedTrue = false;
                for (int i = 0; i < mGroupMembers.size(); i++) {
                    if (isMaxMembers && mGroupMembers.get(i).isCheck) {
                        isHavaSelectedTrue = true;
                    }
                }


                if (isMaxMembers && !isHavaSelectedTrue) {
                    tv_choose_grConfirm.setClickable(false);
                    tv_choose_grConfirm.setSelected(false);
                } else {
                    if (isCheckCount > 1) {
                        tv_choose_grConfirm.setClickable(true);
                        tv_choose_grConfirm.setSelected(true);
                    } else {
                        tv_choose_grConfirm.setClickable(false);
                        tv_choose_grConfirm.setSelected(false);
                        if (isCheckCount != 0 && inputMemberList.size() > 0) {
                            tv_choose_grConfirm.setClickable(true);
                            tv_choose_grConfirm.setSelected(true);
                        }
                    }
                }
            }
        });

    }


    /**
     * 请求成员数据
     */
    private void requestAllMembers() {
        String url = JniNetworkInterfaceGetFriend.SocialHttpGetGroupMember;
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", UserInfoManageUtil.getUserId());
        params.put("token", UserInfoManageUtil.getUserToken());
        params.put("uuId", UserInfoManageUtil.getIMEI());
        params.put("groupId", imObj.groupID);
        LKLogUtil.e("result新的数据==" + params.toString());
        LKPostRequest.getData(mHandler, url, params, GroupMembersAllObj.class, true);
    }


    @Override
    protected void getData(Message msg) {
        super.getData(msg);
        if (msg.obj instanceof GroupMembersAllObj) {
            try {
                GroupMembersAllObj obj = (GroupMembersAllObj) msg.obj;
                if (obj != null && obj.code == 200 && obj.data != null) {
                    ArrayList<GroupMenberListdata> userInfoList = obj.data.userInfoList;
                    if (userInfoList != null) {
                        //去除服务器返回的自己
                        for (int i = 0; i < userInfoList.size(); i++) {
                            if (UserInfoManageUtil.getUserId().equals(userInfoList.get(i).userId)) {
                                userInfoList.remove(i);
                                break;
                            }
                        }

                        if (TextUtils.isEmpty(imObj.members)) {
                            mGroupMembers.addAll(userInfoList);//添加总的数据
                        } else {
                            boolean isR;
                            for (int i = 0; i < userInfoList.size(); ) {
                                isR = false;
                                for (int j = 0; j < inputMemberList.size(); j++) {
                                    if (userInfoList.get(i).phone.equals(inputMemberList.get(j).mobile)) {
                                        userInfoList.remove(i);
                                        isR = true;
                                        i = 0;
                                        break;
                                    }
                                }
                                if (isR) {
                                    continue;
                                }
                                i++;
                            }
                            if (userInfoList != null && userInfoList.size() > 0) {
                                mGroupMembers.addAll(userInfoList);//添加总的数据
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case LKPermissionUtil.EXTERNAL_STORAGE_REQ_CODE_VIDEO:
                //视频通话权限
                boolean isAUDIO = false;
                boolean CMERA = false;
                boolean STOREGE = false;
                for (int j = 0; j < permissions.length; j++) {
                    if (Manifest.permission.RECORD_AUDIO.equals(permissions[j])
                            && grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                        isAUDIO = true;
                    }
                    if (Manifest.permission.CAMERA.equals(permissions[j])
                            && grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                        CMERA = true;
                    }
                    if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[j])
                            && grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                        STOREGE = true;
                    }
                }

//                if (isAUDIO && CMERA && STOREGE) {
//                    if (!videoPermiss) {
//                        VoIPHelper.callMeetingAction(imObj.groupID, imObj, ChooseGroupMemberListActivity.this);
//                        finish();
//                    }
//                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_group_chooseList_back) {//返回按钮
            finish();
        } else if (v.getId() == R.id.tv_choose_grConfirm) {//确定按钮
            if (TextUtils.isEmpty(imObj.members)) {
                isCheckCount = 0;
                for (int i = 0; i < mGroupMembers.size(); i++) {
                    if (mGroupMembers.get(i).isCheck) {
                        isCheckCount++;
                    }
                }
                if (isCheckCount < 2) {
                    LKToastUtil.showToastShort("最少3个人才能进行音视频通话");
                    return;
                }
                creatorGroupVoice();
                videoPermiss = LKPermissionUtil.getInstance().requestMorePermission(ChooseGroupMemberListActivity.this);
                if (videoPermiss) {
                    VoIPHelper.callMeetingAction(imObj.groupID, imObj, ChooseGroupMemberListActivity.this);
                    finish();
                }
            } else {
                joinNewMember();
                finish();
            }
        }
    }


    /**
     * 创建者直接发送，直接创建多人音视频
     */
    private void creatorGroupVoice() {
        //选中的人员
        ArrayList<GroupMenberListdata> checkList = new ArrayList<>();
        GroupMenberListdata mySelf = new GroupMenberListdata();
        mySelf.UID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
        mySelf.smallImg = UserInfoManageUtil.getUserInfo().getLargeImg();
        mySelf.userId = UserInfoManageUtil.getUserId();
        mySelf.phone = UserInfoManageUtil.getUserInfo().getPhone();
        mySelf.isAccept = true;
        mySelf.mobile = UserInfoManageUtil.getUserInfo().getPhone();
        mySelf.nickName = UserInfoManageUtil.getUserInfo().getPhone();
        mySelf.userID = UserInfoManageUtil.getUserId();
        checkList.add(mySelf);
        for (int i = 0; i < mGroupMembers.size(); i++) {
            if (mGroupMembers.get(i).isCheck) {
                checkList.add(mGroupMembers.get(i));
            }
        }
        for (int i = 0; i < checkList.size(); i++) {
            if (TextUtils.isEmpty(checkList.get(i).UID)) {
                checkList.get(i).UID = checkList.get(i).phone.substring(3, checkList.get(i).phone.length());
            }
            if (TextUtils.isEmpty(checkList.get(i).mobile)) {
                checkList.get(i).mobile = checkList.get(i).phone;
            }
            if (TextUtils.isEmpty(checkList.get(i).avatar)) {
                checkList.get(i).avatar = checkList.get(i).smallImg;
            }
            if (TextUtils.isEmpty(checkList.get(i).userID)) {
                checkList.get(i).userID = checkList.get(i).userId;
            }
        }
        //发起音视频逻辑
        String members = LKJsonUtil.arrayListToJsonString(checkList);
        //member数组集合数据
        imObj.members = members;
        LKLogUtil.e("result看看所有集合==" + imObj.members);
    }


    /**
     * 添加新人的逻辑
     */
    private void joinNewMember() {
        ArrayList<GroupMenberListdata> allList = new ArrayList<>();
        //选中的人员
        ArrayList<GroupMenberListdata> checkList = new ArrayList<>();
        //再次邀请加入其它人的逻辑
        for (int i = 0; i < mGroupMembers.size(); i++) {
            if (mGroupMembers.get(i).isCheck) {
                checkList.add(mGroupMembers.get(i));
            }
        }

        for (int i = 0; i < checkList.size(); i++) {
            if (TextUtils.isEmpty(checkList.get(i).UID)) {
                checkList.get(i).UID = checkList.get(i).phone.substring(3, checkList.get(i).phone.length());
            }
            if (TextUtils.isEmpty(checkList.get(i).mobile)) {
                checkList.get(i).mobile = checkList.get(i).phone;
            }
            if (TextUtils.isEmpty(checkList.get(i).avatar)) {
                checkList.get(i).avatar = checkList.get(i).smallImg;
            }
            if (TextUtils.isEmpty(checkList.get(i).userID)) {
                checkList.get(i).userID = checkList.get(i).userId;
            }
        }

        //传递过来的成员
        if (inputMemberList != null) {
            allList.addAll(inputMemberList);
        }
        allList.addAll(checkList);
        LKLogUtil.e("result==" + "看看所有数据" + allList.size());

        for (int i = 0; i < allList.size(); i++) {
            if (TextUtils.isEmpty(allList.get(i).UID)) {
                allList.get(i).UID = allList.get(i).phone.substring(3, allList.get(i).phone.length());
            }
            if (TextUtils.isEmpty(allList.get(i).mobile)) {
                allList.get(i).mobile = allList.get(i).phone;
            }
            if (TextUtils.isEmpty(allList.get(i).avatar)) {
                allList.get(i).avatar = allList.get(i).smallImg;
            }
            if (TextUtils.isEmpty(allList.get(i).userID)) {
                allList.get(i).userID = allList.get(i).userId;
            }

            for (int j = 0; j < cancelList.size(); j++) {
                String fromMobile = cancelList.get(j).fromMobile;
                if (!TextUtils.isEmpty(fromMobile) && fromMobile.equals(allList.get(i).mobile)) {
                    allList.remove(i);
                }
            }
        }
        LKLogUtil.e("result==" + "状态改变后" + allList.size());
        //发起音视频逻辑
        String membersAll = LKJsonUtil.arrayListToJsonString(allList);


        String membersJoin = LKJsonUtil.arrayListToJsonString(checkList);
        if (checkList.size() > 0) {
            //拼装数据----------------
            IMObj joinObj = new IMObj();
            joinObj.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateUNPutThough;
            joinObj.creatorMobile = imObj.creatorMobile;
            joinObj.creatorName = imObj.creatorName;
            joinObj.creatorUID = imObj.creatorMobile.substring(3, imObj.creatorMobile.length());
            joinObj.creatorUserID = imObj.creatorUserID;
            joinObj.fromAvatar = UserInfoManageUtil.getUserInfo().getLargeImg();
            joinObj.fromMobile = UserInfoManageUtil.getUserInfo().getPhone();
            joinObj.fromNick = UserInfoManageUtil.getUserInfo().getNickName();
            joinObj.fromUID = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
            joinObj.groupAvatar = imObj.groupAvatar;
            joinObj.groupID = imObj.groupID;
            joinObj.groupNick = imObj.groupNick;
            //创建json集合
            joinObj.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT;
            joinObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateReceived;
            joinObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice;
            joinObj.roomNum = imObj.roomNum;
            for (int i = 0; i < checkList.size(); i++) {
                //新人 BKL_SocialChatMultimediaMessageTypeInvite
                joinObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeInvite;
                joinObj.members = membersAll;
                if (!TextUtils.isEmpty(checkList.get(i).userID)) {
                    MyImSendOption.getInstance().sendTextJoinOther(checkList.get(i).userID, joinObj);
                }
                LKLogUtil.e("result==" + "已选择的人员id==" + checkList.get(i).userID);
                LKLogUtil.e("result==" + "已选择的人的对象==" + joinObj.toString());
            }

            for (int i = 0; i < inputMemberList.size(); i++) {
                //存在人用 BKL_SocialChatMultimediaMessageTypeMemberJoined
                joinObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeMemberJoined;
                joinObj.members = membersJoin;
                if (UserInfoManageUtil.getUserId().equals(inputMemberList.get(i).userID)) {
                    continue;
                }
                if (!TextUtils.isEmpty(inputMemberList.get(i).userID)) {
                    MyImSendOption.getInstance().sendTextJoinOther(inputMemberList.get(i).userID, joinObj);
                }
                LKLogUtil.e("result==" + "传递过来的人员id==" + inputMemberList.get(i).userID);
                LKLogUtil.e("result==" + "传递过来的人的对象==" + joinObj.toString());
            }
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("resultMembers", checkList);
            setResult(100, intent);
        }
    }


    /**
     * 注册接听、挂断、房间销毁的广播
     */
    private void initBrocadCast() {
        IntentFilter intentFilter = new IntentFilter();
        mBrocadCast = new JoinMemberBrocadCast();
        intentFilter.addAction(HxTestKey.JOIN_REFUSE_OR_CANCEL);
        intentFilter.addAction(HxTestKey.DESTORY_MEETING_ROOMS);
        registerReceiver(mBrocadCast, intentFilter);
    }

    private class JoinMemberBrocadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && HxTestKey.JOIN_REFUSE_OR_CANCEL.equals(intent.getAction())) {
                //会议成员挂断、拒接的广播
                try {
                    IMObj canCelObj = (IMObj) intent.getSerializableExtra(HxTestKey.JOIN_CANCEL_IMOBJ);
                    LKLogUtil.e("result加人的时候改变了数据" + canCelObj.toString());
                    if (canCelObj != null) {
                        cancelList.add(canCelObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (intent != null && HxTestKey.DESTORY_MEETING_ROOMS.equals(intent.getAction())) {
                //房间销毁的广播
                LKToastUtil.showToastLong("通话结束");
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBrocadCast != null) {
            unregisterReceiver(mBrocadCast);
        }
    }
}
