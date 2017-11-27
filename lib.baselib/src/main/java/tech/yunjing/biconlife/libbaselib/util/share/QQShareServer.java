package tech.yunjing.biconlife.libbaselib.util.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import tech.yunjing.biconlife.libbaselib.base.BaseApplication;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * QQ分享逻辑处理类
 * @author huijitao
 */

public class QQShareServer implements IUiListener{

    /**
     * 使用单例
     */
    private static QQShareServer sInstance;
    /**
     * QQ分享处理类
     */
    private static Tencent mTencent;
    /**
     * 调用者
     */
    private static Activity mActivity;
    /**
     * appId
     */
    private static String appID;
    /**
     * 回调接口
     */
    private static BCShareInter shareCallback;


    /**
     * 实例化对象
     * @return
     */
    public static QQShareServer getInstance(Activity activity, String appId, BCShareInter callback) {
        if (sInstance == null) {
            try {
                sInstance = new QQShareServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mActivity = activity;
        if(mActivity == null){
            mActivity = (Activity) BaseApplication.getContext();
        }
        appID = appId;
        shareCallback = callback;
        init();
        return sInstance;
    }

    private static void init() {
        try {
            if (mTencent == null) {
                mTencent = Tencent.createInstance(appID, mActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享到QQ好友（也可以选择QQ空间）
     * @param title     标题
     * @param summary   描述
     * @param targetUrl 要打开的网址
     * @param ImageUrl  图片url
     */
    public void shareToQQ(String title, String summary, String targetUrl, String ImageUrl) {
        try {
            Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ImageUrl);
            mTencent.shareToQQ(mActivity, params, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享到QQ空间（只是分享到空间）
     * @param title     标题
     * @param summary   描述
     * @param targetUrl 要打开的网址
     * @param ImageUrl  图片url集合，虽然是集合，但目前只支持显示单图片（放多张图片也只是显示一张）
     */
    public void shareToQzone(String title, String summary, String targetUrl, ArrayList<String> ImageUrl) {
        try {
            Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);//选填
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);//必填
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, ImageUrl);
            mTencent.shareToQzone(mActivity, params, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //QQ回调
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constants.REQUEST_QQ_SHARE) {
                if (resultCode == Constants.ACTIVITY_OK) {
                    Tencent.handleResultData(data, this);
                }
            }
//            mTencent.onActivityResult(requestCode, resultCode, data);
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCancel() {
        if (shareCallback != null) {
            shareCallback.onShareCancel();
            LKLogUtil.e("======onCancel");
        }
    }

    @Override
    public void onError(UiError uiError) {
        if (shareCallback != null) {
            shareCallback.onShareError(uiError.toString());
            LKLogUtil.e("======onError");
        }
    }

    @Override
    public void onComplete(Object o) {
        if (shareCallback != null) {
            shareCallback.onShareSuccess(o.toString());
            LKLogUtil.e("======onComplete");
        }
    }
}
