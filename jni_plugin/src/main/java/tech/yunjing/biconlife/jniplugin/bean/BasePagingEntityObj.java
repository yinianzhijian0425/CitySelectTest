package tech.yunjing.biconlife.jniplugin.bean;

/**
 * 分页实体基类对象
 * Created by cq on 2017/7/10.
 */

public class BasePagingEntityObj<T> extends BaseEntityObj{
//    private int offset;
//
//    private int limit;

    /** 总记录数*/
    private int total;

    /**
     * 每页显示条数，默认
     */
    private int size;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 当前页
     */
    private int current;

//    private boolean searchCount;
//
//    private boolean openSort;
//
//    private boolean optimizeCount;
//
//    private String orderByField;

    /** T类型对象*/
    private T records;

//    private String condition;
//
//    private int offsetCurrent;
//
//    private boolean asc;

//    public int getOffset() { return offset; }
//
//    public void setOffset(int offset) { this.offset = offset; }
//
//    public int getLimit() { return limit; }
//
//    public void setLimit(int limit) { this.limit = limit; }
    /**
     * 获取总记录数
     */
    public int getTotal() { return total; }
    /**
     * 设置总记录数
     */
    public void setTotal(int total) { this.total = total; }
    /**
     * 获取每页显示条数，默认
     */
    public int getSize() { return size; }
    /**
     * 设置每页显示条数，默认
     */
    public void setSize(int size) { this.size = size; }
    /**
     * 获取总页数
     */
    public int getPages() { return pages; }
    /**
     * 设置总页数
     */
    public void setPages(int pages) { this.pages = pages; }
    /**
     * 获取当前页
     */
    public int getCurrent() { return current; }
    /**
     * 设置当前页
     */
    public void setCurrent(int current) { this.current = current; }

//    public boolean isSearchCount() { return searchCount; }
//
//    public void setSearchCount(boolean searchCount) { this.searchCount = searchCount; }
//
//    public boolean isOpenSort() { return openSort; }
//
//    public void setOpenSort(boolean openSort) { this.openSort = openSort; }
//
//    public boolean isOptimizeCount() { return optimizeCount; }
//
//    public void setOptimizeCount(boolean optimizeCount) { this.optimizeCount = optimizeCount; }
//
//    public String getOrderByField() { return orderByField; }
//
//    public void setOrderByField(String orderByField) { this.orderByField = orderByField; }

    /**
     * 获取T类型对象
     */
    public T getRecords() { return records; }

    /**
     * 设置T类型对象
     */
    public void setRecords(T records) { this.records = records; }


//    public String getCondition() { return condition; }
//
//    public void setCondition(String condition) { this.condition = condition; }
//
//    public int getOffsetCurrent() { return offsetCurrent; }
//
//    public void setOffsetCurrent(int offsetCurrent) { this.offsetCurrent = offsetCurrent; }
//
//    public boolean isAsc() { return asc; }
//
//    public void setAsc(boolean asc) { this.asc = asc; }
}
