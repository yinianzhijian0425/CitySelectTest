package tech.yunjing.biconlife.jniplugin.server;

import tech.yunjing.biconlife.jniplugin.bean.BaseCascadeDataObj;

/**
 * 级联数据基类实体逻辑处理类
 * Created by xining on 2017/10/17 0017.
 * @author
 */

public class BaseCascadeDataObjServer {

    private static BaseCascadeDataObjServer mInstance = null;

    public static BaseCascadeDataObjServer getInstance() {
        if (null == mInstance) {
            synchronized (BaseCascadeDataObjServer.class) {
                if (null == mInstance) {
                    mInstance = new BaseCascadeDataObjServer();
                }
            }
        }
        return mInstance;
    }

    /** 对象实体转换数据库实体*/
    public BaseCascadeDataObj objectEntityConvertDatabaseEntity(BaseCascadeDataObj obj){
        BaseCascadeDataObj data = null;
        if(obj!=null){
            data = obj;
            data.setObjectProperty(obj.getObject().toString());
        }
        return data;
    }

}
