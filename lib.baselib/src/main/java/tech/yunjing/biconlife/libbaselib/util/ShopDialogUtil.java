package tech.yunjing.biconlife.libbaselib.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tech.yunjing.biconlife.jniplugin.share.LKShareInter;
import tech.yunjing.biconlife.jniplugin.share.ShareOfWX;
import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.global.ConfigureConstantUtil;
import tech.yunjing.biconlife.libbaselib.view.LKChooseView;
import tech.yunjing.biconlife.liblkclass.common.util.LKDialogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKNetUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;

/**
 * 商品详情分享dialog处理
 * Created by wsw on 2017/7/17 0017.
 */

public class ShopDialogUtil {
    private static ShopDialogUtil sInstance;
    private ShareOfWX wxShared;

    /**
     * 实例化对象
     *
     * @return
     */
    public static ShopDialogUtil getInstance() {
        if (sInstance == null) {
            sInstance = new ShopDialogUtil();
        }
        return sInstance;
    }

    /**
     * 面对面对话框
     */
    public void initDialog(final Activity activity, final LKShareInter shareCallback) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_invite, null);
        final Dialog dialog = LKDialogUtil.getBottomDialog(activity, view, R.style.UserDetailsDialogStyle);
        dialog.show();
        final LinearLayout ll_show_share = (LinearLayout) view.findViewById(R.id.ll_show_share);
        ll_show_share.setVisibility(View.VISIBLE);
        LKChooseView milv_qq = (LKChooseView) view.findViewById(R.id.milv_qq);
        LKChooseView milv_wechat = (LKChooseView) view.findViewById(R.id.milv_wechat);
        LKChooseView milv_friends = (LKChooseView) view.findViewById(R.id.milv_friends);
        milv_qq.setTvContent(activity.getResources().getString(R.string.str_share_qq));              //QQ分享
        milv_wechat.setTvContent(activity.getResources().getString(R.string.str_share_wechat));      //微信分享
        milv_friends.setTvContent(activity.getResources().getString(R.string.str_share_friends));    //微信朋友圈
        milv_qq.setImgBackground(R.mipmap.icon_invite_qq);
        milv_wechat.setImgBackground(R.mipmap.icon_invite_wechat);
        milv_friends.setImgBackground(R.mipmap.icon_invite_friends);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_invite_sdu_cancel);   //取消
        tv_cancel.setOnClickListener(new View.OnClickListener() { //点击取消
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        milv_qq.setOnClickListener(new View.OnClickListener() {    //点击QQ分享
            @Override
            public void onClick(View v) {
                LKToastUtil.showToastShort("QQ分享");
                dialog.cancel();
//                ShareOfQQ qqShare = new ShareOfQQ(activity, ConfigureConstantUtil.QQ_APP_ID, shareCallback);
////                qqShare.shareToQQ(title, content, url, logo);
//                qqShare.shareToQQ("测试", "啦啦啦", "http://img2.imgtn.bdimg.com/it/u=2670750920,766573320&fm=214&gp=0.jpg", "http://img2.imgtn.bdimg.com/it/u=2670750920,766573320&fm=214&gp=0.jpg");
            }
        });
        milv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                      //点击微信分享
                dialog.cancel();
                LKToastUtil.showToastShort("微信分享");
//                weChatShareReady(activity);
//                wxShared = new ShareOfWX(activity, ConfigureConstantUtil.WECHAT_APP_ID);
//                wxShared.shareWebImage("测试", "啦啦啦", "http://img2.imgtn.bdimg.com/it/u=2670750920,766573320&fm=214&gp=0.jpg", "http://img2.imgtn.bdimg.com/it/u=2670750920,766573320&fm=214&gp=0.jpg", 0, 100, 100);
            }
        });
        milv_friends.setOnClickListener(new View.OnClickListener() {   //点击微信朋友圈分享
            @Override
            public void onClick(View v) {
                dialog.cancel();
                weChatShareReady(activity);
                wxShared = new ShareOfWX(activity, ConfigureConstantUtil.WECHAT_APP_ID);
//                wxShared.shareWebImage(title, content, url, logo, 1, 100, 100);
            }
        });
    }

    /**
     * 微信分享前客户端和网络的判断
     */
    private void weChatShareReady(Activity activity) {
        boolean hasNet = LKNetUtil.hasNetwork(activity);
        if (!hasNet) {
            LKToastUtil.showToastShort(activity.getResources().getString(R.string.str_check_net));
        }
    }
}
