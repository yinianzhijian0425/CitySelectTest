package tech.yunjing.biconlife.liblkclass.lkbase.uibase.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tech.yunjing.biconlife.liblkclass.R;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.http.LKFinalList;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;
import tech.yunjing.biconlife.liblkclass.widget.Loader.LKLoadingView;

/**
 * FragmentActivity基类，注解支持+消息接收传递
 *
 * @author nanPengFei
 */
@SuppressLint("HandlerLeak")
public abstract class LKBaseFragmentActivity extends FragmentActivity {
    /**
     * 首次进入
     */
    protected boolean isFirst = true;
    protected Handler mHandler;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LK.view().inject(this);
        initHandler();
        initView();
        initData();
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
     * 开始请求网络
     */
    protected void requeststart() {
        showLoader();
    }

    /**
     *
     */
    protected abstract void initView();

    /**
     *
     */
    protected abstract void initData();

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

    /**
     * 初始化进度条
     */
    protected void initLoader() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.view_loading, null);

        ImageView ivLoading = (ImageView) view.findViewById(R.id.iv_lkv_loading);
        ivLoading.startAnimation(getLoadingRotateAnimation());
        //进度条属性
        LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
        ViewGroup.LayoutParams rootParams = llRoot.getLayoutParams();
        rootParams.width = LKCommonUtil.dip2px(mRootWAndH);
        rootParams.height = LKCommonUtil.dip2px(mRootWAndH);
        llRoot.setBackgroundResource(mRootColor);
        llRoot.setLayoutParams(rootParams);
        LKLoadingView vLoading = (LKLoadingView) view.findViewById(R.id.lkv_loading);
        ViewGroup.LayoutParams loadParams = vLoading.getLayoutParams();
        vLoading.setIndicatorColor(mLoadColor);
        vLoading.setIndicator(mLoadType);
        loadParams.width = LKCommonUtil.dip2px(mLoadWAndH);
        loadParams.height = LKCommonUtil.dip2px(mLoadWAndH);
        vLoading.setLayoutParams(loadParams);
        TextView tvDes = (TextView) view.findViewById(R.id.tv_des);
        tvDes.setText(mDesContent);
        tvDes.setTextSize(mDesSize);
        tvDes.setTextColor(mDesColor);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mLayout.setGravity(Gravity.CENTER);
        mLayout.addView(view);
        this.addContentView(mLayout, params);
        mLayout.setVisibility(View.GONE);
    }


    /**
     * 获取Loading圈旋转动画
     */
    private RotateAnimation getLoadingRotateAnimation() {
        Interpolator animationInterpolator = new LinearInterpolator();
        int rotationAnimationDuration = 1200;
        RotateAnimation mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(animationInterpolator);
        mRotateAnimation.setDuration(rotationAnimationDuration);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        return mRotateAnimation;
    }

    private static int mLoadColor = Color.parseColor("#FFFFFF");
    private static String mLoadType = "LKLoaderA";
    private static int mLoadWAndH = 30;

    private static int mRootWAndH = 100;
    private static int mRootColor = R.drawable.shape_loading;

    private static int mDesColor = Color.parseColor("#FFFFFF");
    private static float mDesSize = 12;
    private static String mDesContent = "加载中...";


    /**
     * 设置进度条颜色、大小、样式
     *
     * @param loadColor 颜色
     * @param loadWAndH 控件大小DP值
     * @param loadType  :样式可以设置三种(LKLoaderA、LKLoaderB、LKLoaderC)
     */
    public void setLoadView(int loadColor, int loadWAndH, String loadType) {
        mLoadColor = loadColor;
        mLoadWAndH = loadWAndH;
        mLoadType = loadType;
    }

    /**
     * 设置进度条跟布局颜色和宽高
     *
     * @param rootColor
     * @param rootWAndH
     */
    public void setLoadRootView(int rootColor, int rootWAndH) {
        mRootColor = rootColor;
        mRootWAndH = rootWAndH;
    }

    /**
     * 设置进度条显示文字属性
     *
     * @param desColor
     * @param desSize
     * @param desContent
     */
    public void setLoadView(int desColor, float desSize, String desContent) {
        mDesColor = desColor;
        mDesSize = desSize;
        mDesContent = desContent;
    }

    /**
     * 显示进度条
     */
    public void showLoader() {
        if (null == mLayout) {
            initLoader();
        }
        mLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭进度条
     */
    public void closeLoader() {
        if (null != mLayout) {
            mLayout.setVisibility(View.GONE);
        }
    }
}
