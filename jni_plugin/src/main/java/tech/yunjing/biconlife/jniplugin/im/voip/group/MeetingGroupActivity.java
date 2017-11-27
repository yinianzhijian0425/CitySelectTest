package tech.yunjing.biconlife.jniplugin.im.voip.group;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.voip.AgoraManager;
import tech.yunjing.biconlife.jniplugin.im.voip.HxTestKey;
import tech.yunjing.biconlife.jniplugin.im.voip.IMNotifier;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingWindowInter;
import tech.yunjing.biconlife.jniplugin.im.voip.server.MeetingGroupActivityServer;
import tech.yunjing.biconlife.jniplugin.im.voip.util.PlayerTelephoneReceiver;
import tech.yunjing.biconlife.jniplugin.im.voip.view.MeetingIncomeAddPic;
import tech.yunjing.biconlife.jniplugin.im.voip.view.MeetingLayoutAddPic;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKAppUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPermissionUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.activity.LKBaseActivity;

/**
 * 音视频群聊
 * Created by Chen.qi on 2017/8/29 0029.
 */

public class MeetingGroupActivity extends LKBaseActivity implements CenterMeetingWindowInter, View.OnClickListener {
    /**
     * 最小化按钮
     */
    private ImageView iv_meeting_min;
    /**
     * 所有成员的总布局
     */
    private MeetingLayoutAddPic ll_meeting_allMember;
    /**
     * 接听方的显示头像邀请的总布局
     */
    private LinearLayout ll_meeting_head;
    /**
     * 接收人收到邀请，显示邀请人的头像
     */
    private ImageView iv_meeting_avatar;

    /**
     * 邀请人的昵称
     */
    private TextView tv_meeting_nickUser;

    /**
     * 邀请人的提示语(邀请你视频聊天)
     */
    private TextView tv_meeting_callMsg;

    /**
     * 其他成员的总布局
     */
    private MeetingIncomeAddPic ll_meeting_otherImg;

    /**
     * 总时间
     */
    private Chronometer ct_meeting_timer;

    /**
     * 禁言、免提、摄像头转换总布局
     */
    private LinearLayout ll_meeting_optionAll;

    /**
     * 禁言整个大布局
     */
    private RelativeLayout rl_meeting_mute;

    /**
     * 禁言图标
     */
    private ImageView iv_meeting_mute;

    /**
     * 免提整个大布局
     */
    private RelativeLayout rl_meeting_mt;

    /**
     * 免提的图标按钮
     */
    private ImageView iv_meeting_mt;

    /**
     * 摄像头转换
     */
    private RelativeLayout rl_meeting_camera;

    /**
     * 打开或者关闭摄像头
     */
    private TextView tv_meeting_openOrCloseCream;
    /**
     * 摄像头的图标按钮
     */
    private ImageView iv_meeting_camera;

    /**
     * 所有人都拥有额挂断按钮(接听以后显示,发起方拨打后就显示)
     */
    private ImageView iv_meeting_outEnd;

    /**
     * 接受方来电的总布局
     */
    private LinearLayout ll_meeting_income;

    /**
     * 接听方的挂断按钮
     */
    private ImageView iv_meeting_incomeEnd;

    /**
     * 接听方的接听按钮
     */
    private ImageView iv_meeting_incomeAccept;


    /**
     * 锁屏，屏幕相关所用
     */
    private PowerManager.WakeLock mWakeLock;
    private KeyguardManager.KeyguardLock mKeyguardLock = null;
    protected KeyguardManager mKeyguardManager;

    /**
     * 中央控制器
     */
    protected CenterMeetingController mController;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_social_meeting);
        initAllView();
    }


    /**
     * 初始化View控件
     */
    private void initAllView() {
        ll_meeting_allMember = (MeetingLayoutAddPic) findViewById(R.id.ll_meeting_allMember);
        iv_meeting_min = (ImageView) findViewById(R.id.iv_meeting_min);
        ll_meeting_head = (LinearLayout) findViewById(R.id.ll_meeting_head);
        iv_meeting_avatar = (ImageView) findViewById(R.id.iv_meeting_avatar);
        tv_meeting_nickUser = (TextView) findViewById(R.id.tv_meeting_nickUser);
        tv_meeting_callMsg = (TextView) findViewById(R.id.tv_meeting_callMsg);
        ll_meeting_otherImg = (MeetingIncomeAddPic) findViewById(R.id.ll_meeting_otherImg);
        ct_meeting_timer = (Chronometer) findViewById(R.id.ct_meeting_timer);
        ll_meeting_optionAll = (LinearLayout) findViewById(R.id.ll_meeting_optionAll);
        rl_meeting_mute = (RelativeLayout) findViewById(R.id.rl_meeting_mute);
        iv_meeting_mute = (ImageView) findViewById(R.id.iv_meeting_mute);
        rl_meeting_mt = (RelativeLayout) findViewById(R.id.rl_meeting_mt);
        iv_meeting_mt = (ImageView) findViewById(R.id.iv_meeting_mt);
        rl_meeting_camera = (RelativeLayout) findViewById(R.id.rl_meeting_camera);
        tv_meeting_openOrCloseCream = (TextView) findViewById(R.id.tv_meeting_openOrCloseCream);
        iv_meeting_camera = (ImageView) findViewById(R.id.iv_meeting_camera);
        iv_meeting_outEnd = (ImageView) findViewById(R.id.iv_meeting_outEnd);
        ll_meeting_income = (LinearLayout) findViewById(R.id.ll_meeting_income);
        iv_meeting_incomeEnd = (ImageView) findViewById(R.id.iv_meeting_incomeEnd);
        iv_meeting_incomeAccept = (ImageView) findViewById(R.id.iv_meeting_incomeAccept);
    }


    @Override
    protected void initData() {
        mController = CenterHolder.getInstance().getMeetingController();
        if (mController == null) {
            this.finish();
            return;
        }
        initManagers();//初始化manager们
        mController.addChangeDataListener(this);

    }


    @Override
    protected void initViewEvent() {
        initAllEvent();
        initViewCall();//根据来还是去，控制view们
        if (mController.mIsOutCall) {
            mHandler.sendEmptyMessage(7150);
        } else {
            mHandler.sendEmptyMessage(7151);
        }
    }


    /**
     * 来电还是呼出电话
     */
    private void initViewCall() {
        if (mController.isFirstOpenActivity) {
            AgoraManager.getInstance().setMuteVideo(true);
            CenterMeetingController.MeetingCallState callLayout = mController.mIsOutCall ? CenterMeetingController.MeetingCallState.OUTGOING
                    : CenterMeetingController.MeetingCallState.INCOMING;
            if (mController.mMeetCallState == null) {
                //没有初始化过，才进行初始化赋值。
                boolean isPermission = LKPermissionUtil.getInstance().requestMorePermission(MeetingGroupActivity.this);

                setCallState(callLayout);
            }
        }
        mController.isFirstOpenActivity = false;
    }


    /**
     * 根据来、去电，振铃等，做一些操作
     *
     * @param callDirect
     */
    protected void setCallState(CenterMeetingController.MeetingCallState callDirect) {
        mController.mMeetCallState = callDirect;
        iv_meeting_mute.setSelected(mController.isVoiceMute);
        iv_meeting_mt.setSelected(mController.isVoiceMt);

        if (callDirect == CenterMeetingController.MeetingCallState.INCOMING) {//来电
            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", false);
            ll_meeting_allMember.setVisibility(View.GONE);
            iv_meeting_min.setVisibility(View.GONE);
            ll_meeting_head.setVisibility(View.VISIBLE);
            ll_meeting_income.setVisibility(View.VISIBLE);
            iv_meeting_outEnd.setVisibility(View.GONE);
            ct_meeting_timer.setVisibility(View.GONE);
            ll_meeting_optionAll.setVisibility(View.INVISIBLE);
        } else if (callDirect == CenterMeetingController.MeetingCallState.OUTGOING) {
            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", true);
            iv_meeting_min.setVisibility(View.GONE);
            ll_meeting_allMember.setVisibility(View.VISIBLE);
            ll_meeting_head.setVisibility(View.INVISIBLE);
            ll_meeting_otherImg.setVisibility(View.GONE);
            ct_meeting_timer.setVisibility(View.VISIBLE);
            ll_meeting_optionAll.setVisibility(View.VISIBLE);
            iv_meeting_outEnd.setVisibility(View.VISIBLE);
            ll_meeting_income.setVisibility(View.GONE);
        } else if (callDirect == CenterMeetingController.MeetingCallState.INCALL) {
            iv_meeting_min.setVisibility(View.VISIBLE);
            ll_meeting_allMember.setVisibility(View.VISIBLE);
            ll_meeting_head.setVisibility(View.GONE);
            ct_meeting_timer.setVisibility(View.VISIBLE);
            ll_meeting_optionAll.setVisibility(View.VISIBLE);
            iv_meeting_outEnd.setVisibility(View.VISIBLE);
            ll_meeting_income.setVisibility(View.GONE);
            mHandler.sendEmptyMessage(7150);
        }
    }


    /**
     * 初始化控件点击事件
     */
    private void initAllEvent() {
        iv_meeting_min.setOnClickListener(this);
        iv_meeting_mute.setOnClickListener(this);
        iv_meeting_mt.setOnClickListener(this);
        iv_meeting_camera.setOnClickListener(this);
        iv_meeting_outEnd.setOnClickListener(this);
        iv_meeting_incomeAccept.setOnClickListener(this);
        iv_meeting_incomeEnd.setOnClickListener(this);
    }


    /**
     * 初始化需要的serviceManager 们
     * 屏幕亮屏相关
     */
    private void initManagers() {
        mWakeLock = ((PowerManager) this.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "CALL_ACTIVITY#" + super.getClass().getName());
        mKeyguardManager = ((KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE));
    }

    @Override
    public void finish() {
        mHandler.postDelayed(OnCallFinish, 500);
    }

    /**
     * 延时关闭界面
     */
    final Runnable OnCallFinish = new Runnable() {
        @Override
        public void run() {
            CenterService service = CenterHolder.getInstance().getService();
            if (service != null) {
                service.stopServiceAndClear();
            }
            MeetingGroupActivity.super.finish();
        }
    };

    /**
     * 唤醒屏幕资源
     */
    protected void wakeUpScreenMode() {
        if (!(mWakeLock.isHeld())) {
            mWakeLock.setReferenceCounted(false);
            mWakeLock.acquire();
        }
        mKeyguardLock = this.mKeyguardManager.newKeyguardLock("");
        mKeyguardLock.disableKeyguard();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!VoIPHelper.serviceIsRunning(this)) {
            super.finish();
            return;
        }
        setCallState(mController.mMeetCallState);
        wakeUpScreenMode(); //唤醒屏幕
        IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);

        if (mController != null) {
            mController.hideSmallWindow();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mController != null) {
            mController.hideSmallWindow();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mController != null && !ll_meeting_allMember.addOtherMember) {
            //打开内部界面，不显示小窗口
            mController.showSmallWindow();
        }
        PlayerTelephoneReceiver.getInstance().stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseWakeLock();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.removeChangeDataListener(this);
    }

    /**
     * 开启锁屏,停止屏幕常亮
     */
    private void releaseWakeLock() {
        try {
            if (this.mWakeLock.isHeld()) {
                if (this.mKeyguardLock != null) {
                    this.mKeyguardLock.reenableKeyguard();
                    this.mKeyguardLock = null;
                }
                this.mWakeLock.release();
            }
            return;
        } catch (Exception e) {
        } finally {
            try {
                this.mWakeLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onTimerTick(String time) {
        this.ct_meeting_timer.setText(time);
    }


    @Override
    public void onServiceDestroy() {
        super.finish();
    }

    /**
     * 有人接通
     * <p>
     * 注：由于与ios规定的字段是mobile  所有音视频先关判断全部都是mobile获取发送方手机号
     *
     * @param callImObj
     */
    @Override
    public void onCallAnswered(IMObj callImObj) {
        LKLogUtil.e("result==" + "有人接通了" + callImObj.toString());

        if (mController.mIsOutCall) {
            PlayerTelephoneReceiver.getInstance().stop();
            mController.mMeetCallState = CenterMeetingController.MeetingCallState.INCALL;
        }

        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.get(i).isAccept = true;
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
        mHandler.sendEmptyMessage(7103);
    }

    /**
     * 接听后挂断电话
     * <p>
     * 注：由于与ios规定的字段是mobile  所有音视频先关判断全部都是mobile获取发送方手机号
     *
     * @param callImObj
     */
    @Override
    public void onCallReleased(IMObj callImObj) {
        LKLogUtil.e("result==" + "有人接听后挂断" + callImObj.toString());
        Intent intent = new Intent();
        intent.setAction(HxTestKey.JOIN_REFUSE_OR_CANCEL);
        intent.putExtra(HxTestKey.JOIN_CANCEL_IMOBJ, callImObj);
        sendBroadcast(intent);

        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
        mHandler.sendEmptyMessage(7102);


    }


    /**
     * 有人拒绝
     * <p>
     * 注：由于与ios规定的字段是mobile  所有音视频先关判断全部都是mobile获取发送方手机号
     *
     * @param callImObj
     */
    @Override
    public void onRefuseToAnswer(IMObj callImObj) {
        LKLogUtil.e("result==" + "有人拒绝" + callImObj.toString());
        LKLogUtil.e("result有人拒绝数量==" + mController.allMembers.size());

        Intent intent = new Intent();
        intent.setAction(HxTestKey.JOIN_REFUSE_OR_CANCEL);
        intent.putExtra(HxTestKey.JOIN_CANCEL_IMOBJ, callImObj);
        sendBroadcast(intent);
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
        mHandler.sendEmptyMessage(7102);

        LKLogUtil.e("result有人拒绝数量删除以后的--------------==" + mController.allMembers.size());

    }

    /**
     * 销毁整个会话
     *
     * @param callImObj
     */
    @Override
    public void onCreatorCancel(IMObj callImObj) {
        LKLogUtil.e("onCreatorCancelonCreatorCanceloooooooooooooooooooo==" +
                "销毁房间的回调，收到此回到表示需要销毁整个房间");
        LKToastUtil.showToastLong("通话结束");
        if (mController != null) {
            mController.stopTimer();
        }
        PlayerTelephoneReceiver.getInstance().endMp3(MeetingGroupActivity.this, "playend.mp3", true);
        mHandler.sendEmptyMessageDelayed(11, 2000);
        sendBroadcast(new Intent(HxTestKey.DESTORY_MEETING_ROOMS));
    }

    /**
     * 销毁整个房间
     *
     * @param imObjDes
     */
    @Override
    public void onDestoryRoom(IMObj imObjDes) {
        LKLogUtil.e("onDestoryRoom-----------==" +
                "销毁房间的回调，收到此回到表示需要销毁整个房间");
        LKToastUtil.showToastLong("通话结束");
        if (mController != null) {
            mController.stopTimer();
        }
        PlayerTelephoneReceiver.getInstance().endMp3(MeetingGroupActivity.this, "playend.mp3", true);
        mHandler.sendEmptyMessageDelayed(11, 2000);
        sendBroadcast(new Intent(HxTestKey.DESTORY_MEETING_ROOMS));
    }


    @Override
    public void onLeaveChannelSuccess() {
        LKLogUtil.e("result==" + "其他人员已离线，通话结束");


    }

    /**
     * 有新成员加入
     *
     * @param imObj
     */
    @Override
    public void onJoinNewMembers(IMObj imObj) {
        ArrayList<GroupMenberListdata> joinListdata = LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);
        LKLogUtil.e("result==新家的与会人员" + joinListdata.size());
        if (joinListdata != null && joinListdata.size() > 0) {
            mController.allMembers.addAll(joinListdata);
        }

        //新加的与会人员进行去重，因为最小化的回调中也重复加入了
        for (int i = 0; i < mController.allMembers.size() - 1; i++) {
            for (int j = i + 1; j < mController.allMembers.size(); j++) {
                if (mController.allMembers.get(i).mobile.equals(mController.allMembers.get(j).mobile)) {
                    mController.allMembers.remove(j);
                    j--;
                }
            }
        }

        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
        mHandler.sendEmptyMessage(7106);
    }


    /**
     * 加入频道成功
     *
     * @param channel
     * @param uid
     */
    @Override
    public void onJoinChannelSuccess(String channel, int uid) {
        LKLogUtil.e("result==" + "我自己加入频道成功");
        if (mController != null) {
            MeetingGroupActivityServer.getInstance().joinChannelSuccess(mController);
        }
        mHandler.sendEmptyMessage(7100);
    }


    @Override
    protected void dataUpdating(Message msg) {
        super.dataUpdating(msg);
        switch (msg.what) {
            //关闭页面退出频道
            case 11:
                mController.mMeetCallState = CenterMeetingController.MeetingCallState.END;
                if (mController != null && mController.isCremaSwitch) {
                    mController.isCremaSwitch = false;
                    AgoraManager.getInstance().onSwitchCamera();
                }
                PlayerTelephoneReceiver.getInstance().stop();
                AgoraManager.getInstance().leaveChannel();
                finish();
                break;

            //进入页面显示头像数据
            case 7100://自己接听
                mController.startTimer();
                iv_meeting_min.setVisibility(View.VISIBLE);
                setCallState(mController.mMeetCallState);
                for (int i = 0; i < mController.allMembers.size(); i++) {
                    if (UserInfoManageUtil.getUserInfo().getPhone().equals(mController.allMembers.get(i).mobile)) {
                        mController.allMembers.get(i).isAccept = true;
                        break;
                    }
                }
                ll_meeting_allMember.clearnMeetingMemberViews();
                ll_meeting_allMember.addmeetingMemberView(mController.allMembers, this, mController);
                break;
            //有人拒绝或者接听后挂断
            case 7102:
                //接收方显示
                ll_meeting_otherImg.clearnMeetingIncomeMemberViews();
                ll_meeting_otherImg.addMeetingIncomeMemberView(mController);

                ll_meeting_allMember.clearnMeetingMemberViews();
                ll_meeting_allMember.addmeetingMemberView(mController.allMembers, this, mController);
                if (mController.allMembers.size() <= 1) {
                    mController.stopTimer();
                    mHandler.sendEmptyMessageDelayed(11, 2000);
                    LKToastUtil.showToastShort("聊天结束");
                }
                break;
            //有人接通
            case 7103:
                if (mController.mIsOutCall) {
                    mController.startTimer();
                }
                if (mController.imObj.creatorMobile.equals(UserInfoManageUtil.getUserInfo().getPhone())) {
                    iv_meeting_min.setVisibility(View.VISIBLE);
                }
                ll_meeting_allMember.clearnMeetingMemberViews();
                ll_meeting_allMember.addmeetingMemberView(mController.allMembers, this, mController);
                break;

            //创建者取消
            case 7105:
                ll_meeting_allMember.clearnMeetingMemberViews();
                ll_meeting_allMember.addmeetingMemberView(mController.allMembers, this, mController);
                if (mController.allMembers.size() < 2) {
                    mController.stopTimer();
                    mHandler.sendEmptyMessageDelayed(11, 2000);
                    LKToastUtil.showToastShort("群聊人数少于2人，将结束群聊视频通话");
                }
                break;
            //加入新人以后
            case 7106:

                LKLogUtil.e("result==" + "显示的总数" + mController.allMembers.size());

                if (ll_meeting_allMember.getVisibility() != View.VISIBLE) {
                    //其他人员显示
                    ll_meeting_otherImg.clearnMeetingIncomeMemberViews();
                    ll_meeting_otherImg.addMeetingIncomeMemberView(mController);
                } else {
                    //顶部显示所有与会人员
                    ll_meeting_allMember.clearnMeetingMemberViews();
                    ll_meeting_allMember.addmeetingMemberView(mController.allMembers, this, mController);
                }
                break;
            //自己点击了摄像头的开关
            //打开关闭摄像头逻辑
            //发起方进入
            //接通电话后,最小化后再次进入
            //刷新成员列表数据
            case 7150:
                if (!ll_meeting_allMember.addOtherMember) {
                    iv_meeting_min.setVisibility(View.VISIBLE);
                }
                ll_meeting_allMember.clearnMeetingMemberViews();
                ll_meeting_allMember.addmeetingMemberView(mController.allMembers, this, mController);
                break;
            //接收方收到来电
            case 7151:
                ll_meeting_otherImg.clearnMeetingIncomeMemberViews();
                LK.image().bind(iv_meeting_avatar, mController.imObj.fromAvatar, LKImageOptions.getFITXYOptions(R.mipmap.icon_choose_group_default, 8));
                tv_meeting_nickUser.setText(mController.imObj.fromNick);
                ll_meeting_otherImg.addMeetingIncomeMemberView(mController);
                break;
            default:
                break;
        }
    }


    /**
     * 用户离线
     *
     * @param uid
     * @param reason
     */
    @Override
    public void onUserOffline(int uid, int reason) {
        LKLogUtil.e("result==" + "聊天室有其他人员离线了==" + reason + "----uid----" + uid);

        String UID = uid + "";
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (UID.equals(mController.allMembers.get(i).UID)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
        mHandler.sendEmptyMessage(7102);
    }

    /**
     * 打开关闭远端视频的回调
     *
     * @param uid
     * @param enabled
     */
    @Override
    public void onUserMuteVideo(int uid, boolean enabled) {
        LKLogUtil.e("result==" + "打开关闭远端视频的回调 " + uid + "----------" + enabled);
        for (int i = 0; i < mController.allMembers.size(); i++) {
            String uidSelf = mController.allMembers.get(i).UID.trim();
            if (LKAppUtil.getInstance().isIntegerNum(uidSelf)) {
                int newUid = Integer.parseInt(uidSelf);
                if (newUid == uid) {
                    mController.allMembers.get(i).isAccept = true;
                    mController.allMembers.get(i).isMuteVideo = !enabled;
                    break;
                }
            }
        }
        mHandler.sendEmptyMessage(7150);
    }

    /**
     * 超时未接听
     */
    @Override
    public void timeOutNoAnswer() {
        if (mController.mMeetCallState == CenterMeetingController.MeetingCallState.OUTGOING) {
            //发起方,没有任何一个人接听
            //????????未写完逻辑
            //挂断逻辑
            int isAcceptCount = 0;
            for (int i = 0; i < mController.allMembers.size(); i++) {
                if (mController.allMembers.get(i).isAccept) {
                    isAcceptCount++;
                }
            }
            if (isAcceptCount <= 1) {
                if (mController != null) {
                    MeetingGroupActivityServer.getInstance().cancelAndEnd(mController, this);
                }
                LKToastUtil.showToastLong("无人接听,聊天结束");
                PlayerTelephoneReceiver.getInstance().endMp3(MeetingGroupActivity.this, "playend.mp3", true);
                mHandler.sendEmptyMessageDelayed(11, 2000);
            }
        } else if (mController.mMeetCallState == CenterMeetingController.MeetingCallState.INCOMING) {
            //接收方接收到来电后，不接听45秒后自动管段

            if (mController != null) {
                mController.stopTimer();
                MeetingGroupActivityServer.getInstance().timeOutCancel(mController);
            }

            LKToastUtil.showToastLong("聊天已取消");
            PlayerTelephoneReceiver.getInstance().endMp3(MeetingGroupActivity.this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);
        }

    }


    /**
     * 返回键，缩小窗口等结束界面
     */
    public void dismissFinish() {
        showNotification();
        MeetingGroupActivity.super.finish();
    }


    protected void showNotification() {
        Intent intent = new Intent(LKApplication.getContext(), MeetingGroupActivity.class);
        IMNotifier.getInstance(LKApplication.getContext()).showCallingNotification(intent, "群聊通话中...");
    }


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_meeting_min) { //最小化
            ll_meeting_allMember.addOtherMember = false;
            dismissFinish();
        } else if (id == R.id.iv_meeting_mute) {//禁言
            if (iv_meeting_mute.isSelected()) {
                mController.isVoiceMute = false;
            } else {
                mController.isVoiceMute = true;
            }
            iv_meeting_mute.setSelected(mController.isVoiceMute);
            AgoraManager.getInstance().onMuteclick(iv_meeting_mute.isSelected());

        } else if (id == R.id.iv_meeting_mt) {//免提
            if (iv_meeting_mt.isSelected()) {
                mController.isVoiceMt = false;
            } else {
                mController.isVoiceMt = true;
            }
            iv_meeting_mt.setSelected(mController.isVoiceMt);
            AgoraManager.getInstance().onSpeakerphone(iv_meeting_mt.isSelected());

        } else if (id == R.id.iv_meeting_camera) {//摄像头打开或者关闭
            if (iv_meeting_camera.isSelected()) {
                mController.isOpenOrCloseCrema = false;
                tv_meeting_openOrCloseCream.setText("摄像头");
            } else {
                mController.isOpenOrCloseCrema = true;
                tv_meeting_openOrCloseCream.setText("关闭摄像头");
            }
            iv_meeting_camera.setSelected(mController.isOpenOrCloseCrema);
            AgoraManager.getInstance().setMuteVideo(!mController.isOpenOrCloseCrema);
            mHandler.sendEmptyMessage(7150);

        } else if (id == R.id.iv_meeting_outEnd) {//挂断按钮
            mController.stopTimer();
            mController.mMeetCallState = CenterMeetingController.MeetingCallState.END;

            int isAcceptCount = 0;
            for (int i = 0; i < mController.allMembers.size(); i++) {
                if (mController.allMembers.get(i).isAccept) {
                    isAcceptCount++;
                }
            }
            LKLogUtil.e("result==" + "看看整个销毁数量==" + isAcceptCount);
            if (isAcceptCount <= 2) {
                MeetingGroupActivityServer.getInstance().acceptAfterEnd(mController, this);
            } else {
                MeetingGroupActivityServer.getInstance().guaDuan(mController);
            }
            LKToastUtil.showToastLong("聊天已取消");
            PlayerTelephoneReceiver.getInstance().endMp3(MeetingGroupActivity.this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);
        } else if (id == R.id.iv_meeting_incomeAccept) {//来电接听按钮
            mController.mMeetCallState = CenterMeetingController.MeetingCallState.INCALL;
            PlayerTelephoneReceiver.getInstance().stop();
            //点击接通按钮,加入频道
            //设置前置摄像头预览并开启
            String uid = UserInfoManageUtil.getUserInfo().getPhone().substring(3, UserInfoManageUtil.getUserInfo().getPhone().length());
            AgoraManager.getInstance()
                    .setupLocalVideo(getApplicationContext())
                    .joinChannel(mController.imObj.roomNum, Integer.parseInt(uid));

        } else if (id == R.id.iv_meeting_incomeEnd) {//来电挂断按钮 拒绝
            mController.mMeetCallState = CenterMeetingController.MeetingCallState.RUFUSH;
            mController.stopTimer();
            MeetingGroupActivityServer.getInstance().reJuctPhone(mController);
            if (mController.allMembers.size() <= 2) {
                //发送101消除整个会话
                MeetingGroupActivityServer.getInstance().reJuceEndAll(mController);
            }

            LKToastUtil.showToastLong("聊天已取消");
            PlayerTelephoneReceiver.getInstance().endMp3(MeetingGroupActivity.this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }


    /**
     * 邀请其他人员
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            ArrayList<GroupMenberListdata> membersList = data.getParcelableArrayListExtra("resultMembers");
            LKLogUtil.e("result返回的数据---==" + membersList.size());
            mController.allMembers.addAll(membersList);
            String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
            mController.imObj.members = members;
            mHandler.sendEmptyMessage(7150);
        }
    }

}
