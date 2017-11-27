package tech.yunjing.biconlife.jniplugin.view.photoView;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.global.BCFileBasePath;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;
import tech.yunjing.biconlife.liblkclass.http.lkhttp.LKDownloadRequest;

/**
 * 公共图片浏览Dialog界面
 * Created by sun.li on 2017/9/16.
 */

public class BCImageDisplayDialog extends Dialog {

    /**
     * 上下文
     */
    private Context mContext;

//    /** Handler */
//    private Handler mHandler;

    /**
     * Dialog布局父容器
     */
    private RelativeLayout rl_bcidd_layout;

    /**
     * Dialog背景布局
     */
    private RelativeLayout rl_bcidd_background_layout;

    /**
     * 公共图片展示ViewPager
     */
    private BCPhotoViewPager bc_view_pager_photo;

    /**
     * 公共图片展示数量指示器
     */
    private TextView mTvImageCount;

    private TextView mTvSaveImage;

    /**
     * Dialog关闭过程中屏蔽事件专用控件
     */
    private Button btn_bcidd_cancel_blocking_events;

    /**
     * 当前展示的图片位置
     */
    private int currentPosition = 0;

    /**
     * 图片展示适配器对象
     */
    private BCImageAdapter adapter;

    /**
     * 图片地址路径集合
     */
    private List<String> urls;

    /**
     * 待展示图片对象集合
     */
    private List<ImageInfoObj> imageInfoObjList;

    public BCImageDisplayDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BCImageDisplayDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.MyImageDisplayDialogStyleIs);
        mContext = context;
        initView();
    }

//    public BCImageDisplayDialog(@NonNull Context context, @StyleRes int themeResId, Handler handler) {
//        super(context, R.style.MyImageDisplayDialogStyleIs);
//        mContext = context;
//        mHandler = handler;
//        initView();
//    }

    protected BCImageDisplayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    /**
     * 初始化控件
     */
    public void initView() {
        if (mContext == null) {
            mContext = LKApplication.getContext();
        }
        try {
//            if (Build.VERSION.SDK_INT < 19) {
//                return;
//            }
            whetherFullScreenDisplayNoticeBoard(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_bc_photo_view, null);
        rl_bcidd_layout = (RelativeLayout) view.findViewById(R.id.rl_bcidd_layout);
        rl_bcidd_background_layout = (RelativeLayout) view.findViewById(R.id.rl_bcidd_background_layout);
        bc_view_pager_photo = (BCPhotoViewPager) view.findViewById(R.id.bc_view_pager_photo);
        mTvImageCount = (TextView) view.findViewById(R.id.bc_tv_image_count);
        mTvSaveImage = (TextView) view.findViewById(R.id.bc_tv_save_image_photo);
        btn_bcidd_cancel_blocking_events = (Button) view.findViewById(R.id.btn_bcidd_cancel_blocking_events);

        int windowHeight = LKCommonUtil.getScreenHeight();
//        try {
//            int navigationBarHeight = NavigationBarUtil.getNavigationBarHeight((Activity) mContext);
//            windowHeight += navigationBarHeight;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LKCommonUtil.getScreenWidth(), windowHeight);
        setContentView(view, layoutParams);
//        AndroidBug54971Workaround.assistActivity(view);
        setCanceledOnTouchOutside(true);
    }

    /**
     * 获取图片地址路径集合
     */
    public List<String> getUrls() {
        return urls;
    }

    /**
     * 设置图片地址路径集合
     */
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<ImageInfoObj> getImageInfoObjList() { return imageInfoObjList; }

    public void setImageInfoObjList(List<ImageInfoObj> imageInfoObjList) { this.imageInfoObjList = imageInfoObjList; }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        try {
////            if (Build.VERSION.SDK_INT < 19) {
////                return;
////            }
//            whetherFullScreenDisplayNoticeBoard(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 显示Dialog
     */
    @Override
    public void show() {
        super.show();
        btn_bcidd_cancel_blocking_events.setVisibility(View.GONE);
        if (getUrls() == null || getUrls().size() <= 0) {
            hide();
        }
        initViewPager();
    }

    @Override
    public void hide() {
        mTvImageCount.setVisibility(View.GONE);
        mTvSaveImage.setVisibility(View.GONE);
        btn_bcidd_cancel_blocking_events.setVisibility(View.VISIBLE);
        btn_bcidd_cancel_blocking_events.bringToFront();
        btn_bcidd_cancel_blocking_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        whetherFullScreenDisplayNoticeBoard(false);
        imageInfoObjList = null;//测试代码
        if (imageInfoObjList != null && imageInfoObjList.get(currentPosition) != null) {
            ImageInfoObj imageInfoObj = imageInfoObjList.get(currentPosition);
            int width = imageInfoObj.getViewWidth();
            int height = imageInfoObj.getViewHeight();
            int x = imageInfoObj.getxCoordinate();
            int y = imageInfoObj.getyCoordinate();

            double widthF = width/LKCommonUtil.getScreenWidth();
            double heightF = height/LKCommonUtil.getScreenHeight();
            double xF = x/LKCommonUtil.getScreenWidth();
            double yF = y/LKCommonUtil.getScreenHeight();

            LKLogUtil.e("x:"+xF+" y:"+yF+" width:"+widthF+" height:"+heightF);
            /* 缩放动画*/
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, (float) widthF, 1f, (float) heightF, (float) xF, (float) yF);
//            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, Float.intBitsToFloat(width), 1f, Float.intBitsToFloat(height));
            scaleAnimation.setDuration(400);//动画持续时间，毫秒为单位
            scaleAnimation.setFillAfter(true);//控件动画结束时是否保持动画最后的状态
            /* 平移动画*/
            TranslateAnimation translateAnimation = new TranslateAnimation(0,(float) xF , 0, (float) yF);
            translateAnimation.setDuration(400);//动画持续时间，毫秒为单位
            translateAnimation.setFillAfter(true);//控件动画结束时是否保持动画最后的状态

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(translateAnimation);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    bc_view_pager_photo.setVisibility(View.GONE);
                    cancel();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            bc_view_pager_photo.startAnimation(animationSet);
        } else {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.big_image_scale_shrink);
            animation.setFillAfter(true);//控件动画结束时是否保持动画最后的状态
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    bc_view_pager_photo.setVisibility(View.GONE);
                    cancel();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            bc_view_pager_photo.startAnimation(animation);
        }

        Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.anim_window_bottom_dialog_cancel_alpha);
        animation1.setFillAfter(true);//控件动画结束时是否保持动画最后的状态
        rl_bcidd_background_layout.startAnimation(animation1);
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    private void initViewPager() {
        if (getUrls() != null && getUrls().size() > 0) {
            adapter = new BCImageAdapter(getUrls(), mContext);

            adapter.setItemOnClick(new BCImageAdapter.ItemOnClick() {
                @Override
                public void onViewClick() {
                    hide();
                }
            });

            mTvImageCount.setText(currentPosition + 1 + "/" + getUrls().size());
            mTvImageCount.bringToFront();

            bc_view_pager_photo.setAdapter(adapter);
            bc_view_pager_photo.setCurrentItem(currentPosition, false);
            bc_view_pager_photo.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    currentPosition = position;
                    mTvImageCount.setText(currentPosition + 1 + "/" + getUrls().size());
                }
            });

            mTvSaveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //保存按钮点击
                    String img_uri = getUrls().get(currentPosition);
                    saveImage(img_uri);
                }
            });
        }
    }

    /**
     * 保存图片
     */
    private void saveImage(String url) {
        if (url.startsWith("http")) {
            Uri uri = Uri.parse(url);
            String imgName = uri.getLastPathSegment();
            if (!TextUtils.isEmpty(imgName)) {
                if (!imgName.contains(".")) {
                    imgName = imgName + ".jpg";
                }
            } else {
                imgName = System.currentTimeMillis() + ".jpg";
            }
            LKDownloadRequest.downloadImg(handler, url, BCFileBasePath.PATH_DOWNLOADER, imgName, false, 0);
        } else {
            String folderPath = url.substring(0, url.lastIndexOf("/") + 1);
            LKToastUtil.showToastShort("图片已保存至" + folderPath + "文件夹");
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int state = bundle.getInt("state");
            LKLogUtil.e("===state===" + state);
            switch (state) {
                case -2:
                    LKToastUtil.showToastShort("图片已保存至" + BCFileBasePath.PATH_DOWNLOADER + "文件夹");
                    break;
                case -4:
                    LKToastUtil.showToastShort("图片下载失败");
                    break;
                default:
                    break;
            }
        }
    };

    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * 设置初始化加载显示的页码，从0开始（0为第1页），设置页码需要在setUrls方法之后设置
     */
    public void setCurrentPosition(int currentPosition) {
        if (urls != null) {
            if (currentPosition >= urls.size()) {
                currentPosition = urls.size() - 1;
            } else if (currentPosition < 0) {
                currentPosition = 0;
            }
        } else {
            currentPosition = 0;
        }
        this.currentPosition = currentPosition;
    }

    /**
     * 是否全屏显示,true 全屏显示，不显示通知栏；false 不全屏显示，显示通知栏；
     */
    private void whetherFullScreenDisplayNoticeBoard(boolean enable) {
        try {
//            LKLogUtil.e("NavigationBarShow:" + NavigationBarUtil.isNavigationBarShow(mContext));
            if (enable) {
                //设置全屏
                Window window = this.getWindow();
                if (window != null) {
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DITHER);
                    //window.requestWindowFeature(Window.FEATURE_NO_TITLE); 用在activity中，去标题
                    int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            //布局位于状态栏下方
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar 全屏
                    //        | View.SYSTEM_UI_FLAG_IMMERSIVE
                    //        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Navigation bar hiding:  Backwards compatible to ICS.
                    if (Build.VERSION.SDK_INT >= 14) {
                        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // hide nav bar
                    }
                    // Status bar hiding: Backwards compatible to Jellybean
                    if (Build.VERSION.SDK_INT >= 16) {
                        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
                    }
                    // Immersive mode: Backward compatible to KitKat.
                    // Note that this flag doesn't do anything by itself, it only augments the behavior
                    // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
                    // all three flags are being toggled together.
                    // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
                    // Sticky immersive mode differs in that it makes the navigation and status bars
                    // semi-transparent, and the UI flag does not get cleared when the user interacts with
                    // the screen.
                    if (Build.VERSION.SDK_INT >= 18) {
                        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    }
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        uiOptions |= 0x00001000;
//                    } else {
//                        uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
//                    }
                    window.getDecorView().setSystemUiVisibility(uiOptions);

                    window.getDecorView().setSystemUiVisibility(4108);
//                    if(NavigationBarUtil.isNavigationBarShow(mContext)){
////            NavigationBarUtil.hideNavKey(mContext);
//                        if (Build.VERSION.SDK_INT >= 21) {
//                            window.setNavigationBarColor(Color.TRANSPARENT);
//                        }
//                    }
                }
            } else {
                //取消全屏
                Window window = this.getWindow();
                if (window != null) {
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//                    window.requestWindowFeature(Window.FEATURE_NO_TITLE); 用在activity中，去标题
                    int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_VISIBLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    window.getDecorView().setSystemUiVisibility(uiOptions);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            hide();
        }
        return false;
    }
}
