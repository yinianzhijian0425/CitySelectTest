package tech.yunjing.biconlife.libbaselib.base;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import net.wequick.small.Small;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import tech.yunjing.biconlife.jniplugin.bean.personal.UserInfoQrCodeContentObj;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.global.BCBundleJsonKey;
import tech.yunjing.biconlife.jniplugin.global.BCFileBasePath;
import tech.yunjing.biconlife.libbaselib.util.CaptureFragmentHandler;
import tech.yunjing.biconlife.libbaselib.view.ViewfinderView;
import tech.yunjing.biconlife.libbaselib.view.scan.CameraManager;
import tech.yunjing.biconlife.libbaselib.view.scan.decoding.BitmapLuminanceSource;
import tech.yunjing.biconlife.libbaselib.view.scan.decoding.DecodeFormatManager;
import tech.yunjing.biconlife.libbaselib.view.scan.decoding.InactivityTimer;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKPermissionUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

/**
 * 扫一扫Fragment
 * Created by Chen.qi on 2017/8/1 0001.
 */

public class ScanFragment extends BaseFragment implements View.OnClickListener, SurfaceHolder.Callback {

    private boolean camear;
    /**
     * 是否显示title
     */
    public static boolean isShowTitle = false;

    private RelativeLayout ll_top;
    /**
     * 标题
     */
    private TextView tv_describeTitle;
    /**
     * 返回
     */
    private ImageView iv_back;

    /**
     * 返回总布局
     */
    private LinearLayout ll_back;

    /**
     * 更多
     */
    private TextView tv_save;

    private CaptureFragmentHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

//    @LKViewInject(R.id.iv_openLamplight)
//    private ImageView iv_openLamplight;
//
//    @LKViewInject((R.id.iv_photo))
//    private ImageView iv_photo;

    private String photo_path;

    /**
     * 扫一扫总布局
     */
    private LinearLayout ll_scf_scan;
    /**
     * 扫一扫图片
     */
    private ImageView iv_scf_scan;
    /**
     * 扫一扫控件
     */
    private TextView tv_scf_scan;

    /**
     * 文字识别
     */
    private LinearLayout ll_scf_descern;

    /**
     * 文字识别
     */
    private ImageView iv_photo_tj;
    /**
     * 文字识别zi
     */
    private TextView tv_scf_discern;


    private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_SUC = 300;
    private static final int PARSE_BARCODE_FAIL = 303;

    private SensorManager sm;
    private boolean isOpen = false;
    private MyLightSensorListener listener;


    @Override
    protected int initFragmentView() {
        isVisible = true;
        return R.layout.fragement_qrcode;
    }


    @Override
    protected void initView() {

        if (isShowTitle) {
            ViewStub stub = (ViewStub) mActivity.findViewById(R.id.viewstub_title);
            stub.inflate();
            stub.bringToFront();
            ll_top = (RelativeLayout) mActivity.findViewById(R.id.rl_topbar_layout);
            ll_top.setBackgroundColor(Color.parseColor("#00000000"));
            tv_describeTitle = (TextView) mActivity.findViewById(R.id.tv_describeTitle);
            ll_back = (LinearLayout) mActivity.findViewById(R.id.ll_back);
            iv_back = (ImageView) mActivity.findViewById(R.id.iv_back);
            tv_save = (TextView) mActivity.findViewById(R.id.tv_save);
            tv_describeTitle.setText(mActivity.getResources().getString(R.string.str_scanning));
            tv_describeTitle.setTextColor(Color.parseColor("#FFFFFF"));
            tv_describeTitle.setVisibility(View.VISIBLE);

            iv_back.setImageResource(R.mipmap.icon_scan_back_image);
            iv_back.setVisibility(View.VISIBLE);
            ll_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            });

            tv_save.setText(mActivity.getResources().getString(R.string.str_photo_album));
            tv_save.setVisibility(View.GONE);
            tv_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LKToastUtil.showToastShort("跳转到相册选取图片");
                }
            });
        }
        viewfinderView = (ViewfinderView) mActivity.findViewById(R.id.viewfinder_view);
        ll_scf_scan = (LinearLayout) mActivity.findViewById(R.id.ll_scf_scan);
        ll_scf_scan.setOnClickListener(this);
        iv_scf_scan = (ImageView) mActivity.findViewById(R.id.iv_scf_scan);
        tv_scf_scan = (TextView) mActivity.findViewById(R.id.tv_scf_scan);
        iv_scf_scan.setSelected(true);
        tv_scf_scan.setTextColor(mActivity.getResources().getColor(R.color.color_EF7726));

        ll_scf_descern = (LinearLayout) mActivity.findViewById(R.id.ll_scf_descern);
        ll_scf_descern.setOnClickListener(this);
        iv_photo_tj = (ImageView) mActivity.findViewById(R.id.iv_photo_tj);
        tv_scf_discern = (TextView) mActivity.findViewById(R.id.tv_scf_discern);
        iv_photo_tj.setSelected(false);
        tv_scf_discern.setTextColor(mActivity.getResources().getColor(R.color.color_FFFFFF));
        viewfinderView.setActivity(mActivity);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ll_back) {
        } else if (i == R.id.iv_openLamplight) {
            if (!isOpen) {//开灯
                CameraManager.get().enableFlash();
                isOpen = true;
//                    iv_openLamplight.setImageResource(R.mipmap.ic_launcher);
            } else {  // 关灯
                CameraManager.get().disableFlash();
                isOpen = false;
//                    iv_openLamplight.setImageResource(R.mipmap.ic_launcher);
            }

            //选择图片
        } else if (i == R.id.iv_photo) {//                LPToastUtil.showToast(QrCodeActivity.this, "开发中");
            Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
            innerIntent.setType("image/*");
            Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
            startActivityForResult(wrapperIntent, REQUEST_CODE);

        } else if (i == R.id.iv_photo_tj) {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent1, 105);
        } else if (i == R.id.ll_scf_scan) {
            //扫一扫
            iv_scf_scan.setSelected(true);
            iv_photo_tj.setSelected(false);
            tv_scf_scan.setTextColor(mActivity.getResources().getColor(R.color.color_EF7726));
            tv_scf_discern.setTextColor(mActivity.getResources().getColor(R.color.color_FFFFFF));

        } else if (i == R.id.ll_scf_descern) {
            //文字识别
            iv_photo_tj.setSelected(true);
            iv_scf_scan.setSelected(false);
            tv_scf_discern.setTextColor(mActivity.getResources().getColor(R.color.color_EF7726));
            tv_scf_scan.setTextColor(mActivity.getResources().getColor(R.color.color_FFFFFF));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        CameraManager.init(mActivity.getApplication());
        LKLogUtil.e("是否显示title" + isShowTitle);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(mActivity);

        camear = LKPermissionUtil.getInstance().requestCamera(mActivity);
        if (isOpen) {
            CameraManager.get().disableFlash();
            isOpen = false;
//            iv_openLamplight.setImageResource(R.mipmap.ic_launcher);
        }
        SurfaceView surfaceView = (SurfaceView) mActivity.findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        playBeep = true;
        AudioManager audioService = (AudioManager) mActivity.getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;


    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onStop() {
        inactivityTimer.shutdown();
        sm.unregisterListener(listener);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        //获取系统服务
        sm = (SensorManager) mActivity.getSystemService(SENSOR_SERVICE);
        //光线传感器 参数指定获取什么类型的传感器，也可以指定获取所有传感器
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        listener = new MyLightSensorListener();
        //注册一个监听器
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);//第三参数代表采样频率，频率越高，精度越高，越费电
    }


    @Override
    protected void getData(Message msg) {
    }

    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        LKLogUtil.e("result==bbbbbb" + resultString);
        if (resultString.equals("")) {
            LKToastUtil.showToastShort("扫描失败");
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", resultString);
            mActivity.setResult(RESULT_OK, resultIntent);


            Message m = mHandler.obtainMessage();
            m.what = PARSE_BARCODE_SUC;
            m.obj = resultString;
            mHandler.sendMessage(m);
        }
//        QrCodeActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureFragmentHandler(mActivity, this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            mActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) mActivity.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
//        try {
//            Hashtable<DecodeHintType, String> hints = new Hashtable<>();
//            hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true; // 先获取原大小
//             scanBitmap = BitmapFactory.decodeFile(path, options);
//            options.inJustDecodeBounds = false; // 获取新的大小
//            int sampleSize = (int) (options.outHeight / (float) 200);
//            if (sampleSize <= 0)
//                sampleSize = 1;
//            options.inSampleSize = sampleSize;
//            scanBitmap = BitmapFactory.decodeFile(path, options);
//            RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
//            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//            QRCodeReader reader = new QRCodeReader();
//            return reader.decode(bitmap1, hints);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;

        MultiFormatReader multiFormatReader = new MultiFormatReader();

        // 解码的参数
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>(2);
        // 可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<>();
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<>();

            // 这里设置可扫描的类型，我这里选择了都支持
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        // 设置继续的字符编码格式为UTF8
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

        // 设置解析配置参数
        multiFormatReader.setHints(hints);

        // 开始对图像资源解码
        Result rawResult = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true; // 先获取原大小
            Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
//            options.inJustDecodeBounds = false; // 获取新的大小
//            int sampleSize = (int) (options.outHeight / (float) 200);
//            if (sampleSize <= 0)
//                sampleSize = 1;
//            options.inSampleSize = sampleSize;
//            scanBitmap = BitmapFactory.decodeFile(path, options);
            rawResult = multiFormatReader
                    .decodeWithState(new BinaryBitmap(new HybridBinarizer(
                            new BitmapLuminanceSource(scanBitmap))));
//            rawResult = multiFormatReader.decode(scanBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawResult;
    }


    /**
     * 跳转到上一个页面
     *
     * @param resultString
     */
    private void onResultHandler(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            LKToastUtil.showToastShort("扫描失败");
            return;
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", resultString);
//        LKToastUtil.showToastShort(resultString);
        mActivity.setResult(RESULT_OK, resultIntent);
    }

    @Override
    protected void dataUpdating(Message message) {
        super.dataUpdating(message);
//        mProgress.dismiss();
        switch (message.what) {
            case PARSE_BARCODE_SUC:
//                onResultHandler((String) message.obj, scanBitmap);
                onResultHandler((String) message.obj);
                goToAddFriend((String) message.obj);
                LKLogUtil.e("扫描结果：" + (String) message.obj);
                break;
            case PARSE_BARCODE_FAIL:
//                LKToastUtil.showToastShort((String) message.obj);
                LKLogUtil.e("扫描结果PARSE_BARCODE_FAIL：" + (String) message.obj);
                break;
        }

    }

    private void goToAddFriend(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            LKToastUtil.showToastShort("扫描失败");
            return;
        }
        //发送广播，现在直接界面跳转
//        Intent intent = new Intent(JNIBroadCastKey.SCAN_TWO_CODE_SUCCESS);
//        intent.putExtra("Scan_result", resultString);
//        mActivity.sendBroadcast(intent);

        Intent intent = Small.getIntentOfUri(BCBundleJsonKey.Social_FriendDetailsActivity, mActivity);
        if (intent != null) {
            if (!TextUtils.isEmpty(resultString)) {
                UserInfoQrCodeContentObj userInfoQrCodeContentObj = UserInfoManageUtil.disposeUserInfoQrCodeContent(resultString);
                if (userInfoQrCodeContentObj != null && !TextUtils.isEmpty(userInfoQrCodeContentObj.getUserId())) {
                    intent.putExtra("GET_FRIEND_INFO_BY_PHONE_USERID", userInfoQrCodeContentObj.getUserId());
                    startActivity(intent);
                }
            }
        }
//        mActivity.finish();
    }


    /**
     * 将压缩后的bitmap保存为文件，以免扫描本地图片时内存溢出
     *
     * @param bitmap1
     */
    private void saveImage(Bitmap bitmap1) {
        File file = new File(BCFileBasePath.PATH_COPY_FILE);
        if (!file.exists()) {
            file.mkdirs();
        }
        File f1 = new File(BCFileBasePath.PATH_COPY_FILE + "/" + "copy.png");
        if (!f1.exists()) {
            try {
                f1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fot = null;
        try {
            fot = new FileOutputStream(f1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, fot);
        try {
            fot.flush();
            fot.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 压缩图片
     *
     * @param path
     * @return
     */
    public Bitmap getUsableImage(String path) {
        if (path != null && !path.isEmpty()) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);

            WindowManager manager = (WindowManager) mActivity.getSystemService(WINDOW_SERVICE);
            @SuppressWarnings("deprecation")
            int scaleX = (int) (opts.outWidth
                    / manager.getDefaultDisplay().getWidth() * 1.5);
            @SuppressWarnings("deprecation")
            int scaleY = (int) (opts.outHeight
                    / manager.getDefaultDisplay().getHeight() * 1.5);
            int scale = scaleX > scaleY ? scaleX : scaleY;
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scale > 1 ? scale : 1;
            return BitmapFactory.decodeFile(path, opts);
        } else {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    try {
                        Uri uri = data.getData();

                        if (!TextUtils.isEmpty(uri.getAuthority())) {
                            Cursor cursor = mActivity.getContentResolver().query(uri,
                                    new String[]{MediaStore.Images.Media.DATA},
                                    null, null, null);
                            if (null == cursor) {
                                LKToastUtil.showToastShort("图片没找到");
                                return;
                            }
                            cursor.moveToFirst();
                            photo_path = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Images.Media.DATA));
                            cursor.close();
                        } else {
                            photo_path = data.getData().getPath();
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                saveImage(getUsableImage(photo_path));
                                Result result = scanningImage(BCFileBasePath.PATH_COPY_FILE + "/" + "copy.png");
                                if (result != null) {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_SUC;
                                    m.obj = result.getText();
                                    mHandler.sendMessage(m);
                                } else {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_FAIL;
                                    m.obj = "扫描失败";
                                    mHandler.sendMessage(m);
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        LKToastUtil.showToastShort("解析错误");
                    }
                    break;
                case 105:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 光感监听事件
     */
    private class MyLightSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //不同类型的传感器，values的意义是不一样的 这里指的是光线的强弱
            float light = event.values[0];
//            LKLogUtil.e("光线强度====" + light);
            if (light < 50) {
                viewfinderView.setFlashBitmap(true);
            } else {
                viewfinderView.setFlashBitmap(false);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }


}
