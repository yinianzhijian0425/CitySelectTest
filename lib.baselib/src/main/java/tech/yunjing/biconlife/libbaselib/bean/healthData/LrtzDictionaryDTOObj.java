package tech.yunjing.biconlife.libbaselib.bean.healthData;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.bean.BaseEntityObj;

/**
 * 指标信息
 * Created by CHP on 2017/7/20.
 */

public class LrtzDictionaryDTOObj extends BaseEntityObj{

   private int id;
    /**
     * 名称
     */
    private String name;

    /**
     * code
     */
    private String code;
    /**
     * 类别
     */
    private String type;
    /**
     * 单位
     */
    private String unit;

    /**
     *  健康数据范围
     */
    private ArrayList<IndexRangeDTOListDataObj> indexRangeDTOList;
    /**
     *  健康数据单选操作
     */
    private ArrayList<OptionListDataObj> optionList;


    public ArrayList<IndexRangeDTOListDataObj> getIndexRangeDTOList() {
        return indexRangeDTOList;
    }

    public void setIndexRangeDTOList(ArrayList<IndexRangeDTOListDataObj> indexRangeDTOList) {
        this.indexRangeDTOList = indexRangeDTOList;
    }

    public ArrayList<OptionListDataObj> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<OptionListDataObj> optionList) {
        this.optionList = optionList;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
