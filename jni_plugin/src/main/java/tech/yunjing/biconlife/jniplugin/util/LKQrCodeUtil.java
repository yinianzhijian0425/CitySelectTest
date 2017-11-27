package tech.yunjing.biconlife.jniplugin.util;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;

/**
 * 二维码生成工具
 *
 * @author nanPengFei
 */
public class LKQrCodeUtil {
    private static Bitmap mBitmap;
    private static Bitmap mOverBp;
    private static int QR_WIDTH;
    private static int QR_HEIGHT;
    private static int IMAGE_HALFWIDTH;
    static int FOREGROUND_COLOR = 0xff000000;
    static int BACKGROUND_COLOR = 0xffffffff;

    /**
     * 对外提供的生成二维码功能
     *
     * @param context  context
     * @param content  内容
     * @param iconId   中央小图标ID
     * @param qrCodeDp 需要生成的二维码的宽高值，此处规范宽高相等
     * @return Bitmap
     */
    public static Bitmap getQrCode(Context context, String content, int iconId, float qrCodeDp) {

        QR_WIDTH = LKCommonUtil.dip2px(qrCodeDp);
        QR_HEIGHT = LKCommonUtil.dip2px(qrCodeDp);
        IMAGE_HALFWIDTH = LKCommonUtil.dip2px(qrCodeDp / 10);
        mOverBp = BitmapFactory.decodeResource(context.getResources(), iconId);
        try {
            Bitmap cretaeBitmap = cretaeBitmap(content, mOverBp);
            return cretaeBitmap;//将生成的二维码返回
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成二维码的私有方法
     *
     * @param content 内容
     * @param icon    中央图标的bitmap图
     * @return Bitmap
     * @throws WriterException
     */
    private static Bitmap cretaeBitmap(String content, Bitmap icon) throws WriterException {
        icon = zoomBitmap(icon, IMAGE_HALFWIDTH);
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    pixels[y * width + x] = icon.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = FOREGROUND_COLOR;
                    } else {
                        pixels[y * width + x] = BACKGROUND_COLOR;
                    }
                }
            }
        }
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return mBitmap;
    }

    /**
     * 对图片进行缩放的功能
     *
     * @param icon
     * @param height
     * @return
     */
    private static Bitmap zoomBitmap(Bitmap icon, int height) {
        // 缩放图片
        Matrix m = new Matrix();
        float sx = (float) 2 * height / icon.getWidth();
        float sy = (float) 2 * height / icon.getHeight();
        m.setScale(sx, sy);
        // 重新构造一个2h*2h的图片
        return Bitmap.createBitmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), m, false);
    }

    /**
     * 用字符串生成二维码(不带图标的二维码)
     */
    public static Bitmap Create2DCode(Context context, String str, float qrCodeDp) {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        QR_WIDTH = LKCommonUtil.dip2px(qrCodeDp);
        QR_HEIGHT = LKCommonUtil.dip2px(qrCodeDp);
        BitMatrix matrix = null;
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}