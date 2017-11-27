package tech.yunjing.biconlife.jniplugin.util.download;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import tech.yunjing.biconlife.jniplugin.bean.downloadapk.ApkDownloadInfoObj;
import tech.yunjing.biconlife.jniplugin.view.JniCancelAndConfirmDialog;
import tech.yunjing.biconlife.jniplugin.view.JniDownloadProgressBarDialog;
import tech.yunjing.biconlife.liblkclass.common.util.LKDialogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPermissionUtil;

/**
 * 下载应用apk包的Dialog
 * Created by sun.li on 2017/10/18.
 *
 * @author sun.li
 */

public class DownloadApkUtil{

    private static final String TAG = "UpdateChecker";
    private Context mContext;
    /**
     * 版本对比地址
     */
    private String mCheckUrl;
    private ApkDownloadInfoObj mAppVersion;

    /**
     * 下载apk的对话框
     */
    private JniDownloadProgressBarDialog jniDownloadProgressBarDialog;

    private File apkFile;

    private OnButtonClick onButtonClick;

    /** 下载提示框Dialog对象*/
    private JniCancelAndConfirmDialog jniCancelAndConfirmDialog;

    /**
     * 版本更新标志，有更新"Yes"
     */
    public static final String UpdateMarkYes = "Yes";

    public void setCheckUrl(String url) {
        mCheckUrl = url;
    }

    public DownloadApkUtil(Context context, ApkDownloadInfoObj mAppVersion) {
        this.mAppVersion = mAppVersion;
        this.mContext = context;
        // instantiate it within the onCreate method
        initDownloagDialog();
    }

    public void initDownloagDialog(){
        if(jniDownloadProgressBarDialog == null){
            jniDownloadProgressBarDialog = new JniDownloadProgressBarDialog(mContext);
        }
        jniDownloadProgressBarDialog.setTitle("正在下载");
        jniDownloadProgressBarDialog.setCancelable(false);
        jniDownloadProgressBarDialog.setEventInterface(new JniDownloadProgressBarDialog.EventInterface() {
            @Override
            public void cancelOnClick() {

            }
        });
    }

    /** 显示更新提示的Dialog*/
    public void showUpdateDialog() {
        if(jniCancelAndConfirmDialog == null){
            jniCancelAndConfirmDialog = new JniCancelAndConfirmDialog(mContext);
        }
        jniCancelAndConfirmDialog.setTitle("有新版本");
        jniCancelAndConfirmDialog.setCancelable(false);
        jniCancelAndConfirmDialog.setContent(mAppVersion.getInfo());
        jniCancelAndConfirmDialog.setConfirmBtnText("下载");
        jniCancelAndConfirmDialog.setCancelBtnText("忽略");
        jniCancelAndConfirmDialog.setEventInterface(new JniCancelAndConfirmDialog.EventInterface() {
            @Override
            public void cancelOnClick() {
                if (onButtonClick != null) {
                    onButtonClick.negativeClick();
                }
                if(jniCancelAndConfirmDialog!=null){
                    jniCancelAndConfirmDialog.dismiss();
                }
            }

            @Override
            public void confirmOnClick() {
                if (onButtonClick != null) {
                    onButtonClick.positiveClick();
                }
                downLoadApk();
                if(jniCancelAndConfirmDialog!=null){
                    jniCancelAndConfirmDialog.dismiss();
                }
            }
        });
        jniCancelAndConfirmDialog.show();
    }

    public void downLoadApk() {
        if (mAppVersion == null || TextUtils.isEmpty(mAppVersion.getUrl())) {
            if (onButtonClick != null) {
                onButtonClick.negativeClick();
            }
            return;
        }
        jniDownloadProgressBarDialog.setProgressBar(0);
        jniDownloadProgressBarDialog.show();
        String apkUrl = mAppVersion.getUrl();
        String dir = Environment.getExternalStorageDirectory().getPath()+"/tech.yunjing.biconlife/files/apk";
        LKLogUtil.e("dir:" + dir);
        DownloadFileUtil.createOrExistsDir(dir);
        File folder = new File(dir);
        if (folder.exists() && folder.isDirectory()) {
            //do nothing
        } else {
            folder.mkdirs();
        }
        String filename = apkUrl.substring(apkUrl.lastIndexOf("/"), apkUrl.length());
        String destinationFilePath = dir + filename;
        LKLogUtil.e("destinationFilePath:" + destinationFilePath);
        File file = new File(dir,filename);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        apkFile = new File(destinationFilePath);

        Intent intent = new Intent(mContext, DownloadApkService.class);
        intent.putExtra("url", apkUrl);
        intent.putExtra("dest", destinationFilePath);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        mContext.startService(intent);
    }

    private class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadApkService.UPDATE_PROGRESS) {
                int progress = 0;
                try {
                    progress = resultData.getInt("progress");
                } catch (Exception e) {
                    e.printStackTrace();
                    progress = -1;
                }
                if(progress <= DownloadApkService.HttpURLConnectionDownloadCompleteCode && progress >=0){
                    jniDownloadProgressBarDialog.setProgressBar(progress);
                }
                if (progress == DownloadApkService.HttpURLConnectionDownloadCompleteCode) {
                    /* 更新下载链接正常连通*/
                    jniDownloadProgressBarDialog.dismiss();
                    LKLogUtil.e("apkFile:" + apkFile.toString());
                    //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
                    String[] command = {"chmod", "777", apkFile.toString()};
                    try {
                        ProcessBuilder builder = new ProcessBuilder(command);
                        builder.start();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } catch (Exception e) {
                        if (onButtonClick != null) {
                            onButtonClick.negativeClick();
                        }
                    }
                }else if(progress == DownloadApkService.HttpURLConnectionErrorCode){
                    /* 进度异常时回调*/
                    jniDownloadProgressBarDialog.dismiss();
                    if(jniCancelAndConfirmDialog == null){
                        jniCancelAndConfirmDialog = new JniCancelAndConfirmDialog(mContext);
                    }
                    jniCancelAndConfirmDialog.setTitle("版本更新异常");
                    jniCancelAndConfirmDialog.setCancelable(false);
                    jniCancelAndConfirmDialog.setContent(mAppVersion.getInfo());
                    jniCancelAndConfirmDialog.setConfirmBtnText("重新下载");
                    jniCancelAndConfirmDialog.setCancelBtnText("取消");
                    jniCancelAndConfirmDialog.setEventInterface(new JniCancelAndConfirmDialog.EventInterface() {
                        @Override
                        public void cancelOnClick() {
                            if (onButtonClick != null) {
                                onButtonClick.negativeClick();
                            }
                            if(jniCancelAndConfirmDialog!=null){
                                jniCancelAndConfirmDialog.dismiss();
                            }
                        }

                        @Override
                        public void confirmOnClick() {
                            if (onButtonClick != null) {
                                onButtonClick.positiveClick();
                            }
                            downLoadApk();
                            if(jniCancelAndConfirmDialog!=null){
                                jniCancelAndConfirmDialog.dismiss();
                            }
                        }
                    });
                    jniCancelAndConfirmDialog.show();
                }
            }
        }
    }

    public OnButtonClick getOnButtonClick() { return onButtonClick; }

    public void setOnButtonClick(OnButtonClick onButtonClick) { this.onButtonClick = onButtonClick; }

    /**
     * Button点击回调
     */
    public interface OnButtonClick {
        /**
         * 确认按钮点击
         */
        void positiveClick();

        /**
         * 取消按钮点击
         */
        void negativeClick();
    }

}
