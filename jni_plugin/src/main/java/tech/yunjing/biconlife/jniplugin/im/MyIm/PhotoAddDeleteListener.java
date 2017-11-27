package tech.yunjing.biconlife.jniplugin.im.MyIm;

/**
 * 相册图片选中删除监听
 * Created by lijianhui on 2016/11/9
 */
public interface PhotoAddDeleteListener {
    /**
     * 添加图片
     */
    void photoAdd(String s);

    /**
     * 添加删除
     */
    void photoDelete(String s);
}
