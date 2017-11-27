package tech.yunjing.biconlife.libbaselib.util;

/**
 * 对象处理工具类
 * Created by sun.li on 2017/7/18.
 */

public class BCObjectProcessingUtil {

//    /** 对象强制转换为Class对象*/
//    public static <T> T objMandatoryConversionClass(Object obj){
//        T t;
//        try {
//            t = (T)obj;
//        } catch (Exception e) {
//            e.printStackTrace();
//            t = null;
//        }
//        return t;
//    }

    /** 对象强制转换为Class对象
     * @param cls 传入需要转换的对象类型（一般用于转换Class）
     * */
    public static <T> T objMandatoryConversionClass(Object obj,Class cls){
        T t;
        try {
            t = (T)cls.cast(obj);
        } catch (Exception e) {
            e.printStackTrace();
            t = null;
        }
        return t;
    }

    /** 对象强制转换为Class对象
     * @param cls 传入需要转换的对象类型（一般用于转换List<Class>）
     * */
    public static <T> T objMandatoryConversionClass(Object obj,T cls){
        T t;
        try {
            t = (T)cls.getClass().cast(obj);
        } catch (Exception e) {
            e.printStackTrace();
            t = null;
        }
        return t;
    }

}
