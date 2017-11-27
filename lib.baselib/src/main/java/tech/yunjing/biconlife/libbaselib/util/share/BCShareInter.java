package tech.yunjing.biconlife.libbaselib.util.share;

/**
 * 分享的接口回调
 * @author huijitao
 */
public interface BCShareInter {
    /**
     * 分享成功回调
     */
    void onShareSuccess(String s);
    /**
     * 取消分享回调
     */
    void onShareCancel();
    /**
     * 分享失败回调
     */
    void onShareError(String e);
}
