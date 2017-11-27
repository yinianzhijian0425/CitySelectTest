package tech.yunjing.biconlife.liblkclass.http.lkhttp;

import java.util.HashMap;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.http.xhttp.RequestParams;

/**
 * 接口联调测试使用！(无解析)
 *
 * @author nanPengFei
 */
public class LKTestRequest extends LKBaseRequest {
    /**
     * Get请求
     *
     * @param url
     */
    public static void get(String url) {
        RequestParams requestParams = new RequestParams(url);
        LKRequestUtils.get(requestParams, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.e("哥请求成功==" + result);
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e("请求失败==responseCode===" + responseCode + "==responseMsg===" + responseMsg);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {
            }
        });
    }

    /**
     * Post请求,无参数
     *
     * @param url
     */
    public static void post(String url) {
        LKRequestUtils.post(url, new LKRequestCallBack<String>() {
            @Override
            public void onRequestSuccess(String result) {
                LKLogUtil.i("哥请求成功==" + result);
            }

            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e("请求失败==responseCode===" + responseCode + "==responseMsg===" + responseMsg);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {

            }
        });
    }

    /**
     * Post请求,带参数
     *
     * @param url
     * @param params
     */
    public static void post(String url, HashMap<String, Object> params) {

        LKLogUtil.e("result地址==" + url);

        LKRequestUtils.post(url, params, new LKRequestCallBack<String>() {

            @Override
            public void onRequestSuccess(String result) {

                LKLogUtil.e("result哥请求成功==" + result);
            }


            @Override
            public void onRequestFailed(int responseCode, String responseMsg) {
                LKLogUtil.e("请求失败==responseCode===" + responseCode + "==responseMsg===" + responseMsg);
            }

            @Override
            public void onRequestCancel(CancelledException cex) {

            }
        });
    }
}
