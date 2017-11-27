package tech.yunjing.biconlife.jniplugin.im.setting;

import com.hyphenate.chat.EMMessage;

/**
 * 默认通知栏消息通知方式设置
 * Created by nanPengFei on 2016/11/15 10:18.
 */
public class IMDefaultSetProvider implements IMEaseSetProvider {
    @Override
    public boolean isMsgNotifyAllowed(EMMessage message) {
        return true;
    }

    @Override
    public boolean isMsgSoundAllowed(EMMessage message) {
        return true;
    }

    @Override
    public boolean isMsgVibrateAllowed(EMMessage message) {
        return true;
    }

    @Override
    public boolean isSpeakerOpened() {
        return true;
    }
}
