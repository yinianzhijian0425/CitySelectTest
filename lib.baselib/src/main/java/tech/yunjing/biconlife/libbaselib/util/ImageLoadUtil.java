package tech.yunjing.biconlife.libbaselib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络图片加载工具类
 * Created by sun.li on 2017/8/9.
 */

public class ImageLoadUtil {
    private static ImageView iv;

    private static Handler handler=null;

    private static Bitmap bitmap;

    // 构建Runnable对象，在runnable中更新界面
    private static Runnable runnableUi=new  Runnable(){
        @Override
        public void run() {
            //设置图片
            if(iv!=null && bitmap!=null){
                iv.setImageBitmap(bitmap);
            }
        }

    };

    /** 给当前控件加载网络图片*/
    public static void loadNetworkImage(ImageView imageView,final String url){
        //创建属于主线程的handler
        handler=new Handler();
        iv = imageView;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = getHttpBitmap(url);
                handler.post(runnableUi);
            }
        });
        thread.start();
    }

    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(true);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;

    }
}
