package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.global.ex.HttpException;

/**
 * 数据请求回调(最好将泛型填写上，String，则返回请求的json数据，具体模型，则返回解析后对象)(未结束)
 * Created by nanPengFei on 2016/9/1 10:22.
 */
 public abstract class LKRequestCallBack<T> implements Callback.CommonCallback<T> {
    @Override
    public void onSuccess(T result) {
        onRequestSuccess(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        // 网络错误
        if (ex instanceof HttpException) {
            HttpException httpEx = (HttpException) ex;
            int responseCode = httpEx.getCode();
            String responseMsg = httpEx.getMessage();
            onRequestFailed(responseCode, responseMsg);
        } else { // 其他错误
            onRequestFailed(404, "网络异常");
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        onRequestCancel(cex);
    }

    @Override
    public void onFinished() {
        onRequestFinished();
    }

    /**
     *
     * @param result
     */
    public abstract void onRequestSuccess(T result);

    /**
     *
     * @param responseCode
     * @param responseMsg
     */
    public abstract void onRequestFailed(int responseCode, String responseMsg);

    /**
     *
     * @param cex
     */
    public abstract void onRequestCancel(CancelledException cex);

    /**
     *
     */
    public void onRequestFinished() {
//        LKLogUtils.e("基类中打印=======数据请求结束");
    }
}
