package tech.yunjing.biconlife.libbaselib.bean.healthData;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 指标信息与用户健康数据集合
 * Created by CHP on 2017/7/20.
 */

public class IndexDataObj extends BaseEntityObj{
    /**
     * 指标信息
     */
  private LrtzDictionaryDTOObj lrtzDictionaryDTO;

    /**
     * 用户健康数据集合
     */
    private ArrayList<UerAllIndexDTOListObj> userAllIndexDTOList;

    /** 获取用户健康数据集合*/
    public ArrayList<UerAllIndexDTOListObj> getUserAllIndexDTOList() {
        return userAllIndexDTOList;
    }

    /** 设置用户健康数据集合*/
    public void setUserAllIndexDTOList(ArrayList<UerAllIndexDTOListObj> userAllIndexDTOList) {
        this.userAllIndexDTOList = userAllIndexDTOList;
    }

    /** 获取指标信息*/
    public LrtzDictionaryDTOObj getLrtzDictionaryDTO() {
        return lrtzDictionaryDTO;
    }

    /** 设置指标信息*/
    public void setLrtzDictionaryDTO(LrtzDictionaryDTOObj lrtzDictionaryDTO) {
        this.lrtzDictionaryDTO = lrtzDictionaryDTO;
    }
}
