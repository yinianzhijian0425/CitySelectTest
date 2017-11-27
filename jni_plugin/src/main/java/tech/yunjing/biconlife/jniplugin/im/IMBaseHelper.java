package tech.yunjing.biconlife.jniplugin.im;

import android.app.Activity;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImMessageListener;
import tech.yunjing.biconlife.jniplugin.im.listener.IMConnectionListener;
import tech.yunjing.biconlife.jniplugin.im.listener.IMContactListener;
import tech.yunjing.biconlife.jniplugin.im.listener.IMGroupChangeListener;
import tech.yunjing.biconlife.jniplugin.im.setting.IMDefaultSetProvider;
import tech.yunjing.biconlife.jniplugin.im.setting.IMEaseSetProvider;
import tech.yunjing.biconlife.liblkclass.common.util.LKAppUtil;


/**
 * Created by nanPengFei on 2016/11/15 10:32.
 */
public class IMBaseHelper {
    private EaseUserProfileProvider mUserProvider;//用户配置文件
    private IMEaseSetProvider mSetProvider;//通知栏通知属性
    private Context mAppContext = null;
    private boolean mSDKInited = false;//标识SDK是否已经初始化

    private List<Activity> mActivityList = new ArrayList<>();

    /**
     * 添加创建的Activity到集合
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (!mActivityList.contains(activity)) {
            mActivityList.add(0, activity);
        }
    }

    /**
     * 从集合中移除Activity
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    private static IMBaseHelper mInstance = null;

    private IMBaseHelper() {
    }

    public static IMBaseHelper getInstance() {
        if (null == mInstance) {
            synchronized (IMBaseHelper.class) {
                if (null == mInstance) {
                    mInstance = new IMBaseHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化SDK
     *
     * @param context
     * @param options
     * @return
     */
    public synchronized boolean init(Context context, EMOptions options) {
        if (mSDKInited) {
            return true;
        }
        mAppContext = context;
        String processAppName = LKAppUtil.getInstance().getAppName();
        String str="tech.yunjing.biconlife.app.social";
        String str1="tech.yunjing.biconlife";
        if (processAppName == null || (!processAppName.equalsIgnoreCase(str) && !processAppName.equalsIgnoreCase(str1))) {
            return false;
        }
        if (EMClient.getInstance() == null) {
            return false;
        }
        if (options == null) {
            EMClient.getInstance().init(context, initChatOptions());
        } else {
            EMClient.getInstance().init(context, options);
        }
        EMClient.getInstance().setDebugMode(false); //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().addConnectionListener(IMConnectionListener.getInstance());  //注册一个监听连接状态的listener
        EMClient.getInstance().groupManager().addGroupChangeListener(IMGroupChangeListener.getInstance()); //注册群组变化监听
        EMClient.getInstance().contactManager().setContactListener(IMContactListener.getInstance()); //注册IM好友变化监听
        EMClient.getInstance().chatManager().addMessageListener(MyImMessageListener.getInstance()); //注册消息接收监听
        if (mSetProvider == null) {
            mSetProvider = new IMDefaultSetProvider();
        }
        mSDKInited = true;
        return true;
    }


    protected EMOptions initChatOptions() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);//自动接受好友邀请
        options.setAutoAcceptGroupInvitation(true);//自动同意群组邀请
        options.setAutoLogin(true);//设置自动登录
        options.setDeleteMessagesAsExitGroup(true);//退群后自动删除聊天记录
        options.setRequireAck(true);
        options.setAppKey("1195170711115729#botu");
        options.setRequireDeliveryAck(false);
        //you need apply & set your own id if you want to use google cloud messaging.
        //你需要申请&设定你自己的身份证,如果你想使用谷歌云的消息传递。
        //options.setGCMNumber("324169311137");
        //you need apply & set your own id if you want to use Mi push notification
        //你需要申请&设定你自己的身份证,如果你想使用Mi推送通知
        //options.setMipushConfig("2882303761517522631", "5161752278631");
        //you need apply & set your own id if you want to use Huawei push notification
        //你需要申请&设定你自己的身份证,如果你想使用华为推送通知
        //options.setHuaweiPushAppId("10492024");
        return options;
    }



    /**
     * 是否有Activity运行在前台
     *
     * @return
     */
    public boolean hasForegroundActivies() {
        return mActivityList.size() != 0;
    }

    /**
     * 设置用户通知栏信息
     *
     * @param userProvider
     */
    public void setUserProfileProvider(EaseUserProfileProvider userProvider) {
        this.mUserProvider = userProvider;
    }

    /**
     * 获取用户通知栏信息
     */
    public EaseUserProfileProvider getUserProfileProvider() {
        return mUserProvider;
    }

    /**
     * 设置通知栏基本信息
     *
     * @param mSetProvider
     */
    public void setProfileProvider(IMEaseSetProvider mSetProvider) {
        this.mSetProvider = mSetProvider;
    }

    /**
     * 获取通知栏基本信息
     *
     * @return
     */
    public IMEaseSetProvider getProfileProvider() {
        return mSetProvider;
    }


    /**
     * User profile provider
     *
     * @author wei
     */
    public interface EaseUserProfileProvider {
        /**
         * return EaseUser for input username
         *
         * @param username
         * @return
         */
//        EaseUser getUser(String username);

    }

    /**
     * Emojicon provider
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * return EaseEmojicon for input emojiconIdentityCode
         *
         * @param emojiconIdentityCode
         * @return
         */
//        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);

        /**
         * get Emojicon map, key is the text of emoji, value is the resource id or local path of emoji icon(can't be URL on internet)
         *
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }

    private EaseEmojiconInfoProvider emojiconInfoProvider;

    /**
     * Emojicon provider
     *
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider() {
        return emojiconInfoProvider;
    }

    /**
     * set Emojicon provider
     *
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider) {
        this.emojiconInfoProvider = emojiconInfoProvider;
    }


    public Context getContext() {
        return mAppContext;
    }

}
