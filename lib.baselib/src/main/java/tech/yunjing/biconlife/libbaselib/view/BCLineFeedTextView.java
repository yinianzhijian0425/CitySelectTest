package tech.yunjing.biconlife.libbaselib.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;


/**
 * 自动换行textView控件
 * Created by nanPengFei on 2016/12/22 19:38.
 */
public class BCLineFeedTextView extends LinearLayout {

    private LayoutInflater layoutInflater;
    /** 上下文*/
    private Context mContext;

    private LinearLayout ll_linefeedRoot;

    private View mView;

    public BCLineFeedTextView(Context context) {
        this(context, null);
    }

    public BCLineFeedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BCLineFeedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(mContext);
        mView = layoutInflater.inflate(R.layout.layout_linefeed_ltextview, null);
        ll_linefeedRoot = (LinearLayout) mView.findViewById(R.id.ll_linefeedRoot);
        LK.view().inject(this, mView);
        this.addView(mView);
    }

    /**
     * 设置数据
     *
     * @param tags
     */
    public void initData(ArrayList<String> tags) {
        int zy = LKCommonUtil.dip2px(10);
        int sx = LKCommonUtil.dip2px(5);
        int screenWidth = LKCommonUtil.getScreenWidth();
        ll_linefeedRoot.setOrientation(LinearLayout.VERTICAL);
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ArrayList<TextView> textViews = new ArrayList<>();
        int width;
        for (int i = 0; i < tags.size(); i++) {
            width = 0;
            for (int j = 0; j < textViews.size(); j++) {
                TextView _textView = textViews.get(j);
                _textView.measure(0, 0);
                width += _textView.getMeasuredWidth();
            }
            String text = tags.get(i);
            TextView textView = new TextView(mContext);
            textView.setPadding(zy, sx, zy, sx);
            textView.setTextColor(Color.RED);
            textView.setText(text);
            textView.measure(0, 0);
            if ((width + textView.getMeasuredWidth()) > screenWidth) {
                ll_linefeedRoot.addView(linearLayout);
                textViews.clear();
                textViews.add(textView);
                linearLayout = new LinearLayout(mContext);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(textView);
            } else {
                textViews.add(textView);
                linearLayout.addView(textView);
            }
            if (i == tags.size() - 1) {
                ll_linefeedRoot.addView(linearLayout);
            }
        }
    }

}
