package tech.yunjing.biconlife.liblkclass.http.xhttp.request;

import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.http.xhttp.RequestParams;
import tech.yunjing.biconlife.liblkclass.http.xhttp.app.RequestTracker;

/**
 * Created by wyouflf on 15/11/4.
 * Uri请求创建工厂
 */
public final class UriRequestFactory {

    private static Class<? extends RequestTracker> defaultTrackerCls;

    private static final HashMap<String, Class<? extends UriRequest>>
            SCHEME_CLS_MAP = new HashMap<String, Class<? extends UriRequest>>();

    private UriRequestFactory() {
    }

    public static UriRequest getUriRequest(RequestParams params, Type loadType) throws Throwable {

        // get scheme
        String scheme = null;
        String uri = params.getUri();
        int index = uri.indexOf(":");
        String startStr = "/";
        if (index > 0) {
            scheme = uri.substring(0, index);
        } else if (uri.startsWith(startStr)) {
            scheme = "file";
        }

        // get UriRequest
        if (!TextUtils.isEmpty(scheme)) {
            String http = "http";
            String assets = "assets";
            String file = "file";
            Class<? extends UriRequest> cls = SCHEME_CLS_MAP.get(scheme);
            if (cls != null) {
                Constructor<? extends UriRequest> constructor
                        = cls.getConstructor(RequestParams.class, Class.class);
                return constructor.newInstance(params, loadType);
            } else {
                if (scheme.startsWith(http)) {
                    return new HttpRequest(params, loadType);
                } else if (assets.equals(scheme)) {
                    return new AssetsRequest(params, loadType);
                } else if (file.equals(scheme)) {
                    return new LocalFileRequest(params, loadType);
                } else {
                    throw new IllegalArgumentException("The url not be support: " + uri);
                }
            }
        } else {
            throw new IllegalArgumentException("The url not be support: " + uri);
        }
    }

    public static void registerDefaultTrackerClass(Class<? extends RequestTracker> trackerCls) {
        UriRequestFactory.defaultTrackerCls = trackerCls;
    }

    public static RequestTracker getDefaultTracker() {
        try {
            return defaultTrackerCls == null ? null : defaultTrackerCls.newInstance();
        } catch (Throwable ex) {
            LKLogUtil.e(ex.getMessage(), ex);
        }
        return null;
    }

    public static void registerRequestClass(String scheme, Class<? extends UriRequest> uriRequestCls) {
        SCHEME_CLS_MAP.put(scheme, uriRequestCls);
    }
}
