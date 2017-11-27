package tech.yunjing.biconlife.liblkclass.lkbase.uibase.pager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 页面基类
 * 针对于数据的加载，暂时设置为子类手动加载，防止用户流量消耗过大
 *
 * @author nanPengFei
 */
public abstract class LKBasePager {
    public Activity mActivity;
    public View mRootView;
    protected int initLayout;
    /**
     * 用于子类消息接收
     */
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dataUpdating(msg);
        }
    };

    public LKBasePager(Activity activity) {
        this.mActivity = activity;
        initLayout = initLPBasePagerView();
        this.mRootView = View.inflate(mActivity, initLayout, null);
        LK.view().inject(this, mRootView);
        initView();
//		initData();
    }

    /**
     * 子类重写，便于布局填充和注解支持
     *
     * @return int
     */
    public abstract int initLPBasePagerView();

    /**
     *
     */
    public abstract void initView();

    /**
     *
     */
    public abstract void initData();

    /**
     * @param msg
     */
    protected abstract void dataUpdating(Message msg);
}
