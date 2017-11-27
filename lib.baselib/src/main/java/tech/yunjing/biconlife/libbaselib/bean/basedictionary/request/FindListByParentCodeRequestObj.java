package tech.yunjing.biconlife.libbaselib.bean.basedictionary.request;

import tech.yunjing.biconlife.jniplugin.bean.request.BaseRequestObj;

/**
 * 根据父code获取子字典接口请求
 * Created by CHP on 2017/10/13.
 */

public class FindListByParentCodeRequestObj extends BaseRequestObj {
    /**
     * 父字典code
     */
    private String code;
    /**
     * 父字典code
     */
    public String getCode() {
        return code;
    }
    /**
     * 父字典code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
