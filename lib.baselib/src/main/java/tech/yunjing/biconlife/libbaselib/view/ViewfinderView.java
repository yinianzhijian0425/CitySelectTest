package tech.yunjing.biconlife.libbaselib.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.zxing.ResultPoint;

import net.wequick.small.Small;

import java.util.Collection;
import java.util.HashSet;

import tech.yunjing.biconlife.libbaselib.R;
import tech.yunjing.biconlife.libbaselib.global.BCBundleJsonKey;
import tech.yunjing.biconlife.libbaselib.view.scan.CameraManager;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


public final class ViewfinderView extends View {
    private static final String TAG = "nan";
    /**
     * 刷新界面的时间
     */
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;

    /**
     * 四个绿色边角对应的长度
     */
    private int ScreenRate;

    /**
     * 四个绿色边角对应的宽度
     */
    private static final int CORNER_WIDTH = 3;
    /**
     * 扫描框中的中间线的宽度
     */
    private static final int MIDDLE_LINE_WIDTH = 3;

    /**
     * 扫描框中的中间线的与扫描框左右的间隙
     */
    private static final int MIDDLE_LINE_PADDING = 5;

    /**
     * 中间那条线每次刷新移动的距离
     */
    private static final int SPEEN_DISTANCE = 5;

    /**
     * 手机的屏幕密度
     */
    private static float density;
    /**
     * 字体大小
     */
    private static final int TEXT_SIZE = 12;
    /**
     * 字体大小
     */
    private static final int TEXT_SIZE_15 = 15;
    /**
     * 字体距离扫描框下面的距离
     */
    private static final int TEXT_PADDING_TOP = 15;

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 中间滑动线的最顶端位置
     */
    private int slideTop;

    /**
     * 中间滑动线的最底端位置
     */
    private int slideBottom;

    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;

    private final int resultPointColor;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private boolean isOpen;
    boolean isFirst;
    private final Bitmap bitmap;
    private final int lineWidth;
    private int flashImageWidth;
    private Bitmap flashOpen;
    private double bottom;
    private boolean isVisibleFalsh;

    private Activity mActivity;
    private Rect frame;
    private int width;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = context.getResources().getDisplayMetrics().density;
        //将像素转换成dp
        ScreenRate = (int) (20 * density);

        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);

        resultPointColor = resources.getColor(R.color.color_EF7726);
        possibleResultPoints = new HashSet<>(5);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_scan_line);
        lineWidth = bitmap.getWidth();
        flashOpen = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_flash_defaut);
        flashImageWidth = flashOpen.getWidth();


    }

    @Override
    public void onDraw(Canvas canvas) {
        //中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
//        Rect frame = CameraManager.get().getFramingRect();
        frame = CameraManager.get().getFramingRect();
        if (frame == null) {
            return;
        }

        //初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }

        //��ȡ��Ļ�Ŀ�͸�
//        int width = canvas.getWidth();
        width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(resultBitmap != null ? resultColor : maskColor);

        //画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        //扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
//        Resources resources = getResources();
//       int color= resources.getColor(R.color.color_FFFFFF);
//        paint.setColor(color);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {
            paint.setStrokeWidth(1);
            paint.setColor(getResources().getColor(R.color.color_F8F8F8));
            canvas.drawLine(frame.left + ScreenRate, frame.top + 1, frame.right - ScreenRate, frame.top + 1, paint);
            canvas.drawLine(frame.left + 1, frame.top + ScreenRate, frame.left + 1, frame.bottom - ScreenRate, paint);
            canvas.drawLine(frame.left + ScreenRate, (float) (frame.bottom - 1.5), frame.right - ScreenRate, (float) (frame.bottom - 1.5), paint);
            canvas.drawLine((float) (frame.right - 1.5), frame.top + ScreenRate, (float) (frame.right - 1.5), frame.bottom - ScreenRate, paint);


//
//            canvas.drawRect(frame.left, frame.top, frame.right,
//                    frame.bottom, paint);


            //画扫描框边上的角，总共8个部分
            paint.setColor(getResources().getColor(R.color.color_EF7726));

//			paint.setColor(Color.GREEN);
            canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top
                    + ScreenRate, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top
                    + ScreenRate, paint);
            canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                    + ScreenRate, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - ScreenRate,
                    frame.left + CORNER_WIDTH, frame.bottom, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH,
                    frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate,
                    frame.right, frame.bottom, paint);
            //绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }
//            canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop - MIDDLE_LINE_WIDTH / 2, frame.right - MIDDLE_LINE_PADDING, slideTop + MIDDLE_LINE_WIDTH / 2, paint);

            canvas.drawBitmap(bitmap, (LKCommonUtil.getScreenWidth() - lineWidth) / 2, slideTop - MIDDLE_LINE_WIDTH / 2, paint);
            //画扫描框下面的字
            paint.setColor(Color.WHITE);
            paint.setTextSize(TEXT_SIZE * density);
//            paint.setAlpha(0x40);
//            paint.setTypeface(Typeface.create("System", Typeface.BOLD));
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getResources().getString(R.string.str_scan_text), width / 2, (float) (frame.bottom + (float) (TEXT_PADDING_TOP + 5) * density), paint);

            paint.setColor(getResources().getColor(R.color.color_EF7726));
            paint.setTextSize(TEXT_SIZE_15 * density);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getResources().getString(R.string.str_scan_text_my_code), width / 2, (float) (frame.bottom + (float) 2 * TEXT_PADDING_TOP * density + TEXT_SIZE * density), paint);

            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
//			if (currentPossible.isEmpty()) {
//				lastPossibleResultPoints = null;
//			} else {
//				possibleResultPoints = new HashSet<ResultPoint>(5);
//				lastPossibleResultPoints = currentPossible;
//				paint.setAlpha(OPAQUE);
//				paint.setColor(resultPointColor);
//				for (ResultPoint point : currentPossible) {
//					canvas.drawCircle(frame.left + point.getX(), frame.top
//							+ point.getY(), 6.0f, paint);
//				}
//			}
//			if (currentLast != null) {
//				paint.setAlpha(OPAQUE / 2);
//				paint.setColor(resultPointColor);
//				for (ResultPoint point : currentLast) {
//					canvas.drawCircle(frame.left + point.getX(), frame.top
//							+ point.getY(), 3.0f, paint);
//				}
//			}


            //只刷新扫描框的内容，其他地方不刷新
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);

        }
        if (flashOpen != null) {
            if (isVisibleFalsh) {
                paint.setColor(Color.WHITE);
                canvas.drawBitmap(flashOpen, (LKCommonUtil.getScreenWidth() - flashImageWidth) / 2, frame.bottom - LKCommonUtil.dip2px(60), paint);
                canvas.drawText("轻触点亮", (LKCommonUtil.getScreenWidth() - flashImageWidth) / 2 + LKCommonUtil.dip2px(10), frame.bottom - LKCommonUtil.dip2px(10), paint);
            } else {
                if (!isOpen) {
                    paint.setColor(Color.TRANSPARENT);
                    canvas.drawBitmap(flashOpen, (LKCommonUtil.getScreenWidth() - flashImageWidth) / 2, frame.bottom - LKCommonUtil.dip2px(60), paint);
                    canvas.drawText("轻触点亮", (LKCommonUtil.getScreenWidth() - flashImageWidth) / 2 + LKCommonUtil.dip2px(10), frame.bottom - LKCommonUtil.dip2px(10), paint);
                } else {

                }
            }
        }
        bottom = frame.bottom - LKCommonUtil.dip2px(60);
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }


    public void setFlashBitmap(boolean isVisible) {
        isVisibleFalsh = isVisible;
        invalidate();
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        //是否开启手电筒
        if (isVisibleFalsh) {
            if (x > (LKCommonUtil.getScreenWidth() - flashImageWidth) / 2 - LKCommonUtil.dip2px(20) && x < (LKCommonUtil.getScreenWidth() - flashImageWidth) / 2 + LKCommonUtil.dip2px(20)) {
                if (y > bottom - LKCommonUtil.dip2px(20) && y < bottom + LKCommonUtil.dip2px(50)) {
                    if (!isOpen) {//开灯
                        flashOpen = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_flash_open);
                        CameraManager.get().enableFlash();
                        isOpen = true;
                        flashImageWidth = flashOpen.getWidth();
                    } else {  // 关灯
                        flashOpen = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_flash_defaut);
                        CameraManager.get().disableFlash();
                        isOpen = false;
                        flashImageWidth = flashOpen.getWidth();
                    }
                    invalidate();
                }
            }
        }
        if (x > (width / 2) - LKCommonUtil.dip2px(40) && x < (width / 2) + LKCommonUtil.dip2px(40)) {
            if (y > (frame.bottom + (float) 2 * TEXT_PADDING_TOP * density + TEXT_SIZE * density) - (LKCommonUtil.dip2px(20)) && y < (frame.bottom + (float) 2 * TEXT_PADDING_TOP * density + TEXT_SIZE * density) + LKCommonUtil.dip2px(20)) {
//                mActivity.sendBroadcast(new Intent("GO_TO_MY_DIMENSIONAL_CODE"));
                LKLogUtil.e("点击了我的二维码");
                Small.openUri(BCBundleJsonKey.Social_MyTwoDimensionalCodeActivity, mActivity);
            }
        }
        return super.onTouchEvent(event);
    }
}
