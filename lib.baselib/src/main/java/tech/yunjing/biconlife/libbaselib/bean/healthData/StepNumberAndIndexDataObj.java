package tech.yunjing.biconlife.libbaselib.bean.healthData;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 获取今日总步数、标准步数及指标数据
 * Created by CHP on 2017/7/20.
 */

public class StepNumberAndIndexDataObj extends BaseEntityObj{
    /**
     * 标准步数
     */
    private String standardStepNumber;

    /**
     * 今日步数
     */
    private String todayStepNumber;

    /**
     * 指标信息与用户健康数据集合
     */
    private ArrayList<IndexDataObj> indexData;

    public String getStandardStepNumber() {
        return standardStepNumber;
    }

    public void setStandardStepNumber(String standardStepNumber) {
        this.standardStepNumber = standardStepNumber;
    }

    public String getTodayStepNumber() {
        return todayStepNumber;
    }

    public void setTodayStepNumber(String todayStepNumber) {
        this.todayStepNumber = todayStepNumber;
    }

    public ArrayList<IndexDataObj> getIndexData() {
        return indexData;
    }

    public void setIndexData(ArrayList<IndexDataObj> indexData) {
        this.indexData = indexData;
    }
}
