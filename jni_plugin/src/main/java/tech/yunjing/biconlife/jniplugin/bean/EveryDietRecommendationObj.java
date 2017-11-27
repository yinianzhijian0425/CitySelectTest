package tech.yunjing.biconlife.jniplugin.bean;

/**
 * Created by xining on 2017/8/8 0008.
 * 饮食推荐-食物详情（每餐（早餐、中餐、晚餐、加餐））
 */

public class EveryDietRecommendationObj extends BaseEntityObj {
    /***
     * 食谱id
     */
    private int id;
    /***
     *餐饮类型1早餐、2午餐、3晚餐、4加餐
     */
    private int type;
    /***
     *食物组合id
     */
    private String combinationId;
    /***
     *食物名称组合
     */
    private String combinationName;
    /***
     *千卡
     */
    private int kcal;
    /***
     *规格说明
     */
    private String specifications;
    /***
     *名称
     */
    private String name;
    /***
     *列表展示图
     */
    private String itemImage;

    /**
     * 介绍食物亮点
     */
    private String instruction;

    /***
     *获取食谱id
     */
    public int getId() {
        return id;
    }

    /***
     *设置食谱id
     */
    public void setId(int id) {
        this.id = id;
    }

    /***
     *获取餐饮类型1早餐、2午餐、3晚餐、4加餐
     */
    public int getType() {
        return type;
    }

    /***
     *设置餐饮类型1早餐、2午餐、3晚餐、4加餐
     */
    public void setType(int type) {
        this.type = type;
    }

    /***
     *获取食物组合id
     */
    public String getCombinationId() {
        return combinationId;
    }

    /***
     *设置食物组合id
     */
    public void setCombinationId(String combinationId) {
        this.combinationId = combinationId;
    }

    /***
     *获取食物名称组合
     */
    public String getCombinationName() {
        return combinationName;
    }

    /***
     *设置食物名称组合
     */
    public void setCombinationName(String combinationName) {
        this.combinationName = combinationName;
    }

    /***
     *获取千卡
     */
    public int getKcal() {
        return kcal;
    }

    /***
     *设置千卡
     */
    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    /***
     *获取规格说明
     */
    public String getSpecifications() {
        return specifications;
    }

    /***
     *设置规格说明
     */
    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    /***
     *获取名称
     */
    public String getName() {
        return name;
    }

    /***
     *设置名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     *获取列表展示图
     */
    public String getItemImage() {
        return itemImage;
    }

    /***
     *设置列表展示图
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * 获取介绍食物亮点
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * 设置介绍食物亮点
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
