package tech.yunjing.biconlife.jniplugin.util.download;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * Created by sun.li on 2017/10/18.
 *
 * @author sun.li
 */

public class DownloadApkService extends IntentService{

    /** 每次读取字节*/
    private static final int FILE_SIZE = 4*1024;

    /** 更新状态回调key*/
    public static final int UPDATE_PROGRESS = 8344;

    private long downloadManagerID = -1;

    /** 服务当前状态*/
    private boolean serviceStart = true;

    /** HttpURL链接异常Code*/
    public static final int HttpURLConnectionErrorCode = -1;

    /** HttpURL连接下载成功100%Code*/
    public static final int HttpURLConnectionDownloadCompleteCode = 100;

    /** 下载完成状态*/
    private boolean downloadCompletionStatus = false;

    public DownloadApkService() {
        super("DownloadService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = "";
        try {
            urlToDownload = intent.getStringExtra("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileDestination = "";
        try {
            fileDestination = intent.getStringExtra("dest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultReceiver receiver = null;
        try {
            receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!TextUtils.isEmpty(urlToDownload) && !TextUtils.isEmpty(fileDestination) && receiver!=null){
            downloadHttpURL(urlToDownload,fileDestination,receiver);
        }
    }


    /** HttpURL方式下载apk*/
    private void downloadHttpURL(String urlToDownload,String fileDestination,ResultReceiver receiver){
        if(!serviceStart){
            stopSelf();//停止服务
            return;
        }
        HttpURLConnection connection = null;
        InputStream input = null;
        BufferedInputStream bis = null;
        FileOutputStream fileOutputStream = null;
        try {
            LKLogUtil.e("urlToDownload:"+urlToDownload);
            URL url = new URL(urlToDownload);

            connection = (HttpURLConnection) url
                    .openConnection();
            //连接超时时间
            connection.setConnectTimeout(5 * 1000);
            //读操作超时时间
            connection.setReadTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.setRequestMethod("GET");
            //连接
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();
            LKLogUtil.e("fileLength:"+fileLength);
            // download the file
            if(fileLength>0){
                input = connection.getInputStream();
                bis = new BufferedInputStream(input);

                fileOutputStream = new FileOutputStream(fileDestination);
                byte[] bytes = new byte[FILE_SIZE];
                long total = 0;
                int count;
                while ((count = bis.read(bytes))!= -1) {
                    //写入
                    fileOutputStream.write(bytes, 0, count);

                    total += count;
                    LKLogUtil.e("total:"+total);
                    if(total >= fileLength){
                        downloadCompletionStatus = true;
                    }
                    // publishing the progress....
                    Bundle resultData = new Bundle();
                    resultData.putInt("progress" ,(int) (total * 100 / fileLength));
                    receiver.send(UPDATE_PROGRESS, resultData);
                }

                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,HttpURLConnectionDownloadCompleteCode);
                receiver.send(UPDATE_PROGRESS, resultData);
            }else{
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,HttpURLConnectionErrorCode);
                receiver.send(UPDATE_PROGRESS, resultData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LKLogUtil.e("downloadCompletionStatus:" + downloadCompletionStatus);
            if(!downloadCompletionStatus){
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,HttpURLConnectionErrorCode);
                receiver.send(UPDATE_PROGRESS, resultData);
            }
            try {
                if(input!=null){
                    input.close();
                }
                if(bis!=null) {
                    bis.close();
                }
                //记得关闭输入流
                if(fileOutputStream!=null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(connection!=null){
                connection.disconnect();
            }
            serviceStart = false;
            stopSelf();//停止服务
        }
    }

}
