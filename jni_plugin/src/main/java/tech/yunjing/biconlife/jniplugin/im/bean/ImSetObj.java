package tech.yunjing.biconlife.jniplugin.im.bean;

/**
 * Im免打扰，置顶设置
 * Created by lh on 2017/9/1.
 */

public class ImSetObj {

    /**
     * 免打扰
     */
    public boolean noDisturb;
    /**
     * 置顶
     */
    public boolean stick;

    public ImSetObj(boolean noDisturb, boolean stick) {
        this.noDisturb = noDisturb;
        this.stick = stick;
    }


}
