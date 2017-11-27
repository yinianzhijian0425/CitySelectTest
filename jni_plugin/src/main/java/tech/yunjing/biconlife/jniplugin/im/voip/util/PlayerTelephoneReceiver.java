package tech.yunjing.biconlife.jniplugin.im.voip.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;


/**
 * 语音播放操作(听筒播放)
 * Created by Chen.qi on 2017/8/10
 */
public class PlayerTelephoneReceiver implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static PlayerTelephoneReceiver mInstance = null;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;


    public static PlayerTelephoneReceiver getInstance() {
        if (mInstance == null) {
            synchronized (PlayerTelephoneReceiver.class) {
                if (mInstance == null) {
                    mInstance = new PlayerTelephoneReceiver();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化播放控件(听筒播放)
     */
    public void initPlayer(Activity activity, boolean isTingTong) {
        try {
            mMediaPlayer = new MediaPlayer();
            if (isTingTong) {
                mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.setMicrophoneMute(false);
                mAudioManager.setSpeakerphoneOn(false);
                mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            }
            mMediaPlayer.setOnPreparedListener(this);//当prepare被调用时触发
            mMediaPlayer.setOnCompletionListener(this);//监听播放结束
            mMediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 播放语音听筒
     *
     * @param activity
     * @param voiceUrlFileName 音乐文件
     */
    public void playUrl(Activity activity, String voiceUrlFileName, boolean isTingtong) {
        stop();
        if (mMediaPlayer == null) {
            initPlayer(activity, isTingtong);
        }
        try {
            AssetFileDescriptor fd = activity.getAssets().openFd(voiceUrlFileName);
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 播放结束的音乐
     *
     * @param activity
     * @param voiceUrlFileName 音乐文件
     */
    public void playUrlEnd(Activity activity, String voiceUrlFileName, boolean isTingTong) {
        stop();
        if (mMediaPlayer == null) {
            initPlayer(activity, isTingTong);
        }
        try {
            AssetFileDescriptor fd = activity.getAssets().openFd(voiceUrlFileName);
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    /**
     * 播放
     */
    public void play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    /**
     * 停止
     */
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mAudioManager != null) {
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager = null;
        }

    }

    /**
     * 播放完成回调
     *
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    /**
     * 播放出现错误
     *
     * @param mediaPlayer
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        onCompletion(mediaPlayer);
        stop();
        return false;
    }

    /**
     * 准备并播放
     *
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }


    /**
     * 开始音乐播放
     *
     * @param activity   上下文
     * @param strMp3Name assets中播放音乐的文件名
     * @param isTingTong 是否听筒播放
     */
    public void startMp3(Activity activity, String strMp3Name, boolean isTingTong) {
        stop();
        playUrl(activity, strMp3Name, isTingTong);
        play();
    }

    /**
     * 结束音乐播放
     *
     * @param activity   上下文
     * @param strType    assets中播放音乐的文件名
     * @param isTingTong 是否听筒播放
     */
    public void endMp3(Activity activity, String strType, boolean isTingTong) {
        stop();
        playUrlEnd(activity, strType, isTingTong);
        play();
    }


}
