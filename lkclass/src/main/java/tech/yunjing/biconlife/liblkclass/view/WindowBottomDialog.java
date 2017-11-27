package tech.yunjing.biconlife.liblkclass.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import tech.yunjing.biconlife.liblkclass.R;

/**
 * 系统底部弹出的Dialog
 * Created by sun.li on 2017/8/12.
 */

public class WindowBottomDialog extends Dialog{

    /** 上下文*/
    private Context mContext;

    /** 父容器*/
    private RelativeLayout rl_wb_parent_layout;

    /** 底部容器*/
    private RelativeLayout rl_wb_bottom_layout;

    /** Dialog关闭过程中屏蔽事件专用控件*/
    private Button btn_wbd_cancel_blocking_events;

    public WindowBottomDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public WindowBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.MyDialogStyleIsTranslucentTrue);
        mContext = context;
        initView();
    }

    protected WindowBottomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    /** 初始化控件*/
    public void initView(){
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_windowbottom,null);
        rl_wb_parent_layout = (RelativeLayout) view.findViewById(R.id.rl_wb_parent_layout);
        rl_wb_bottom_layout = (RelativeLayout) view.findViewById(R.id.rl_wb_bottom_layout);
        btn_wbd_cancel_blocking_events = (Button) view.findViewById(R.id.btn_wbd_cancel_blocking_events);
        rl_wb_bottom_layout.bringToFront();
        rl_wb_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
        rl_wb_bottom_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*防止点击了底部有内容区域触发关闭功能*/
            }
        });
        setContentView(view,layoutParams);
    }

    public void setBottomView(View view){
        if(rl_wb_bottom_layout!=null){
            rl_wb_bottom_layout.removeAllViews();
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            rl_wb_bottom_layout.addView(view);
        }
    }

    /** 显示Dialog*/
    @Override
    public void show() {
        super.show();
        btn_wbd_cancel_blocking_events.setVisibility(View.GONE);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        rl_wb_parent_layout.setVisibility(View.VISIBLE);
        rl_wb_bottom_layout.setVisibility(View.VISIBLE);
        rl_wb_parent_layout.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_window_bottom_dialog_show_alpha));
        rl_wb_bottom_layout.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_window_bottom_dialog_show_translate));
    }

    @Override
    public void hide(){
        btn_wbd_cancel_blocking_events.setVisibility(View.VISIBLE);
        btn_wbd_cancel_blocking_events.bringToFront();
        btn_wbd_cancel_blocking_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_window_bottom_dialog_cancel_alpha);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_wb_parent_layout.setVisibility(View.GONE);
                rl_wb_bottom_layout.setVisibility(View.GONE);
                cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_wb_parent_layout.startAnimation(animation);
        rl_wb_bottom_layout.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_window_bottom_dialog_cancel_translate));
    }

    @Override
    public void cancel() {
        super.cancel();
    }


}
