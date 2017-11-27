package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 下载相关进度回调
 * Created by NPF on 2017/3/24 09:13.
 */
abstract class LKProgressCallback<T> implements Callback.ProgressCallback<T> {
    @Override
    public void onSuccess(T result) {
        onRequestSuccess(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if (isOnCallback) {
            onRequestFailed(0, ex.toString());
        } else {
            onRequestFailed(1, ex.toString());
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

    @Override
    public void onWaiting() {
        onRequestWaiting();
    }

    @Override
    public void onStarted() {
        onRequestStarted();
    }

    /**
     *
     * @param result
     */
    abstract void onRequestSuccess(T result);

    /**
     *
     * @param responseCode
     * @param responseMsg
     */
    abstract void onRequestFailed(int responseCode, String responseMsg);

    /**
     *
     * @param cex
     */
    abstract void onRequestCancel(CancelledException cex);

    void onRequestStarted() {
        LKLogUtil.e("基类中打印===下载开始");
    }

    void onRequestWaiting() {
        LKLogUtil.e("基类中打印===下载等待");
    }

    void onRequestFinished() {
        LKLogUtil.e("基类中打印===下载完成====数据请求结束");
    }


}
