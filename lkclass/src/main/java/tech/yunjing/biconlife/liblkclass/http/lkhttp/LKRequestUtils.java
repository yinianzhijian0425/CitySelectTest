package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.http.xhttp.RequestParams;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 网络请求基础工具集(项目中不直接使用，主要是为了类库中Get、Post请求工具的封装提供方便)
 * Created by nanPengFei on 2016/9/1 10:18.
 */
public class LKRequestUtils {


    /**
     * Get方式请求网络数据
     *
     * @param params            参数(包含地址)
     * @param lkRequestCallBack 回调
     */
    public static Callback.Cancelable get(RequestParams params, LKRequestCallBack lkRequestCallBack) {
        return LK.http().get(params, lkRequestCallBack);

    }

    /**
     * Get方式请求网络数据
     *
     * @param url
     * @param requestParams
     * @param lkRequestCallBack
     * @return
     */
    public static Callback.Cancelable get(String url, RequestParams requestParams, LKRequestCallBack lkRequestCallBack) {

        return LK.http().get(requestParams, lkRequestCallBack);

    }

    /**
     * post带参数请求
     * requestParams.addBodyParameter(key, value,"multipart/form-data");
     *
     * @param url
     * @param params            TreeMap
     * @param lkRequestCallBack
     */
    public static Callback.Cancelable post(String url, TreeMap<String, Object> params, LKRequestCallBack lkRequestCallBack) {
        RequestParams requestParams = new RequestParams(url);
        if (null != params && params.size() > 0) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if ("lktime".equals(key)) {
                    //超时时间
                    int lktime = (int) value;
                    if (0 == lktime) {
                        lktime = 60000;
                    }
                    requestParams.setConnectTimeout(lktime);
                } else {
                    if (value instanceof String) {
                        requestParams.addBodyParameter(key, (String) value);
                    } else if (value instanceof File) {
                        //使用multipart表单上传文件
                        requestParams.setMultipart(true);
                        requestParams.addBodyParameter(key, value, null);
                    } else {
                        requestParams.addParameter(key, value);
                    }
                }
            }
        }
        return LK.http().post(requestParams, lkRequestCallBack);
    }


    /**
     * post带参数请求
     * requestParams.addBodyParameter(key, value,"multipart/form-data");
     *
     * @param url
     * @param params
     * @param lkRequestCallBack
     */
    public static Callback.Cancelable post(String url, HashMap<String, Object> params, LKRequestCallBack lkRequestCallBack) {
        RequestParams requestParams = new RequestParams(url);
        if (null != params && params.size() > 0) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if ("lktime".equals(key)) {
                    //超时时间
                    int lktime = (int) value;
                    if (0 == lktime) {
                        lktime = 60000;
                    }
                    requestParams.setConnectTimeout(lktime);
                } else {
                    if (value instanceof String) {
                        requestParams.addBodyParameter(key, (String) value);
                    } else if (value instanceof File) {
                        //使用multipart表单上传文件
                        requestParams.setMultipart(true);
                        requestParams.addBodyParameter(key, value, null);
                    } else {
                        requestParams.addParameter(key, value);
                    }
                }
            }
        }
        return LK.http().post(requestParams, lkRequestCallBack);
    }

    /**
     * post不带参数请求
     *
     * @param url
     * @param lkRequestCallBack
     */
    public static Callback.Cancelable post(String url, LKRequestCallBack lkRequestCallBack) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setConnectTimeout(60000);
        return LK.http().post(requestParams, lkRequestCallBack);
    }
}
