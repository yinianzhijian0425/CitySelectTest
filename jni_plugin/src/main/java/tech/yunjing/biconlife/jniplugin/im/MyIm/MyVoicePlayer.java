package tech.yunjing.biconlife.jniplugin.im.MyIm;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.chat.EMMessage;

import tech.yunjing.biconlife.jniplugin.im.key.LKPrefKeyList;
import tech.yunjing.biconlife.liblkclass.common.util.LKPrefUtil;


/**
 * 语音播放操作
 * Created by lh on 2016/10/31 14:55.
 */
public class MyVoicePlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static MyVoicePlayer mInstance = null;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private EMMessage mEMMessage;

    public static MyVoicePlayer getInstance() {
        if (mInstance == null) {
            synchronized (MyVoicePlayer.class) {
                if (mInstance == null) {
                    mInstance = new MyVoicePlayer();
                }
            }
        }
        return mInstance;
    }

    private ImageView imageView;
    private int length;

    /**
     * 初始化播放控件
     */
    public void initPlayer(Activity activity, ImageView imageView, int length) {
        try {
            stop();
            mMediaPlayer = new MediaPlayer();
            mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            boolean isVoiceCall = LKPrefUtil.getBoolean(LKPrefKeyList.STREAM_VOICE_CALL, false);
            if (isVoiceCall) {//播放模式---听筒
                mAudioManager.setMicrophoneMute(false);
                mAudioManager.setSpeakerphoneOn(false);
                mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            } else {//播放模式---正常
                mAudioManager.setSpeakerphoneOn(true);
                mAudioManager.setMode(AudioManager.MODE_NORMAL);
                mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
            mMediaPlayer.setOnPreparedListener(this);//当prepare被调用时触发
            mMediaPlayer.setOnCompletionListener(this);//监听播放结束
            mMediaPlayer.setOnErrorListener(this);
            this.imageView = imageView;
            this.length = length;
            setAnimator(imageView, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放语音
     *
     * @param activity
     * @param videoUrl
     */
    public void playUrl(Activity activity, String videoUrl, EMMessage emMessage) {
        this.mEMMessage = emMessage;
        if (mMediaPlayer == null){
            initPlayer(activity, imageView, length);}
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(videoUrl);
            mMediaPlayer.prepare();//prepare之后自动播放
            mAnimator.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObjectAnimator mAnimator;

    /**
     * 设置语音点击事件
     *
     * @param view
     * @param length
     */
    public void setAnimator(View view, int length) {
        mAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1);
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(length);
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
        if (null != mAudioManager) {
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager = null;
        }
        if (mEMMessage != null) {
            mEMMessage.setAttribute("isPlaying", false);
        }
        if (mAnimator != null) {
            mAnimator.cancel();
            imageView.setAlpha(1.0f);
        }
    }

    /**
     * 播放完成回调
     *
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (null != mMediaPlayer && null != mAudioManager) {
            mMediaPlayer.release();
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mMediaPlayer = null;
            mAudioManager = null;
        }
        if (mEMMessage != null) {
            mEMMessage.setAttribute("isPlaying", false);
        }
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
}
