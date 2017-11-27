package tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.Serializable;
import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.voip.IMNotifier;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.im.voip.group.MeetingGroupActivity;
import tech.yunjing.biconlife.jniplugin.im.voip.util.ChronometerUtil;
import tech.yunjing.biconlife.jniplugin.im.voip.view.MeetingWindowMonitorView;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * Created by dup on 2017/5/26.
 * 会议单音视频中央控制器<br>
 * 1.提供service通知数据的方法;
 * 2.监听器回调方式,通知各个监听器(音视频界面,小窗口等)
 */
public class CenterMeetingController implements CenterMeetingControllerInter, Serializable {

    public boolean mIsOutCall;//是否是去电
    public boolean isFirstOpenActivity = true;//是否第一次打开界面。如果第一次，则执行打电话等操作。如果不是第一次（最小化再进来），不执行打电话
    private MeetingWindowMonitorView mSmallMonitorView;//小窗口控件
    private WindowManager mWindowManager;


    /**
     * 静音
     */
    public boolean isVoiceMute = false;
    /**
     * 免提
     */
    public boolean isVoiceMt = true;

    /**
     * 打开或者关闭摄像头
     */
    public boolean isOpenOrCloseCrema = false;

    /**
     * 摄像头的翻转变量保存，声网默认是保存前一次摄像头方向，顾每次推出将其重置
     */
    public boolean isCremaSwitch = false;

    /**
     * 传递过来的集合
     */
    private ArrayList<GroupMenberListdata> groupMenberListdatas;


    public ArrayList<GroupMenberListdata> allMembers;


    public IMObj imObj;

    /**
     * 当前状态
     */
    public MeetingCallState mMeetCallState;

    private Context context;


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

    /**
     * 获取数据
     *
     * @param intent
     */
    private void initData(Intent intent) {
        if (intent == null) {
            return;
        }
        allMembers = new ArrayList<>();
        try {
            //是否是发起者
            mIsOutCall = (intent.getBooleanExtra(VoIPHelper.EXTRA_OUTGOING_CALL, false));//获取是否是去电
            imObj = (IMObj) intent.getSerializableExtra(VoIPHelper.TRANST_MSG_OBJ);
            LKLogUtil.e("result==" + "接收到的imObj对象数据-------" + imObj);
            groupMenberListdatas = LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);

            if (groupMenberListdatas != null) {
                for (int i = 0; i < groupMenberListdatas.size(); i++) {
//                    if (imObj.creatorUserID.equals(groupMenberListdatas.get(i).userID)) {
//                        groupMenberListdatas.get(i).isAccept = true;
//                    }
                    if (!TextUtils.isEmpty(imObj.fromMobile) && imObj.fromMobile.equals(groupMenberListdatas.get(i).mobile)) {
                        groupMenberListdatas.get(i).isAccept = true;
                    }
                }
                allMembers.addAll(groupMenberListdatas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化
     *
     * @param intent
     */
    private boolean initCall(Intent intent) {
        ChronometerUtil.getInstance().setOnChronometerTickListener(new ChronometerUtil.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(String time) {
                for (CenterMeetingWindowInter listener : mListeners) {
                    listener.onTimerTick(time);
                }
            }
        });
        return true;
    }

    /**
     * 跳转界面
     *
     * @param intentParam
     */
    private void goToActivity(Intent intentParam, Context mContext) {
        //启动会议视频界面
        intentParam.setClass(mContext, MeetingGroupActivity.class);
        intentParam.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            service.startActivity(intentParam);
            //开启捕捉己方视频window
            attachSmallWindowView(service, intentParam);
        }
    }


    /**
     * 添加小窗口控件。先将大小设置为1px。当主界面消失时，再设置变大
     *
     * @param service
     * @param intentParam
     */
    private void attachSmallWindowView(CenterService service, Intent intentParam) {
        mWindowManager = (WindowManager) service.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        int screenWidth = point.x;
        int screenHeight = point.y;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        mSmallMonitorView = new MeetingWindowMonitorView(service.getApplicationContext(), intentParam);
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
        params.y = screenHeight / 2;
        mSmallMonitorView.setParams(params);
        mWindowManager.addView(mSmallMonitorView, params);
    }

    @Override
    public void onServiceDestroy() {
        //这demo里要取消请求。
        stopTimer();
        isFirstOpenActivity = true;
        removeRemoteView();
        for (CenterMeetingWindowInter listener : mListeners) {
            if (listener != null) {
                listener.onServiceDestroy();
            }
        }

        IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
        CenterHolder.getInstance().clearController();
    }

    /**
     * 超时未接听
     */
    @Override
    public void timeOutNoAnswer() {
        if (mMeetCallState == CenterMeetingController.MeetingCallState.INCOMING || mMeetCallState == CenterMeetingController.MeetingCallState.OUTGOING) {
            for (CenterMeetingWindowInter listener : mListeners) {
                listener.timeOutNoAnswer();
            }
        }
    }

    /**
     * 拒绝、挂断、接通的回调
     *
     * @param callImObj 类型
     */
    @Override
    public void onCallEventCallBack(IMObj callImObj) {
        if (callImObj != null) {
            int multimediaState = callImObj.multimediaState;
            switch (multimediaState) {
                //接受
                case SocialChatCallStat.BKL_SocialChatMultimediaStateReceived:
                    startTimer();
                    for (CenterMeetingWindowInter listener : mListeners) {
                        listener.onCallAnswered(callImObj);
                    }
                    break;
                //拒绝
                case SocialChatCallStat.BKL_SocialChatMultimediaStateRejected:
                    IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
                    for (CenterMeetingWindowInter listener : mListeners) {
                        listener.onRefuseToAnswer(callImObj);
                    }
                    break;
                //挂断
                case SocialChatCallStat.BKL_SocialChatMultimediaStateHangUp:
                    IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
                    for (CenterMeetingWindowInter listener : mListeners) {
                        listener.onCallReleased(callImObj);
                    }
                    break;
                //销毁整个会话
                case SocialChatCallStat.BKL_SocialChatMultimediaStateEnd:
                    IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
                    for (CenterMeetingWindowInter listener : mListeners) {
                        listener.onCreatorCancel(callImObj);
                    }
                    break;
                //未响应
                case SocialChatCallStat.BKL_SocialChatMultimediaStateNotResponding:
                    IMNotifier.getInstance(LKApplication.getContext()).cancelCCPNotification(IMNotifier.CCP_NOTIFICATOIN_ID_CALLING);
                    for (CenterMeetingWindowInter listener : mListeners) {
                        listener.timeOutNoAnswer();
                    }
                    break;
                default:
                    break;
            }

        }

    }


    /**
     * 加入成功
     *
     * @param channel
     * @param uid
     */
    @Override
    public void onJoinChannelSuccess(String channel, int uid) {
        for (CenterMeetingWindowInter listener : mListeners) {
            listener.onJoinChannelSuccess(channel, uid);
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
        for (CenterMeetingWindowInter listener : mListeners) {
            listener.onUserOffline(uid, reason);
        }
    }

    @Override
    public void onUserMuteVideo(int uid, boolean enabled) {
        for (CenterMeetingWindowInter listener : mListeners) {
            listener.onUserMuteVideo(uid, enabled);
        }
    }


    @Override
    public void onLeaveChannelSuccess() {
        LKLogUtil.e("result==" + "退出频道了-------------7777777777777777----------");
        for (CenterMeetingWindowInter listener : mListeners) {
            listener.onLeaveChannelSuccess();
        }

    }

    @Override
    public void onJoinNewMembers(IMObj imObj) {
        for (CenterMeetingWindowInter listener : mListeners) {
            listener.onJoinNewMembers(imObj);
        }
    }

    @Override
    public void onDestoryRoom(IMObj imObjDes) {
        for (CenterMeetingWindowInter listener : mListeners) {
            listener.onDestoryRoom(imObjDes);
        }
    }

    /**
     * 去掉window中的视频控件
     */
    private void removeRemoteView() {
        if (mSmallMonitorView != null) {
            mWindowManager.removeView(mSmallMonitorView);
            mSmallMonitorView = null;
        }
    }


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
     * 隐藏小窗口，悬浮框
     */
    public void hideSmallWindow() {
        if (mSmallMonitorView != null && mWindowManager != null) {
            mSmallMonitorView.isIntent = false;
            ViewGroup.LayoutParams layoutParams = mSmallMonitorView.getLayoutParams();
            layoutParams.width = 1;
            layoutParams.height = 1;
            mWindowManager.updateViewLayout(mSmallMonitorView, layoutParams);
        }
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
        }
    };


    /********************************以下是监听者们的方法******************/
    /**
     * 监听们
     */
    public ArrayList<CenterMeetingWindowInter> mListeners = new ArrayList<>();

    /**
     * 添加数据改变监听
     *
     * @param listener
     */
    @Override
    public void addChangeDataListener(CenterMeetingWindowInter listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    /**
     * 移除监听，防止内存溢出
     *
     * @param listener
     */
    @Override
    public void removeChangeDataListener(CenterMeetingWindowInter listener) {
        if (mListeners.contains(listener)) {
            mListeners.remove(listener);
        }
    }


    /**
     * 通话界面显示类型
     * INCOMING 来电
     * RUFUSH 拒绝
     * OUTGOING 呼出
     * END 结束
     * INCALL 接通
     */
    public enum MeetingCallState {
        INCOMING, RUFUSH, OUTGOING, END, INCALL
    }

}
