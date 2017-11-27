package tech.yunjing.biconlife.jniplugin.server;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.db.GeneralDb;
import tech.yunjing.biconlife.jniplugin.db.daTable.RemindFunctionObj;
import tech.yunjing.biconlife.liblkclass.common.util.LKTimeUtil;

/**
 * 提醒功能相关数据逻辑处理类
 * Created by sun.li on 2017/7/11.
 */

public class RemindFunctionRelatedServer {

    private static RemindFunctionRelatedServer sInstance;

    /**
     * 实例化对象
     */
    public static RemindFunctionRelatedServer getInstance() {
        if (sInstance == null) {
            sInstance = new RemindFunctionRelatedServer();
        }
        return sInstance;
    }

    /**
     * 获取提醒功能信息集合
     */
    public ArrayList<RemindFunctionObj> getRemindFunctionList() {
        ArrayList<RemindFunctionObj> RemindFunctionObjs = null;
        try {
            RemindFunctionObjs = (ArrayList<RemindFunctionObj>) GeneralDb.getQueryByOrder(RemindFunctionObj.class,
                    RemindFunctionObj.EnumClass.updateDate.toString(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (RemindFunctionObjs == null) {
            RemindFunctionObjs = new ArrayList<>();
        }
        return RemindFunctionObjs;
    }

    /**
     * 获取对应提醒功能信息
     */
    public RemindFunctionObj getRemindFunction(String remindID) {
        ArrayList<RemindFunctionObj> RemindFunctionObjs = null;
        try {
            RemindFunctionObjs = (ArrayList<RemindFunctionObj>) GeneralDb.getQueryByWhere(RemindFunctionObj.class,
                    RemindFunctionObj.EnumClass.remindID.toString(), new String[]{remindID});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (RemindFunctionObjs != null && RemindFunctionObjs.size()>0) {
            return RemindFunctionObjs.get(0);
        }
        return null;
    }


    /**
     * 保存提醒功能信息
     */
    public void saveRemindFunctionInfo(RemindFunctionObj remindFunctionObj) {
        if (remindFunctionObj != null) {
            remindFunctionObj.setUpdateDate(LKTimeUtil.getNowDateString());
            if(remindFunctionObj.getAlarmID()==null || TextUtils.isEmpty(remindFunctionObj.getAlarmID())){
                String remindUUID = remindFunctionObj.getRemindID()+remindFunctionObj.getRemindTitle()+remindFunctionObj.getRemindStartTime()+remindFunctionObj.getRemindTitle()+remindFunctionObj.getRemindRepetitionDate();
                remindFunctionObj.setAlarmID(remindUUID);
            }
            if (remindFunctionObj.getRemindID() <= 0) {
                GeneralDb.insert(remindFunctionObj);
            } else {
                GeneralDb.update(remindFunctionObj);
            }
        }
    }

    /**
     * 保存提醒功能信息集合
     */
    public void saveRemindFunctionAllInfo(ArrayList<RemindFunctionObj> remindFunctionObjs) {
        if(remindFunctionObjs!=null && remindFunctionObjs.size()>0){
            for (int i = 0; i < remindFunctionObjs.size(); i++) {
                RemindFunctionObj remindFunctionObj = remindFunctionObjs.get(i);
                if (remindFunctionObj != null) {
                    remindFunctionObj.setUpdateDate(LKTimeUtil.getNowDateString());
                    if(remindFunctionObj.getAlarmID()==null || TextUtils.isEmpty(remindFunctionObj.getAlarmID())){
                        String remindUUID = remindFunctionObj.getRemindID()+remindFunctionObj.getRemindTitle()+remindFunctionObj.getRemindStartTime()+remindFunctionObj.getRemindTitle()+remindFunctionObj.getRemindRepetitionDate();
                        remindFunctionObj.setAlarmID(remindUUID);
                    }
                    if (remindFunctionObj.getRemindID() <= 0) {
                        GeneralDb.insert(remindFunctionObj);
                    } else {
                        GeneralDb.update(remindFunctionObj);
                    }
                }
            }
        }
    }

    /**
     * 保存提醒功能信息
     */
    public void updateRemindFunctionInfos(ArrayList<RemindFunctionObj> RemindFunctionObjs) {
        if (RemindFunctionObjs != null && RemindFunctionObjs.size() > 0) {
            List<RemindFunctionObj> datas = GeneralDb.getQueryAll(RemindFunctionObj.class);
            if(datas == null || datas.size() == 0){
                GeneralDb.insertAll(RemindFunctionObjs);
            }else {
                GeneralDb.updateALL(RemindFunctionObjs);
            }
        }
    }

    /**
     * 删除指定提醒功能信息
     */
    public void deleteRemindFunctionInfo(RemindFunctionObj RemindFunctionObj) {
        if (RemindFunctionObj != null) {
            GeneralDb.delete(RemindFunctionObj);
        }
    }

    /**
     * 删除所有提醒功能信息
     */
    public void deleteAllRemindFunctionInfo() {
        GeneralDb.deleteAll(RemindFunctionObj.class);
    }

}
