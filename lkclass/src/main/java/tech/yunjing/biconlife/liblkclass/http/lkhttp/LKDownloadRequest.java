package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.common.task.PriorityExecutor;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.http.LKFinalList;
import tech.yunjing.biconlife.liblkclass.http.xhttp.RequestParams;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 下载操作请求
 * Created by nanPengFei on 2016/12/2 16:06.
 */
public class LKDownloadRequest extends LKBaseRequest {
    /**
     * 图片下载保存本地
     *
     * @param handler
     * @param fileUrl
     * @param savaPath
     * @param showLoader
     * @param tag        标识，不能为-1
     * @return
     */
    public static Callback.Cancelable downloadImg(final Handler handler, final String fileUrl, String savaPath, String fileName, boolean showLoader, final int tag) {
        if (tag == LK_TAG) {
            LKLogUtil.e("tag不能为-1");
            return null;
        }
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        File tempFile = new File(savaPath, fileName);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        RequestParams params = new RequestParams(fileUrl);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(savaPath + "/" + fileName);
        params.setConnectTimeout(1800000);
        return LKRequestUtils.get(params, new LKRequestCallBack<File>() {
            @Override
            public void onRequestSuccess(File result) {
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, fileUrl, result);
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                sendHandler(handler, LKFinalList.REQUEST_FAILURE, tag, fileUrl);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
                sendHandler(handler, LKFinalList.REQUEST_CANCEL, tag, fileUrl);
            }
        });
    }

    /**
     * 文件下载
     *
     * @param handler
     * @param fileUrl
     * @param savaPath
     * @return
     */
    public static Callback.Cancelable downloadFile(final Handler handler, final String fileUrl, String savaPath, String fileName, boolean showLoader, final int tag) {
        if (tag == LK_TAG) {
            LKLogUtil.e("tag不能为-1");
            return null;
        }
        if (showLoader) {
            handler.sendEmptyMessage(LKFinalList.REQUEST_START);
        }
        File tempFile = new File(savaPath, fileName);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        RequestParams params = new RequestParams(fileUrl);
        //设置是否在下载是自动断点续传
        params.setAutoResume(true);
        //设置是否根据头信息自动命名文件
        params.setAutoRename(true);
        params.setSaveFilePath(savaPath + "/" + fileName);
        params.setConnectTimeout(1800000);
        //自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setExecutor(new PriorityExecutor(2, true));
        //是否可以被立即停止.
        params.setCancelFast(true);
        return LK.http().get(params, new LKProgressCallback<File>() {
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
//                    sendHandler(handler, LKFinalList.FILE_DOWNLOADING, tag, total + ":" + current);
                    sendHandler(handler, LKFinalList.FILE_DOWNLOADING, tag, current, total);
                }
            }

            @Override
            void onRequestSuccess(File result) {
                sendHandler(handler, LKFinalList.REQUEST_SUCCESS, tag, fileUrl, result);
            }

            @Override
            void onRequestFailed(int responseCode, String responseMsg) {
                sendHandler(handler, LKFinalList.REQUEST_FAILURE, tag, fileUrl);
            }

            @Override
            void onRequestCancel(CancelledException cex) {
                sendHandler(handler, LKFinalList.REQUEST_CANCEL, tag, fileUrl);
            }
        });

    }

    /**
     * 暂停下载
     *
     * @param cancelable
     */
    public static void stopDownload(Callback.Cancelable cancelable) {
        cancelable.cancel();
    }

    static void sendHandler(Handler handler, int state, int tag, Object... objects) {
        Message message = handler.obtainMessage();
        message.what = state;
        //主要用于标识请求
        message.arg1 = tag;
        Bundle bundle = new Bundle();

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
                bundle.putInt("state", LKFinalList.REQUEST_SUCCESS);
                break;
            case LKFinalList.FILE_DOWNLOADING:
                bundle.putInt("state", LKFinalList.FILE_DOWNLOADING);
                bundle.putLong("current", (long) objects[0]);
                bundle.putLong("total", (long) objects[1]);
                break;
            case LKFinalList.REQUEST_FAILURE:
                bundle.putInt("state", LKFinalList.REQUEST_FAILURE);
            case LKFinalList.REQUEST_CANCEL:
                bundle.putInt("state", LKFinalList.REQUEST_CANCEL);
                message.obj = objects[0];
                break;
            default:
                break;
        }
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
