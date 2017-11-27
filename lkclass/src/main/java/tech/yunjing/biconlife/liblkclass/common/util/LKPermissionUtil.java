package tech.yunjing.biconlife.liblkclass.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * 权限处理
 * Created by nanPengFei on 2016/12/21 14:11.
 */
public class LKPermissionUtil {
    private static LKPermissionUtil mInstance;

    private LKPermissionUtil() {
    }

    public static LKPermissionUtil getInstance() {
        if (null == mInstance) {
            synchronized (LKPermissionUtil.class) {
                if (null == mInstance) {
                    mInstance = new LKPermissionUtil();
                }
            }
        }
        return mInstance;
    }


    /**
     * 判断是否拥有某种权限,第一次没有则弹窗询问,用户拒绝后，
     * 请到对应的Activity中重写onRequestPermissionsResult进行判断requestCode值进行相应提示
     *
     * @param activity
     * @param permission
     * @param requestCode
     * @return
     */
    private boolean requestPermission(Activity activity, String[] permission, int requestCode) {
        //默认没有权限
        boolean isHasPermission = false;
        if (ContextCompat.checkSelfPermission(activity, permission[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, permission, requestCode);
        } else {
            isHasPermission = true;
        }
        return isHasPermission;
    }

    /**
     * 相机权限申请
     */
    public static final int REQUEST_PERMISSION_CAMERA = 501;
    /**
     * SD卡权限申请
     */
    public static final int REQUEST_PERMISSION_SD = 502;
    /**
     * 录音权限申请
     */
    public static final int REQUEST_PERMISSION_AUDIO = 503;
    /**
     * 定位权限申请
     */
    public static final int REQUEST_PERMISSION_LOCATION = 504;
    /**
     * 挂载SD卡权限申请
     */
    public static final int REQUEST_PERMISSION_FILESYSTEMS = 505;
    /**
     * 获取读取手机通讯录
     */
    public static final int REQUEST_PERMISSION_CONTACTS = 506;
    /**
     * 一次申请多个全校所用
     */
    public static final int EXTERNAL_STORAGE_REQ_CODE = 100;
    /**
     * 状态
     */
    public static final int REQUEST_PERMISSION_STATE = 507;
    /**
     * SD卡和
     */
    public static final int REQUEST_PERMISSION_CSD_STATE = 508;


    /**
     * 关于权限申请
     * 一次申请多个权限所用 ---视频所用
     */
    public static final int EXTERNAL_STORAGE_REQ_CODE_VIDEO = 101;


    /**
     * 相机权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestCamera(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.CAMERA
                }, REQUEST_PERMISSION_CAMERA);
    }

    /**
     * SD卡权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestSD(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_PERMISSION_SD);
    }

    /**
     * 录音权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestAudio(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.RECORD_AUDIO
                }, REQUEST_PERMISSION_AUDIO);
    }

    /**
     * 定位权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestLocation(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, REQUEST_PERMISSION_LOCATION);
    }

    /**
     * 联系人权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestContacts(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                }, REQUEST_PERMISSION_CONTACTS);
    }

    /**
     * 挂载SD卡权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestFilesystems(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
                }, REQUEST_PERMISSION_FILESYSTEMS);
    }

    /**
     * 挂载SD卡权限申请
     *
     * @param activity
     * @return
     */
    public boolean requestStatesystems(Activity activity) {
        return requestPermission(activity,
                new String[]{Manifest.permission.READ_PHONE_STATE
                }, REQUEST_PERMISSION_STATE);
    }

    /**
     * 申请提供的所有权限
     *
     * @param activity
     */
    public void requestAll(Activity activity) {
        requestCamera(activity);
        requestSD(activity);
        requestAudio(activity);
        requestLocation(activity);
        requestFilesystems(activity);
    }


    /**
     * 一次申请多个权限
     */
    public boolean requestMorePermission(Activity activity) {
        return requestPermission(activity, new String[]
                {Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_REQ_CODE_VIDEO);
    }

    /**
     * 一次申请拍照SD卡权限
     */
    public boolean requestCarmaSDPermission(Activity activity) {
        return requestPermission(activity, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISSION_CSD_STATE);

    }


}