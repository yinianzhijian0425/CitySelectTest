package tech.yunjing.biconlife.jniplugin.server;

import java.util.ArrayList;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.db.GeneralDb;
import tech.yunjing.biconlife.jniplugin.db.daTable.NationalUrbanInfoObj;

/**
 * 全国城市信息相关逻辑处理类
 * Created by sun.li on 2017/7/11.
 */

public class NationalUrbanInfoRelatedServer {

    private static NationalUrbanInfoRelatedServer sInstance;

    /**
     * 实例化对象
     */
    public static NationalUrbanInfoRelatedServer getInstance() {
        if (sInstance == null) {
            sInstance = new NationalUrbanInfoRelatedServer();
        }
        return sInstance;
    }

    /**
     * 获取全国城市信息集合
     */
    public ArrayList<NationalUrbanInfoObj> getNationalUrbanInfoList() {
        ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = null;
        try {
            nationalUrbanInfoObjs = (ArrayList<NationalUrbanInfoObj>) GeneralDb.getQueryByOrder(NationalUrbanInfoObj.class,
                    NationalUrbanInfoObj.EnumClass.id.toString(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nationalUrbanInfoObjs == null) {
            nationalUrbanInfoObjs = new ArrayList<>();
        }
        return nationalUrbanInfoObjs;
    }

    /**
     * 获取全国对应级别城市信息集合
     * @param level 级别：1、省级；2、市级；3、县级；
     */
    public ArrayList<NationalUrbanInfoObj> getCorrespondingLevelUrbanInfoList(int level) {
        ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = null;
        try {
            nationalUrbanInfoObjs = (ArrayList<NationalUrbanInfoObj>) GeneralDb.getQueryByOrder(NationalUrbanInfoObj.class,
                    NationalUrbanInfoObj.EnumClass.id.toString(), false,NationalUrbanInfoObj.EnumClass.level.toString(),new Integer[]{level});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nationalUrbanInfoObjs == null) {
            nationalUrbanInfoObjs = new ArrayList<>();
        }
        return nationalUrbanInfoObjs;
    }

    /**
     * 获取全国对应子级别城市信息集合
     * @param parent_id 父级别ID；
     */
    public ArrayList<NationalUrbanInfoObj> getChildLevelUrbanInfoList(int parent_id) {
        ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = null;
        try {
            nationalUrbanInfoObjs = (ArrayList<NationalUrbanInfoObj>) GeneralDb.getQueryByOrder(NationalUrbanInfoObj.class,
                    NationalUrbanInfoObj.EnumClass.id.toString(), false,NationalUrbanInfoObj.EnumClass.parent_id.toString(),new Integer[]{parent_id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nationalUrbanInfoObjs == null) {
            nationalUrbanInfoObjs = new ArrayList<>();
        }
        return nationalUrbanInfoObjs;
    }

    public NationalUrbanInfoObj getUrbanInfo(int id){
        ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = null;
        try {
            nationalUrbanInfoObjs = (ArrayList<NationalUrbanInfoObj>) GeneralDb.getQueryByWhere(NationalUrbanInfoObj.class,NationalUrbanInfoObj.EnumClass.id.toString(),new Integer[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nationalUrbanInfoObjs!=null && nationalUrbanInfoObjs.size()>0){
            return nationalUrbanInfoObjs.get(0);
        }else{
            return null;
        }
    }

    /**
     * 保存全国城市信息
     */
    public void saveNationalUrbanInfoInfo(NationalUrbanInfoObj nationalUrbanInfoObj) {
        if (nationalUrbanInfoObj != null) {
            NationalUrbanInfoObj oldObj = getUrbanInfo(nationalUrbanInfoObj.getId());
            if (oldObj == null) {
                GeneralDb.insert(nationalUrbanInfoObj);
            } else {
                nationalUrbanInfoObj.setUrbanID(oldObj.getUrbanID());
                GeneralDb.update(nationalUrbanInfoObj);
            }
        }
    }

    /**
     * 保存全国城市信息集合
     */
    public void saveNationalUrbanInfoAllInfo(ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs) {
        if(nationalUrbanInfoObjs!=null && nationalUrbanInfoObjs.size()>0){
            for (int i = 0; i < nationalUrbanInfoObjs.size(); i++) {
                NationalUrbanInfoObj nationalUrbanInfoObj = nationalUrbanInfoObjs.get(i);
                if (nationalUrbanInfoObj != null) {
                    NationalUrbanInfoObj oldObj = getUrbanInfo(nationalUrbanInfoObj.getId());
                    if (oldObj == null) {
                        GeneralDb.insert(nationalUrbanInfoObj);
                    } else {
                        nationalUrbanInfoObj.setId(oldObj.getUrbanID());
                        GeneralDb.update(nationalUrbanInfoObj);
                    }
                }
            }
        }
    }

    /**
     * 保存全国城市信息
     */
    public void updateNationalUrbanInfoInfos(ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs) {
        if (nationalUrbanInfoObjs != null && nationalUrbanInfoObjs.size() > 0) {
            List<NationalUrbanInfoObj> datas = GeneralDb.getQueryAll(NationalUrbanInfoObj.class);
            if(datas == null || datas.size() == 0){
                GeneralDb.insertAll(nationalUrbanInfoObjs);
            }else {
                GeneralDb.updateALL(nationalUrbanInfoObjs);
            }
        }
    }

    /**
     * 删除指定全国城市信息
     */
    public void deleteNationalUrbanInfoInfo(NationalUrbanInfoObj nationalUrbanInfoObj) {
        if (nationalUrbanInfoObj != null) {
            GeneralDb.delete(nationalUrbanInfoObj);
        }
    }

    /**
     * 删除所有全国城市信息
     */
    public void deleteAllNationalUrbanInfoInfo() {
        GeneralDb.deleteAll(NationalUrbanInfoObj.class);
    }

}
