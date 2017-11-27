package tech.yunjing.biconlife.jniplugin.bean.city;

/**
 * 城市级联选择，市级数据结构对象
 * Created by sun.li on 2017/8/5 0005.
 */

public class UrbanCascadeSelectionMunicipalObj {

    /** 城市名称+ID
     * 格式：名称_id
     * */
    private String name;

    /** 县级名称+id数据
     * 格式：名称_id
     * */
    private String[] area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }
}
