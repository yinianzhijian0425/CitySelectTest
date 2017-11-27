package tech.yunjing.biconlife.libbaselib.inter.utils;

import tech.yunjing.biconlife.libbaselib.inter.RefreshIntoSignsDataInterface;

/**
 * 刷新录入体征回调共计类
 * Created by CHP on 2017/7/31.
 */

public class RefreshIntoSignsDataUtils {
    private static RefreshIntoSignsDataUtils mInstance;
    /**
     * 刷新数据接口
     */
    private RefreshIntoSignsDataInterface refresh;
    private RefreshIntoSignsDataUtils() {
    }

    public static RefreshIntoSignsDataUtils getInstance() {
        if (null == mInstance) {
            synchronized (RefreshIntoSignsDataUtils.class) {
                if (null == mInstance) {
                    mInstance = new RefreshIntoSignsDataUtils();
                }
            }
        }
        return mInstance;
    }
    public RefreshIntoSignsDataInterface getRefreshIntoSignsDataInterface(){
        return refresh;
    }

    public void setRefreshIntoSignsDataInterface(RefreshIntoSignsDataInterface region) {
        this.refresh = region;

    }
}
