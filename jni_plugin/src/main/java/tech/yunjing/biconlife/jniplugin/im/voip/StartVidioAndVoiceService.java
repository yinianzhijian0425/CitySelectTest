package tech.yunjing.biconlife.jniplugin.im.voip;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.CenterService;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.VoIPHelper;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * Created by Chen.qi on 2017/8/15
 */
public class StartVidioAndVoiceService extends Service implements Serializable {


    /**
     * 接受音视频消息的广播
     */
    private VoiceBrodCastReceiver brodcastReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        brodcastReceiver = new VoiceBrodCastReceiver();
        initBroadCastReceiver();
    }


    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(HxTestKey.BROADCASE_RECEIVER_VOIP);
        registerReceiver(brodcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //双重保证。保证数据都被清掉
        if (brodcastReceiver != null) {
            try {
                unregisterReceiver(brodcastReceiver);
            } catch (Exception e) {
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private class VoiceBrodCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            if (HxTestKey.BROADCASE_RECEIVER_VOIP.equals(intent.getAction())) {
                try {
                    String imTag = intent.getStringExtra(VoIPHelper.RECEIVER_ID);

                    LKLogUtil.e("result77777777777777777777wwwwwww==" + imTag);

                    IMObj imObj = (IMObj) intent.getSerializableExtra(VoIPHelper.TRANST_MSG_OBJ);
                    LKLogUtil.e("result开启音视频收到的数据" + imObj.toString());

                    boolean isOpenYSP = false;

                    if (imObj != null && !TextUtils.isEmpty(imObj.members)) {
                        ArrayList<GroupMenberListdata> groupMenberListdatas =
                                LKJsonUtil.jsonToArrayList(imObj.members, GroupMenberListdata.class);
                        //群主是否邀请自己开启音视频
                        if (groupMenberListdatas != null && groupMenberListdatas.size() > 0) {
                            for (int i = 0; i < groupMenberListdatas.size(); i++) {
                                if (UserInfoManageUtil.getUserInfo().getPhone().equals(groupMenberListdatas.get(i).mobile)) {
                                    isOpenYSP = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        isOpenYSP = true;
                    }

                    if (!isOpenYSP || imObj == null || TextUtils.isEmpty(imTag)) {
                        return;
                    }
                    Intent intentObj = new Intent(StartVidioAndVoiceService.this, CenterService.class);
                    intentObj.putExtra(VoIPHelper.TRANST_MSG_OBJ, imObj);
                    intentObj.putExtra(VoIPHelper.RECEIVER_ID, imTag);
                    StartVidioAndVoiceService.this.startService(intentObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
