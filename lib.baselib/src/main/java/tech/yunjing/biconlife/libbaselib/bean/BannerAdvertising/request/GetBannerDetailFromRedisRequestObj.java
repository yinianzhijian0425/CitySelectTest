package tech.yunjing.biconlife.libbaselib.bean.BannerAdvertising.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 获取对应模块轮播图接口-------请求实体
 * Created by sun.li on 2017/8/15.
 */

public class GetBannerDetailFromRedisRequestObj extends BaseRequestObj {

    /**
     * 轮播模块：1.商城 2.医疗 3.激励
     */
    private int bannerModular;

    /**
     * 轮播位置: 1.首页
     */
    private int bannerPosition;

    /**
     * 获取轮播模块：1.商城 2.医疗 3.激励
     */
    public int getBannerModular() { return bannerModular; }

    /**
     * 设置轮播模块：1.商城 2.医疗 3.激励
     */
    public void setBannerModular(int bannerModular) { this.bannerModular = bannerModular; }

    /**
     * 获取轮播位置: 1.首页
     */
    public int getBannerPosition() { return bannerPosition; }

    /**
     * 设置轮播位置: 1.首页
     */
    public void setBannerPosition(int bannerPosition) { this.bannerPosition = bannerPosition; }
}
