package tech.yunjing.biconlife.liblkclass.lkbase.uibase.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.http.LKFinalList;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.activity.LKBaseActivity;

/**
 * Fragment基类，注解支持+消息接收传递+实现懒加载
 *
 * @author nanPengFei
 */
@SuppressLint("HandlerLeak")
public abstract class LKBaseFragment extends Fragment {
    public Activity mActivity;
    private View mRootView = null;
    /**
     * 是否可见
     */
    protected boolean isVisible = false;
    /**
     * 控件是否初始化完成
     */
    protected boolean isPrepared = false;
    /**
     * 首次进入
     */
    protected boolean isFirst = true;
    protected Handler mHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
        mActivity = getActivity();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(initFragmentView(), container, false);
            LK.view().inject(this, mRootView);
            initHandler();
        }
        // 由于本地保存，所以二次加载前将旧的移除
        LKCommonUtil.removeSelfFromParent(mRootView);
        return mRootView;
    }

    /**
     * Fragment所依赖的Activity创建完成后会调用此方法
     * <p/>
     * 注：多个Fragment使用时第一个fragment在显示时会执行此处的initView() 和initData();(例如ViewPager+Fragment)
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //已经显示，并且是第一次进入则加载数据
        if (isVisible && isFirst) {
            isFirst = false;
            initView();
            initData();
            initViewEvent();
        }
    }

    /**
     * 用于子类消息接收
     */
    protected void initHandler() {
        // TODO Auto-generated method stub
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case LKFinalList.REQUEST_START:
                            requeststart();
                            break;
                        case LKFinalList.REQUEST_SUCCESS:
                            getData(msg);
                            break;
                        case LKFinalList.REQUEST_CANCEL:
                            requestCancel();
                            break;
                        case LKFinalList.REQUEST_FAILURE:
                            requestFail(msg);
                            break;
                        case LKFinalList.FILE_DOWNLOADING:
                            String progress = (String) msg.obj;
                            String[] split = progress.split(":");
                            onLoading(Long.valueOf(split[0]), Long.valueOf(split[1]));
                            break;
                        default:
                            dataUpdating(msg);
                            break;
                    }
                }
            };
        }
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

//        LKLogUtils.e(getUserVisibleHint() + "==setUserVisibleHint=" + isFirst + "==isVisible==" + isVisible + "==isPrepared==" + isPrepared);
    }

    /**
     * Fragment显示的情况下会执行这里；
     * <p/>
     * 注：多个Fragment使用时，除去第一个Fragment外，其余均会在显示时执行此处的initView() 和initData();(例如ViewPager+Fragment)
     */
    protected void onVisible() {
        // fragment可见时执行
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        isFirst = false;
        initView();
        initData();
        initViewEvent();
    }

    protected void onInvisible() {
        // fragment不可见时执行
    }

    /**
     *
     * @return
     */
    protected abstract int initFragmentView();

    /**
     * 开始请求网络
     */
    protected void requeststart() {
        showLoader();
    }

    /**
     * 数据处理层，初始化界面数据
     */
    protected abstract void initData();

    /**
     * 界面处理层，初始化界面View
     */
    protected abstract void initView();

    /**
     * 控件事件处理层，初始化界面View事件
     */
    protected abstract void initViewEvent();

    protected void getData(Message msg) {
        closeLoader();
    }

    protected void onLoading(long total, long current) {
    }

    protected void requestCancel() {
        closeLoader();
    }

    protected void requestFail(Message msg) {
        closeLoader();
    }

    protected void dataUpdating(Message msg) {
        closeLoader();
    }

    @Override
    public void onDestroy() {
        //由于本地保存，所以二次加载前将旧的移除
        LKCommonUtil.removeSelfFromParent(mRootView);
        super.onDestroy();
    }

    /**
     * 设置进度条颜色、大小、样式
     *
     * @param loadColor 颜色
     * @param loadWAndH 控件大小DP值
     * @param loadType  :样式可以设置三种(LKLoaderA、LKLoaderB、LKLoaderC)
     */
    public void setLoadView(int loadColor, int loadWAndH, String loadType) {
        if (null != mActivity) {
            ((LKBaseActivity) mActivity).setLoadView(loadColor, loadWAndH, loadType);
        }
    }

    /**
     * 设置进度条跟布局颜色和宽高
     *
     * @param rootColor
     * @param rootWAndH
     */
    public void setLoadRootView(int rootColor, int rootWAndH) {
        if (null != mActivity) {
            ((LKBaseActivity) mActivity).setLoadRootView(rootColor, rootWAndH);
        }
    }

    /**
     * 设置进度条显示文字属性
     *
     * @param desColor
     * @param desSize
     * @param desContent
     */
    public void setTextView(int desColor, float desSize, String desContent) {
        if (null != mActivity) {
            ((LKBaseActivity) mActivity).setTextView(desColor, desSize, desContent);
        }
    }

    protected void showLoader() {
        if (mActivity != null) {
            ((LKBaseActivity) mActivity).showLoader();
        }
    }

    protected void closeLoader() {
        if (mActivity != null) {
            ((LKBaseActivity) mActivity).closeLoader();
        }
    }
}
