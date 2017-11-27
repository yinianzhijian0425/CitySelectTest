package tech.yunjing.biconlife.liblkclass.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;

import tech.yunjing.biconlife.liblkclass.global.LKApplication;

/**
 * 数据库增删改查工具类
 * Created by nanPengFei on 2016/3/19 0019.
 */
public class LKDBUtil {
    /**
     * 判断数据库中是否存在指定表
     *
     * @param openHelper
     * @param tabName
     * @return
     */
    public static boolean hasTable(SQLiteOpenHelper openHelper, String tabName) {
        if (TextUtils.isEmpty(tabName)) {
            return false;
        }
        boolean result = false;
        SQLiteDatabase writableDatabase = null;
        Cursor cursor = null;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = writableDatabase.rawQuery(sql, null);
            if (cursor != null && cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        return result;
    }

    /**
     * 表格创建
     *
     * @param openHelper
     * @param tableName
     * @param filedArray
     */
    public static void createTable(SQLiteOpenHelper openHelper, String tableName, ArrayList<String> filedArray) {
        //判断表是否存在
        boolean isCreateSuccess = hasTable(openHelper, tableName);
        if (!isCreateSuccess) {
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 0; i < filedArray.size(); i++) {
                if (i != filedArray.size() - 1) {
                    stringBuffer.append(filedArray.get(i) + " varchar,");
                } else {
                    stringBuffer.append(filedArray.get(i) + " varchar");
                }
            }
            String sqlString = "CREATE TABLE IF NOT EXISTS " + tableName + "( _id integer primary key autoincrement," + stringBuffer.toString() + ")";
            SQLiteDatabase writableDatabase = null;
            try {
                writableDatabase = openHelper.getWritableDatabase();
                writableDatabase.execSQL(sqlString);
            } catch (Exception ep) {
                ep.printStackTrace();
            } finally {
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
    }

    /**
     * 删除指定表格
     *
     * @param openHelper
     * @param tableName
     */
    public static void deleteTable(SQLiteOpenHelper openHelper, String tableName) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            //删除已经存在的的数据库中表
            writableDatabase.execSQL("DROP TABLE IF EXISTS " + tableName);
        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
    }


    /**
     * 数据库插入数据方法
     *
     * @param openHelper
     * @param tableName     表名
     * @param contentValues 需要存储的表字段和值的集合
     */
    public static long insert(SQLiteOpenHelper openHelper, String tableName, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = null;
        long isInsert = -1;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            isInsert = writableDatabase.insert(tableName, null, contentValues);
        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        return isInsert;
    }

    /**
     * 根据字段删除表中指定数据
     *
     * @param openHelper
     * @param tableName
     * @param fieldKey
     * @param fieldValue
     * @return
     */
    public static int deleteValue(SQLiteOpenHelper openHelper, String tableName, String fieldKey, String fieldValue) {
        return deleteValue(openHelper, tableName, new String[]{fieldKey}, new String[]{fieldValue});
    }

    /**
     * 根据字段删除表中指定数据
     *
     * @param openHelper
     * @param tableName   表名
     * @param fieldKeys   表字段数组
     * @param fieldValues 字段对应的值数组
     * @return
     */
    public static int deleteValue(SQLiteOpenHelper openHelper, String tableName, String[] fieldKeys, String[] fieldValues) {
        if (fieldKeys.length != fieldValues.length || fieldKeys.length == 0) {
            //参数和字段数量不对应，则直接返回
            return 0;
        }
        SQLiteDatabase writableDatabase = null;
        //删除的总行数
        int deleteLines = 0;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            //表字段封装
            String keyStr = "";
            for (int i = 0; i < fieldKeys.length; i++) {
                if (i == fieldKeys.length - 1) {
//                    keyStr = keyStr + fieldKeys[i] + " = ?";
                    keyStr = String.format("%1$s%2$s = ?", keyStr, fieldKeys[i]);
                } else {
//                    keyStr = keyStr + fieldKeys[i] + " = ? AND ";
                    keyStr = String.format("%1$s%2$s = ? AND ", keyStr, fieldKeys[i]);
                }
            }
            //字段参数封装
            String[] values = new String[fieldValues.length];
            for (int i = 0; i < fieldValues.length; i++) {
                values[i] = fieldValues[i];
            }
            deleteLines = writableDatabase.delete(tableName, keyStr, values);


        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        return deleteLines;
    }

    /**
     * 查询指定数据是否存在
     *
     * @param openHelper
     * @param tableName  表名
     * @param fieldKey   查询的键
     * @param fieldValue 查询的键的值
     * @return
     */
    public static boolean hasValue(SQLiteOpenHelper openHelper, String tableName, String fieldKey, String fieldValue) {
        return hasValue(openHelper, tableName, new String[]{fieldKey}, new String[]{fieldValue});
    }

    /**
     * 查询指定数据是否存在
     *
     * @param openHelper
     * @param tableName
     * @param fieldKeys
     * @param fieldValues
     * @return
     */
    public static boolean hasValue(SQLiteOpenHelper openHelper, String tableName, String[] fieldKeys, String[] fieldValues) {
        SQLiteDatabase writableDatabase = null;
        Cursor cursor = null;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            String keyStr = "";
            for (int i = 0; i < fieldKeys.length; i++) {
                if (i == 0) {
                    keyStr = fieldKeys[i] + "=? ";
                } else {
//                    keyStr = keyStr + " and " + fieldKeys[i] + "=? ";
                    keyStr = String.format("%1$s and %2$s=? ", keyStr, fieldKeys[i]);
                }
            }
            cursor = writableDatabase.query(tableName, null, keyStr, fieldValues, null, null, null, null);
            return cursor.moveToFirst();
        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        return false;
    }

    /**
     * 根据列一对应的值，返回另一列的值
     *
     * @param openHelper
     * @param tableName    表名
     * @param fieldKey     查询的键
     * @param fieldValue   查询的键的值
     * @param backFieldKey 需要返回的字段
     * @return
     */
    public static String queryFieldKey(SQLiteOpenHelper openHelper, String tableName, String fieldKey, String fieldValue, String backFieldKey) {
        String backFeldValue = "";
        SQLiteDatabase writableDatabase = null;
        Cursor cursor = null;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            cursor = writableDatabase.query(tableName, null, fieldKey + "=?", new String[]{fieldValue}, null, null, null, null);
            while (cursor.moveToNext()) {
                //返回数值
                backFeldValue = cursor.getString(cursor.getColumnIndex(backFieldKey));
            }
        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        return backFeldValue;
    }

    /**
     * 修改操作
     *
     * @param openHelper
     * @param tableName
     * @param contentValues
     * @param oldKey
     * @param oldValue
     * @return
     */
    public static int updataValue(SQLiteOpenHelper openHelper, String tableName, ContentValues contentValues, String oldKey, String oldValue) {
        return updataValue(openHelper, tableName, contentValues, new String[]{oldKey}, new String[]{oldValue});
    }

    /**
     * 修改操作
     *
     * @param openHelper
     * @param tableName
     * @param contentValues
     * @param oldKeys
     * @param oldValues
     * @return
     */
    public static int updataValue(SQLiteOpenHelper openHelper, String tableName, ContentValues contentValues, String[] oldKeys, String[] oldValues) {
        int update = -1;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = openHelper.getWritableDatabase();
            String key = "";
            for (int i = 0; i < oldKeys.length; i++) {
                if (i == 0) {
//                    key = oldKeys[i] + "=? ";
                    key = String.format("%1$s=? ", oldKeys[i]);
                } else {
//                    key = key + " and " + oldKeys[i] + "=? ";
                    key = String.format("%1$s and %2$s=? ", key, oldKeys[i]);
                }
            }
            update = writableDatabase.update(tableName, contentValues, key, oldValues);
        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
        }
        return update;
    }

    /**
     * 删除数据库
     *
     * @return
     */
    public static boolean deleteDB(String dbName) {
        String databaseName = dbName + ".db";
        return LKApplication.getContext().deleteDatabase(databaseName);
    }
}
