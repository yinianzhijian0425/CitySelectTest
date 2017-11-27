package tech.yunjing.biconlife.jniplugin.im.MyIm;

import java.io.File;

/**
 * 语音录制监听
 * Created by mrn on 2016/10/29 0029.
 */
public interface LKVoiceRecorderListener {

    void noPermission();

    void amplitude(int amplitude);

    void voiceLength(int voiceLength);

    void stopRecording(int voiceLength, String voicePath, String voiceFileName, File voiceFile);

    void recordFail();
}
