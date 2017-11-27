package tech.yunjing.biconlife.jniplugin.im.voip.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingWindowInter;
import tech.yunjing.biconlife.liblkclass.common.util.LKAppUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;

/**
 * 小窗口群聊
 */
public class MeetingWindowMonitorView extends RelativeLayout implements View.OnTouchListener, CenterMeetingWindowInter {
    private Intent intent;
    private final CenterMeetingController mController;
    private static int statusBarHeight;//记录系统状态栏的高度
    private WindowManager windowManager;//用于更新小悬浮窗的位置
    private WindowManager.LayoutParams mParams;//小悬浮窗的参数
    private float xInScreen;//记录当前手指位置在屏幕上的横坐标值
    private float yInScreen;//记录当前手指位置在屏幕上的纵坐标值
    private float xDownInScreen;//记录手指按下时在屏幕上的横坐标的值
    private float yDownInScreen;//记录手指按下时在屏幕上的纵坐标的值
    private float xInView;//记录手指按下时在小悬浮窗的View上的横坐标的值
    private float yInView;//记录手指按下时在小悬浮窗的View上的纵坐标的值
    public boolean isIntent = true;//防止快速点击
    private TextView tv_small_window;//小窗口中的文本view

    private Context context;

    public MeetingWindowMonitorView(final Context context) {
        super(context);
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View inflate = LayoutInflater.from(context).inflate(R.layout.small_window_monitor, this, true);
        tv_small_window = (TextView) inflate.findViewById(R.id.tv_small_window);
        inflate.setOnTouchListener(this);
        mController = CenterHolder.getInstance().getMeetingController();
        mController.addChangeDataListener(this);
    }


    public MeetingWindowMonitorView(Context context, Intent intentParam) {
        this(context);
        this.context = context;
        intent = intentParam;
    }


    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                float xMove = xInScreen - xDownInScreen;
                float yMove = yInScreen - yDownInScreen;
                if (Math.abs(xMove) < 20 && Math.abs(yMove) < 20 && isIntent) {
                    isIntent = false;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onTimerTick(final String time) {
        tv_small_window.setText(time);
        postInvalidate();
    }


    @Override
    public void onServiceDestroy() {
    }

    /**
     * 接通(最小化后)
     *
     * @param callImObj
     */
    @Override
    public void onCallAnswered(IMObj callImObj) {
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.get(i).isAccept = true;
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
    }

    /**
     * 挂断(最小化后)
     *
     * @param callImObj
     */
    @Override
    public void onCallReleased(IMObj callImObj) {
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
    }

    /**
     * 拒绝(最小化后)
     *
     * @param callImObj
     */
    @Override
    public void onRefuseToAnswer(IMObj callImObj) {
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
    }

    /**
     * 加入频道成功(最小化后)
     *
     * @param channel
     * @param uid
     */
    @Override
    public void onJoinChannelSuccess(String channel, int uid) {


    }

    /**
     * 用户离线(最小化后)
     *
     * @param uid
     * @param reason
     */

    @Override
    public void onUserOffline(int uid, int reason) {
        LKLogUtil.e("result==" + "聊天室有其他人员离线了最小化后==" + reason + "----uid----" + uid);
        String UID = uid + "";
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (UID.equals(mController.allMembers.get(i).UID)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
    }

    @Override
    public void onUserMuteVideo(int uid, boolean enabled) {
        LKLogUtil.e("result==" + "打开关闭远端视频的回调最小化后" + uid + "----------" + enabled);
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
    }

    @Override
    public void timeOutNoAnswer() {
        if (mController.mMeetCallState == CenterMeetingController.MeetingCallState.INCOMING
                || mController.mMeetCallState == CenterMeetingController.MeetingCallState.OUTGOING) {
            if (tv_small_window != null) {
                tv_small_window.setText("无人接听");
            } else {
                LKToastUtil.showToastShort("无人接听");
            }
            CenterService service = CenterHolder.getInstance().getService();
            if (service != null) {
                service.stopServiceAndClear();
            }
        }
    }

    /**
     * 销毁整个会话
     *
     * @param callImObj
     */
    @Override
    public void onCreatorCancel(IMObj callImObj) {
        for (int i = 0; i < mController.allMembers.size(); i++) {
            if (callImObj.fromMobile.equals(mController.allMembers.get(i).mobile)) {
                mController.allMembers.remove(i);
                break;
            }
        }
        String members = LKJsonUtil.arrayListToJsonString(mController.allMembers);
        mController.imObj.members = members;
        mController.stopTimer();
        if (tv_small_window != null) {
            tv_small_window.setText("聊天结束");
        }
        LKToastUtil.showToastShort("聊天结束");
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            service.stopServiceAndClear();
        }

    }

    @Override
    public void onLeaveChannelSuccess() {
        LKLogUtil.e("result==" + "最小化视图中的onLeaveChannelSuccess回调方法");
        if (tv_small_window != null) {
            tv_small_window.setText("其他人员已离线，通话结束");
        } else {
            LKToastUtil.showToastShort("其他人员已离线，通话结束");
        }
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            service.stopServiceAndClear();
        }
    }

    /**
     * 有新人加入
     *
     * @param imObj
     */
    @Override
    public void onJoinNewMembers(IMObj imObj) {
        LKLogUtil.e("resultz最小化所有新家的与会人员----==" + imObj.members);
        ArrayList<GroupMenberListdata> joinListdata = LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);
        LKLogUtil.e("result==最小化新家的与会人员" + joinListdata.size());

        if (joinListdata != null && joinListdata.size() > 0) {
            mController.allMembers.addAll(joinListdata);
        }

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

    }

    @Override
    public void onDestoryRoom(IMObj imObjDes) {
        LKLogUtil.e("result==" + "最小化回调中的onDestory销毁房间的回调");
        if (tv_small_window != null) {
            tv_small_window.setText("其他人员已离线，通话结束");
        } else {
            LKToastUtil.showToastShort("其他人员已离线，通话结束");
        }
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            service.stopServiceAndClear();
        }
    }
}
