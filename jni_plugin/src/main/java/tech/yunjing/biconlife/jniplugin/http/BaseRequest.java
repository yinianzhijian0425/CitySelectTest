package tech.yunjing.biconlife.jniplugin.http;

import android.os.Handler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;
import tech.yunjing.biconlife.jniplugin.bean.response.BaseResponseObj;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.http.lkhttp.LKGetRequest;
import tech.yunjing.biconlife.liblkclass.http.lkhttp.LKPostRequest;

/**
 * Post请求基类
 * Created by sun.li on 2017/7/10.
 */

public class BaseRequest {

    /**
     * get请求
     */
    public static void getRequest(final Handler handler, String path, BaseRequestObj request, BaseResponseObj response, boolean isShowLoding) {
        try {
            String params = objToUriString(request);
            String uri = path + params;
            LKLogUtil.e("result==" + params.toString());
            LKGetRequest.getData(handler, uri, response.getClass(), isShowLoding);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求
     */
    public static void getValueRequest(final Handler handler, String path, BaseRequestObj request, BaseResponseObj response, boolean isShowLoding) {
        try {
            String params = objToUriString(request);
            String uri = path + params;
            LKLogUtil.e("params:" + params.toString());
            LKGetRequest.getData(handler, uri, response.getClass(), isShowLoding);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Post请求
     */
    public static void postRequest(final Handler handler, String path, BaseRequestObj request, BaseResponseObj response, boolean isShowLoding) {
        try {
            TreeMap<String, Object> params = objToTree(request);
            LKLogUtil.e("params:" + params.toString());
            LKPostRequest.getData(handler, path, params, response.getClass(), isShowLoding);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象转换为get请求报文
     */
    private static String objToUriString(Object obj) throws IllegalArgumentException, IllegalAccessException {

        String getUriStr = "";
        Class clazz = obj.getClass();
        List<Class> clazzs = new ArrayList<Class>();

        do {
            clazzs.add(clazz);
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));

        for (Class iClazz : clazzs) {
            Field[] fields = iClazz.getDeclaredFields();
            for (Field field : fields) {
                Object objVal = null;
                field.setAccessible(true);
                objVal = field.get(obj);
                /*需要忽略基类中的默认字段*/
                if (!field.getName().contains("serialVersionUID") && !field.getName().contains("change")) {
                    if ("".equals(getUriStr)) {
                        getUriStr += "?" + field.getName() + "=" + objVal;
                    } else {
                        getUriStr += "&" + field.getName() + "=" + objVal;
                    }
                }
            }
        }
        return getUriStr;
    }

    /**
     * 对象转换为HashMap<String, Object>
     *
     * @param obj
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static HashMap<String, Object> objToHash(Object obj) throws IllegalArgumentException, IllegalAccessException {

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        Class clazz = obj.getClass();
        List<Class> clazzs = new ArrayList<Class>();

        do {
            clazzs.add(clazz);
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));

        for (Class iClazz : clazzs) {
            Field[] fields = iClazz.getDeclaredFields();
            for (Field field : fields) {
                Object objVal = null;
                field.setAccessible(true);
                objVal = field.get(obj);
                /*需要忽略基类中的默认字段*/
                if (!field.getName().contains("serialVersionUID") && !field.getName().contains("change")) {
                    hashMap.put(field.getName(), objVal);
                }
            }
        }

        return hashMap;
    }

    /**
     * 对象转换为HashMap<String, Object>
     *
     * @param obj
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static TreeMap<String, Object> objToTree(Object obj) throws IllegalArgumentException, IllegalAccessException {

        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        Class clazz = obj.getClass();
        List<Class> clazzs = new ArrayList<Class>();

        do {
            clazzs.add(clazz);
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));

        for (Class iClazz : clazzs) {
            Field[] fields = iClazz.getDeclaredFields();
            for (Field field : fields) {
                Object objVal = null;
                field.setAccessible(true);
                objVal = field.get(obj);
                /*需要忽略基类中的默认字段*/
                if (!field.getName().contains("serialVersionUID") && !field.getName().contains("change")) {
                    treeMap.put(field.getName(), objVal);
                }
            }
        }

        return treeMap;
    }
}
