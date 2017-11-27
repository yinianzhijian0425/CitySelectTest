package tech.yunjing.biconlife.jniplugin.bean.city;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 县级的json文件对应对象
 * Created by sun.li on 2017/8/5 0005.
 */

public class CountyLevelJsonObj extends BaseEntityObj {

    private int id;
    private String name;
    private int parent_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "XianObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                '}';
    }
}
