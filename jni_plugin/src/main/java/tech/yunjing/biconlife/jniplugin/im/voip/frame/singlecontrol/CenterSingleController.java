package tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.Serializable;
import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.IMNotifier;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.im.voip.single.SingleVideoActivity;
import tech.yunjing.biconlife.jniplugin.im.voip.single.SingleVoiceActivity;
import tech.yunjing.biconlife.jniplugin.im.voip.util.ChronometerUtil;
import tech.yunjing.biconlife.jniplugin.im.voip.view.SingleWindowMonitorView;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 单人音视频中央控制器<br>
 * 1.提供service通知数据的方法;
 * 2.监听器回调方式,通知各个监听器(音视频界面,小窗口等)
 * <p>
 * Created by Chen.qi on 2017/8/15
 */
public class CenterSingleController implements CenterSingleControllerInter, Serializable {
    /**
     * 是否是去电
     */
    public boolean mIsOutCall;
    /**
     * 对方的手机号,来去都有此数据，会议没有此数据
     */
    public String mCallPerNumber;
    /**
     * 被呼叫者的昵称,作为打电话的人才有数据
     */
    public String mCalledName;

    /**
     * 别呼叫人的头像，作为打电话的人才有数据
     */
    public String mCalledAvatar;

    /**
     * 是否正在响铃（等待接听）
     */
    public boolean isCalling;
    /**
     * 是否第一次打开界面。如果第一次，则执行打电话等操作。如果不是第一次（最小化再进来），不执行打电话
     */
    public boolean isFirst = true;
    /**
     * 是否是自己挂断
     */
    public boolean isSelfEnd;
    /**
     * 小窗口控件
     */
    private SingleWindowMonitorView mSmallMonitorView;
    /**
     * 最小化窗口管理器
     */
    private WindowManager mWindowManager;
    /**
     * 当前状态
     */
    public CallState mCallState;
    /**
     * 音频的静音
     */
    public boolean isVoiceMute = false;
    /**
     * 音频的免提
     */
    public boolean isVoiceMt = false;
    /**
     * 视频的uid最小化使用
     */
    public int mVideoUid;
    /**
     * 视频的免提
     */
    public boolean isVideoMT = true;
    /**
     * 视频的摄像头翻转
     */
    public boolean isVideoCameraSwitch = false;

    /**
     * 对方是否应答
     */
    public boolean isAccept = false;
    /**
     * 接收人的ID
     */
    public String chatID;
    /**
     * 透传所需的对象（预留）
     */
    public IMObj imObj;

    private Context context;

    /**
     * 初始化
     *
     * @param intent
     */
    private boolean initCall(Intent intent) {
        ChronometerUtil.getInstance().setOnChronometerTickListener(new ChronometerUtil.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(String time) {
                for (CenterSingleWindowInter listener : mListeners) {
                    listener.onTimerTick(time);
                }
            }
        });
        return true;
    }


    /**
     * 获取数据
     *
     * @param intent
     */
    private void initData(Intent intent) {
        if (intent == null) {
            return;
        }

        try {
            //接收人的id
            chatID = intent.getStringExtra(VoIPHelper.RECEIVER_ID);
            imObj = (IMObj) intent.getSerializableExtra(VoIPHelper.TRANST_MSG_OBJ);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * 1.获取来去电类型.如果是去电，则手动设置类型。如果是来电，则sdk返回类型
         *2.获取来电还是去电，并初始化数据
         */
        mIsOutCall = (intent.getBooleanExtra(VoIPHelper.EXTRA_OUTGOING_CALL, false));//获取是否是去电
        if (!mIsOutCall) {
            // 如果是来电，获取信息
//            mCallId = intent.getStringExtra(ECDevice.CALLID);
//            mCallPerNumber = intent.getStringExtra(ECDevice.CALLER);
        } else {
            //去电
            mCalledName = intent.getStringExtra(VoIPHelper.EXTRA_CALL_NAME);
            mCalledAvatar = intent.getStringExtra(VoIPHelper.EXTRA_CALL_AVATAR);
            mCallPerNumber = intent.getStringExtra(VoIPHelper.EXTRA_CALL_NUMBER);
        }
    }


    /**
     * 跳转界面
     *
     * @param intentParam
     */
    private void goToActivity(Intent intentParam, Context mContext) {
        boolean voice = intentParam.getBooleanExtra(VoIPHelper.EXTRA_SINGLE_CALL_VOICE, false);//音频
        boolean video = intentParam.getBooleanExtra(VoIPHelper.EXTRA_SINGLE_CALL_VIDEO, false);//视频
        boolean isOutCall = intentParam.getBooleanExtra(VoIPHelper.EXTRA_OUTGOING_CALL, false);//是否是呼出
        if (mContext == null) {
            mContext = LKApplication.getContext();
        }
        if (isOutCall) {
            if (voice) {
                //启动单人语音界面
                intentParam.setClass(mContext, SingleVoiceActivity.class);
                intentParam.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CenterService service = CenterHolder.getInstance().getService();
                if (service != null) {
                    intentParam.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);
                    mContext.startActivity(intentParam);
                    attachSmallWindowView(service, intentParam, 0);
                }
            } else if (video) {
                //启动单人视频界面
                intentParam.setClass(mContext, SingleVideoActivity.class);
                intentParam.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CenterService service = CenterHolder.getInstance().getService();
                if (service != null) {
                    intentParam.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);
                    mContext.startActivity(intentParam);
                    attachSmallWindowView(service, intentParam, 1);
                }
            }

        } else {
            if (LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType)
                    && SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeCreate == imObj.multimediaMessageType
                    && SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVoice == imObj.multimediaType) {
                //单人音频呼入创建
                //启动单人语音界面
                intentParam.setClass(mContext, SingleVoiceActivity.class);
                intentParam.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CenterService service = CenterHolder.getInstance().getService();
                if (service != null) {
                    intentParam.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);
                    mContext.startActivity(intentParam);
                    attachSmallWindowView(service, intentParam, 0);
                }

            } else if (LKOthersFinalList.MSGTYPE_CMD_TEXT.equals(imObj.messageType)
                    && SocialChatCallStat.BKL_SocialChatMultimediaMessageTypeCreate == imObj.multimediaMessageType
                    && SocialChatCallStat.BKL_SocialChatMultimediaTypeSingleVideo == imObj.multimediaType) {
                //单人视频呼入创建
                //启动单人视频界面
                intentParam.setClass(mContext, SingleVideoActivity.class);
                intentParam.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CenterService service = CenterHolder.getInstance().getService();
                if (service != null) {
                    intentParam.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);
                    mContext.startActivity(intentParam);
                    attachSmallWindowView(service, intentParam, 1);
                }
            }
        }
    }

    /********************************以下是监听者们的方法******************/
    /**
     * 监听们
     */
    public ArrayList<CenterSingleWindowInter> mListeners = new ArrayList<>();

    /**
     * 添加数据改变监听
     *
     * @param listener
     */
    @Override
    public void addChangeDataListener(CenterSingleWindowInter listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    @Override
    public void removeChangeDataListener(CenterSingleWindowInter listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
        }
    }


    /**
     * 添加小窗口控件。先将大小设置为1px。当主界面消失时，再设置变大
     *
     * @param service
     * @param intentParam
     */
    private void attachSmallWindowView(CenterService service, Intent intentParam, int type) {
        mWindowManager = (WindowManager) service.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        int screenWidth =point.x;
        int screenHeight = point.y;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        mSmallMonitorView = new SingleWindowMonitorView(service, intentParam, type);
        params.format = PixelFormat.RGBA_8888;
        params.width = 1;
        params.height = 1;
        params.format = PixelFormat.TRANSLUCENT;

        if (Build.VERSION.SDK_INT > 24) {
            //针对7.1.1系统
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        params.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = screenWidth;
        if (type == 0) {
            params.y = screenHeight / 2;
        } else {
            params.y = -screenHeight;
        }
        mSmallMonitorView.setParams(params);
        mWindowManager.addView(mSmallMonitorView, params);
    }


    @Override
    public void onServiceDestroy() {
        stopTimer();
        isFirst = true;
        IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
        removeRemoteView();//确保小窗口消失。
        CenterHolder.getInstance().clearController();
    }

    @Override
    public void onCallEventCallBack(String voipCall) {
        if ("接通".equals(voipCall)) {
            //对方应答
            isCalling = true;
            mCallState = CallState.INCALL_ANSWER;
            startTimer();
            for (CenterSingleWindowInter listener : mListeners) {
                listener.onCallAnswered(voipCall);
            }
        } else if ("挂断".equals(voipCall)) {
            //呼叫结束
            mCallState = CallState.END;
            IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
            removeRemoteView();
            for (CenterSingleWindowInter listener : mListeners) {
                listener.onCallReleased(voipCall);
            }
        } else if ("拒绝".equals(voipCall)) {
            //呼叫结束
            mCallState = CallState.RUFUSH;
            IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
            removeRemoteView();
            for (CenterSingleWindowInter listener : mListeners) {
                listener.onRefuseToAnswer(voipCall);
            }
        }

    }

    /**
     * 超时未接听
     */
    @Override
    public void timeOutNoAnswer() {
        if (mCallState == CallState.INCOMING || mCallState == CallState.OUTGOING) {
            for (CenterSingleWindowInter listener : mListeners) {
                listener.timeOutNoAnswer();
            }
        }
    }

    /**
     * 获取远端视频uid
     *
     * @param uid
     */
    @Override
    public void onGetRemoteVideo(int uid) {
        if (mCallState == CallState.INCALL_ANSWER) {
            mVideoUid = uid;
            for (CenterSingleWindowInter listener : mListeners) {
                listener.onGetRemoteVideo(uid);
            }
        }
    }


    @Override
    public void onUserOffline(int uid, int reason) {
        if (mCallState == CallState.INCALL_ANSWER || mCallState == CallState.END) {
            for (CenterSingleWindowInter listener : mListeners) {
                listener.onUserOffline(uid, reason);
            }
        }
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid) {
        for (CenterSingleWindowInter listener : mListeners) {
            listener.onJoinChannelSuccess(channel, uid);
        }
    }

    @Override
    public void onLeaveChannelSuccess() {
        for (CenterSingleWindowInter listener : mListeners) {
            listener.onLeaveChannelSuccess();
        }
    }

    /**
     * 初始化数据
     *
     * @param intent
     */
    @Override
    public void init(Intent intent, Context mContext) {
        context = mContext;
        initCall(intent);
        initData(intent);
        goToActivity(intent, mContext);
    }


    /********************************以下是其他类调用的方法******************/

    /**
     * 开始计时
     */
    public void startTimer() {
        if (!ChronometerUtil.getInstance().mStarted) {
            ChronometerUtil.getInstance().setBase(SystemClock.elapsedRealtime());
            ChronometerUtil.getInstance().start();
        }
    }

    /**
     * 停止计时
     */
    public void stopTimer() {
        if (ChronometerUtil.getInstance().mStarted) {
            ChronometerUtil.getInstance().stop();
        }
    }

    /**
     * 展示小窗口，悬浮框
     */
    public void showSmallWindow() {
        if (mSmallMonitorView != null && mWindowManager != null) {
            mSmallMonitorView.isIntent = true;
            ViewGroup.LayoutParams layoutParams = mSmallMonitorView.getLayoutParams();
            layoutParams.width = LKCommonUtil.dip2px(70);
            layoutParams.height = LKCommonUtil.dip2px(70);
            mWindowManager.updateViewLayout(mSmallMonitorView, layoutParams);
        }
    }


    /**
     * 展示视频小窗口，悬浮框
     * 视频单人的
     */
    public void showSmallWindowVideo() {
        if (mSmallMonitorView != null && mWindowManager != null) {
            mSmallMonitorView.isIntent = true;
            ViewGroup.LayoutParams layoutParams = mSmallMonitorView.getLayoutParams();
            layoutParams.width = LKCommonUtil.dip2px(120);
            layoutParams.height = LKCommonUtil.dip2px(150);
            mWindowManager.updateViewLayout(mSmallMonitorView, layoutParams);
            mSmallMonitorView.upCremaSurfaceView();
        }
    }


    /**
     * 隐藏小窗口，悬浮框
     */
    public void hideSmallWindow() {
        if (mSmallMonitorView != null && mWindowManager != null) {
            ViewGroup.LayoutParams layoutParams = mSmallMonitorView.getLayoutParams();
            layoutParams.width = 1;
            layoutParams.height = 1;
            mWindowManager.updateViewLayout(mSmallMonitorView, layoutParams);
            mSmallMonitorView.clearnSurfaceView();
        }
    }

    /**
     * 去掉window中的视频控件
     */
    public void removeRemoteView() {
        if (mSmallMonitorView != null) {
            mWindowManager.removeView(mSmallMonitorView);
            mSmallMonitorView = null;
        }
    }


    /**
     * 通话界面显示类型
     * INCOMING 来电
     * RUFUSH 拒绝
     * OUTGOING 呼出
     * INCALL 来电
     * END 结束
     */
    public enum CallState {
        INCOMING, RUFUSH, OUTGOING, INCALL_ANSWER, END;
    }


}
