package tech.yunjing.biconlife.jniplugin.im.voip.frame;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.Serializable;

import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImSendOption;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.AgoraManager;
import tech.yunjing.biconlife.jniplugin.im.voip.HxTestKey;
import tech.yunjing.biconlife.jniplugin.im.voip.IMNotifier;
import tech.yunjing.biconlife.jniplugin.im.voip.OnPartyListener;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingControllerInter;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleControllerInter;
import tech.yunjing.biconlife.jniplugin.im.voip.util.PlayerTelephoneReceiver;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 收到音视频邀请，启动service；主动发起音视频邀请，启动service。
 * <li>接收音视频通知</li>
 * <li>通知中央调度器</li>
 * 注意，sdk音视频数据更新通知是通过回调方法，不是广播。（收到音视频邀请是广播）
 * <p>
 * Created by Chen.qi on 2017/8/15
 */
public class CenterService extends Service implements OnPartyListener, Serializable {

    /**
     * 单人音视频中控器
     */
    private CenterSingleControllerInter mSingleController;
    /**
     * 群聊中控器
     */
    private CenterMeetingControllerInter mMeetingController;
    /**
     * 锁屏开屏监听
     */
    private ScreenListener screenListener;

    /**
     * 接通、挂断的广播
     */
    private VoiceBrodCastReceiver brodcastReceiver;

    /**
     * 是否是单人超时(超时时间所用，判断是单人的还是群的)
     */
    private boolean timeOutIsSingle = false;


    private IMObj imObj;
    private String chatID;
    private boolean EXTRA_OUTGOING_CALL = false;


    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        LKLogUtil.e("服务是否正在运行==" + VoIPHelper.serviceIsRunning(this));
        if (VoIPHelper.serviceIsRunning(this)) {
            LKToastUtil.showToastShort("正在通话中，无法创建!");
            stopServiceAndClear();
            return;
        }
        CenterHolder.getInstance().putService(this);
        brodcastReceiver = new VoiceBrodCastReceiver();
        initBroadCastReceiver();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            EXTRA_OUTGOING_CALL = intent.getBooleanExtra(VoIPHelper.EXTRA_OUTGOING_CALL, false);
            try {
                chatID = intent.getStringExtra(VoIPHelper.RECEIVER_ID);//接收人的id
                imObj = (IMObj) intent.getSerializableExtra(VoIPHelper.TRANST_MSG_OBJ);
                LKLogUtil.e("CenterService中接收的对象数据---==" + imObj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        proceed(intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 具体操作
     *
     * @param intent
     * @param flags
     * @param startId
     */
    private void proceed(Intent intent, int flags, int startId) {
        if (intent != null) {
            //注册开关屏监听器
            screenListener = new ScreenListener(this);
            boolean isOutCall = intent.getBooleanExtra(VoIPHelper.EXTRA_OUTGOING_CALL, false);//是否是呼出
            boolean voice = intent.getBooleanExtra(VoIPHelper.EXTRA_SINGLE_CALL_VOICE, false);//音频
            boolean video = intent.getBooleanExtra(VoIPHelper.EXTRA_SINGLE_CALL_VIDEO, false);//视频
            intent.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);

            if (isOutCall) {
                boolean isSingle = intent.getBooleanExtra(VoIPHelper.EXTRA_SINGLE_CALL, false);//是否单人呼出
                if (isSingle && voice) {//单人音频
                    timeOutIsSingle = true;
                    AgoraManager.getInstance().init(this, 0);
                    AgoraManager.getInstance().onSpeakerphone(false);
                    if (imObj != null) {
                        AgoraManager.getInstance()
                                .setOnPartyListener(this)
                                .joinChannel(imObj.roomNum, 0);
                    }
                    //单人呼出
                    mSingleController = new CenterSingleController();
                    CenterHolder.getInstance().putSingleController((CenterSingleController) mSingleController);
                    mSingleController.init(intent, this);//初始化单人音视频中控器
                } else if (isSingle && video) {//单人视频
                    timeOutIsSingle = true;
                    AgoraManager.getInstance().init(this, 1);
                    AgoraManager.getInstance().onSpeakerphone(false);
                    if (imObj != null) {
                        AgoraManager.getInstance()
                                .setOnPartyListener(this)
                                .joinChannel(imObj.roomNum, 0);
                    }
                    //单人呼出
                    mSingleController = new CenterSingleController();
                    CenterHolder.getInstance().putSingleController((CenterSingleController) mSingleController);
                    mSingleController.init(intent, this);//初始化单人音视频中控器

                } else {
                    //群聊呼出
                    timeOutIsSingle = false;
                    AgoraManager.getInstance().init(this, 1);
                    AgoraManager.getInstance().onSpeakerphone(false);
                    AgoraManager.getInstance()
                            .setOnPartyListener(this)
                            .joinChannel(imObj.roomNum, Integer.parseInt(imObj.fromUID));

                    mMeetingController = new CenterMeetingController();
                    CenterHolder.getInstance().putMeetingController((CenterMeetingController) mMeetingController);
                    mMeetingController.init(intent, this);//初始化会议音视频中控器
                }
            } else {
                //呼入
                if (imObj == null) {
                    stopServiceAndClear();
                    return;
                }
                if (LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType)
                        && SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeCreate == imObj.multimediaMessageType
                        && SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice == imObj.multimediaType) {
                    //单人音频呼入创建
                    timeOutIsSingle = true;
                    AgoraManager.getInstance().init(this, 0);
                    AgoraManager.getInstance()
                            .setOnPartyListener(this);
                    AgoraManager.getInstance().onSpeakerphone(true);
                    mSingleController = new CenterSingleController();
                    CenterHolder.getInstance().putSingleController((CenterSingleController) mSingleController);
                    mSingleController.init(intent, this);//初始化单人音频中控器

                } else if (LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType)
                        && SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeCreate == imObj.multimediaMessageType
                        && SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo == imObj.multimediaType) {
                    //单人视频呼入创建
                    timeOutIsSingle = true;
                    AgoraManager.getInstance().init(this, 1);
                    AgoraManager.getInstance().onSpeakerphone(true);
                    AgoraManager.getInstance()
                            .setupLocalVideo(getApplicationContext())
                            .setOnPartyListener(this);
                    mSingleController = new CenterSingleController();
                    CenterHolder.getInstance().putSingleController((CenterSingleController) mSingleController);
                    mSingleController.init(intent, this);//初始化单人视频中控器
                } else if (LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType) &&
                        (SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeCreate == imObj.multimediaMessageType
                                || SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeInvite == imObj.multimediaMessageType) &&
                        (SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice == imObj.multimediaType
                                || SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVideo == imObj.multimediaType)) {//多人音视频
                    timeOutIsSingle = false;
                    AgoraManager.getInstance().init(this, 1);
                    AgoraManager.getInstance().onSpeakerphone(true);
                    AgoraManager.getInstance()
                            .setupLocalVideo(getApplicationContext())
                            .setOnPartyListener(this);
                    mMeetingController = new CenterMeetingController();
                    CenterHolder.getInstance().putMeetingController((CenterMeetingController) mMeetingController);
                    mMeetingController.init(intent, this);//初始化会议音视频中控器
                }
            }

            if (timeOutIsSingle) {
                //单聊超时  60秒未接听，强制挂断，清除服务
                hander.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSingleController != null) {
                            mSingleController.timeOutNoAnswer();
                        }
                    }
                }, 60 * 1000);
            } else {
                //群聊超时 45秒未接听，强制挂断，清除服务
                hander.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mMeetingController != null) {
                            mMeetingController.timeOutNoAnswer();
                        }
                    }
                }, 45 * 1000);
            }

        } else {
            LKToastUtil.showToastShort("创建通话失败");
            stopServiceAndClear();
        }
    }

    /**
     * 停止service并且清除数据等.
     */
    public void stopServiceAndClear() {
        stopSelf();
        clearServer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //双重保证。保证数据都被清掉
        clearServer();
    }

    /**
     * 清除数据。缓存等
     */
    private void clearServer() {

        if (brodcastReceiver != null) {
            try {
                unregisterReceiver(brodcastReceiver);
            } catch (Exception e) {
            }
        }
        if (screenListener != null) {
            try {
                screenListener.unregisterListener();
            } catch (Exception e) {
            }
        }
        if (mSingleController != null) {
            mSingleController.onServiceDestroy();
        }

        if (mMeetingController != null) {
            mMeetingController.onServiceDestroy();
        }
        CenterHolder.getInstance().clearService();
        mSingleController = null;
        mMeetingController = null;
        IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
        AgoraManager.getInstance().leaveChannel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 加入成功(接受邀请以后所走的回调)
     *
     * @param channel 频道
     * @param uid     uid
     */
    @Override
    public void onJoinChannelSuccess(String channel, int uid) {
        if (EXTRA_OUTGOING_CALL) {
            if (imObj != null) {
                if (imObj.multimediaType == SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice) {//群聊加入成功
                    MyImSendOption.getInstance().sendTextGroupVideoOrVoice(chatID, imObj);
                } else {//单聊
                    MyImSendOption.getInstance().sendTextVoico(chatID, imObj);
                }
            }
        } else {
            PlayerTelephoneReceiver.getInstance().stop();
            if (mMeetingController != null && imObj.multimediaType == SocialChatCallStat.BKL_SocialChatMultimediaTypeMutilVoice) {
                mMeetingController.onJoinChannelSuccess(channel, uid);
            } else if (mSingleController != null) {
                mSingleController.onJoinChannelSuccess(channel, uid);
            }
        }
    }

    @Override
    public void onGetRemoteVideo(int uid) {
        if (mSingleController != null) {
            mSingleController.onGetRemoteVideo(uid);
        }
    }

    @Override
    public void onLeaveChannelSuccess() {
        LKLogUtil.e("result==" + "onLeaveChannelSuccess退出频道" + "-----单聊-------" + mSingleController);
        LKLogUtil.e("result==" + "onLeaveChannelSuccess退出频道" + "------群聊群聊-------" + mMeetingController);
        if (mSingleController != null) {
            mSingleController.onLeaveChannelSuccess();
        } else if (mMeetingController != null) {
            mMeetingController.onLeaveChannelSuccess();
        }
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        LKLogUtil.e("result==" + "用户uid离线时的回调" + uid);
        if (mSingleController != null) {
            mSingleController.onUserOffline(uid, reason);
        } else if (mMeetingController != null) {
            mMeetingController.onUserOffline(uid, reason);
        }
    }

    /**
     * 关闭、打开远端摄像头回调多人音视频所用
     *
     * @param uid
     * @param muted
     */
    @Override
    public void onUserMuteVideo(int uid, boolean muted) {
        if (mMeetingController != null) {
            mMeetingController.onUserMuteVideo(uid, muted);
        }
    }


    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(HxTestKey.BROADCAST_SINGLE_RECEIVER);
        filter.addAction(HxTestKey.BROADCAST_SINGLE_HAND_UP);
        filter.addAction(HxTestKey.BROADCAST_SINGLE_REJECT);
        filter.addAction(HxTestKey.BROADCAST_CREATED_CANCEL);
        filter.addAction(HxTestKey.BROADCAST_MEMBERS_JOIN_NEW);
        //销毁会话
        filter.addAction(HxTestKey.IMOBJ_DESTORY_DATA);
        registerReceiver(brodcastReceiver, filter);
    }


    class VoiceBrodCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            try {
                if (HxTestKey.BROADCAST_SINGLE_RECEIVER.equals(intent.getAction())) {//通话接受,接通
                    PlayerTelephoneReceiver.getInstance().stop();
                    IMObj imObjAccept = (IMObj) intent.getSerializableExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE);
                    if (imObjAccept != null && mMeetingController != null) {
                        mMeetingController.onCallEventCallBack(imObjAccept);
                    } else if (mSingleController != null) {
                        mSingleController.onCallEventCallBack("接通");
                    }
                } else if (HxTestKey.BROADCAST_SINGLE_HAND_UP.equals(intent.getAction())) {//挂断
                    IMObj imObjEnd = (IMObj) intent.getSerializableExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE);
                    if (imObjEnd != null && mMeetingController != null) {
                        mMeetingController.onCallEventCallBack(imObjEnd);
                    } else if (mSingleController != null) {
                        mSingleController.onCallEventCallBack("挂断");
                        PlayerTelephoneReceiver.getInstance().stop();
                    }
                } else if (HxTestKey.BROADCAST_SINGLE_REJECT.equals(intent.getAction())) {
                    //拒绝接听
                    IMObj imObjReject = (IMObj) intent.getSerializableExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE);
                    if (imObjReject != null && mMeetingController != null) {
                        mMeetingController.onCallEventCallBack(imObjReject);
                    } else if (mSingleController != null) {
                        mSingleController.onCallEventCallBack("拒绝");
                        PlayerTelephoneReceiver.getInstance().stop();
                    }
                } else if (HxTestKey.BROADCAST_CREATED_CANCEL.equals(intent.getAction())) {
                    //销毁整个会话
                    IMObj imObjReject = (IMObj) intent.getSerializableExtra(HxTestKey.IMOBJ_TRANSMIT_MESSAGE);
                    if (imObjReject != null && mMeetingController != null) {
                        mMeetingController.onCallEventCallBack(imObjReject);
                        PlayerTelephoneReceiver.getInstance().stop();
                    }
                } else if (HxTestKey.BROADCAST_MEMBERS_JOIN_NEW.equals(intent.getAction())) {
                    //有新人加入
                    IMObj imObjNewJoin = (IMObj) intent.getSerializableExtra(HxTestKey.JOIN_MEMBERS_IMOBJ);
                    LKLogUtil.e("result==" + "有新人加入会议的广播");
                    if (imObjNewJoin != null && mMeetingController != null) {
                        LKLogUtil.e("result新人加入---==" + imObjNewJoin.toString());
                        mMeetingController.onJoinNewMembers(imObjNewJoin);
                    }
                } else if (HxTestKey.IMOBJ_DESTORY_DATA.equals(intent.getAction())) {
                    //销毁所有会话
                    // *************************** 注意***********************此回调暂时没有用到
                    IMObj imObjNewJoin = (IMObj) intent.getSerializableExtra(HxTestKey.IMOBJ_DESTORY_DATA);
                    if (imObjNewJoin != null && mMeetingController != null) {
                        LKLogUtil.e("result新人加入---==" + imObjNewJoin.toString());
                        mMeetingController.onDestoryRoom(imObjNewJoin);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
