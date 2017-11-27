package tech.yunjing.biconlife.jniplugin.im.voip.single;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
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

/**
 * 单人视频聊天
 * Created by Chen.qi on 2017/8/17
 */
public class SingleVideoActivity extends LKBaseActivity implements CenterSingleWindowInter, View.OnClickListener {

    /**
     * 最小化按钮
     */
    private ImageView iv_video_min;

    /**
     * 头像等的总布局
     */
    private LinearLayout ll_video_head;

    /**
     * 头像
     */
    private ImageView iv_video_avatar;


    /**
     * SufrfaceView视频显示整体部分
     */
    private RelativeLayout rl_video_accept;

    /**
     * 别人的图像
     */
    private FrameLayout remote_video_view_container;

    /**
     * 自己的图像
     */
    private FrameLayout local_video_view_container;

    /**
     * 昵称
     */
    private TextView tv_video_nickUser;

    /**
     * 对方接听，挂断等回调的一个状态
     */
    private TextView tv_video_callMsg;

    /**
     * 通话时长的一个总布局
     */
    private LinearLayout ll_video_times;

    /**
     * 通话的时间
     */
    private Chronometer ct_video_timer;

    /**
     * 禁言，扩音，挂断总布局
     */
    private LinearLayout ll_video_request;

    /**
     * 摄像头转换ImageView
     */
    private ImageView iv_video_camera;

    /**
     * 免提ImageView
     */
    private ImageView iv_video_mt;

    /**
     * 摄像头布局
     */
    private RelativeLayout rl_video_camera;

    /**
     * 挂断布局
     */
    private RelativeLayout rl_video_outEnd;

    /**
     * 免提布局
     */
    private RelativeLayout rl_video_mt;

    /**
     * 来电挂断按钮
     */
    private ImageView iv_video_incomeEnd;

    /**
     * 来电接听按钮
     */
    private ImageView iv_video_incomeAccept;

    /**
     * 呼叫方挂断按钮
     */
    private ImageView iv_video_outEnd;

    /**
     * 带接听出现的接听、挂断的总布局
     */
    private LinearLayout ll_video_income;

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


    @Override
    protected void initView() {
        setContentView(R.layout.activity_social_video);
        initAllView();
        isCreated = true;
    }


    /**
     * 初始化view
     */
    private void initAllView() {
        iv_video_min = (ImageView) findViewById(R.id.iv_video_min);
        ll_video_head = (LinearLayout) findViewById(R.id.ll_video_head);
        iv_video_avatar = (ImageView) findViewById(R.id.iv_video_avatar);
        rl_video_accept = (RelativeLayout) findViewById(R.id.rl_video_accept);
        remote_video_view_container = (FrameLayout) findViewById(R.id.remote_video_view_container);
        local_video_view_container = (FrameLayout) findViewById(R.id.local_video_view_container);
        tv_video_nickUser = (TextView) findViewById(R.id.tv_video_nickUser);
        tv_video_callMsg = (TextView) findViewById(R.id.tv_video_callMsg);
        ll_video_times = (LinearLayout) findViewById(R.id.ll_video_times);
        ct_video_timer = (Chronometer) findViewById(R.id.ct_video_timer);
        ll_video_request = (LinearLayout) findViewById(R.id.ll_video_request);
        iv_video_camera = (ImageView) findViewById(R.id.iv_video_camera);
        iv_video_mt = (ImageView) findViewById(R.id.iv_video_mt);
        rl_video_camera = (RelativeLayout) findViewById(R.id.rl_video_camera);
        rl_video_outEnd = (RelativeLayout) findViewById(R.id.rl_video_outEnd);
        rl_video_mt = (RelativeLayout) findViewById(R.id.rl_video_mt);
        iv_video_incomeEnd = (ImageView) findViewById(R.id.iv_video_incomeEnd);
        iv_video_incomeAccept = (ImageView) findViewById(R.id.iv_video_incomeAccept);
        iv_video_outEnd = (ImageView) findViewById(R.id.iv_video_outEnd);
        ll_video_income = (LinearLayout) findViewById(R.id.ll_video_income);
        iv_video_min.setOnClickListener(this);
        iv_video_camera.setOnClickListener(this);
        iv_video_outEnd.setOnClickListener(this);
        iv_video_mt.setOnClickListener(this);
        iv_video_incomeEnd.setOnClickListener(this);
        iv_video_incomeAccept.setOnClickListener(this);
        remote_video_view_container.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        mController = CenterHolder.getInstance().getSingleController();
        if (mController != null) {
            mController.addChangeDataListener(this);
        } else {
            this.finish();
            return;
        }
        if (mController != null) {
            strChannel = mController.imObj.roomNum;
        }
        initManagers();
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
        if (callDirect == CenterSingleController.CallState.INCOMING) {//来电
            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", false);
            ll_video_request.setVisibility(View.GONE);
            //来电的大布局
            ll_video_income.setVisibility(View.VISIBLE);
            tv_video_callMsg.setText("邀请你进行视频聊天");
        } else if (callDirect == CenterSingleController.CallState.OUTGOING) {
            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", true);
            //发起方的大布局
            ll_video_request.setVisibility(View.VISIBLE);
            rl_video_camera.setVisibility(View.GONE);
            rl_video_outEnd.setVisibility(View.VISIBLE);
            rl_video_mt.setVisibility(View.GONE);
            //来电的大布局
            ll_video_income.setVisibility(View.GONE);
            tv_video_callMsg.setText("正在等待对方接听");
        } else if (callDirect == CenterSingleController.CallState.INCALL_ANSWER) {//接通以后
            if (isZuixoahua) {
                LKLogUtil.e("result看看这个uid00000000000---------------------==" + mController.mVideoUid);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setCallReceiver();
                        //先清空容器
                        remote_video_view_container.removeAllViews();
                        local_video_view_container.removeAllViews();
//                        //设置本地前置摄像头预览并启动
//                        AgoraManager.getInstance().setupLocalVideo(SingleVideoActivity.this).startPreview();
//                        //将本地摄像头预览的SurfaceView添加到容器中
//                        local_video_view_container.addView(AgoraManager.getInstance().getLocalSurfaceView());
                        AgoraManager.getInstance().setupLocalVideoNew(local_video_view_container, SingleVideoActivity.this);
                        //注意SurfaceView要创建在主线程
                        AgoraManager.getInstance().setupRemoteVideo(SingleVideoActivity.this, mController.mVideoUid);
                        remote_video_view_container.addView(AgoraManager.getInstance().getSurfaceView(mController.mVideoUid));
                        rl_video_accept.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                iv_video_min.setVisibility(View.VISIBLE);
                tv_video_callMsg.setVisibility(View.INVISIBLE);
                ll_video_times.setVisibility(View.VISIBLE);
                ll_video_income.setVisibility(View.GONE);
                ll_video_request.setVisibility(View.VISIBLE);
                rl_video_camera.setVisibility(View.VISIBLE);
                rl_video_outEnd.setVisibility(View.VISIBLE);
                rl_video_mt.setVisibility(View.VISIBLE);
            }


        } else if (callDirect == CenterSingleController.CallState.RUFUSH) {
//            isInitiartor = true;
//            PlayerTelephoneReceiver.getInstance().startMp3(this, "phonering.mp3", true);
//            mHandler.sendEmptyMessageDelayed(7, 30 * 1000);
//            //发起方的大布局
//            ll_video_request.setVisibility(View.VISIBLE);
//            rl_video_camera.setVisibility(View.GONE);
//            rl_video_outEnd.setVisibility(View.VISIBLE);
//            rl_video_mt.setVisibility(View.GONE);
//            //来电的大布局
//            ll_video_income.setVisibility(View.GONE);

        } else if (callDirect == CenterSingleController.CallState.END) {

        }
    }


    /**
     * 接通以后
     */
    private void setCallReceiver() {
        if (mController == null) {
            return;
        }
        iv_video_mt.setSelected(mController.isVideoMT);
        iv_video_camera.setSelected(mController.isVideoCameraSwitch);
        rl_video_accept.setVisibility(View.VISIBLE);
        ll_video_head.setVisibility(View.INVISIBLE);
        iv_video_min.setVisibility(View.VISIBLE);
        tv_video_callMsg.setVisibility(View.INVISIBLE);
        ll_video_times.setVisibility(View.VISIBLE);
        ll_video_income.setVisibility(View.GONE);
        ll_video_request.setVisibility(View.VISIBLE);
        rl_video_camera.setVisibility(View.VISIBLE);
        rl_video_outEnd.setVisibility(View.VISIBLE);
        rl_video_mt.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_video_min) {//最小化
            dismissFinish();
        } else if (id == R.id.iv_video_camera) {  //摄像头转换
            if (mController == null) {
                return;
            }
            if (iv_video_camera.isSelected()) {
                mController.isVideoCameraSwitch = false;
            } else {
                mController.isVideoCameraSwitch = true;
            }
            iv_video_camera.setSelected(mController.isVideoCameraSwitch);
            AgoraManager.getInstance().onSwitchCamera();
        } else if (id == R.id.iv_video_mt) {  //免提
            if (mController == null) {
                return;
            }

            if (iv_video_mt.isSelected()) {
                mController.isVideoMT = false;
            } else {
                mController.isVideoMT = true;
            }
            iv_video_mt.setSelected(mController.isVideoMT);
            AgoraManager.getInstance().onSpeakerphone(mController.isVideoMT);
        } else if (id == R.id.iv_video_outEnd) {   //挂断
            if (mController == null) {
                return;
            }

            mController.stopTimer();
            mController.mCallState = CenterSingleController.CallState.END;
            LKToastUtil.showToastLong("聊天已取消");
            AgoraManager.getInstance().leaveChannel();
            iv_video_outEnd.setEnabled(false);

            mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;

            if (mController.mIsOutCall) {
                if (mController.isAccept) {
                    mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
                    //接听后挂断后的操作----发起方
                    insertTextMessage("通话时长：" + ct_video_timer.getText().toString().trim());
                } else {
                    mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp;
                    //对方尚未接听自己主动挂断
                    insertTextMessage("已取消");
                }
            } else {
                mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateEnd;
                //接听后挂断后的操作----接受方
                insertTextMessage("通话时长：" + ct_video_timer.getText().toString().trim());
            }

            mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
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
            String timeAll = ct_video_timer.getText().toString().trim();
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
            imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
            MyImSendOption.getInstance().sendSingleEnd(mController.chatID, imObjEnd);

            PlayerTelephoneReceiver.getInstance().endMp3(SingleVideoActivity.this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);


        } else if (id == R.id.iv_video_incomeEnd) {//  //来电拒接
            if (mController == null) {
                return;
            }

            //收到邀请自己主动挂断
            insertTextMessage("已拒绝");

            mController.mCallState = CenterSingleController.CallState.RUFUSH;
            LKToastUtil.showToastLong("已拒绝，聊天结束");
            AgoraManager.getInstance().leaveChannel();
            iv_video_incomeAccept.setEnabled(false);
            iv_video_incomeEnd.setEnabled(false);
            mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
            mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateRejected;
            mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
            MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);


            IMObj endO = mController.imObj;
            IMObj imObjEnd = new IMObj();
            imObjEnd.creatorMobile = endO.creatorMobile;
            imObjEnd.tipMessage = "视频通话已结束";
            imObjEnd.callState = SocialChatCallStat.BKL_SocialChatMultimediaCallStateReject;
            imObjEnd.messageType = LKOthersFinalList.MSGTYPE_CMD_TEXT_END_101;
            imObjEnd.groupNick = endO.groupNick;
            imObjEnd.nick = UserInfoManageUtil.getUserInfo().getNickName();
            imObjEnd.groupID = endO.groupID;
            imObjEnd.avatar = UserInfoManageUtil.getUserInfo().getLargeImg();
            imObjEnd.groupAvatar = endO.groupAvatar;
            imObjEnd.duration = "0";
            imObjEnd.roomNum = mController.imObj.roomNum;
            imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
            MyImSendOption.getInstance().sendSingleEnd(mController.chatID, imObjEnd);

            PlayerTelephoneReceiver.getInstance().endMp3(this, "playend.mp3", true);
            mHandler.sendEmptyMessageDelayed(11, 2000);
        } else if (id == R.id.iv_video_incomeAccept) { //接听来电
            if (mController != null) {
                mController.mCallState = CenterSingleController.CallState.INCALL_ANSWER;
                mController.startTimer();
            }
            ll_video_head.setVisibility(View.INVISIBLE);
            rl_video_accept.setVisibility(View.VISIBLE);
            iv_video_min.setVisibility(View.VISIBLE);
            iv_video_incomeAccept.setEnabled(false);
            iv_video_incomeEnd.setEnabled(false);
            PlayerTelephoneReceiver.getInstance().stop();
            //点击接通按钮,加入频道
            //设置前置摄像头预览并开启
            AgoraManager.getInstance()
                    .setupLocalVideo(getApplicationContext())
                    .joinChannel(strChannel, 0);


            mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
            mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateReceived;
            mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
//            mController.imObj.fromUID = uid + "";
            MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);


            mHandler.sendEmptyMessage(8);
        } else if (id == R.id.remote_video_view_container) {
            if (ll_video_request.getVisibility() != View.VISIBLE) {
                ll_video_request.setVisibility(View.VISIBLE);
                iv_video_min.setVisibility(View.VISIBLE);
                ll_video_times.setVisibility(View.VISIBLE);
            } else {
                ll_video_request.setVisibility(View.GONE);
                iv_video_min.setVisibility(View.GONE);
                ll_video_times.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 来电还是呼出电话
     */
    private void initViewCall() {
        LKLogUtil.e("resultinITviewCall方法中--==" + mController);
        CenterSingleController.CallState callLayout = mController.mIsOutCall ? CenterSingleController.CallState.OUTGOING
                : CenterSingleController.CallState.INCOMING;
        if (mController.mCallState == null) {
            boolean isPermission = LKPermissionUtil.getInstance().requestMorePermission(SingleVideoActivity.this);
            //没有初始化过，才进行初始化赋值。
            setCallState(callLayout);
        }
        if (mController != null) {
            mController.isFirst = false;
        }
    }


    /**
     * 返回键，缩小窗口等结束界面
     */
    public void dismissFinish() {
        showNotification();
        SingleVideoActivity.super.finish();
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
            SingleVideoActivity.super.finish();
        }
    };

    @Override
    public void finish() {
        mHandler.postDelayed(OnCallFinish, 500);
    }


    protected void showNotification() {
        Intent intent = new Intent(this, SingleVideoActivity.class);
        IMNotifier.getInstance(LKApplication.getContext()).showCallingNotification(intent, "视频通话中...");
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
            // wake up screen
            // BUG java.lang.RuntimeException: WakeLock under-locked
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

    private boolean isZuixoahua = false;

    private int isCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        isCount++;
        if (isCount <= 1) {
            if (!VoIPHelper.serviceIsRunning(this)) {
                super.finish();
                return;
            }
            //唤醒屏幕
            wakeUpScreenMode();
            //清楚通知
            IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
            if (mController != null) {
                LKLogUtil.e("result==" + "再次进入的状态" + mController.mCallState);
                if (mController.mCallState == CenterSingleController.CallState.INCALL_ANSWER) {
                    isZuixoahua = true;
                }
                //设置静音和扩音要在onresume()设置，设置在oncreate中不管用。
                setCallState(mController.mCallState);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        LKLogUtil.e("result看看这个控制器在onStart方法中==" + mController);

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
            mController.showSmallWindowVideo();
        }
        PlayerTelephoneReceiver.getInstance().stop();
        isCount = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                finish();
                break;
            //20秒对方未接听，代表对方无人接听
            case 7:
                //来电以后未接听
                if (mController != null && mController.mCallState == CenterSingleController.CallState.OUTGOING) {
                    LKToastUtil.showToastLong("无人接听,聊天结束");
                    PlayerTelephoneReceiver.getInstance().endMp3(SingleVideoActivity.this, "playend.mp3", true);
                    MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);
                    mHandler.sendEmptyMessageDelayed(6, 2000);
                } else if (mController != null && mController.mCallState == CenterSingleController.CallState.INCOMING) {
                    LKToastUtil.showToastLong("聊天结束");
                    PlayerTelephoneReceiver.getInstance().endMp3(SingleVideoActivity.this, "playend.mp3", true);
                    mController.imObj.multimediaMessageType = SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeStateChange;
                    mController.imObj.multimediaState = SocialChatCallStat.BKL_SocialChatMultimediaStateNotResponding;
                    mController.imObj.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
                    MyImSendOption.getInstance().sendVoiceAndVideoTrans(mController.chatID, mController.imObj);


                    //接收方超时未接听
                    IMObj endO = mController.imObj;
                    IMObj imObjEnd = new IMObj();
                    imObjEnd.creatorMobile = endO.creatorMobile;
                    imObjEnd.tipMessage = "视频通话已结束";
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
                    imObjEnd.multimediaType = SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo;
                    MyImSendOption.getInstance().sendSingleEnd(mController.chatID, imObjEnd);


                    mHandler.sendEmptyMessageDelayed(6, 2000);
                }
                break;
            //接受聊天邀请
            case 8:
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
                ct_video_timer.stop();
                LKToastUtil.showToastLong("对方挂断，通话结束");
                PlayerTelephoneReceiver.getInstance().endMp3(SingleVideoActivity.this, "playend.mp3", true);
                mHandler.sendEmptyMessageDelayed(11, 2000);
                break;
            //关闭页面退出频道
            case 11:
                if (mController != null && mController.isVideoCameraSwitch) {
                    mController.isVideoCameraSwitch = false;
                    AgoraManager.getInstance().onSwitchCamera();
                }
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
                        tv_video_nickUser.setText(mController.mCalledName);
                        LK.image().bind(iv_video_avatar, mController.mCalledAvatar, LKImageOptions.getFITXYOptions(R.mipmap.icon_choose_group_default, 8));
                    } else {
                        tv_video_nickUser.setText(mController.imObj.fromNick);
                        LK.image().bind(iv_video_avatar, mController.imObj.fromAvatar, LKImageOptions.getFITXYOptions(R.mipmap.icon_choose_group_default, 8));
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
        this.ct_video_timer.setText(chronometer);
    }


    @Override
    public void onServiceDestroy() {
        super.finish();

        LKLogUtil.e("result==" + "onServiceDestory");


    }


    /**
     * 对端应答，通话计时开始
     *
     * @param call 通话的数据
     */
    @Override
    public void onCallAnswered(String call) {
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

    @Override
    public void timeOutNoAnswer() {
        mHandler.sendEmptyMessage(7);
    }


    /**
     * 获取远端视频uid
     *
     * @param uid
     */
    @Override
    public void onGetRemoteVideo(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //先清空容器
                local_video_view_container.removeAllViews();
                //设置本地前置摄像头预览并启动
                AgoraManager.getInstance().setupLocalVideo(SingleVideoActivity.this).startPreview();
                //将本地摄像头预览的SurfaceView添加到容器中
                local_video_view_container.addView(AgoraManager.getInstance().getLocalSurfaceView());
                LKLogUtil.e("result==onGetRemoteVideo头像显示----------" + uid);
                //注意SurfaceView要创建在主线程
                AgoraManager.getInstance().setupRemoteVideo(SingleVideoActivity.this, uid);
                remote_video_view_container.addView(AgoraManager.getInstance().getSurfaceView(uid));
            }
        });
    }

    @Override
    public void onLeaveChannelSuccess() {
        mHandler.sendEmptyMessageDelayed(11, 2000);
    }


    @Override
    public void onUserOffline(final int uid, int reason) {
        LKLogUtil.e("视频离线nUserOffline方法===" + uid);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //从PartyRoomLayout移除远程视频的SurfaceView
//                remote_video_view_container.removeView(AgoraManager.getInstance().getSurfaceView(uid));
                //清除缓存的SurfaceView
                AgoraManager.getInstance().removeSurfaceView(uid);
            }
        });
        mHandler.sendEmptyMessageDelayed(11, 2000);
    }


    /**
     * 加入成功
     *
     * @param channel
     * @param uid
     */
    @Override
    public void onJoinChannelSuccess(String channel, int uid) {
        LKLogUtil.e("视频加入成功onJoinChannelSuccess==" + uid);
        mHandler.sendEmptyMessage(8);

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
        if (conversation != null) {
            conversation.insertMessage(message);
        }
        sendBroadcast(new Intent("SHOUDAOXIAOXI"));//发送广播刷新消息列表
        Intent intent = new Intent(LKBroadcastKeyList.BROADCAST_MSG_HASNEW + mController.chatID);
        intent.putExtra(LKIntentKeyList.MSG_HASNEW, message);
        sendBroadcast(intent);
    }

}
