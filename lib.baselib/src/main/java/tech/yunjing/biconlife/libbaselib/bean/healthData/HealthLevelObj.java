package tech.yunjing.biconlife.libbaselib.bean.healthData;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;
import tech.yunjing.biconlife.libbaselib.bean.healthReport.HealthLevelListObj;


/**
 * 首页获取用户健康等级及提示语实体类
 * Created by CHP on 2017/7/20.
 */

public class HealthLevelObj extends BaseEntityObj{
    /**
     * 星级
     */
      private int star;
    /**
     * 等级
     */
    private String leval;
    /**
     * 提示语
     */
    private String tip;

   private ArrayList<HealthLevelListObj> indexList;

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ArrayList<HealthLevelListObj> getIndexList() {
        return indexList;
    }

    public void setIndexList(ArrayList<HealthLevelListObj> indexList) {
        this.indexList = indexList;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getLeval() {
        return leval;
    }

    public void setLeval(String leval) {
        this.leval = leval;
    }

    public ArrayList<HealthLevelListObj> getList() {
        return indexList;
    }

    public void setList(ArrayList<HealthLevelListObj> list) {
        this.indexList = list;
    }
}
