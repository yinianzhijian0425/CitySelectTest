package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import tech.yunjing.biconlife.liblkclass.http.LKFinalList;

/**
 * 网络数据请求基类(主要进行数据传送处理)
 *
 * @author nanPengFei
 */
abstract class LKBaseRequest {
    /**
     * 默认标识
     */
    static final int LK_TAG = -1;

    /**
     * 未创建Message下发送通知
     *
     * @param handler
     * @param state
     * @param tag
     * @param objects
     */
    static void sendHandler(Handler handler, int state, int tag, Object... objects) {
        Message message = handler.obtainMessage();
        message.what = state;
        //主要用于标识请求
        message.arg1 = tag;
        switch (state) {
            case LKFinalList.REQUEST_SUCCESS:
                if (objects.length > 1) {
                    Object object = objects[1];
                    if (null == object) {
                        message.what = LKFinalList.REQUEST_FAILURE;
                        message.obj = objects[0];
                    } else {
                        message.obj = object;
                    }
                } else if (objects.length == 1) {
                    message.what = LKFinalList.REQUEST_FAILURE;
                    message.obj = objects[0];
                }
                break;
            case LKFinalList.FILE_DOWNLOADING:
            case LKFinalList.REQUEST_FAILURE:
            case LKFinalList.REQUEST_CANCEL:
                message.obj = objects[0];
                break;
            default:
                break;
        }
        handler.sendMessage(message);
    }

    /**
     * 创建Message下发送通知
     *
     * @param handler
     * @param message
     * @param result
     * @param state
     */
    static void sendHandler(Handler handler, Message message, String result, int state) {
        switch (state) {
            case LKFinalList.REQUEST_SUCCESS:
                int code = 200;
                try {
                    JSONObject mJSONObject = new JSONObject(result);
                    code = mJSONObject.optInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                message.arg2 = code;
                break;
            case LKFinalList.REQUEST_FAILURE:
                message.what = LKFinalList.REQUEST_FAILURE;
                message.obj = result;
                break;
            case LKFinalList.REQUEST_CANCEL:
                message.what = LKFinalList.REQUEST_CANCEL;
                message.obj = result;
                break;
            default:
                break;
        }
        handler.sendMessage(message);
    }

}