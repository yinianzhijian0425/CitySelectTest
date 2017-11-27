package tech.yunjing.biconlife.liblkclass.http.xhttp.app;


import org.json.JSONException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashSet;

import tech.yunjing.biconlife.liblkclass.common.Callback;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.ex.HttpException;
import tech.yunjing.biconlife.liblkclass.http.xhttp.HttpMethod;
import tech.yunjing.biconlife.liblkclass.http.xhttp.request.UriRequest;

/**
 * Author: wyouflf
 * Time: 2014/05/30
 */
public class HttpRetryHandler {

    protected int maxRetryCount = 2;

    protected static HashSet<Class<?>> blackList = new HashSet<Class<?>>();

    static {
        blackList.add(HttpException.class);
        blackList.add(Callback.CancelledException.class);
        blackList.add(MalformedURLException.class);
        blackList.add(URISyntaxException.class);
        blackList.add(NoRouteToHostException.class);
        blackList.add(PortUnreachableException.class);
        blackList.add(ProtocolException.class);
        blackList.add(NullPointerException.class);
        blackList.add(FileNotFoundException.class);
        blackList.add(JSONException.class);
        blackList.add(UnknownHostException.class);
        blackList.add(IllegalArgumentException.class);
    }

    public HttpRetryHandler() {
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public boolean canRetry(UriRequest request, Throwable ex, int count) {

        LKLogUtil.w(ex.getMessage(), ex);

        if (count > maxRetryCount) {
            LKLogUtil.w(request.toString());
            LKLogUtil.w("The Max Retry times has been reached!");
            return false;
        }

        if (!HttpMethod.permitsRetry(request.getParams().getMethod())) {
            LKLogUtil.w(request.toString());
            LKLogUtil.w("The Request Method can not be retried.");
            return false;
        }

        if (blackList.contains(ex.getClass())) {
            LKLogUtil.w(request.toString());
            LKLogUtil.w("The Exception can not be retried.");
            return false;
        }

        return true;
    }
}
