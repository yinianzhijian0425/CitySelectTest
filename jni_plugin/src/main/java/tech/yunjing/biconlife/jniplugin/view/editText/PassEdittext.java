package tech.yunjing.biconlife.jniplugin.view.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 密码EditText
 * Created by Administrator on 2017/6/14.
 */

@SuppressLint("AppCompatCustomView")
public class PassEdittext extends EditText {
    private Handler mHander;

    public void setmHander(Handler mHander) {
        this.mHander = mHander;
    }

    public PassEdittext(Context context) {
        this(context, null);
    }

    public PassEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mHander.sendEmptyMessage(12);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
    }


}
