package tech.yunjing.biconlife.liblkclass.global;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * Created by nanPengFei on 2016/8/31 16:30.
 */
public class LKApplication extends Application {
    private static Handler mHandler;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LK.Ext.init(LKApplication.this);
        mHandler = new Handler();
        mContext = this;
    }


    /**
     * 获取主Handler
     *
     * @return Handler
     */
    public static Handler getMainHandler() {
        return mHandler;
    }

    public static Context getContext() {
        return mContext;
    }





}
