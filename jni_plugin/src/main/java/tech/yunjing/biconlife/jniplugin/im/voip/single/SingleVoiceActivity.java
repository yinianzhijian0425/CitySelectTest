package tech.yunjing.biconlife.jniplugin.im.voip.single;

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

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImSendOption;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKBroadcastKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKIntentKeyList;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.AgoraManager;
import tech.yunjing.biconlife.jniplugin.im.voip.IMNotifier;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleWindowInter;
import tech.yunjing.biconlife.jniplugin.im.voip.util.PlayerTelephoneReceiver;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPermissionUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.activity.LKBaseActivity;
import tech.yunjing.biconlife.liblkclass.widget.LKCircleImageView;

/**
 * 单人音频聊天
 * Created by Chen.qi on 2017/8/910 0009.
 */
public class SingleVoiceActivity extends LKBaseActivity implements CenterSingleWindowInter, View.OnClickListener {

    /**
     * 最小化按钮
     */
    private ImageView iv_sva_min;

    /**
     * 头像
     */
    private LKCircleImageView iv_sva_avatar;

    /**
     * 昵称
     */
    private TextView tv_sva_nickUser;

    /**
     * 对方接听，挂断等回调的一个状态
     */
    private TextView tv_sva_callMsg;

    /**
     * 通话时长的一个总布局
     */
    private LinearLayout ll_sva_times;

    /**
     * 通话的时间
     */
    private Chronometer ct_sva_timer;

    /**
     * 禁言，扩音，挂断总布局
     */
    private LinearLayout ll_sva_request;

    /**
     * 静音ImageView
     */
    private ImageView iv_sva_mute;

    /**
     * 免提ImageView
     */
    private ImageView iv_sva_mt;

    /**
     * 禁言布局
     */
    private RelativeLayout rl_sva_mute;

    /**
     * 挂断布局
     */
    private RelativeLayout rl_sva_outEnd;

    /**
     * 免提布局
     */
    private RelativeLayout rl_sva_mt;

    /**
     * 来电挂断按钮
     */
    private ImageView iv_sva_incomeEnd;

    /**
     * 来电接听按钮
     */
    private ImageView iv_sva_incomeAccept;

    /**
     * 呼叫方挂断按钮
     */
    private ImageView iv_sva_outEnd;

    /**
     * 带接听出现的接听、挂断的总布局
     */
    private LinearLayout ll_sva_income;

    /**
     * 接收的透传消息--创建的频道
     */
    private String strChannel;

    /**
     * 中央控制器
     */
    protected CenterSingleController mController;

    /**
     * 是否已创建
     */
    private boolean isCreated = false;

    /**
     * 判断在线人数，看是否发送销毁
     */
    private int isCount = 0;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_social_voice_singel);
        initAllView();
        isCreated = true;
    }


    /**
     * 初始化view
     */
    private void initAllView() {
        iv_sva_min = (ImageView) findViewById(R.id.iv_sva_min);
        iv_sva_avatar = (LKCircleImageView) findViewById(R.id.iv_sva_avatar);
        tv_sva_nickUser = (TextView) findViewById(R.id.tv_sva_nickUser);
        tv_sva_callMsg = (TextView) findViewById(R.id.tv_sva_callMsg);
        ll_sva_times = (LinearLayout) findViewById(R.id.ll_sva_times);
        ct_sva_timer = (Chronometer) findViewById(R.id.ct_sva_timer);
        ll_sva_request = (LinearLayout) findViewById(R.id.ll_sva_request);
        iv_sva_mute = (ImageView) findViewById(R.id.iv_sva_mute);
        iv_sva_mt = (ImageView) findViewById(R.id.iv_sva_mt);
        rl_sva_mute = (RelativeLayout) findViewById(R.id.rl_sva_mute);
        rl_sva_outEnd = (RelativeLayout) findViewById(R.id.rl_sva_outEnd);
        rl_sva_mt = (RelativeLayout) findViewById(R.id.rl_sva_mt);
        iv_sva_incomeEnd = (ImageView) findViewById(R.id.iv_sva_incomeEnd);
        iv_sva_incomeAccept = (ImageView) findViewById(R.id.iv_sva_incomeAccept);
        iv_sva_outEnd = (ImageView) findViewById(R.id.iv_sva_outEnd);
        ll_sva_income = (LinearLayout) findViewById(R.id.ll_sva_income);
    }


    @Override
    protected void initData() {
        mController = CenterHolder.getInstance().getSingleController();
        if (mController == null) {
            this.finish();
            return;
        } else {
            strChannel = mController.imObj.roomNum;
            initManagers();
            mController.addChangeDataListener(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (!isCreated) {
            setIntent(intent);
            super.onNewIntent(intent);
            initData();
        }
    }


    @Override
    protected void initViewEvent() {
        initAllEvent();
        initViewCall();//根据来还是去，控制view们

        mHandler.sendEmptyMessage(7005);

    }

    /**
     * 根据来、去电，振铃等，做一些操作
     *
     * @param callDirect
     */
    protected void setCallState(CenterSingleController.CallState callDirect) {
        mController.mCallState = callDirect;
        iv_sva_mute.setSelected(mController.isVoiceMute);
        iv_sva_mt.setSelected(mController.isVoiceMt);
        if (callDirect == CenterSingleController.CallState.INCOMING) {//来电
            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", false);
            ll_sva_request.setVisibility(View.GONE);
            //来电的大布局
            ll_sva_income.setVisibility(View.VISIBLE);
            tv_sva_callMsg.setText("邀请你进行语音聊天");
        } else if (callDirect == CenterSingleController.CallState.OUTGOING) {//拨打方拨打电话
            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", true);
            //发起方的大布局
            ll_sva_request.setVisibility(View.VISIBLE);
            rl_sva_mute.setVisibility(View.GONE);
            rl_sva_outEnd.setVisibility(View.VISIBLE);
            rl_sva_mt.setVisibility(View.GONE);
            //来电的大布局
            ll_sva_income.setVisibility(View.GONE);
            tv_sva_callMsg.setText("正在等待对方接听");
        } else if (callDirect == CenterSingleController.CallState.INCALL_ANSWER) {//接通以后
            iv_sva_min.setVisibility(View.VISIBLE);
            tv_sva_callMsg.setVisibility(View.INVISIBLE);
            ll_sva_times.setVisibility(View.VISIBLE);
            ll_sva_income.setVisibility(View.GONE);
            ll_sva_request.setVisibility(View.VISIBLE);
            rl_sva_mute.setVisibility(View.VISIBLE);
            rl_sva_outEnd.setVisibility(View.VISIBLE);
            rl_sva_mt.setVisibility(View.VISIBLE);

        } else if (callDirect == CenterSingleController.CallState.RUFUSH) {
//            isInitiartor = true;
//            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", true);
//            mHandler.sendEmptyMessageDelayed(7, 30 * 1000);
//            //发起方的大布局
//            ll_sva_request.setVisibility(View.VISIBLE);
//            rl_sva_mute.setVisibility(View.GONE);
//            rl_sva_outEnd.setVisibility(View.VISIBLE);
//            rl_sva_mt.setVisibility(View.GONE);
//            //来电的大布局
//            ll_sva_income.setVisibility(View.GONE);

        } else if (callDirect == CenterSingleController.CallState.END) {


        }
    }


    /**
     * 接通以后
     */
    private void setCallReceiver() {
        iv_sva_min.setVisibility(View.VISIBLE);
        tv_sva_callMsg.setVisibility(View.INVISIBLE);
        ll_sva_times.setVisibility(View.VISIBLE);
        ll_sva_income.setVisibility(View.GONE);
        ll_sva_request.setVisibility(View.VISIBLE);
        rl_sva_mute.setVisibility(View.VISIBLE);
        rl_sva_outEnd.setVisibility(View.VISIBLE);
        rl_sva_mt.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化控件点击事件
     */
    private void initAllEvent() {
        iv_sva_min.setOnClickListener(this);
        iv_sva_mute.setOnClickListener(this);
        iv_sva_outEnd.setOnClickListener(this);
        iv_sva_mt.setOnClickListener(this);
        iv_sva_incomeEnd.setOnClickListener(this);
        iv_sva_incomeAccept.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_sva_min) { //最小化
            dismissFinish();
        } else if (view.getId() == R.id.iv_sva_mute) {  //禁言
            if (iv_sva_mute.isSelected()) {
                mController.isVoiceMute = false;
            } else {
                mController.isVoiceMute = true;
            }
            iv_sva_mute.setSelected(mController.isVoiceMute);
            AgoraManager.getInstance().onMuteclick(iv_sva_mute.isSelected());
        } else if (view.getId() == R.id.iv_sva_mt) { //免提
            if (iv_sva_mt.isSelected()) {
                mController.isVoiceMt = false;
            } else {
                mController.isVoiceMt = true;
            }
            iv_sva_mt.setSelected(mController.isVoiceMt);
            AgoraManager.getInstance().onSpeakerphone(iv_sva_mt.isSelected());
        } else if (view.getId() == R.id.iv_sva_outEnd) { //挂断
            mController.mCallState = CenterSingleController.CallState.END;
            LKToastUtil.showToastLong("聊天已取消");
            AgoraManager.getInstance().leaveChannel();
            iv_sva_outEnd.setEnabled(false);
            mController.stopTimer();
            mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;

            if (mController.mIsOutCall) {
                if (mController.isAccept) {
                    mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
                    //自己接听后挂断后的操作----发起方
                    insertTextMessage("通话时长：" + ct_sva_timer.getText().toString().trim());
                } else {
                    mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp;
                    //对方尚未接听自己主动挂断
                    insertTextMessage("已取消");
                }
            } else {
                mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
                //自己接听后挂断后的操作----接受方
                insertTextMessage("通话时长：" + ct_sva_timer.getText().toString().trim());
            }

            mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
            MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);


            IMObj endO = mController.imObj;
            IMObj imObjEnd = new IMObj();
            imObjEnd.creatorMobile = endO.creatorMobile;
            imObjEnd.tipMessage = "语音通话已结束";
            if (mController.mIsOutCall) {
                if (mController.isAccept) {
                    imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
                } else {
                    imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateCancel;
                }
            } else {
                imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStatePutThough;
            }
            imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
            imObjEnd.groupNick = endO.groupNick;
            imObjEnd.nick = UserInfoManageUtil.getUserInfo().getNickName();
            imObjEnd.groupID = endO.groupID;
            imObjEnd.avatar = UserInfoManageUtil.getUserInfo().getLargeImg();
            imObjEnd.groupAvatar = endO.groupAvatar;
            String timeAll = ct_sva_timer.getText().toString().trim();
            String strTime = "0";
            if (!TextUtils.isEmpty(timeAll) && timeAll.contains(":")) {
                String[] split = timeAll.split(":");
                if (split.length == 2) {
                    //分钟
                    String min = split[0];
                    String sec = split[1];
                    strTime = (Integer.parseInt(min) * 60 + Integer.parseInt(sec)) + "";
                } else if (split.length == 3) {
                    //小时
                    String hor = split[0];
                    String min = split[1];
                    String sec = split[2];
                    strTime = (Integer.parseInt(hor) * 3600 + Integer.parseInt(min) * 60 + Integer.parseInt(sec)) + "";
                }
            }

            LKLogUtil.e("result总时间==" + strTime);
            imObjEnd.duration = strTime;
            imObjEnd.roomNum = mController.imObj.roomNum;
            imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
            MyImSendOption.getInstance().sendSingleEnd(mController.chatID, imObjEnd);


            PlayerTelephoneReceiver.getInstance().endMp3(SingleVoiceActivity.this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);
        } else if (view.getId() == R.id.iv_sva_incomeEnd) { //来电拒接
            if (mController == null) {
                return;
            }
            //收到邀请自己主动挂断
            insertTextMessage("已拒绝");
            mController.mCallState = CenterSingleController.CallState.RUFUSH;
            LKToastUtil.showToastLong("已拒绝，聊天结束");
            AgoraManager.getInstance().leaveChannel();
            iv_sva_incomeAccept.setEnabled(false);
            iv_sva_incomeEnd.setEnabled(false);

            mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
            mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateRejected;
            mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
            MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);

            IMObj endO = mController.imObj;
            IMObj imObjEnd = new IMObj();
            imObjEnd.creatorMobile = endO.creatorMobile;
            imObjEnd.tipMessage = "语音通话已结束";
            imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateReject;
            imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
            imObjEnd.groupNick = endO.groupNick;
            imObjEnd.nick = UserInfoManageUtil.getUserInfo().getNickName();
            imObjEnd.groupID = endO.groupID;
            imObjEnd.avatar = UserInfoManageUtil.getUserInfo().getLargeImg();
            imObjEnd.groupAvatar = endO.groupAvatar;
            imObjEnd.duration = "0";
            imObjEnd.roomNum = mController.imObj.roomNum;
            imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
            MyImSendOption.getInstance().sendSingleEnd(mController.chatID, imObjEnd);


            PlayerTelephoneReceiver.getInstance().endMp3(this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);
        } else if (view.getId() == R.id.iv_sva_incomeAccept) {  //接听来电
            mController.isVoiceMt = false;
            mController.mCallState = CenterSingleController.CallState.INCALL_ANSWER;
            mController.startTimer();
            iv_sva_incomeAccept.setEnabled(false);
            iv_sva_incomeEnd.setEnabled(false);
            PlayerTelephoneReceiver.getInstance().stop();
            //点击接通按钮,加入频道
            AgoraManager.getInstance()
                    .joinChannel(strChannel, 0);
        }
    }

    /**
     * 来电还是呼出电话
     */
    private void initViewCall() {
        if (mController == null) {
            return;
        }
        CenterSingleController.CallState callLayout = mController.mIsOutCall ? CenterSingleController.CallState.OUTGOING
                : CenterSingleController.CallState.INCOMING;
        if (mController.mCallState == null) {

            //吊起需要的系统权限，以供用户自行选择
            boolean isPermission = LKPermissionUtil.getInstance().requestMorePermission(SingleVoiceActivity.this);

            //没有初始化过，才进行初始化赋值。
            setCallState(callLayout);
        }
        mController.isFirst = false;
    }


    /**
     * 返回键，缩小窗口等结束界面
     */
    public void dismissFinish() {
        showNotification();
        SingleVoiceActivity.super.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    /**
     * 延时关闭界面
     */
    final Runnable OnCallFinish = new Runnable() {
        @Override
        public void run() {
            //呼叫结束
            IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
            //去除悬浮框
            if (mController != null) {
                mController.removeRemoteView();
            }
            CenterService service = CenterHolder.getInstance().getService();
            if (service != null) {
                service.stopServiceAndClear();
            }
            SingleVoiceActivity.super.finish();
        }
    };

    @Override
    public void finish() {
        mHandler.postDelayed(OnCallFinish, 500);
    }

    protected void showNotification() {
        Intent intent = new Intent(this, SingleVoiceActivity.class);
        IMNotifier.getInstance(LKApplication.getContext()).showCallingNotification(intent, "语音通话中...");
    }


    protected PowerManager.WakeLock mWakeLock;

    private KeyguardManager.KeyguardLock mKeyguardLock = null;

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

    protected KeyguardManager mKeyguardManager;

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

    /**
     * 初始化需要的serviceManager 们
     */
    private void initManagers() {
        mWakeLock = ((PowerManager) this.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "CALL_ACTIVITY#" + super.getClass().getName());
        mKeyguardManager = ((KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE));
    }


    @Override
    protected void onResume() {
        super.onResume();
        isCount++;
        if (isCount <= 1) {
            LKLogUtil.e("ronResume返回的服务是否运行==" + VoIPHelper.serviceIsRunning(this));
            if (!VoIPHelper.serviceIsRunning(this)) {
                super.finish();
                return;
            }


            //唤醒屏幕
            wakeUpScreenMode();
            //清楚通知
            IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
            initState();
            if (mController != null) {
                mController.hideSmallWindow();
            }
        }
    }

    /**
     * 重设状态
     */
    protected void initState() {
        //设置静音和扩音要在onresume()设置，设置在oncreate中不管用。
        if (mController != null) {
            setCallState(mController.mCallState);
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
    protected void onPause() {
        super.onPause();
        releaseWakeLock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mController != null) {
            mController.showSmallWindow();
        }
        PlayerTelephoneReceiver.getInstance().stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCount = 0;
        isCreated = false;
        if (mController != null) {
            mController.removeChangeDataListener(this);
        }
    }

    @Override
    protected void dataUpdating(Message msg) {
        super.dataUpdating(msg);
        switch (msg.what) {
            //关闭页面
            case 6:
                AgoraManager.getInstance().leaveChannel();
                finish();
                break;
            //20秒对方未接听，代表对方无人接听
            case 7:
                //来电以后未接听
                if (mController != null && mController.mCallState == CenterSingleController.CallState.OUTGOING) {
                    LKToastUtil.showToastLong("无人接听,聊天结束");
                    PlayerTelephoneReceiver.getInstance().endMp3(SingleVoiceActivity.this, "playend.mp3", true);
                    mHandler.sendEmptyMessageDelayed(6, 2000);
                } else if (mController != null && mController.mCallState == CenterSingleController.CallState.INCOMING) {


                    LKToastUtil.showToastLong("聊天结束");
                    PlayerTelephoneReceiver.getInstance().endMp3(SingleVoiceActivity.this, "playend.mp3", true);
                    mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
                    mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateNotResponding;
                    mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
                    MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);


                    //接收方超时未接听
                    IMObj endO = mController.imObj;
                    IMObj imObjEnd = new IMObj();
                    imObjEnd.creatorMobile = endO.creatorMobile;
                    imObjEnd.tipMessage = "语音通话已结束";
                    imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateCancel;
                    imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
                    imObjEnd.groupNick = endO.groupNick;
                    imObjEnd.nick = UserInfoManageUtil.getUserInfo().getNickName();
                    imObjEnd.groupID = endO.groupID;
                    imObjEnd.avatar = UserInfoManageUtil.getUserInfo().getLargeImg();
                    imObjEnd.groupAvatar = endO.groupAvatar;
                    String strTime = "0";
                    imObjEnd.duration = strTime;
                    imObjEnd.roomNum = mController.imObj.roomNum;
                    imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
                    MyImSendOption.getInstance().sendSingleEnd(mController.chatID, imObjEnd);


                    mHandler.sendEmptyMessageDelayed(6, 2000);
                }
                break;
            //接受聊天邀请
            case 8:
                if (mController != null) {
                    AgoraManager.getInstance().onSpeakerphone(mController.isVoiceMt);
                }
                setCallReceiver();
                break;
            //拒绝接听
            case 9:
                LKToastUtil.showToastLong("对方拒绝了你的聊天请求");
                PlayerTelephoneReceiver.getInstance().endMp3(this, "playend.mp3", true);
                mHandler.sendEmptyMessageDelayed(11, 2000);
                break;
            //通话结束
            case 10:
                ct_sva_timer.stop();
                LKToastUtil.showToastLong("对方挂断，通话结束");
                AgoraManager.getInstance().leaveChannel();
                PlayerTelephoneReceiver.getInstance().endMp3(SingleVoiceActivity.this, "playend.mp3", true);
                mHandler.sendEmptyMessageDelayed(11, 2000);
                break;
            //关闭页面退出频道
            case 11:
                PlayerTelephoneReceiver.getInstance().stop();
                AgoraManager.getInstance().leaveChannel();
                if (mController != null) {
                    mController.removeRemoteView();
                }
                finish();
                break;
            case 7005:
                if (mController != null) {
                    if (mController.mIsOutCall) {
                        tv_sva_nickUser.setText(mController.mCalledName);
                        LK.image().bind(iv_sva_avatar, mController.mCalledAvatar, LKImageOptions.getFITXYOptions(R.mipmap.default_user_voip_avatar));
                    } else {
                        tv_sva_nickUser.setText(mController.imObj.fromNick);
                        LK.image().bind(iv_sva_avatar, mController.imObj.fromAvatar, LKImageOptions.getFITXYOptions(R.mipmap.default_user_voip_avatar));
                    }
                }
                break;
            default:
                break;

        }
    }

    ////所有回调操作---------------------------------------------------------------------------------------------

    @Override
    public void onTimerTick(String chronometer) {
        this.ct_sva_timer.setText(chronometer);
    }


    @Override
    public void onServiceDestroy() {

        super.finish();
    }


    /**
     * 对端应答，通话计时开始
     *
     * @param call 通话的数据
     */
    @Override
    public void onCallAnswered(String call) {
        LKLogUtil.e("result单聊页面==" + "对方应答");
        if (mController != null) {
            mController.isAccept = true;
        }
        mHandler.sendEmptyMessage(8);


    }

    /**
     * 对方挂断
     *
     * @param call
     */
    @Override
    public void onCallReleased(String call) {
        LKLogUtil.e("result单聊页面==" + "对方挂断或者拒绝");
        mHandler.sendEmptyMessage(10);


    }

    /**
     * 对方拒绝接听
     *
     * @param call
     */
    @Override
    public void onRefuseToAnswer(String call) {
        mHandler.sendEmptyMessage(9);
    }

    /**
     * 超时未接听
     */
    @Override
    public void timeOutNoAnswer() {

        mHandler.sendEmptyMessage(7);

    }

    @Override
    public void onGetRemoteVideo(int uid) {

    }

    @Override
    public void onLeaveChannelSuccess() {
        LKLogUtil.e("result==" + "对方离线");
        mHandler.sendEmptyMessageDelayed(11, 2000);
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        LKLogUtil.e("result==" + "用户离线时的回调");
        mHandler.sendEmptyMessageDelayed(11, 2000);
    }

    /**
     * 加入成功(接受邀请以后所走的回调)
     *
     * @param channel
     * @param uid
     */
    @Override
    public void onJoinChannelSuccess(String channel, int uid) {
        mHandler.sendEmptyMessage(8);
        mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
        mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateReceived;
        mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice;
        mController.imObj.fromUID = uid + "";
        MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);
    }

    /**
     * 插入消息到数据库
     */
    private void insertTextMessage(String content) {
        String userId = UserInfoManageUtil.getUserInfo().getUserId();
        String smallImg = UserInfoManageUtil.getUserInfo().getLargeImg();
        String nickName = UserInfoManageUtil.getUserInfo().getNickName();
        EMMessage message = EMMessage.createTxtSendMessage(content, mController.chatID);
        //如果是群聊，设置chattype，默认是单聊
        message.setAttribute("messageType", LKOthersFinalList.MSGTYPE_TEXT_VOICEANDVIDEO);//消息类型为音视频消息

        message.setAttribute("conversationType", LKOthersFinalList.CONVERSATIONTYPE_SINGLE);//消息类型(0,单聊,1群聊)
        message.setAttribute("userID", userId);//用户id
        message.setAttribute("avatar", smallImg);//用户头像
        message.setAttribute("nick", nickName);//用户昵称
        message.setAttribute("multimediaType", mController.imObj.multimediaType);

        if (mController.mIsOutCall) {
            message.setAttribute("chatAvatar", mController.mCalledAvatar);
            message.setAttribute("chatNick", mController.mCalledName);
        } else {
            message.setAttribute("chatAvatar", mController.imObj.fromAvatar);
            message.setAttribute("chatNick", mController.imObj.fromNick);
        }

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mController.chatID);
        if (conversation!=null){
        conversation.insertMessage(message);}
        sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
        Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + mController.chatID);
        intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
        sendBroadcast(intent);
    }
}
