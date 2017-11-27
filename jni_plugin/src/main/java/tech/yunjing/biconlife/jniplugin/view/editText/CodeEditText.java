package tech.yunjing.biconlife.jniplugin.view.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 监听 全选，复制，剪切,黏贴
 * Created by Chen.qi on 2017/9/15 0015.
 */

@SuppressLint("AppCompatCustomView")
public class CodeEditText extends EditText {

    private Handler mHandler;

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public CodeEditText(Context context) {
        super(context);
    }

    public CodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
//        id:16908319  --- 全选

//        id:16908328  --- 选择

//        id:16908320  --- 剪贴

//        id:16908321  --- 复制

//        id:16908322  --- 粘贴

//        id:16908324  --- 输入法

        if (id == 16908322 && mHandler != null) {
            mHandler.sendEmptyMessage(10);
        }
        return super.onTextContextMenuItem(id);
    }


}
