package tech.yunjing.biconlife.libbaselib.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import tech.yunjing.biconlife.app.mine.util.ParseFilePath;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.jniplugin.bean.personal.UserInfoObj;
import tech.yunjing.biconlife.libbaselib.global.BCBroadCastKey;
import tech.yunjing.biconlife.jniplugin.global.BCFileBasePath;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.widget.LKCircleImageView;


/**
 * @author chaohaipeng 头像处理
 */
public class ChooserHeadImg {
    //作用: 用户选择头像 图片请求码
    public static final int USERINFO_IAMGELIB_REQUEST = 108;
    // 作用: 用户拍照请求码
    public static final int USERINFO_CAMERA_REQUEST = 109;
    // 作用: 剪贴图片页面请求码
    public static final int USERINFO_CLIP_REQUEST = 110;
    private Activity mActivity;
    private File mFileUri;

    public ChooserHeadImg(Activity activity) {
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
            mActivity.startActivityForResult(intent, ChooserHeadImg.USERINFO_IAMGELIB_REQUEST);
        }
    }

    /**
     * 直接拍照做头像
     */
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getFileHeadPhoto()));
        mActivity.startActivityForResult(intent, ChooserHeadImg.USERINFO_CAMERA_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, LKCircleImageView headimg) {
        if (requestCode == ChooserHeadImg.USERINFO_IAMGELIB_REQUEST) {//用户选择头像 图片请求码
            Uri originalUri = data.getData();
            Uri uri_ = ParseFilePath.getUri(mActivity, originalUri);
            startPhotoZoom(uri_, ChooserHeadImg.USERINFO_CLIP_REQUEST, mActivity);
        } else if (requestCode == ChooserHeadImg.USERINFO_CAMERA_REQUEST) {  // 用户拍照请求码
            LKLogUtil.e("创建文件=====成功"+getFileHeadPhoto());
            Uri uri = Uri.fromFile(getFileHeadPhoto());
            Uri _uri = ParseFilePath.getUri(mActivity, uri);
            startPhotoZoom(_uri, ChooserHeadImg.USERINFO_CLIP_REQUEST, mActivity);
        } else if (requestCode == ChooserHeadImg.USERINFO_CLIP_REQUEST) {//剪贴图片页面请求码
            if (null != mFileUri) {
                Bitmap bm = BitmapFactory.decodeFile(mFileUri.getAbsolutePath());
                headimg.setImageBitmap(bm);// 设置图像
                //发送修改头像广播
                mActivity.sendBroadcast(new Intent(BCBroadCastKey.BROADCAST_IMGHEAD_UPSUCCESS));
            }
        }
    }


    /**
     * 头像裁剪
     *
     * @param uri
     * @param requestCode
     * @param context
     */
    public void startPhotoZoom(Uri uri, int requestCode, Activity context) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 93);
        intent.putExtra("outputY", 93);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        String user_id = UserInfoManageUtil.getUserId();
        if(TextUtils.isEmpty(user_id)){
            user_id = "yunjing";
        }
        File f1 = new File(BCFileBasePath.PATH_CACHE_IMG + "/" + user_id);
        if (!f1.exists()) {
            f1.mkdirs();
        }
//        File f1 = new File(BCFileBasePath.PATH_CACHE_IMG);
//        if (!f1.exists()) {
//            f1.mkdirs();
//        }
        mFileUri = new File(f1, BCFileBasePath.PATH_HEADIMG);
        Uri fileUri = Uri.fromFile(mFileUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("return-data", false); //裁剪后的数据不以bitmap的形式返回
        context.startActivityForResult(intent, requestCode);
    }

    public File getFileHeadPhoto() {
        File ss = new File(BCFileBasePath.PATH_CACHE_IMG);
        LKLogUtil.e("创建文件=====");
        if (!ss.isDirectory()) {
            ss.mkdirs();
        }
        UserInfoObj userInfoObj = UserInfoManageUtil.getUserInfo();
        String userName = "";
        if(userInfoObj!=null){
            userName = userInfoObj.getName();
        }
        File file = new File(ss, userName+".jpg");
        return file;
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
