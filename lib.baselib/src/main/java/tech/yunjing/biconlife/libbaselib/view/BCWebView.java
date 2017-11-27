package tech.yunjing.biconlife.libbaselib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

import static tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil.getScreenHeight;
import static tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil.getScreenWidth;

/**
 * 公共WebView展示控件，适配手机屏幕
 * Created by sun.li on 2017/8/3.
 * @author sun.li
 */

public class BCWebView extends WebView {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * WebView的加载回调方法对象
     */
    private LoadCallBack loadCallBack;

    /**
     * WebView的滑动回调方法对象
     */
    private SlidingMonitor slidingMonitor;

    public BCWebView(Context context) {
        super(context);
        mContext = context;
        initWebView();
    }

    public BCWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initWebView();
    }

    public BCWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initWebView();
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        try {
            //声明WebSettings子类
            WebSettings webSettings = this.getSettings();
//        //设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(false); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小false
            //其他细节操作
            //优先使用缓存缓存
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //不使用缓存
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            //设置可以访问文件
            webSettings.setAllowFileAccess(true);
            //支持通过JS打开新窗口
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            //支持自动加载图片
            webSettings.setLoadsImagesAutomatically(true);
            /**
             * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
             * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放;3、NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
             */
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            //设置编码格式
            webSettings.setDefaultTextEncodingName("utf-8");
            // 支持JavaScript
            webSettings.setJavaScriptEnabled(true);
            //设置适应Html5的一些方法，DOM储存API没有打开
            webSettings.setDomStorageEnabled(true);
            //缩放操作//支持缩放，默认为true。是下面那个的前提。
            webSettings.setSupportZoom(false);
            //设置内置的缩放控件。若为false，则该WebView不可缩放
            webSettings.setBuiltInZoomControls(true);
            //隐藏原生的缩放控件
            webSettings.setDisplayZoomControls(false);
            //视频支持// 排版适应屏幕
//            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            // 可任意比例缩放
            webSettings.setUseWideViewPort(false);
            //1.none，默认值，2.software，软件加速，3.hardware，硬件加速。
//            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            // 5.1以上默认禁止了https和http混用 这是开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            this.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        //表示按返回键
                        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
                            //后退
                            goBack();
                            //已处理
                            return true;
                        }
                    }
                    return false;
                }
            });
            this.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
//                    setLayerType(View.LAYER_TYPE_HARDWARE,null);
                    LKLogUtil.e("BCWebView" + "内容加载完成1");
                    try {
                        setUrlLoader();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        imgReset();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        videoReset();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (getLoadCallBack() != null) {
                        getLoadCallBack().onLoadingFinished();
                    }
                }

//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    LKLogUtil.e("url:" + url);
//                    return super.shouldOverrideUrlLoading(view, url);
//                }

//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                    LKLogUtil.e("WebResourceRequest:" + request.toString());
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.loadUrl(request.getUrl().toString());
//                    } else {
//                        view.loadUrl(request.toString());
//                    }
//                    return true;
//                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // 在APP内部打开链接，不要调用系统浏览器
                    LKLogUtil.e(url);
                    view.loadUrl(url);
                    return true;
                }

                /*ssl认证失败回调*/
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    /*千万不要调用super.onReceivedSslError()。这是因为WebViewClient的onReceivedSslError()函数中包含了一条handler.cancel()
                    （见源码，其含义是停止加载，所以如果调用了super.onReceivedSslError()，其结果就是第一次访问时无法加载，第二次以后可以加载（不知道为什么），
                    而且还可能发生libc的段错误：*/
//                    super.onReceivedSslError(view, handler, error);
                    LKLogUtil.e("onReceivedSslError: " + error.getPrimaryError());
                    if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                            || error.getPrimaryError() == SslError.SSL_EXPIRED
                            || error.getPrimaryError() == SslError.SSL_INVALID
                            || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                        handler.proceed();
                    } else {
                        handler.cancel();
                    }
                }

                /* webView加载失败回调*/

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    try {
                        LKLogUtil.e("WebRequest:" + request.toString());
                        LKLogUtil.e("ReceivedError:" + error.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }
            try {
                // 注入JS方法
                this.addJavascriptInterface(new LoaderHandler(), "handler");
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Js注入的接口类
     */
    class LoaderHandler {
        @JavascriptInterface
        public void show(String data) {
            LKLogUtil.e("BCWebView-LoaderHandler" + data);
            if (getLoadCallBack() != null) {
                getLoadCallBack().onLoadingFinished(data);
            }
        }
    }

    /**
     * 注入JS方法，回调handler接口中的方法
     */
    private void setUrlLoader() {
        // 通过内部类定义的方法获取html页面加载的内容，这个需要添加在webview加载完成后的回调中
//        this.loadUrl("javascript:window.handler.show(document.getElementsByTagName('html')[0].innerHTML);");
//        this.loadUrl("javascript:window.handler.show(document.body.innerHTML);");
        this.loadUrl("javascript:window.handler.show(document.documentElement.outerHTML);");
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        this.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "  img.style.width = '100%';  img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 对视频进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void videoReset() {
        this.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('video'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var video = objs[i];   " +
                "   video.style.width = '100%'; video.style.maxWidth = '100%'; video.style.height = 'auto';  " +
                "}" +
                "})()");
        this.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('iframe'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var iframe = objs[i];   " +
                "   iframe.style.width = '100%'; iframe.style.maxWidth = '100%'; iframe.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    /**
     * 获取WebView的加载回调方法对象
     */
    public LoadCallBack getLoadCallBack() {
        return loadCallBack;
    }

    /**
     * 设置WebView的加载回调方法对象
     */
    public void setLoadCallBack(LoadCallBack loadCallBack) {
        this.loadCallBack = loadCallBack;
    }

    /**
     * 获取WebView的滑动回调方法对象
     */
    public SlidingMonitor getSlidingMonitor() {
        return slidingMonitor;
    }

    /**
     * 设置WebView的滑动回调方法对象
     */
    public void setSlidingMonitor(SlidingMonitor slidingMonitor) {
        this.slidingMonitor = slidingMonitor;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (getSlidingMonitor() != null) {
            getSlidingMonitor().onSChanged(x, y, oldx, oldy);
        }
    }

    /**
     * WebView的加载回调方法
     */
    public interface LoadCallBack {
        /**
         * webView内容加载完毕回调
         */
        void onLoadingFinished();

        /**
         * webView-url链接加载完成回调，内容回调，抓取WebView展示内容源代码返回
         *
         * @param content 文本内容
         */
        void onLoadingFinished(String content);
    }

    /**
     * WebView的滑动回调方法
     */
    public interface SlidingMonitor {
        /**
         * WebView滑动接口
         *
         * @param x    x轴当前变化
         * @param y    y轴当前变化
         * @param oldx x轴旧的变化
         * @param oldy y轴旧的变化
         */
        void onSChanged(int x, int y, int oldx, int oldy);
    }
}
