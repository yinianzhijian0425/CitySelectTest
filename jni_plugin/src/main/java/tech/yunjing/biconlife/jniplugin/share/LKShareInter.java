package tech.yunjing.biconlife.jniplugin.share;

/**
 * 调用QQ或者Sina授权分享的Activity实现此接口
 */
public interface LKShareInter {

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
     *
     * @param e
     */
    void onShareError(String e);
}
