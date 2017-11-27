package tech.yunjing.biconlife.jniplugin.bean.city;

import java.util.List;

/**
 * 城市级联选择，省级数据结构对象
 * Created by sun.li on 2017/8/5 0005.
 */

public class UrbanCascadeSelectionProvincialObj {

    /** 省名称+ID
     * 格式：名称_id
     * */
    private String name;

    private List<UrbanCascadeSelectionMunicipalObj> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UrbanCascadeSelectionMunicipalObj> getCity() {
        return city;
    }

    public void setCity(List<UrbanCascadeSelectionMunicipalObj> city) {
        this.city = city;
    }
}
