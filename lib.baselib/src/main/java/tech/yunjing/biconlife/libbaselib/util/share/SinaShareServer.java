package tech.yunjing.biconlife.libbaselib.util.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.base.BaseApplication;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 新浪分享逻辑处理类
 * Created by sun.li on 2017/8/15.
 */

public class SinaShareServer implements WbShareCallback {

    /**
     * 使用单例
     */
    private static SinaShareServer sInstance;
    /**
     * 调用者
     */
    private static Activity mActivity;
    /**
     * 微博分享相关
     */
    private static WbShareHandler shareHandler;
    /**
     * 微博授权相关
     */
    private static SsoHandler mSsoHandler;
    /**
     * 回调接口
     */
    private static BCShareInter mShareCallback;

    /**
     * 实例化对象
     *
     * @return
     */
    public static SinaShareServer getInstance(Activity activity, BCShareInter shareCallback) {
        if (sInstance == null) {
            try {
                sInstance = new SinaShareServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mActivity = activity;
        try {
            if (mActivity == null) {
                mActivity = (Activity) BaseApplication.getContext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mShareCallback = shareCallback;
        initSinaShare();
        return sInstance;
    }

    /**
     * 初始化新浪微博
     */
    private static void initSinaShare() {
        //微博分享相关
        try {
            if (shareHandler == null) {
                if (mActivity == null) {
                    mActivity = (Activity) BaseApplication.getContext();
                }
                //微博，初始化
                WbSdk.install(mActivity, new AuthInfo(mActivity, SinaConstants.APP_KEY, SinaConstants.REDIRECT_URL, SinaConstants.SCOPE));
                shareHandler = new WbShareHandler(mActivity);
                shareHandler.registerApp();
                //微博授权（没作用，可以调起微博客户端）
//                mSsoHandler = new SsoHandler(mActivity);
//                mSsoHandler.authorize(new WbAuthListener() {
//                    @Override
//                    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
//                        LKLogUtil.e("授权成功");
//                    }
//
//                    @Override
//                    public void cancel() {
//                        LKLogUtil.e("授权取消");
//                    }
//
//                    @Override
//                    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
//                        LKLogUtil.e("授权失败");
//                    }
//                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //新浪微博分享
    public void shareWeiBo(String url) {
        try {
            if (!TextUtils.isEmpty(url)) {
                LKLogUtil.e("SinaShare" + "Url=" + url);
                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                //文本方式
                //        weiboMessage.textObject = getTextObj(url);
                //图片方式
                weiboMessage.imageObject = getImageObj(url);
                //多媒体方式
                //        weiboMessage.mediaObject = getWebpageObj(url);
                if (shareHandler != null) {
                    shareHandler.shareMessage(weiboMessage, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(String url) {
        ImageObject imageObject = new ImageObject();
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(BaseApplication.getAppResources(), R.mipmap.icon_share_sina);
            imageObject.setImageObject(bitmap);
            imageObject.actionUrl = url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageObject;
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String url) {
        TextObject textObject = new TextObject();
        try {
            textObject.title = "测试分享标题";
            textObject.text = "测试分享正文：与你相约，是一种清浅的禅意，而我，便在红尘最深处的禅意里，等你--题辞.微尘陌上";
            textObject.actionUrl = url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(String url) {
        WebpageObject mediaObject = new WebpageObject();
        try {
            mediaObject.identify = Utility.generateGUID();
            mediaObject.title = "测试title";
            mediaObject.description = "测试描述";
            Bitmap bitmap = BitmapFactory.decodeResource(BaseApplication.getAppResources(), R.mipmap.icon_share_sina);
            // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
            mediaObject.setThumbImage(bitmap);
            mediaObject.actionUrl = url;
            mediaObject.defaultText = "Webpage 默认文案";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaObject;
    }

    //微博回调
    protected void onNewIntent(Intent intent) {
        shareHandler.doResultIntent(intent, this);
    }

    //新浪微博分享回调接口
    @Override
    public void onWbShareSuccess() {
        if (mShareCallback != null) {
            mShareCallback.onShareSuccess("分享成功");
        }
    }

    @Override
    public void onWbShareCancel() {
        if (mShareCallback != null) {
            mShareCallback.onShareCancel();
        }
    }

    @Override
    public void onWbShareFail() {
        if (mShareCallback != null) {
            mShareCallback.onShareError("分享失败");
        }
    }
}
