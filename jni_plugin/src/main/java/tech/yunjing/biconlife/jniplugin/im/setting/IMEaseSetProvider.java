package tech.yunjing.biconlife.jniplugin.im.setting;

import com.hyphenate.chat.EMMessage;

/**
 * 状态栏提醒方式设置
 * Created by nanPengFei on 2016/11/15 10:16.
 */
public interface IMEaseSetProvider {
    /**
     * 是否允许新消息提醒
     *
     * @param message
     * @return
     */
    boolean isMsgNotifyAllowed(EMMessage message);

    /**
     * 是否开启声音提醒
     *
     * @param message
     * @return
     */
    boolean isMsgSoundAllowed(EMMessage message);

    /**
     * 是否开启震动提醒
     *
     * @param message
     * @return
     */
    boolean isMsgVibrateAllowed(EMMessage message);

    boolean isSpeakerOpened();
}
