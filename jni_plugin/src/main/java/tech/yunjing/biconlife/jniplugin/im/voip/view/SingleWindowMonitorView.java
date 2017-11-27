package tech.yunjing.biconlife.jniplugin.im.voip.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.voip.AgoraManager;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterHolder;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleWindowInter;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 小窗口单聊
 */
public class SingleWindowMonitorView extends RelativeLayout implements View.OnTouchListener, CenterSingleWindowInter {

    private Intent intent;

    private final CenterSingleController mController;

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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 7001:
                    //注意SurfaceView要创建在主线程
                    if (local_video_widow != null) {
                        local_video_widow.removeAllViews();
                        AgoraManager.getInstance().setupRemoteVideo(context, mController.mVideoUid);
                        local_video_widow.addView(AgoraManager.getInstance().getSurfaceView(mController.mVideoUid));
                        local_video_widow.setVisibility(VISIBLE);
                    }
                    break;
                case 7002:
                    if (local_video_widow != null) {
                        local_video_widow.removeAllViews();
                        local_video_widow.setVisibility(GONE);
                        if (mController.mVideoUid != 0) {
                            AgoraManager.getInstance().removeSurfaceView(mController.mVideoUid);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 小窗口中的文本view
     */
    private TextView tv_small_window;
    /**
     * 最小化后对方的视图
     */
    private FrameLayout local_video_widow;

    private Context context;

    public SingleWindowMonitorView(final Context context, int type) {
        super(context);
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View inflate;
        if (type == 0) {
            inflate = LayoutInflater.from(context).inflate(R.layout.small_window_monitor, this, true);
            tv_small_window = (TextView) inflate.findViewById(R.id.tv_small_window);
        } else {
            inflate = LayoutInflater.from(context).inflate(R.layout.small_window_video, this, true);
            local_video_widow = (FrameLayout) inflate.findViewById(R.id.local_video_widow);
        }

        inflate.setOnTouchListener(this);
        mController = CenterHolder.getInstance().getSingleController();
        mController.addChangeDataListener(this);
    }


    public SingleWindowMonitorView(Context context, Intent intentParam, int type) {
        this(context, type);
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
                    handler.sendEmptyMessage(7002);
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
    public void onTimerTick(String time) {
        if (tv_small_window != null) {
            tv_small_window.setText(time);
        }
        postInvalidate();
    }

    @Override
    public void onServiceDestroy() {

    }


    /**
     * 对方接听
     *
     * @param call
     */
    @Override
    public void onCallAnswered(String call) {
        if (tv_small_window != null) {
            tv_small_window.setText("00:00");
        }
    }


    /**
     * 挂断
     *
     * @param call
     */
    @Override
    public void onCallReleased(String call) {
        if ("挂断".equals(call)) {
            if (tv_small_window != null) {
                tv_small_window.setText("通话结束");
            }
            if (mController.isCalling && !mController.isSelfEnd) {
                //如果已接通,并且不是自己挂断的，表示对方挂断
                LKToastUtil.showToastShort("对方已结束通话");
            }
            CenterService service = CenterHolder.getInstance().getService();
            if (service != null) {
                service.stopServiceAndClear();
            }
        }

    }


    /**
     * 对方拒绝
     *
     * @param call
     */
    @Override
    public void onRefuseToAnswer(String call) {
        if ("拒绝".equals(call)) {
            if (tv_small_window != null) {
                tv_small_window.setText("对方拒绝，通话结束");
            } else {
                LKToastUtil.showToastShort("对方拒绝，通话结束");
            }
            CenterService service = CenterHolder.getInstance().getService();
            if (service != null) {
                service.stopServiceAndClear();
            }

        }
    }


    /**
     * 超过时间无人接听
     */
    @Override
    public void timeOutNoAnswer() {
        if (mController.mCallState == CenterSingleController.CallState.INCOMING || mController.mCallState == CenterSingleController.CallState.OUTGOING) {
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
     * 获取远端视频uid
     *
     * @param uid
     */
    @Override
    public void onGetRemoteVideo(int uid) {
        mController.mVideoUid = uid;
    }

    @Override
    public void onLeaveChannelSuccess() {
        if (tv_small_window != null) {
            tv_small_window.setText("对方离线，通话结束");
        }
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            service.stopServiceAndClear();
        }
    }


    @Override
    public void onUserOffline(int uid, int reason) {
        if (tv_small_window != null) {
            tv_small_window.setText("对方离线，通话结束");
        }
        CenterService service = CenterHolder.getInstance().getService();
        if (service != null) {
            service.stopServiceAndClear();
        }

    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid) {

    }

    /**
     * 最小化后更新对方视图的显示
     */
    public void upCremaSurfaceView() {
        handler.sendEmptyMessage(7001);
    }

    /**
     * 清楚最小化视图
     */
    public void clearnSurfaceView() {
        handler.sendEmptyMessage(7002);
    }
}
