package tech.yunjing.biconlife.libbaselib.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import tech.yunjing.biconlife.liblkclass.common.util.LKDialogUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKToastUtil;

/**
 * 图片背景切换动画帮助类
 *
 * Created by jameson on 9/3/16.
 */
public class ViewSwitchUtils {

    /** 转换时长，毫秒*/
    private static int transitionTime = 300;

    public static void startSwitchBackgroundAnim(ImageView view, Bitmap bitmap) {
        Drawable oldDrawable = view.getDrawable();
        Drawable oldBitmapDrawable ;
        TransitionDrawable oldTransitionDrawable = null;
        if (oldDrawable instanceof TransitionDrawable) {
            oldTransitionDrawable = (TransitionDrawable) oldDrawable;
            oldBitmapDrawable = oldTransitionDrawable.findDrawableByLayerId(oldTransitionDrawable.getId(1));
        } else if (oldDrawable instanceof BitmapDrawable) {
            oldBitmapDrawable = oldDrawable;
        } else {
            oldBitmapDrawable = new ColorDrawable(0xffc2c2c2);
        }

        if (oldTransitionDrawable == null) {
            oldTransitionDrawable = new TransitionDrawable(new Drawable[]{oldBitmapDrawable, new BitmapDrawable(bitmap)});
            oldTransitionDrawable.setId(0, 0);
            oldTransitionDrawable.setId(1, 1);
            oldTransitionDrawable.setCrossFadeEnabled(false);
            view.setImageDrawable(oldTransitionDrawable);
        } else {
            oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(0), oldBitmapDrawable);
            oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(1), new BitmapDrawable(bitmap));
        }
        oldTransitionDrawable.startTransition(transitionTime);
    }
}
