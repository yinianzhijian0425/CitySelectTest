package tech.yunjing.biconlife.libbaselib.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tech.yunjing.biconlife.app.mine.util.ParseFilePath;
import tech.yunjing.biconlife.libbaselib.global.BCBroadCastKey;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;


/**
 * 获取系统图片工具类
 *
 * @author chaohaipeng 头像处理
 */
public class ChooserSystemImg {
    //作用: 用户选择头像 图片请求码
    public static final int USERINFO_IAMGELIB_REQUEST = 108;
    // 作用: 用户拍照请求码
    public static final int USERINFO_CAMERA_REQUEST = 109;
    // 作用: 剪贴图片页面请求码
    public static final int USERINFO_CLIP_REQUEST = 110;
    private Activity mActivity;
    private File mFileUri;

    public static final String ImagePathLogo = "ImagePathLogo";

    public ChooserSystemImg(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 从图库选择头像
     */
    public void imageLibs() {
        Boolean isSdcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if (isSdcardExist) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            mActivity.startActivityForResult(intent, ChooserSystemImg.USERINFO_IAMGELIB_REQUEST);
        }
    }

    private Uri fileUri;

    /**
     * 直接拍照做头像
     */
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            mActivity.startActivityForResult(intent, ChooserSystemImg.USERINFO_CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == ChooserSystemImg.USERINFO_IAMGELIB_REQUEST) {//用户选择相册图片请求码
            Uri originalUri = data.getData();
            if (originalUri != null) {
                Uri uri_ = ParseFilePath.getUri(mActivity, originalUri);
                String imagePath = getRealFilePath(mActivity, uri_);
                Intent albumIntent = new Intent(BCBroadCastKey.BROADCAST_IMG_ALBUM_UPSUCCESS);
                albumIntent.putExtra(ImagePathLogo, imagePath);
                LKLogUtil.e("相册选择图片URL：" + imagePath);
                mActivity.sendBroadcast(albumIntent);
            }
        } else if (requestCode == ChooserSystemImg.USERINFO_CAMERA_REQUEST) {  // 用户拍照请求码
            LKLogUtil.e("创建文件=====成功" + getOutputMediaFileUri(MEDIA_TYPE_IMAGE));
            Uri uri = fileUri;
            if (uri != null) {
                Uri _uri = ParseFilePath.getUri(mActivity, uri);
//            startPhotoZoom(_uri, ChooserSystemImg.USERINFO_CLIP_REQUEST, mActivity);
                String imagePath = getRealFilePath(mActivity, _uri);
                Intent shootingIntent = new Intent(BCBroadCastKey.BROADCAST_IMG_SHOOTING_UPSUCCESS);
                shootingIntent.putExtra(ImagePathLogo, imagePath);
                LKLogUtil.e("拍照图片URL：" + imagePath);
                mActivity.sendBroadcast(shootingIntent);
                fileUri = null;
            }
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");

            LKLogUtil.e("LOG_TAG========Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        } catch (Exception e) {
            e.printStackTrace();
            LKLogUtil.e("LOG_TAG=========Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                LKLogUtil.e("LOG_TAG======failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    public static void saveMyBitmap(String bitPathName, Bitmap mBitmap) {
        try {
            File f = new File(bitPathName);
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
