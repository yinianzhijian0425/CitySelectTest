package tech.yunjing.biconlife.jniplugin.bean.request;

/**
 * 请求数据
 * Created by CHP on 2017/7/18.
 */

public class RegionRequestObj extends BaseRequestObj {
    /**
     * 编码
     */
    private String code;
    /**
     * 父字典编号
     */
    private int parentId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


}
