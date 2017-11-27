package tech.yunjing.biconlife.libbaselib.server;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.db.GeneralDb;
import tech.yunjing.biconlife.jniplugin.db.daTable.DeliveryAddressBean;
import tech.yunjing.biconlife.jniplugin.util.StringProcessingUtil;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKTimeUtil;

/**
 * 收货地址相关数据逻辑处理类
 * Created by sun.li on 2017/7/11.
 */

public class DeliveryAddressRelatedServer {

    private static DeliveryAddressRelatedServer sInstance;

    /**
     * 实例化对象
     */
    public static DeliveryAddressRelatedServer getInstance() {
        if (sInstance == null) {
            sInstance = new DeliveryAddressRelatedServer();
        }
        return sInstance;
    }

    /**
     * 获取收货地址集合
     */
    public ArrayList<DeliveryAddressBean> getDeliveryAddressList() {
        String loginUserId = UserInfoManageUtil.getUserId();
        ArrayList<DeliveryAddressBean> deliveryAddressBeans = null;
        try {
            if(!TextUtils.isEmpty(loginUserId)){
                deliveryAddressBeans = (ArrayList<DeliveryAddressBean>) GeneralDb.getQueryByOrder(DeliveryAddressBean.class,
                        DeliveryAddressBean.EnumClass.updatedDate.toString(), true,
                        DeliveryAddressBean.EnumClass.addressUserID.toString(),new String[]{loginUserId});
            }else{
                deliveryAddressBeans = (ArrayList<DeliveryAddressBean>) GeneralDb.getQueryByOrder(DeliveryAddressBean.class,
                        DeliveryAddressBean.EnumClass.updatedDate.toString(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (deliveryAddressBeans == null) {
            deliveryAddressBeans = new ArrayList<>();
        }
        return deliveryAddressBeans;
    }

    /** 获取系统默认地址*/
    public DeliveryAddressBean getDefaultAddressInfo(){
        DeliveryAddressBean deliveryAddressBean = new DeliveryAddressBean();
        List<DeliveryAddressBean> list = getDeliveryAddressList();
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).isDefaultAddress()){
                    deliveryAddressBean = list.get(i);
                }
            }
            if(deliveryAddressBean==null || TextUtils.isEmpty(deliveryAddressBean.getId())){
                deliveryAddressBean = list.get(0);
            }
        }
        return deliveryAddressBean;
    }

    /**
     * 生成收货地址对象
     */
    public DeliveryAddressBean getDeliveryAddressBean(DeliveryAddressBean deliveryAddressBean, String userName, String phone, String city, String address) {
        String loginUserId = UserInfoManageUtil.getUserId();
        if (deliveryAddressBean == null) {
            deliveryAddressBean = new DeliveryAddressBean();
        }
        deliveryAddressBean.setUserName(userName);
        deliveryAddressBean.setPhone(StringProcessingUtil.stringRemoveBlankSpace(phone));
        deliveryAddressBean.setCity(city);
        deliveryAddressBean.setAddress(address);
        if (!TextUtils.isEmpty(loginUserId)) {
            deliveryAddressBean.setAddressUserID(loginUserId);
        }
        return deliveryAddressBean;
    }

    /**
     * 保存收货地址信息
     */
    public void saveDeliveryAddressInfo(DeliveryAddressBean deliveryAddressBean) {
        String loginUserId = UserInfoManageUtil.getUserId();
        if (deliveryAddressBean != null) {
            String nowDateStr = LKTimeUtil.getNowDateString();
            if (!TextUtils.isEmpty(nowDateStr)) {
                deliveryAddressBean.setUpdatedDate(nowDateStr);
            }
            if (!TextUtils.isEmpty(loginUserId)) {
                deliveryAddressBean.setAddressUserID(loginUserId);
            }
            if (deliveryAddressBean.getUserID() <= 0) {
                GeneralDb.insert(deliveryAddressBean);
            } else {
                GeneralDb.update(deliveryAddressBean);
            }
        }
    }

    /**
     * 保存收货地址信息
     */
    public void updateDeliveryAddressInfos(ArrayList<DeliveryAddressBean> deliveryAddressBeans) {
        String loginUserId = UserInfoManageUtil.getUserId();
        if (deliveryAddressBeans != null && deliveryAddressBeans.size() > 0) {
            String nowDateStr = LKTimeUtil.getNowDateString();
            for (int i = 0; i < deliveryAddressBeans.size(); i++) {
                if (!TextUtils.isEmpty(nowDateStr) && deliveryAddressBeans.get(i).isDefaultAddress()) {
                    deliveryAddressBeans.get(i).setUpdatedDate(nowDateStr);
                }
                if (!TextUtils.isEmpty(loginUserId)) {
                    deliveryAddressBeans.get(i).setAddressUserID(loginUserId);
                }
            }
            List<DeliveryAddressBean> datas = getDeliveryAddressList();
            if(datas == null || datas.size() == 0){
                GeneralDb.insertAll(deliveryAddressBeans);
            }else {
                GeneralDb.updateALL(deliveryAddressBeans);
            }
        }
    }

    /**
     * 删除不合格的收货信息
     */
    public void deleteUnqualifiedDeliveryAddressInfo() {
        List<DeliveryAddressBean> deliveryAddressBeans = getDeliveryAddressList();
        if (deliveryAddressBeans != null && deliveryAddressBeans.size() > 0) {
            for (int i = 0; i < deliveryAddressBeans.size(); i++) {
                if (TextUtils.isEmpty(deliveryAddressBeans.get(i).getUserName().trim())) {
                    GeneralDb.delete(deliveryAddressBeans.get(i));
                }
            }
        }
    }

    /**
     * 删除指定收货信息
     */
    public void deleteDeliveryAddressInfo(DeliveryAddressBean deliveryAddressBean) {
        if (deliveryAddressBean != null) {
            GeneralDb.delete(deliveryAddressBean);
        }
    }

    /**
     * 删除所有收货信息
     */
    public void deleteAllDeliveryAddressInfo() {
        List<DeliveryAddressBean> list = getDeliveryAddressList();
        if(list!=null){
            for (int i = 0; i < list.size(); i++) {
                deleteDeliveryAddressInfo(list.get(i));
            }
        }
//        GeneralDb.deleteAll(DeliveryAddressBean.class);
    }

    /**
     * 收货地址信息完整有效性判断
     *
     * @return string msg有效性判断反馈，如为“”则表示均有效，如不为“”则为无效，需提示
     */
    public String deliveryAddressInfoValidityJudgment(DeliveryAddressBean deliveryAddressBean) {
        if (deliveryAddressBean != null) {
            if (TextUtils.isEmpty(deliveryAddressBean.getUserName().trim())) {
                return "请输入收货人姓名";
            } else if (TextUtils.isEmpty(deliveryAddressBean.getPhone().trim())) {
                return "请输入手机号码";
            } else if (!LKCommonUtil.isPhone(deliveryAddressBean.getPhone().trim())) {
                return "手机格式不对，请重新输入";
            } else if (TextUtils.isEmpty(deliveryAddressBean.getCity().trim())) {
                return "请选择省、市、区";
            } else if (TextUtils.isEmpty(deliveryAddressBean.getAddress().trim())) {
                return "请输入详细地址";
            } else {
                return "";
            }
        } else {
            return "请输入收货人信息";
        }
    }

}
