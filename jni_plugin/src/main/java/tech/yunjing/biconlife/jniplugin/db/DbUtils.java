package tech.yunjing.biconlife.jniplugin.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

/**
 * 数据库操作类
 * Created by lenovo on 2017/1/5.
 */
public class DbUtils {
    protected static LiteOrm liteOrm;
    public static void createDb(Context activity, String DB_NAME) {
        DB_NAME = DB_NAME + ".db";
        if (liteOrm == null) {
            liteOrm = LiteOrm.newCascadeInstance(activity, DB_NAME);
            liteOrm.setDebugged(true);
        }
    }
    public static LiteOrm getLiteOrm() {
        return liteOrm;
    }
    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        try {
            liteOrm.save(t);
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
            liteOrm.save(list);
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
            return liteOrm.query(cla);
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
            return  liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?",  value));
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
            return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
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
                return  liteOrm.<T>query(new QueryBuilder(cla).appendOrderDescBy(field));
            }else{
                return  liteOrm.<T>query(new QueryBuilder(cla).appendOrderAscBy(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     * @param cla
     * @param field
     * @param value
     */
//        public static <T> void deleteWhere(Class<T> cla,String field,String [] value){
//            liteOrm.delete(cla, WhereBuilder.create().where(field + "=?", value));
//        }
    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        try {
            liteOrm.deleteAll(cla);
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
            liteOrm.delete(t);
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
            liteOrm.update(t, ConflictAlgorithm.Replace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static <T> void updateALL(List<T> list) {
        try {
            liteOrm.update(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeDb(){
        try {
            liteOrm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
