package tech.yunjing.biconlife.libbaselib.base;

import android.app.Activity;
import android.content.res.Resources;

import java.util.LinkedList;
import java.util.List;
import tech.yunjing.biconlife.jniplugin.base.AppBaseApplication;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;
public class BaseApplication extends AppBaseApplication {

    private List<Activity> mActivityList = new LinkedList<>();
    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        if (null == mInstance) {
            synchronized (BaseApplication.class) {
                if (null == mInstance) {
                    mInstance = new BaseApplication();
                }
            }
        }
        return mInstance;
    }

    public static Resources getAppResources() {
        return mInstance.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * 添加activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null && !mActivityList.contains(activity)) {
            mActivityList.add(activity);
        }
    }


    /**
     * 遍历所有的Activity并finish
     */
    public void exitApp() {
        try {
            for (int i = 0; i < mActivityList.size(); i++) {
                Activity activity = mActivityList.get(i);
                if (null != activity) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历指定的Activity并finish
     */
    public void exit(int num) {
        for (int i = 1; i <= num; i++) {
            if (mActivityList.size() >= num) {
                Activity activity = mActivityList.get(mActivityList.size() - i);
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 从集合中移除activity并finish
     */
    public void finish() {
        Activity activity = mActivityList.get(mActivityList.size() - 1);
        mActivityList.remove(mActivityList.size() - 1);
        if (activity != null) {
            activity.finish();
        }
    }


}
