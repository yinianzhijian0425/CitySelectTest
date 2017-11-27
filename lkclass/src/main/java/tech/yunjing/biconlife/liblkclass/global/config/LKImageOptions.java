package tech.yunjing.biconlife.liblkclass.global.config;

import android.widget.ImageView;

import tech.yunjing.biconlife.liblkclass.bitmap.ImageOptions;
import tech.yunjing.biconlife.liblkclass.common.util.DensityUtil;

/**
 * 图片参数配置集(未结束)
 * Created by nanPengFei on 2016/9/1 14:45.
 */
public class LKImageOptions {

    /**
     * 指定宽高和默认图
     *
     * @param defIcon
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public static ImageOptions getOptions(int defIcon, int imageWidth, int imageHeight) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //图片大小
                .setSize(imageWidth, imageHeight)
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /**
     * 指定默认图
     *
     * @param defIcon
     * @return
     */
    public static ImageOptions getOptions(int defIcon) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .setAutoRotate(true)
                .build();
        return imageOptions;
    }

    /**
     * 指定默认图和圆角
     *
     * @param defIcon
     * @param radius
     * @return
     */
    public static ImageOptions getOptions(int defIcon, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //ImageView圆角半径
                .setRadius(DensityUtil.dip2px(radius))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /**
     * 指定默认图和圆角修改type
     *
     * @param defIcon
     * @param radius
     * @return
     */
    public static ImageOptions getOptionsType(int defIcon, int radius, ImageView.ScaleType scaleType) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //ImageView圆角半径
                .setRadius(DensityUtil.dip2px(radius))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                .setImageScaleType(scaleType)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /**
     * 指定默认图和圆角修改type
     *
     * @param defIcon
     * @param radius
     * @return
     */
    public static ImageOptions getOptionsType(int defIcon, int radius, ImageView.ScaleType scaleType, boolean isCrop) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //ImageView圆角半径
                .setRadius(DensityUtil.dip2px(radius))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(isCrop)
                .setImageScaleType(scaleType)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /**
     * 指定默认图、圆形
     *
     * @param defIcon
     * @return
     */
    public static ImageOptions getCircleOptions(int defIcon) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(defIcon)
                .setFailureDrawableId(defIcon)
                //设置使用缓存
                .setUseMemCache(true)
                //设置支持gif
                .setIgnoreGif(true)
                //设置显示圆形图片
                .setCircular(true)
                .setSquare(true)
                .build();
        return imageOptions;
    }

    /**
     * 设置图片充满,展示大图使用
     *
     * @return
     */
    public static ImageOptions getFITXYOptions(int defIcon) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /**
     * 指定默认图和圆角,并且图片充满
     *
     * @param defIcon
     * @param radius
     * @return
     */
    public static ImageOptions getFITXYOptions(int defIcon, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //ImageView圆角半径
                .setRadius(DensityUtil.dip2px(radius))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /**
     * 只设置图片圆角
     *
     * @param radius
     * @return
     */
    public static ImageOptions getFITXYRadius(int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //ImageView圆角半径
                .setRadius(DensityUtil.dip2px(radius))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        return imageOptions;
    }

    /**
     * 只设置图片圆角
     *
     * @param radius
     * @return
     */
    public static ImageOptions getRadius(int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                //ImageView圆角半径
                .setRadius(DensityUtil.dip2px(radius))
                .build();
        return imageOptions;
    }

    /**
     * 设置图片充满,展示大图使用
     *
     * @return
     */
    public static ImageOptions getFITXYOptions() {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        return imageOptions;
    }

    /***
     * 设置图片展示效果
     * @param scaleType
     * @return
     */
    public static ImageOptions getOptionsType(ImageView.ScaleType scaleType, int defIcon) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setImageScaleType(scaleType)
                //加载中默认显示图片
                .setLoadingDrawableId(defIcon)
                //加载失败后默认显示图片
                .setFailureDrawableId(defIcon)
                .build();
        return imageOptions;
    }

    /***
     * 设置图片展示效果
     * @param scaleType
     * @return
     */
    public static ImageOptions getOptionsType(ImageView.ScaleType scaleType) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setImageScaleType(scaleType)
                .build();
        return imageOptions;
    }

}
