package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.http.LKFinalList;
import tech.yunjing.biconlife.liblkclass.http.xhttp.RequestParams;

/**
 * Get方式请求并解析数据
 *
 * @author nanPengFei
 */
public class LKGetRequest extends LKBaseRequest {
    /**
     * get方式请求数据
     *
     * @param handler Handler
     * @param url     地址
     * @param cls     实体字节码
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, final Class<?> cls, boolean showLoader) {
        return getData(handler, url, cls, showLoader, LK_TAG);
    }

    /**
     * get方式请求数据
     *
     * @param handler Handler
     * @param url     地址
     * @param cls     实体字节码
     */
    public static Callback.Cancelable getData(final Handler handler, final String url, final Class<?> cls, boolean showLoader, final int tag) {
        LKLogUtil.e("请求-----url:" + url.toString());
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        RequestParams requestParams = new RequestParams(url);
        return LKRequestUtils.get(requestParams, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, url, LKJsonUtil.parseJsonToBean(result, cls));
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e("result失败---==" + responseCode + "-----失败信息----" + responseMsg);
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
        RequestParams requestParams = new RequestParams(url);
        return LKRequestUtils.get(requestParams, new LKRequestCallBack<String>() {
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
     * 版本更新专用
     *
     * @param handler
     * @param url
     * @param message
     */
    public static Callback.Cancelable getDataVersion(final Handler handler, final String url, final Message message, boolean showLoader) {
        if (message.what < 0) {
            LKLogUtil.e("message.what 必须>0");
            return null;
        }

        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        RequestParams requestParams = new RequestParams(url);
        return LKRequestUtils.get(requestParams, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result版本更新数据==" + result);
                message.obj = result;
                message.what = 5;
                handler.sendMessage(message);
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                message.what = 6;
                handler.sendMessage(message);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
            }
        });
    }


    /**
     * get方式请求数据
     *
     * @param handler
     * @param url
     * @param cls
     * @param showLoader
     * @param timeOut    超时时间
     * @return
     */
    public static Callback.Cancelable getDataForTime(Handler handler, String url, Class<?> cls, boolean showLoader, int timeOut) {
        return getDataForTime(handler, url, cls, showLoader, timeOut, LK_TAG);
    }


    /**
     * get方式请求数据
     *
     * @param handler
     * @param url
     * @param cls
     * @param showLoader
     * @param timeOut    超时时间
     * @return
     */
    public static Callback.Cancelable getDataForTime(final Handler handler, final String url, final Class<?> cls, boolean showLoader, int timeOut, final int tag) {
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        RequestParams requestParams = new RequestParams(url);
        requestParams.setConnectTimeout(timeOut);
        return LKRequestUtils.get(url, requestParams, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("result==" + result);
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, url, LKJsonUtil.parseJsonToBean(result, cls));
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                sendHandler(handler, LKFinalList.REQUEST_FAILURE, tag, url);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, LKFinalList.REQUEST_CANCEL, tag, url);
            }
        });
    }
}