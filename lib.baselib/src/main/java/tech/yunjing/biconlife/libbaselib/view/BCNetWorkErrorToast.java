package tech.yunjing.biconlife.libbaselib.view;

/**
 * 网络异常时弹出的Toast
 * Created by sun.li on 2017/3/17 0017.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * 带滚动监听的scrollview
 */
public class BCNetWorkErrorToast extends Toast {


    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public BCNetWorkErrorToast(Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}