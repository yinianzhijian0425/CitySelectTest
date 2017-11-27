package tech.yunjing.biconlife.jniplugin.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import tech.yunjing.biconlife.jniplugin.R;


/**
 * 公共确认取消Dialog
 * Created by sun.li on 2017/7/14.
 */
public class JniCancelAndConfirmDialog extends Dialog {

    private LayoutInflater layoutInflater;

    /**
     * 上下文
     */
    private Context mContext;

    private View view;

    /**
     * 标题文本框
     */
    private TextView tv_jcacd_title;

    /**
     * 内容文本框
     */
    private TextView tv_jcacd_content;

    /**
     * 取消按钮
     */
    private TextView btn_jcacd_cancel;

    /**
     * 确认按钮
     */
    private TextView btn_jcacd_confirm;

    /**
     * 事件接口对象
     */
    EventInterface eventInterface = null;

    public JniCancelAndConfirmDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
        initViewEvent();
    }

    public JniCancelAndConfirmDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
        initViewEvent();
    }

    protected JniCancelAndConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
        initViewEvent();
    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.dialog_jcacd_cancel_and_confirm, null);
        tv_jcacd_title = (TextView) view.findViewById(R.id.tv_jcacd_title);
        tv_jcacd_content = (TextView) view.findViewById(R.id.tv_jcacd_content);
        btn_jcacd_cancel = (TextView) view.findViewById(R.id.btn_jcacd_cancel);
        btn_jcacd_confirm = (TextView) view.findViewById(R.id.btn_jcacd_confirm);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void visibleCancle() {
        if (btn_jcacd_cancel != null) {
            btn_jcacd_cancel.setVisibility(View.GONE);

        }

    }

    /**
     * 初始化布局控件事件
     */
    private void initViewEvent() {
        btn_jcacd_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventInterface != null) {
                    eventInterface.cancelOnClick();
                }
                cancel();
            }
        });

        btn_jcacd_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventInterface != null) {
                    eventInterface.confirmOnClick();
                }
                cancel();
            }
        });
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            tv_jcacd_title.setVisibility(View.GONE);
        } else {
            tv_jcacd_title.setVisibility(View.VISIBLE);
            tv_jcacd_title.setText(title);
        }
    }

    /**
     * 设置内容
     */
    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) {
            tv_jcacd_content.setVisibility(View.GONE);
        } else {
            tv_jcacd_content.setVisibility(View.VISIBLE);
            tv_jcacd_content.setText(content);
        }
    }

    /**
     * 设置左侧取消按钮显示文本
     */
    public void setCancelBtnText(String cancelStr) {
        if (!TextUtils.isEmpty(cancelStr)) {
            btn_jcacd_cancel.setText(cancelStr);
        }
    }

    /**
     * 设置右侧确认按钮显示文本
     */
    public void setConfirmBtnText(String confirmStr) {
        if (!TextUtils.isEmpty(confirmStr)) {
            btn_jcacd_confirm.setText(confirmStr);
        }
    }


    /**
     * 设置取消按钮的粗细
     **/
    public void setCancelBold() {
        TextPaint paint = btn_jcacd_cancel.getPaint();
        paint.setFakeBoldText(true);
    }

    /**
     * 设置标题字体颜色
     */
    public void setTitleColor(int color) {
        tv_jcacd_title.setTextColor(color);
    }

    /**
     * 设置内容字体颜色
     */
    public void setContentColor(int color) {
        tv_jcacd_title.setTextColor(color);
    }

    /**
     * 设置标题的大小
     **/
    public void setTitleSize(int size) {
        tv_jcacd_title.setTextSize(size);
    }

    /**
     * 设置取消按钮字体颜色
     */
    public void setCancelBtnColor(int color) {
        btn_jcacd_cancel.setTextColor(color);
    }

    /**
     * 设置确认按钮字体颜色
     */
    public void setConfirmBtnColor(int color) {
        btn_jcacd_confirm.setTextColor(color);
    }

    /**
     * 设置事件接口对象
     */
    public void setEventInterface(EventInterface eventInterface) {
        this.eventInterface = eventInterface;
    }

    public interface EventInterface {

        /**
         * 取消按钮点击事件
         */
        void cancelOnClick();

        /**
         * 确认按钮点击事件
         */
        void confirmOnClick();

    }

}
