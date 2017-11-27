package tech.yunjing.biconlife.libbaselib.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tech.yunjing.biconlife.libbaselib.R;

/**
 * @Function 自定义显示选项控件(上面图片, 下面文字)
 * @Author liJH
 * @Time 2016/9/21
 */
public class LKChooseView extends LinearLayout {

    private ImageView iv_image;
    private TextView tv_name;

    public LKChooseView(Context context) {
        this(context, null);
    }

    public LKChooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public LKChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_invite_share, null);
        tv_name= (TextView) view.findViewById(R.id.tv_name);
        iv_image= (ImageView) view.findViewById(R.id.iv_image);
        initView(view);
    }

    private void initView(View view) {
        this.addView(view);
    }

    /**
     * 设置图片
     *
     * @param imgId
     */
    public void setImgBackground(int imgId) {
        iv_image.setBackgroundResource(imgId);
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setTvContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            tv_name.setText(content);
            tv_name.setTextSize(14f);
        }
    }
}
