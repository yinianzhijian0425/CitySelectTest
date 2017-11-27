package tech.yunjing.biconlife.libbaselib.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Hashtable;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.base.ScanFragment;
import tech.yunjing.biconlife.libbaselib.view.scan.CameraManager;
import tech.yunjing.biconlife.libbaselib.view.scan.PlanarYUVLuminanceSource;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * Created by CHP on 2017/7/10.
 */

public class DecodeMainHandler extends Handler {

    private static final String TAG = DecodeMainHandler.class.getSimpleName();

    private final ScanFragment fragment;
    private final MultiFormatReader multiFormatReader;

    DecodeMainHandler(ScanFragment fragment, Hashtable<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.fragment = fragment;
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.decode) {//Log.d(TAG, "Got decode message");
            decode((byte[]) message.obj, message.arg1, message.arg2);

        } else if (message.what == R.id.quit) {
            Looper.myLooper().quit();

        }
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
     * reuse the same reader objects from one decode to the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(byte[] data, int width, int height) {
        if(data == null){
            return;
        }
        long start = System.currentTimeMillis();
        Result rawResult = null;

        //modify here
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
        }
        int tmp = width; // Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;

        PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, width, height);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            rawResult = multiFormatReader.decodeWithState(bitmap);
        } catch (ReaderException re) {
            // continue
        } finally {
            multiFormatReader.reset();
        }

        if (rawResult != null) {
            long end = System.currentTimeMillis();
            LKLogUtil.e("Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
            Message message = Message.obtain(fragment.getHandler(), R.id.decode_succeeded, rawResult);
            Bundle bundle = new Bundle();
            bundle.putParcelable(DecodeMainThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
            message.setData(bundle);
            //Log.d(TAG, "Sending decode succeeded message...");
            message.sendToTarget();
        } else {
            Message message = Message.obtain(fragment.getHandler(), R.id.decode_failed);
            message.sendToTarget();
        }
    }

}
