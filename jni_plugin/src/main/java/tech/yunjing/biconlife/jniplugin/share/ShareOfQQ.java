package tech.yunjing.biconlife.jniplugin.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Author: CQ
 */
public class ShareOfQQ implements IUiListener {
    private static Tencent mTencent;
    private Activity mActivity;

    private String appId;

    private LKShareInter shareCallback;

    public ShareOfQQ(Activity mActivity, String appId, LKShareInter shareCallback) {
        this.mActivity = mActivity;
        this.appId = appId;
        this.shareCallback = shareCallback;
        init();
    }

    private void init() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(appId, mActivity);
        }
    }

    /**
     * @param title     标题
     * @param summary   描述
     * @param targetUrl 要打开的网址
     * @param ImageUrl  图片url
     */
    public void shareToQQ(String title, String summary, String targetUrl, String ImageUrl) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ImageUrl);
        mTencent.shareToQQ(mActivity, params, this);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.handleResultData(data, this);
            }
        }
        mTencent.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onCancel() {
        if (shareCallback != null) {
            shareCallback.onShareCancel();
        }
    }

    @Override
    public void onError(UiError uiError) {
        if (shareCallback != null) {
            shareCallback.onShareError(uiError.toString());
        }
    }

    @Override
    public void onComplete(Object o) {
        if (shareCallback != null) {
            shareCallback.onShareSuccess(o.toString());
        }
    }
}
