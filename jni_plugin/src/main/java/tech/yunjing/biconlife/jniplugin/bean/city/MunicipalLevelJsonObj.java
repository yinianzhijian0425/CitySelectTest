package tech.yunjing.biconlife.jniplugin.bean.city;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 县级的json文件对应对象
 * Created by sun.li on 2017/8/5 0005.
 */

public class MunicipalLevelJsonObj extends BaseEntityObj {
    private int id;
    private String name;
    private String post_code;
    private int parent_id;
    private int is_filter_view;
    private int filter_sort;
    private String firstchar;


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

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getIs_filter_view() {
        return is_filter_view;
    }

    public void setIs_filter_view(int is_filter_view) {
        this.is_filter_view = is_filter_view;
    }

    public int getFilter_sort() {
        return filter_sort;
    }

    public void setFilter_sort(int filter_sort) {
        this.filter_sort = filter_sort;
    }

    public String getFirstchar() {
        return firstchar;
    }

    public void setFirstchar(String firstchar) {
        this.firstchar = firstchar;
    }

    @Override
    public String toString() {
        return "TerritoryObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", post_code='" + post_code + '\'' +
                ", parent_id=" + parent_id +
                ", is_filter_view=" + is_filter_view +
                ", filter_sort=" + filter_sort +
                ", firstchar='" + firstchar + '\'' +
                '}';
    }
}
