package tech.yunjing.biconlife.jniplugin.global;

import android.os.Environment;

/**
 * 文件基础路径
 * Created by CQ on 2017/6/23.
 */

public class BCFileBasePath {
    public static final String PATHBASE = Environment.getExternalStorageDirectory().getPath();//SD卡根目录
    public static final String PATH = PATHBASE + "/Android/data/tech.yunjing.bclife";//SD卡根目录
    public static final String PATH_ROOT = PATH + "/bcLife/";
    public static String PATH_HEADIMG = "headimg.jpg";//头像的名字
    public static String PATH_CACHE_IMG = PATH_ROOT + "cacheImg";//图片保存路径
    public static String PATH_COPY_FILE = PATH_ROOT + "Photo";//本地二维码图片
    public static String PATH_DOWNLOADER = PATH_ROOT + "downloader";//本地图片下载路径


}
