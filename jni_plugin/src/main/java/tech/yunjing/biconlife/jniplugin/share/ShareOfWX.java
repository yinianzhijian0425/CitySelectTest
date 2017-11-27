package tech.yunjing.biconlife.jniplugin.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.net.URL;

import tech.yunjing.biconlife.jniplugin.util.WXShareUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;


public class ShareOfWX {
    private IWXAPI wxApi;
    private Context context;
    public static String appId;

    public ShareOfWX(Context context, String appId) {
        this.context = context;
        ShareOfWX.appId = appId;
        init();
    }

    private void init() {
        wxApi = WXAPIFactory.createWXAPI(context, appId);
        wxApi.registerApp(appId);

        if (!wxApi.isWXAppInstalled()) {
            LKToastUtil.showToastShort("你还未安装微信客户端");
            return;
        }
    }

    /**
     * 分享图片
     *
     * @param flag (0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void shareImg(int flag, Bitmap bmp, int imgWidth, int imgHeight) {
        if (bmp == null) {
            return;
        }
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, imgWidth, imgHeight, true);
        bmp.recycle();
        msg.thumbData = WXShareUtil.bmpToByteArray(thumbBmp, true); // 设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "WX_Share";
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }

    /**
     * 分享本地图片 -caiyan
     *
     * @param flag (0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void shareImgLocal(int flag, Bitmap bmp) {
        if (bmp == null) {
            return;
        }
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createBitmap(bmp);
        bmp.recycle();
        msg.thumbData = WXShareUtil.bmpToByteArray(thumbBmp, true); // 设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "WX_Share";
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }

    /**
     * 分享文本
     *
     * @param text 文本
     * @param flag (0:分享到微信好友，1：分享到微信朋友圈)
     */
    public void shareText(int flag, String text) {
        WXTextObject textObj = new WXTextObject();  // 初始化一个WXTextObject对象
        textObj.text = text;
        WXMediaMessage msg = new WXMediaMessage();    // 用WXTextObject对象初始化一个WXMediaMessage对象
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "WX_Share"; // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        wxApi.sendReq(req);
    }

    /**
     * 分享 网页(分享的时候使用网络图片)
     *
     * @param flag        (0:分享到微信好友，1：分享到微信朋友圈)
     * @param title       分享的标题
     * @param description 描述
     * @param webUrl      网页地址
     * @param imgUrl      图片地址
     */
    public void shareWebImage(final String title, final String description, final String webUrl, final String imgUrl, final int flag, final int imgWidth, final int imgHeight) {
        WXImageObject imgObj = new WXImageObject();
        imgObj.imagePath = imgUrl;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                    final Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, imgWidth, imgHeight, true);
                    bmp.recycle();
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = webUrl;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = title;
                    msg.description = description;
                    msg.thumbData = WXShareUtil.bmpToByteArray(thumbBmp, true);
                    msg.setThumbImage(thumbBmp);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    wxApi.sendReq(req);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
