package tech.yunjing.biconlife.libbaselib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.liblkclass.common.util.LKDialogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;


/**弹框逻辑处理类
 * Created by wsw on 2017/6/13 0013.
 */

public class PromptlyBuyUtil {

    private static PromptlyBuyUtil sInstance;
    private static OnCustomizationListenerListener customization;

    /**
     * 实例化对象
     *
     * @return
     */
    public static PromptlyBuyUtil getInstance() {
        if (sInstance == null) {
            sInstance = new PromptlyBuyUtil();
        }
        return sInstance;
    }

    /**
     *留言回复弹出输入框
     */
    public static void Messagereply(final Context context, final HashMap<String, Object> params, final Handler mHandler, final String fromNick) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_messag_ereply, null);
        TextView tv_message_send = (TextView) dialogView.findViewById(R.id.tv_message_send);
        final EditText et_message = (EditText) dialogView.findViewById(R.id.et_message);
        final Dialog dialog = LKDialogUtil.getDialogOutSide((Activity) context, dialogView, R.style.UserDetailsDialogStyle, 0, false);
        showSoftInputFromWindow(dialog,et_message);
        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        dialog.getWindow().setAttributes(p);
        et_message.setHint(fromNick);
        tv_message_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_message.getText().toString())){
                    LKToastUtil.showToastShort("留言不能为空");
                    return;
                }
                if (null!=customization){
                    customization.getCustomization(et_message.getText().toString());
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /**
     * 设置Edittext获取焦点并弹出软键盘
     *
     * @param editText
     */
    public static void showSoftInputFromWindow(Dialog dialog, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void setCustomizationListener(OnCustomizationListenerListener customization){
        PromptlyBuyUtil.customization = customization;
    }

    /**
     * 定制完成接口
     */
    public interface OnCustomizationListenerListener {
        void getCustomization(String pc);
    }
}
