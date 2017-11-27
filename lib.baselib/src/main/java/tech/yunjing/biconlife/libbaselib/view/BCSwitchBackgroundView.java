package tech.yunjing.biconlife.libbaselib.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.util.ImageUtil;
import tech.yunjing.biconlife.libbaselib.util.ViewSwitchUtils;
import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.common.util.LKAppUtil;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 可切换背景布局控件
 * Created by sun.li on 2017/3/17 0017.
 */
public class BCSwitchBackgroundView extends RelativeLayout {

    private Context mContext;

    private ImageView iv_bc_switch_background_bg_image;

    private RelativeLayout rl_bc_switch_background_view_layout;

    /**
     * 是否低倍模糊，默认false
     */
    private boolean itsLessThanFuzzy = false;

    private int fuzzyMultiple = 15;

    private int ratio = 10;

    public BCSwitchBackgroundView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BCSwitchBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public BCSwitchBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.layout_bc_switch_background_view, null);
        iv_bc_switch_background_bg_image = (ImageView) view.findViewById(R.id.iv_bc_switch_background_bg_image);
        rl_bc_switch_background_view_layout = (RelativeLayout) view.findViewById(R.id.rl_bc_switch_background_view_layout);
        addView(view);
    }

    /**
     * 设置背景图片
     */
    public void setBackgroundImage(int resourcesID) {
        Bitmap bitmap = ImageUtil.drawable2Bitmap(getResources().getDrawable(resourcesID));
        setLayoutBG(bitmap, iv_bc_switch_background_bg_image);
    }

    /**
     * 设置背景图片
     */
    public void setBackgroundImage(Bitmap bitmap) {
        setLayoutBG(bitmap, iv_bc_switch_background_bg_image);
    }

    /**
     * 设置背景图片
     */
    public void setBackgroundImage(Drawable drawable) {
        Bitmap bitmap = ImageUtil.drawable2Bitmap(drawable);
        setLayoutBG(bitmap, iv_bc_switch_background_bg_image);
    }

    /**
     * 设置背景图片
     */
    public void setBackgroundImage(String url) {
        if(!TextUtils.isEmpty(url)){
            LK.image().loadDrawable(url, LKImageOptions.getFITXYOptions(R.mipmap.icon_square_image_default), new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable drawable) {
                    setBackgroundImage(drawable);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    public boolean isItsLessThanFuzzy() { return itsLessThanFuzzy; }

    /**
     * 设置是否低倍模糊，默认false
     */
    public void setItsLessThanFuzzy(boolean itsLessThanFuzzy) { this.itsLessThanFuzzy = itsLessThanFuzzy; }

    private Runnable mBlurRunnable;

    private void setLayoutBG(final Bitmap mbitmap, final ImageView view) {
        if (mbitmap == null || view == null) {
            return;
        }
        try {
            //判断前后图片是否是同一张图片
            if (view.getDrawable() != null) {
                Drawable.ConstantState drawableCs = view.getDrawable().getConstantState();
                if (ImageUtil.bitmap2Drawable(mContext, mbitmap).getConstantState().equals(drawableCs)) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mBlurRunnable != null) {
            view.removeCallbacks(mBlurRunnable);
        }

        if (isItsLessThanFuzzy()) {
            fuzzyMultiple = 1;
            ratio = 5;
        } else {
            fuzzyMultiple = 15;
            ratio = 10;
        }
        mBlurRunnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ImageUtil.blurBitmap(mbitmap, fuzzyMultiple, ratio, false);
                try {
                    ViewSwitchUtils.startSwitchBackgroundAnim(view, bitmap);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        view.postDelayed(mBlurRunnable, 1);

    }
}