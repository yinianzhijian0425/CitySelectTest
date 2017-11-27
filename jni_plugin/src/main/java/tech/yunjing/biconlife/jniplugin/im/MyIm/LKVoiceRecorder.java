package tech.yunjing.biconlife.jniplugin.im.MyIm;

import android.media.MediaRecorder;
import android.os.SystemClock;
import android.text.format.Time;

import com.hyphenate.chat.EMClient;
import com.hyphenate.util.PathUtil;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * 语音录制操作
 * Created by nanPengFei on 2016/10/28 17:43.
 */
public class LKVoiceRecorder {
    MediaRecorder mRecorder;
    private final String EXTENSION = ".amr";
    public volatile boolean mIsRecording = false;//是否正在录音
    private long mStartTime;//开始时间
    private String mVoiceFilePath = null;//语音文件路径
    private String mVoiceFileName = null;//语音文件名称
    private File mVoiceFile;//语音文件
    private LKVoiceRecorderListener mListener;//录音状态坚挺
    private ScheduledExecutorService mTimer = null;
    private Runnable mTimerTask = null;
    private static int period = 1000;  //1s
    private int currentSeconds = 0;
    private ScheduledFuture<?> scheduledFuture;

    public LKVoiceRecorder(LKVoiceRecorderListener listener) {
        this.mListener = listener;
    }

    /**
     * 开始录制语音文件
     */
    public String startRecording() {
        String filePath = null;
        mVoiceFile = null;
        try {// 每次都需要创建mRecorder,否则,会有异常时从setOutputFile试图重用
            if (mRecorder != null) {
//                mRecorder.release();
//                mRecorder = null;
                discardRecording();
            }
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //指定Audio编码方式，目前只有AMR_NB格式
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setAudioChannels(1); // MONO
            mRecorder.setAudioSamplingRate(8000); // 8000Hz
            mRecorder.setAudioEncodingBitRate(64); // seems if change this to
            mVoiceFileName = getVoiceFileName(EMClient.getInstance().getCurrentUser());
            mVoiceFilePath = PathUtil.getInstance().getVoicePath() + "/" + mVoiceFileName;
            mVoiceFile = new File(mVoiceFilePath);
            mRecorder.setOutputFile(mVoiceFile.getAbsolutePath());
            mRecorder.prepare();
            mRecorder.start();
            mStartTime = System.currentTimeMillis();
            mIsRecording = true;
            startTimer();
            new Thread(new Runnable() {//通知振幅
                @Override
                public void run() {
                    try {
                        while (mIsRecording) {
                            mListener.amplitude(mRecorder.getMaxAmplitude() * 13 / 0x7FFF);
                            SystemClock.sleep(100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            filePath = mVoiceFile == null ? null : mVoiceFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            LKLogUtil.e("=========录音异常=========" + e.toString());
            mListener.noPermission();//通知没有权限并关闭弹窗
            discardRecording();
        }
        return filePath;
    }


    private void startTimer() {
        currentSeconds = 0;
        mTimer = new ScheduledThreadPoolExecutor(1);
        mTimerTask = new Runnable() {
            @Override
            public void run() {
                currentSeconds = currentSeconds + 1;
                mListener.voiceLength(currentSeconds);
            }
        };
        scheduledFuture = mTimer.scheduleAtFixedRate(mTimerTask, period, period, TimeUnit.MILLISECONDS);
    }

    private void stopTimer() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        }
        if (mTimer != null) {
            mTimer.shutdown();
            mTimer = null;
        }
        currentSeconds = 0;
    }

    /**
     * 停止录音并删除录音记录
     */
    public void discardRecording() {
        stopTimer();
        if (mRecorder != null) {
            mIsRecording = false;
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                deleteFile(mVoiceFile);
            } catch (Exception e) {
                e.printStackTrace();
                mRecorder = null;
//                LKLogUtils.e("删除录音异常==" + e.toString());
            }
        }
    }

    /**
     * 删除语音文件
     *
     * @param voiceFile
     */
    public void deleteFile(File voiceFile) {
        if (voiceFile != null && voiceFile.exists() && !voiceFile.isDirectory()) {
            voiceFile.delete();
        }
    }


    /**
     * 停止录音
     *
     * @return
     */
    public void stopRecoding() {
        stopTimer();
        if (mRecorder != null) {
            mIsRecording = false;
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            if (mVoiceFile == null || !mVoiceFile.exists() || !mVoiceFile.isFile()) {
                mListener.recordFail();
                return;
            }
            if (mVoiceFile != null && mVoiceFile.length() == 0) {
                mVoiceFile.delete();
                mListener.recordFail();
                return;
            }
            int seconds = (int) (System.currentTimeMillis() - mStartTime) / 1000;
            mListener.stopRecording(seconds, mVoiceFilePath, mVoiceFileName, mVoiceFile);
        } else {
            mListener.recordFail();
        }
    }

    private String getVoiceFileName(String uid) {
        Time now = new Time();
        now.setToNow();
        return uid + now.toString().substring(0, 15) + EXTENSION;
    }

    /**
     * 判断是否正在录音
     *
     * @return
     */
    public boolean isRecording() {
        return mIsRecording;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (mRecorder != null) {
            mRecorder.release();
        }
    }
}
