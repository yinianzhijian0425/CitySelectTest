package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.TreeMap;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.http.LKFinalList;

/**
 * Post方式请求并解析数据
 *
 * @author nanPengFei
 */
public class LKPostRequest extends LKBaseRequest {


    public static Callback.Cancelable getData(Handler handler, String url, Class<?> cls, boolean showLoader) {
        return getData(handler, url, cls, showLoader, LK_TAG);
    }

    /**
     * post方式请求数据(无参数)
     *
     * @param handler Handler
     * @param url     连接
     * @param cls     实体字节码
     * @param tag     标识
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, final Class<?> cls, boolean showLoader, final int tag) {
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }

        return LKRequestUtils.post(url, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, url, LKJsonUtil.parseJsonToBean(result, cls));
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e(responseCode + "==失败==" + responseMsg);
                sendHandler(handler, LKFinalList.REQUEST_FAILURE, tag, url);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, LKFinalList.REQUEST_CANCEL, tag, url);
            }
        });
    }


    public static Callback.Cancelable getData(final Handler handler, final String url, HashMap<String, Object> params, final Class<?> cls, boolean showLoader) {
        return getData(handler, url, params, cls, showLoader, LK_TAG);
    }

    /**
     * post方式请求数据(带参数)
     *
     * @param handler Handler
     * @param url     连接
     * @param params  参数
     * @param cls     实体字节码
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, HashMap<String, Object> params, final Class<?> cls, boolean showLoader, final int tag) {
        LKLogUtil.e("url:" + url.toString());
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        return LKRequestUtils.post(url, params, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, url, LKJsonUtil.parseJsonToBean(result, cls));
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e(responseCode + "==失败===" + responseMsg);
                sendHandler(handler, LKFinalList.REQUEST_FAILURE, tag, url);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, LKFinalList.REQUEST_CANCEL, tag, url);
            }
        });
    }

    /**
     *
     * @param handler
     * @param url
     * @param params TreeMap
     * @param cls
     * @param showLoader
     * @return
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, TreeMap<String, Object> params, final Class<?> cls, boolean showLoader) {
        return getData(handler, url, params, cls, showLoader, LK_TAG);
    }

    /**
     * post方式请求数据(带参数)
     *
     * @param handler Handler
     * @param url     连接
     * @param params  参数 TreeMap
     * @param cls     实体字节码
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, TreeMap<String, Object> params, final Class<?> cls, boolean showLoader, final int tag) {
        LKLogUtil.e("url:" + url.toString());
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        return LKRequestUtils.post(url, params, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, url, LKJsonUtil.parseJsonToBean(result, cls));
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e(responseCode + "==失败===" + responseMsg);
                sendHandler(handler, LKFinalList.REQUEST_FAILURE, tag, url);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, LKFinalList.REQUEST_CANCEL, tag, url);
            }
        });
    }


    /**
     * 不需要解析请求成功后json数据的无参方式
     *
     * @param handler
     * @param url
     * @param message
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, final Message message, boolean showLoader) {
        if (message.what < 0) {
            LKLogUtil.e("message.what 必须>0");
            return null;
        }


        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        return LKRequestUtils.post(url, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, message, result, LKFinalList.REQUEST_SUCCESS);
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                sendHandler(handler, message, url, LKFinalList.REQUEST_FAILURE);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, message, url, LKFinalList.REQUEST_CANCEL);
            }
        });
    }

    /**
     * 不需要解析请求成功后json数据的带参方式
     *
     * @param handler
     * @param url
     * @param params
     * @param message
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, HashMap<String, Object> params, final Message message, boolean showLoader) {
        if (message.what < 0) {
            LKLogUtil.e("message.what 必须>0");
            return null;
        }


        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        return LKRequestUtils.post(url, params, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, message, result, LKFinalList.REQUEST_SUCCESS);
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                sendHandler(handler, message, url, LKFinalList.REQUEST_FAILURE);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, message, url, LKFinalList.REQUEST_CANCEL);
            }
        });
    }
}