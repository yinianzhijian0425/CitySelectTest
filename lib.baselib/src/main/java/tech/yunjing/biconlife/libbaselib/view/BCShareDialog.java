package tech.yunjing.biconlife.libbaselib.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.wequick.small.Small;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.bean.BCShareInfoObj;
import tech.yunjing.biconlife.libbaselib.global.BCBundleJsonKey;
import tech.yunjing.biconlife.libbaselib.global.BCIntentKey;
import tech.yunjing.biconlife.libbaselib.global.BCShareKey;
import tech.yunjing.biconlife.libbaselib.util.share.BCShareInter;
import tech.yunjing.biconlife.libbaselib.util.share.QQConstants;
import tech.yunjing.biconlife.libbaselib.util.share.QQShareServer;
import tech.yunjing.biconlife.libbaselib.util.share.SinaShareServer;
import tech.yunjing.biconlife.libbaselib.util.share.WeiXinConstants;
import tech.yunjing.biconlife.libbaselib.util.share.WeiXinShareServer;
import tech.yunjing.biconlife.liblkclass.common.util.LKDialogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKNetUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;

/**
 * 公共分享Diaolog工具类
 */
public class BCShareDialog {

    /**
     * 分享活动链接（暂假数据）
     */
    private String shareUrl = "https://www.lookmw.cn/jdmw/109022.html";
    /**
     * 调用者
     */
    private Activity mActivity;
    /**
     * 分享弹出框
     */
    private static BCShareDialog sInstance;


    /**
     * 实例化对象
     *
     * @return
     */
    public static BCShareDialog getInstance() {
        if (sInstance == null) {
            sInstance = new BCShareDialog();
        }
        return sInstance;
    }

    /**
     * 默认分享方法
     */
    public void initDialog(final Activity activity, final BCShareInter shareCallback) {
        mActivity = activity;
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share_invite, null);
        final Dialog dialog = LKDialogUtil.getBottomDialog(activity, view, R.style.UserDetailsDialogStyle);
        dialog.show();
        //微信分享
        LinearLayout ll_dialog_share_wechat = (LinearLayout) view.findViewById(R.id.ll_dialog_share_wechat);
        LinearLayout ll_share_wechat_circle = (LinearLayout) view.findViewById(R.id.ll_share_wechat_circle);
        //QQ分享
        LinearLayout ll_share_qq = (LinearLayout) view.findViewById(R.id.ll_share_qq);
        //伯图好友分享
        LinearLayout ll_dialog_share_botu = (LinearLayout) view.findViewById(R.id.ll_dialog_share_botu);
        //生活圈分享
        LinearLayout ll_share_life_circle = (LinearLayout) view.findViewById(R.id.ll_share_life_circle);
        //复制链接
        LinearLayout ll_share_copy = (LinearLayout) view.findViewById(R.id.ll_share_copy);
        //新浪微博
        LinearLayout ll_dialog_share_sina = (LinearLayout) view.findViewById(R.id.ll_dialog_share_sina);
        TextView tv_share_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);   //取消
        tv_share_cancel.setOnClickListener(new View.OnClickListener() { //点击取消
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        ll_share_copy.setOnClickListener(new View.OnClickListener() {    //复制链接
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("复制链接");
                ChatShareReady(activity);
                dialog.hide();
            }
        });
        ll_share_life_circle.setOnClickListener(new View.OnClickListener() {    //生活圈分享
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("生活圈分享");
                ChatShareReady(activity);
                dialog.hide();
            }
        });
        ll_dialog_share_botu.setOnClickListener(new View.OnClickListener() {    //伯图好友分享
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("伯图好友分享");
                ChatShareReady(activity);
                dialog.hide();
            }
        });
        ll_share_qq.setOnClickListener(new View.OnClickListener() {    //点击QQ分享
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("QQ分享");
                ChatShareReady(activity);
                dialog.hide();
                //QQ好友和空间分享（成功）
                QQShareServer.getInstance(activity, QQConstants.QQ_APP_ID, shareCallback).shareToQQ("自定义封装QQ分享测试", "美文欣赏", shareUrl, "http://image.hnol.net/c/2017-07/25/07/20170725074645191-5058976.jpg");
                //QQ空间分享（成功）
//                ArrayList<String> imgs = new ArrayList<String>();
//                imgs.add("http://image.hnol.net/c/2017-07/25/07/20170725074645191-5058976.jpg");
//                imgs.add("http://image.hnol.net/c/2017-07/24/18/201707241821262371-5058976.jpg");
//                imgs.add("http://image.hnol.net/c/2017-07/24/18/201707241809422581-5058976.jpg");
//                QQShareServer.getInstance(activity, ConfigureConstantUtil.QQ_APP_ID, shareCallback).shareToQzone("QQ分享测试", "美文欣赏", shareUrl,imgs);
            }
        });
        ll_dialog_share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                      //点击微信分享
                dialog.hide();
//                LKToastUtil.showToastShort("微信分享");
                ChatShareReady(activity);
                WeiXinShareServer.getInstance(activity, WeiXinConstants.WECHAT_APP_ID).shareWebImage("自定义封装微信分享测试", "美文欣赏", shareUrl, "http://image.hnol.net/c/2017-07/25/07/20170725074645191-5058976.jpg", 0, 100, 100);
            }
        });
        ll_share_wechat_circle.setOnClickListener(new View.OnClickListener() {   //点击微信朋友圈分享
            @Override
            public void onClick(View v) {
                dialog.hide();
//                LKToastUtil.showToastShort("微信朋友圈分享");
                ChatShareReady(activity);
                WeiXinShareServer.getInstance(activity, WeiXinConstants.WECHAT_APP_ID).shareWebImage("自定义封装微信分享测试", "美文欣赏", shareUrl, "http://image.hnol.net/c/2017-07/25/07/20170725074645191-5058976.jpg", 1, 100, 100);
            }
        });
        ll_dialog_share_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
//                LKToastUtil.showToastShort("新浪微博分享");
                ChatShareReady(activity);
                SinaShareServer.getInstance(activity, shareCallback).shareWeiBo(shareUrl);
            }
        });
    }

    /**
     * 带参数的调用方式，使用自定义的标题，分享链接等参数
     * title  标题
     * describe  描述
     * shareurl  分享链接
     * imgurl  图片路径
     * isbotu  是否显示伯图全景内置分享
     */
    public void initDialog(final Activity activity, final BCShareInter shareCallback, final BCShareInfoObj bcShareInfoObj, boolean isbotu) {
        if(bcShareInfoObj == null){
            return;
        }
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share_invite, null);
        final Dialog dialog = LKDialogUtil.getBottomDialog(activity, view, R.style.UserDetailsDialogStyle);
        dialog.show();
        //微信分享
        LinearLayout ll_dialog_share_wechat = (LinearLayout) view.findViewById(R.id.ll_dialog_share_wechat);
        LinearLayout ll_share_wechat_circle = (LinearLayout) view.findViewById(R.id.ll_share_wechat_circle);
        //QQ分享
        LinearLayout ll_share_qq = (LinearLayout) view.findViewById(R.id.ll_share_qq);
        //伯图好友分享
        LinearLayout ll_dialog_share_botu = (LinearLayout) view.findViewById(R.id.ll_dialog_share_botu);
        //伯图内置分享
        LinearLayout ll_botu = (LinearLayout) view.findViewById(R.id.ll_botu);
        //生活圈分享
        LinearLayout ll_share_life_circle = (LinearLayout) view.findViewById(R.id.ll_share_life_circle);
        //复制链接
        LinearLayout ll_share_copy = (LinearLayout) view.findViewById(R.id.ll_share_copy);
        //新浪微博
        LinearLayout ll_dialog_share_sina = (LinearLayout) view.findViewById(R.id.ll_dialog_share_sina);
        TextView tv_share_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);   //取消
        tv_share_cancel.setOnClickListener(new View.OnClickListener() { //点击取消
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        //设置是否显示伯图全景内部的分享
        if(isbotu){
            ll_botu.setVisibility(View.VISIBLE);
        }else {
            ll_botu.setVisibility(View.GONE);
        }
        ll_share_copy.setOnClickListener(new View.OnClickListener() {    //复制链接
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("复制链接");
                ChatShareReady(activity);
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                ClipData mClipData = ClipData.newPlainText("Label", bcShareInfoObj.getWebUrl());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                LKToastUtil.showToastShort(BCShareKey.CopyLinkSuccessPrompt);
                dialog.hide();
            }
        });
        ll_share_life_circle.setOnClickListener(new View.OnClickListener() {    //生活圈分享
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("生活圈分享");
                ChatShareReady(activity);
                try {
                    Intent intent = Small.getIntentOfUri(BCBundleJsonKey.Social_CreateNewZoneActivity,activity);
                    if(intent!=null){
                        intent.putExtra(BCIntentKey.ShareEntityObjectLogo,bcShareInfoObj);
                        activity.startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.hide();
            }
        });
        ll_dialog_share_botu.setOnClickListener(new View.OnClickListener() {    //伯图好友分享
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("伯图好友分享");
                ChatShareReady(activity);
                dialog.hide();
                try {
                    Intent intent = Small.getIntentOfUri(BCBundleJsonKey.Social_BoTuFriendShareActivity,activity);
                    if(intent!=null){
                        intent.putExtra(BCIntentKey.ShareEntityObjectLogo,bcShareInfoObj);
                        activity.startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ll_share_qq.setOnClickListener(new View.OnClickListener() {    //点击QQ分享
            @Override
            public void onClick(View v) {
//                LKToastUtil.showToastShort("QQ分享");
                ChatShareReady(activity);
                dialog.hide();
                //QQ好友和空间分享（成功）
                QQShareServer.getInstance(activity, QQConstants.QQ_APP_ID, shareCallback).shareToQQ(bcShareInfoObj.getTitle(), bcShareInfoObj.getDescription()
                        , bcShareInfoObj.getWebUrl(), bcShareInfoObj.getImgUrl());
                //QQ空间分享（成功）
//                ArrayList<String> imgs = new ArrayList<String>();
//                imgs.add("http://image.hnol.net/c/2017-07/25/07/20170725074645191-5058976.jpg");
//                imgs.add("http://image.hnol.net/c/2017-07/24/18/201707241821262371-5058976.jpg");
//                imgs.add("http://image.hnol.net/c/2017-07/24/18/201707241809422581-5058976.jpg");
//                QQShareServer.getInstance(activity, ConfigureConstantUtil.QQ_APP_ID, shareCallback).shareToQzone("QQ分享测试", "美文欣赏", shareUrl,imgs);
            }
        });
        ll_dialog_share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                      //点击微信分享
                dialog.hide();
//                LKToastUtil.showToastShort("微信分享");
                ChatShareReady(activity);
                WeiXinShareServer.getInstance(activity, WeiXinConstants.WECHAT_APP_ID).shareWebImage(bcShareInfoObj.getTitle(), bcShareInfoObj.getDescription()
                        , bcShareInfoObj.getWebUrl(), bcShareInfoObj.getImgUrl(), 0, 100, 100);
            }
        });
        ll_share_wechat_circle.setOnClickListener(new View.OnClickListener() {   //点击微信朋友圈分享
            @Override
            public void onClick(View v) {
                dialog.hide();
//                LKToastUtil.showToastShort("微信朋友圈分享");
                ChatShareReady(activity);
                WeiXinShareServer.getInstance(activity, WeiXinConstants.WECHAT_APP_ID).shareWebImage(bcShareInfoObj.getTitle(), bcShareInfoObj.getDescription()
                        , bcShareInfoObj.getWebUrl(), bcShareInfoObj.getImgUrl(), 1, 100, 100);
            }
        });
        ll_dialog_share_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
//                LKToastUtil.showToastShort("新浪微博分享");
                ChatShareReady(activity);
                SinaShareServer.getInstance(activity, shareCallback).shareWeiBo(bcShareInfoObj.getWebUrl());
            }
        });
    }


    /**
     * 分享前客户端和网络的判断
     */
    private void ChatShareReady(Activity activity) {
        boolean hasNet = LKNetUtil.hasNetwork(activity);
        if (!hasNet) {
            LKToastUtil.showToastShort(activity.getResources().getString(R.string.str_check_net));
        }
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
