package tech.yunjing.biconlife.jniplugin.bean.city;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 省级的json文件对应对象
 * Created by sun.li on 2017/8/5 0005.
 */

public class ProvincialLevelJsonObj extends BaseEntityObj {
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "ShengObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
