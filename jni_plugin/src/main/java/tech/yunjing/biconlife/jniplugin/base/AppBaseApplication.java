package tech.yunjing.biconlife.jniplugin.base;

import cn.jpush.android.api.JPushInterface;
import tech.yunjing.biconlife.jniplugin.db.GeneralDb;
import tech.yunjing.biconlife.jniplugin.global.BCSharePreKey;
import tech.yunjing.biconlife.jniplugin.im.IMBaseHelper;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;


public class AppBaseApplication extends LKApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        IMBaseHelper.getInstance().init(this, null);
        LKPrefUtil.createPref(this, BCSharePreKey.FILENAME_SP);
        GeneralDb.createDb(getContext());
        JPushInterface.init(this);            // 初始化 JPush
        JPushInterface.requestPermission(this);//申请权限,6.0以上
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        LK.Ext.setDebug(true); // 设置本应用开启日志,发布时请关闭日志
    }
}
