package tech.yunjing.biconlife.jniplugin.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

import tech.yunjing.biconlife.jniplugin.base.AppBaseApplication;
import tech.yunjing.biconlife.jniplugin.global.BCDataBaseUtil;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;

/**
 * 父code获取子字典
 * Created by CHP on 2017/10/13.
 */

public class ParentCodeDb {
    private static LiteOrm liteOrm_GeneralDb;
    public static void createDb(Context activity) {
        if (liteOrm_GeneralDb == null) {
            String userID= UserInfoManageUtil.getUserId();
            liteOrm_GeneralDb = LiteOrm.newCascadeInstance(activity, BCDataBaseUtil.FIND_PARENT_CODE+userID+".db");
            liteOrm_GeneralDb.setDebugged(true);
        }
    }
    public static LiteOrm getLiteOrm() {
        createDb(AppBaseApplication.getContext());
        return liteOrm_GeneralDb;
    }
    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        try {
            getLiteOrm().save(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list) {
        try {
            getLiteOrm().save(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla) {
        try {
            return getLiteOrm().query(cla);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, Object[] value) {
        try {
            return  getLiteOrm().<T>query(new QueryBuilder(cla).where(field + "=?",  value));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByWhereLength(Class<T> cla, String field, Object[] value, int start, int length) {
        try {
            return getLiteOrm().<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询所有通过某字段排序
     *
     * @param cla
     * @param field
     * @param descOrAsc 降序或升序，true为desc降序；false为asc升序；
     * @return
     */
    public static <T> List<T> getQueryByOrder(Class<T> cla, String field, boolean descOrAsc) {
        try {
            if(descOrAsc){
                return  getLiteOrm().<T>query(new QueryBuilder(cla).appendOrderDescBy(field));
            }else{
                return  getLiteOrm().<T>query(new QueryBuilder(cla).appendOrderAscBy(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询所有通过某字段排序并且某字段（field1）包含 Vlaue的值
     *
     * @param cla
     * @param field
     * @param descOrAsc 降序或升序，true为desc降序；false为asc升序；
     * @return
     */
    public static <T> List<T> getQueryByOrder(Class<T> cla, String field, boolean descOrAsc,String field1,Object[] value) {
        try {
            if(descOrAsc){
                return  getLiteOrm().<T>query(new QueryBuilder(cla).where(field1 + " LIKE ?", value).appendOrderDescBy(field));
            }else{
                return  getLiteOrm().<T>query(new QueryBuilder(cla).where(field1 + " LIKE ?", value).appendOrderAscBy(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     * @param cla
     * @param field
     * @param value
     */
//        public static <T> void deleteWhere(Class<T> cla,String field,String [] value){
//            getLiteOrm().delete(cla, WhereBuilder.create().where(field + "=?", value));
//        }
    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        try {
            getLiteOrm().deleteAll(cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某条信息
     *
     * @param t
     */
    public static <T> void delete(T t) {
        try {
            getLiteOrm().delete(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        try {
            getLiteOrm().update(t, ConflictAlgorithm.Replace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static <T> void updateALL(List<T> list) {
        try {
            getLiteOrm().update(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 打开数据库*/
    public static void openDb(){
        try {
            getLiteOrm().openOrCreateDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 关闭数据库*/
    public static void closeDb(){
        try {
            getLiteOrm().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
