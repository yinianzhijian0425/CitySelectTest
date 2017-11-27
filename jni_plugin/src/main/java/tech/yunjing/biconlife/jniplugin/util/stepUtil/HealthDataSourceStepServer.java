package tech.yunjing.biconlife.jniplugin.util.stepUtil;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.global.BCSharePreKey;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;

/**
 * 健康数据步数逻辑处理
 * Created by CHP on 2017/7/3.
 */

public class HealthDataSourceStepServer {
    private static HealthDataSourceStepServer sInstance;
    private Messenger messenger;
    //控件
    private Messenger mGetReplyMessenger;
    private Intent intent;
    private Context mcontext;
    private Handler mHandler;
    private boolean isRunningServices;
    private boolean isBind = false;
    /**
     * 实例化对象
     *
     * @return
     */
    public static HealthDataSourceStepServer getInstance() {
        if (sInstance == null) {
            sInstance = new HealthDataSourceStepServer();
        }
        return sInstance;
    }

    public void init(Context context, Handler handler) {
        mcontext = context;
        mHandler = handler;
        mGetReplyMessenger = new Messenger(mHandler);
        boolean isRunning =isServiceWork(context, "tech.yunjing.biconlife.jniplugin.util.stepUtil.StepSerVice");
        LKLogUtil.e("服务是否在运行===" + isRunning);
       boolean isOpen= LKPrefUtil.getBoolean(BCSharePreKey.STEP_SWITCH_BTN_STATE,true);
        if(isOpen){
            setupService();
        }


    }

    //以bind形式开启service，故有ServiceConnection接收回调
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LKLogUtil.e("服务开启=======" + name);
            isRunningServices = true;
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, BCSharePreKey.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
                LKLogUtil.e("服务开启======23242354235=" + name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public String getTodayDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        return sdf.format(date);
    }

    public void requestServer() {
        try {
            Message msgl = Message.obtain(null, BCSharePreKey.MSG_FROM_CLIENT);
            msgl.replyTo = mGetReplyMessenger;
            if (messenger != null) {
                messenger.send(msgl);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启服务
     */
    public void setupService() {
        intent = new Intent(mcontext, StepSerVice.class);
        isBind=mcontext.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        mcontext.startService(intent);
    }

    /**
     * 取消连接
     */
    public void disConnect() {
        if (isBind) {
            LKLogUtil.e("服务开启===" + isRunningServices);
            //取消服务绑定
            mcontext.unbindService(conn);
            isBind=false;
        }
    }
    /**
     * 停止服务
     */
    public void stopService() {
        if (intent!=null&&isBind) {
            mcontext.stopService(intent);
        }
    }
    /**
     * 天数解析
     *
     * @return
     */
    public long setJsonTime(String starTime) {
        LKLogUtil.e("开始时间==" + starTime);
        long timeetemp = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(starTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startDate != null) {
            timeetemp = startDate.getTime();
        }
        return timeetemp;
    }



    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
