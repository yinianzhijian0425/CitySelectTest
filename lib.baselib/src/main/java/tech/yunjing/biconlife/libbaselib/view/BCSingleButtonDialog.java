package tech.yunjing.biconlife.libbaselib.view;

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

import tech.yunjing.biconlife.libbaselib.R;

/**
 * 公共单按钮Dialog
 * Created by sun.li on 2017/7/14.
 */
public class BCSingleButtonDialog extends Dialog {

    private LayoutInflater layoutInflater;

    /**
     * 上下文
     */
    private Context mContext;

    private View view;

    /**
     * 标题文本框
     */
    private TextView tv_ssb_title;

    /**
     * 内容文本框
     */
    private TextView tv_ssb_content;

    /**
     * 确认按钮
     */
    private TextView btn_ssb_confirm;

    /**
     * 事件接口对象
     */
    EventInterface eventInterface = null;

    public BCSingleButtonDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
        initViewEvent();
    }

    public BCSingleButtonDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
        initViewEvent();
    }

    protected BCSingleButtonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
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
        this.setCancelable(false);
        layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.dialog_sbd_single_button, null);
        tv_ssb_title = (TextView) view.findViewById(R.id.tv_ssb_title);
        tv_ssb_content = (TextView) view.findViewById(R.id.tv_ssb_content);
        btn_ssb_confirm = (TextView) view.findViewById(R.id.btn_ssb_confirm);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 初始化布局控件事件
     */
    private void initViewEvent() {
        btn_ssb_confirm.setOnClickListener(new View.OnClickListener() {
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
            tv_ssb_title.setVisibility(View.GONE);
        } else {
            tv_ssb_title.setVisibility(View.VISIBLE);
            tv_ssb_title.setText(title);
        }
    }

    /**
     * 设置内容
     */
    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) {
            tv_ssb_content.setVisibility(View.GONE);
        } else {
            tv_ssb_content.setVisibility(View.VISIBLE);
            tv_ssb_content.setText(content);
        }
    }

    /**
     * 设置右侧确认按钮显示文本
     */
    public void setConfirmBtnText(String confirmStr) {
        if (!TextUtils.isEmpty(confirmStr)) {
            btn_ssb_confirm.setText(confirmStr);
        }
    }


    /**
     * 设置标题字体颜色
     */
    public void setTitleColor(int color) {
        tv_ssb_title.setTextColor(color);
    }

    /**
     * 设置内容字体颜色
     */
    public void setContentColor(int color) {
        tv_ssb_title.setTextColor(color);
    }

    /**
     * 设置标题的大小
     **/
    public void setTitleSize(int size) {
        tv_ssb_title.setTextSize(size);
    }

    /**
     * 设置确认按钮字体颜色
     */
    public void setConfirmBtnColor(int color) {
        btn_ssb_confirm.setTextColor(color);
    }

    /**
     * 设置事件接口对象
     */
    public void setEventInterface(EventInterface eventInterface) {
        this.eventInterface = eventInterface;
    }

    public interface EventInterface {
        /**
         * 确认按钮点击事件
         */
        void confirmOnClick();
    }

}
