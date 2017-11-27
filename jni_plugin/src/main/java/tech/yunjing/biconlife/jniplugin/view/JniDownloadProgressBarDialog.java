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
import android.widget.ProgressBar;
import android.widget.TextView;

import tech.yunjing.biconlife.jniplugin.R;


/**
 * 公共进度加载Dialog
 * Created by sun.li on 2017/7/14.
 */
public class JniDownloadProgressBarDialog extends Dialog {

    private LayoutInflater layoutInflater;

    /**
     * 上下文
     */
    private Context mContext;

    private View view;

    /**
     * 标题文本框
     */
    private TextView tv_jdpbd_title;

    /**
     * 进度条内容文本框
     */
    private ProgressBar pb_jdpbd_progress_bar;

    /**
     * 进度展示文本框
     */
    private TextView btn_jdpbd_progress_show;

    /**
     * 事件接口对象
     */
    EventInterface eventInterface = null;

    public JniDownloadProgressBarDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
        initViewEvent();
    }

    public JniDownloadProgressBarDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
        initViewEvent();
    }

    protected JniDownloadProgressBarDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
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
        view = layoutInflater.inflate(R.layout.dialog_jdpbd_progress_layout, null);
        tv_jdpbd_title = (TextView) view.findViewById(R.id.tv_jdpbd_title);
        pb_jdpbd_progress_bar = (ProgressBar) view.findViewById(R.id.pb_jdpbd_progress_bar);
        btn_jdpbd_progress_show = (TextView) view.findViewById(R.id.btn_jdpbd_progress_show);
        addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 初始化布局控件事件
     */
    private void initViewEvent() {

    }

    public void visiblePprogressShowText(boolean visibility) {
        if (btn_jdpbd_progress_show != null) {
            if(visibility){
                btn_jdpbd_progress_show.setVisibility(View.VISIBLE);
            }else{
                btn_jdpbd_progress_show.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            tv_jdpbd_title.setVisibility(View.GONE);
        } else {
            tv_jdpbd_title.setVisibility(View.VISIBLE);
            tv_jdpbd_title.setText(title);
        }
    }

    /**
     * 设置进度条进度
     */
    public void setProgressBar(int progress) {
        if (progress<0) {
            progress = 0;
        } else if(progress>100){
            progress = 100;
        }
        pb_jdpbd_progress_bar.setProgress(progress);
        if(btn_jdpbd_progress_show.getVisibility() == View.VISIBLE){
            setProgressShowText(progress+"%");
        }
    }

    /**
     * 设置进度展示文本框显示文本
     */
    public void setProgressShowText(String cancelStr) {
        if (!TextUtils.isEmpty(cancelStr)) {
            btn_jdpbd_progress_show.setText(cancelStr);
        }
    }


    /**
     * 设置标题字体颜色
     */
    public void setTitleColor(int color) {
        tv_jdpbd_title.setTextColor(color);
    }

    /**
     * 设置标题的大小
     **/
    public void setTitleSize(int size) {
        tv_jdpbd_title.setTextSize(size);
    }

    /**
     * 设置进度展示文本框字体颜色
     */
    public void setProgressShowTextColor(int color) {
        btn_jdpbd_progress_show.setTextColor(color);
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

    }

}
