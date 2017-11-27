package tech.yunjing.biconlife.jniplugin.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.base.AppBaseActivity;
import tech.yunjing.biconlife.jniplugin.base.AppBaseApplication;
import tech.yunjing.biconlife.jniplugin.db.daTable.RemindFunctionObj;
import tech.yunjing.biconlife.jniplugin.global.JNIBroadCastKey;
import tech.yunjing.biconlife.jniplugin.bean.city.ProvincialLevelJsonObj;
import tech.yunjing.biconlife.jniplugin.bean.city.MunicipalLevelJsonObj;
import tech.yunjing.biconlife.jniplugin.bean.city.CountyLevelJsonObj;
import tech.yunjing.biconlife.jniplugin.bean.city.UrbanCascadeSelectionProvincialObj;
import tech.yunjing.biconlife.jniplugin.bean.city.UrbanCascadeSelectionMunicipalObj;
import tech.yunjing.biconlife.jniplugin.db.daTable.NationalUrbanInfoObj;
import tech.yunjing.biconlife.jniplugin.server.NationalUrbanInfoRelatedServer;
import tech.yunjing.biconlife.jniplugin.server.RemindFunctionRelatedServer;
import tech.yunjing.biconlife.jniplugin.util.PCChose.JsonBean;
import tech.yunjing.biconlife.jniplugin.util.PCChose.JsonFileReader;
import tech.yunjing.biconlife.jniplugin.view.pickerview.OptionsPickerView;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 省市选择工具
 * Created by CHP on 2017/7/12.
 */
public class ProvincesCitiyUtil {
    private ArrayList<JsonBean> options1Items = null;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;

    private static ProvincesCitiyUtil sInstance;
    private Activity mActivity;
    private OptionsPickerView pvOptions;
    private TextView showText;
    private OnProvinceCityListener provinceCity;

    private ArrayList<ProvincialLevelJsonObj> shengObj;
    ArrayList<MunicipalLevelJsonObj> shiObj;
    ArrayList<CountyLevelJsonObj> xianObj;

    private boolean shengLoading = false;

    private boolean shiLoading = false;

    private boolean xianLoading = false;

    private ProvincesCitiyDataInit provincesCitiyDataInit;


    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 10:
                    Intent intent = new Intent();
                    intent.setAction(JNIBroadCastKey.Regional_Data_Init_Success);
                    mActivity.sendBroadcast(intent);
                    if (getProvincesCitiyDataInit() != null) {
                        getProvincesCitiyDataInit().initSuccess();
                    }
                    try {
                        ((AppBaseActivity) mActivity).closeLoader();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showPickerView();
                    break;
                case 1:
                    shengLoading = true;
                    initJsonData(false);
                    break;
                case 2:
                    shiLoading = true;
                    initJsonData(false);
                    break;
                case 3:
                    xianLoading = true;
                    initJsonData(false);
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 实例化对象
     *
     * @return
     */
    public static ProvincesCitiyUtil getInstance() {
        if (sInstance == null) {
            sInstance = new ProvincesCitiyUtil();
        }
        return sInstance;
    }

    public void init(Activity activity, TextView showText) {
        this.showText = showText;
        options1Items = new ArrayList<>();
        options2Items = new ArrayList<>();
        options3Items = new ArrayList<>();
        initData(activity);
    }

    synchronized public void initData(Activity activity) {
        mActivity = activity;
        try {
            ((AppBaseActivity) mActivity).showLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<RemindFunctionObj> remindFunctionObjList = RemindFunctionRelatedServer.getInstance().getRemindFunctionList();
        if (remindFunctionObjList != null && remindFunctionObjList.size() > 0) {
            shengLoading = true;
            shiLoading = true;
            xianLoading = true;
            initJsonData(true);
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initJsonFile();
                }
            }).start();
        }
    }

    public void showDialog() {
        if (pvOptions != null) {
            pvOptions.show();
        }
    }

    /**
     * 弹出省市区
     */
    private void showPickerView() {
        if (options1Items != null) {
            try {
                //返回的分别是三个级别的选中位置
//                mTvAddress.setText(text);
                pvOptions = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置

                        //                String text = options1Items.get(options1).getName() +
                        //                        options2Items.get(options1).get(options2) +
                        //                        options3Items.get(options1).get(options2).get(options3);
                        String province = options1Items.get(options1).getName();
                        String city = options2Items.get(options1).get(options2);
                        String area = options3Items.get(options1).get(options2).get(options3);
                        String text = province.split("_")[0] + " " + city.split("_")[0] + " " + area.split("_")[0];
                        String provinceId = province.split("_")[1];
                        String cityId = city.split("_")[1];
                        String areaId = area.split("_")[1];
                        if (showText != null) {
                            showText.setText(text);
                        }
                        if (provinceCity != null) {
                            provinceCity.getProvinceCity(text, provinceId, cityId, areaId);
                        }
                    }
                }).setTitleText("")
                        .setDividerColor(ContextCompat.getColor(mActivity, R.color.color_919090))
                        .setTextColorCenter(ContextCompat.getColor(mActivity, R.color.color_2D2A2A))
                        .setContentTextSize(18)
                        .setLineSpacingMultiplier(2.0f)
                        .setBackgroundId(ContextCompat.getColor(mActivity, R.color.color_33000000))
                        .setSubmitColor(ContextCompat.getColor(mActivity, R.color.color_EF7726))
                        .setCancelColor(ContextCompat.getColor(mActivity, R.color.color_2D2A2A))
                        .setOutSideCancelable(true)
                        .build();
          /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
     * 关键逻辑在于循环体
     * <p>
     * <p>
     * 添加省份数据
     * <p>
     * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
     * PickerView会通过getPickerViewText方法获取字符串显示出来。
     */
    private void initJsonFile() {

        shengLoading = false;
        shiLoading = false;
        xianLoading = false;
        LKLogUtil.e("数据获取开始");

        new Thread(new Runnable() {
            @Override
            public void run() {
                //  获取省份json数据
                String x1 = JsonFileReader.getJson(AppBaseApplication.getContext(), "sheng.json");
                shengObj = LKJsonUtil.jsonToArrayList(x1, ProvincialLevelJsonObj.class);
                handler.sendEmptyMessage(1);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //  获取市json数据
                String x2 = JsonFileReader.getJson(AppBaseApplication.getContext(), "shi.json");
                shiObj = LKJsonUtil.jsonToArrayList(x2, MunicipalLevelJsonObj.class);
                handler.sendEmptyMessage(2);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //  获取县json数据
                String x3 = JsonFileReader.getJson(AppBaseApplication.getContext(), "xian.json");
                xianObj = LKJsonUtil.jsonToArrayList(x3, CountyLevelJsonObj.class);

                handler.sendEmptyMessage(3);
            }
        }).start();

    }

    /**
     * 全国省级城市信息集合
     */
    private ArrayList<UrbanCascadeSelectionProvincialObj> provincialList;

    /**
     * 初始化全国省级城市信息集合
     */
    private void initProvincialList(boolean isLocal) {
        if (provincialList == null) {
            if (isLocal) {
                provincialList = getLocalProvincialList();
            } else {
                provincialList = getProvincialList();
            }
        }
    }

    private void initJsonData(final boolean isLocal) {   //解析数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (shengLoading && shiLoading && xianLoading) {
                    if (options1Items != null) {
                        LKLogUtil.e("数据获取开始");
                        initProvincialList(isLocal);
                        LKLogUtil.e("数据获取结束");
                        LKLogUtil.e("数据拼接开始");

                        LKLogUtil.e("数据拼接结束");
                        //  获取json数据
                        ArrayList<JsonBean> jsonBean = parseData(LKJsonUtil.arrayListToJsonString(provincialList));//用Gson 转成实体
                        LKLogUtil.e("数据处理开始");
                        options1Items = jsonBean;
                        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                                CityList.add(CityName);//添加城市
                                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                                    City_AreaList.add("");
                                } else {
                                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                                    }
                                }
                                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                            }
                            /**
                             * 添加城市数据
                             */
                            options2Items.add(CityList);
                            /**
                             * 添加地区数据
                             */
                            options3Items.add(Province_AreaList);

                        }
                    } else {
                        ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = NationalUrbanInfoRelatedServer.getInstance().getCorrespondingLevelUrbanInfoList(1);
                        if (nationalUrbanInfoObjs == null || nationalUrbanInfoObjs.size() <= 0) {
                            saveProvincialList();
                        }
                    }
                    handler.sendEmptyMessage(10);
                    LKLogUtil.e("数据处理结束");
                }
            }
        }).start();
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public void setProvinceCityListener(OnProvinceCityListener provinceCity) {
        this.provinceCity = provinceCity;

    }


    public interface OnProvinceCityListener {
        void getProvinceCity(String pc, String provinceId, String cityId, String areaId);
    }

    /**
     * 获取省级本地数据
     */
    private ArrayList<UrbanCascadeSelectionProvincialObj> getLocalProvincialList() {
        ArrayList<UrbanCascadeSelectionProvincialObj> list = new ArrayList<>();
        ArrayList<NationalUrbanInfoObj> lists = NationalUrbanInfoRelatedServer.getInstance().getCorrespondingLevelUrbanInfoList(1);
        if (lists != null) {
            for (int i = 0; i < lists.size(); i++) {
                NationalUrbanInfoObj obj = lists.get(i);
                UrbanCascadeSelectionProvincialObj sheng = getLocalMunicipalObj(obj.getId(), obj.getName());
                list.add(sheng);
            }
        }
        return list;
    }

    /**
     * 获取本地市级数据
     */
    private UrbanCascadeSelectionProvincialObj getLocalMunicipalObj(int id, String name) {
        UrbanCascadeSelectionProvincialObj sheng = new UrbanCascadeSelectionProvincialObj();
        sheng.setName(name + "_" + id);
        ArrayList<NationalUrbanInfoObj> lists = NationalUrbanInfoRelatedServer.getInstance().getChildLevelUrbanInfoList(id);
        if (lists != null) {
            List<UrbanCascadeSelectionMunicipalObj> list = new ArrayList<>();
            ArrayList<NationalUrbanInfoObj> deleteData = new ArrayList<>();

            for (int i = 0; i < lists.size(); i++) {
                NationalUrbanInfoObj obj = lists.get(i);
                if (obj.getParent_id() == id) {
                    UrbanCascadeSelectionMunicipalObj shi = new UrbanCascadeSelectionMunicipalObj();
                    shi.setName(obj.getName() + "_" + obj.getId());
                    String[] xian = getLocalCountyObj(obj.getId(), obj.getName());
                    shi.setArea(xian);
                    list.add(shi);

                    deleteData.add(obj);
                }
            }
            lists.removeAll(deleteData);
            sheng.setCity(list);
        }
        return sheng;
    }

    /**
     * 获取本地县级数据
     */
    private String[] getLocalCountyObj(int id, String name) {
        String[] xian = new String[]{};
        ArrayList<NationalUrbanInfoObj> lists = NationalUrbanInfoRelatedServer.getInstance().getChildLevelUrbanInfoList(id);
        if (lists != null) {
            List<String> xianList = new ArrayList<>();
            ArrayList<NationalUrbanInfoObj> deleteData = new ArrayList<>();

            for (int i = 0; i < lists.size(); i++) {
                NationalUrbanInfoObj obj = lists.get(i);
                if (obj.getParent_id() == id) {
                    xianList.add(obj.getName() + "_" + obj.getId());
                    deleteData.add(obj);
                }
            }
            try {
                if (deleteData != null && deleteData.size() > 0) {
                    lists.removeAll(deleteData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (xianList != null && xianList.size() > 0) {
                xian = new String[xianList.size()];
                xianList.toArray(xian);
            }
        }
        return xian;
    }

    /**
     * 获取省级数据
     */
    private ArrayList<UrbanCascadeSelectionProvincialObj> getProvincialList() {
        ArrayList<UrbanCascadeSelectionProvincialObj> list = new ArrayList<>();
        if (shengObj != null) {
            for (int i = 0; i < shengObj.size(); i++) {
                ProvincialLevelJsonObj obj = shengObj.get(i);
                UrbanCascadeSelectionProvincialObj sheng = getMunicipalObj(obj.getId(), obj.getName());
                list.add(sheng);
            }
        }
        return list;
    }

    /**
     * 获取市级数据
     */
    private UrbanCascadeSelectionProvincialObj getMunicipalObj(int id, String name) {
        UrbanCascadeSelectionProvincialObj sheng = new UrbanCascadeSelectionProvincialObj();
        sheng.setName(name + "_" + id);
        if (shiObj != null) {
            List<UrbanCascadeSelectionMunicipalObj> list = new ArrayList<>();
            ArrayList<MunicipalLevelJsonObj> deleteData = new ArrayList<>();

            for (int i = 0; i < shiObj.size(); i++) {
                MunicipalLevelJsonObj obj = shiObj.get(i);
                if (obj.getParent_id() == id) {
                    UrbanCascadeSelectionMunicipalObj shi = new UrbanCascadeSelectionMunicipalObj();
                    shi.setName(obj.getName() + "_" + obj.getId());
                    String[] xian = getCountyObj(obj.getId(), obj.getName());
                    shi.setArea(xian);
                    list.add(shi);

                    deleteData.add(obj);
                }
            }
            shiObj.removeAll(deleteData);
            sheng.setCity(list);
        }
        return sheng;
    }

    /**
     * 获取县级数据
     */
    private String[] getCountyObj(int id, String name) {
        String[] xian = new String[]{};
        if (xianObj != null) {
            List<String> xianList = new ArrayList<>();
            ArrayList<CountyLevelJsonObj> deleteData = new ArrayList<>();

            for (int i = 0; i < xianObj.size(); i++) {
                CountyLevelJsonObj obj = xianObj.get(i);
                if (obj.getParent_id() == id) {
                    xianList.add(obj.getName() + "_" + obj.getId());
                    deleteData.add(obj);
                }
            }
            try {
                if (deleteData != null && deleteData.size() > 0) {
                    xianObj.removeAll(deleteData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (xianList != null && xianList.size() > 0) {
                xian = new String[xianList.size()];
                xianList.toArray(xian);
            }
        }
        return xian;
    }


    /**
     * 保存省级数据
     */
    synchronized private void saveProvincialList() {
        if (shengObj != null) {
            ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = new ArrayList<>();
            for (int i = 0; i < shengObj.size(); i++) {
                ProvincialLevelJsonObj obj = shengObj.get(i);
                NationalUrbanInfoObj nationalUrbanInfoObj = new NationalUrbanInfoObj();
                nationalUrbanInfoObj.setId(obj.getId());
                nationalUrbanInfoObj.setName(obj.getName());
                nationalUrbanInfoObj.setParent_id(0);
                nationalUrbanInfoObj.setLevel(1);
                nationalUrbanInfoObjs.add(nationalUrbanInfoObj);
                saveMunicipalObj(obj.getId());
            }
            NationalUrbanInfoRelatedServer.getInstance().saveNationalUrbanInfoAllInfo(nationalUrbanInfoObjs);
        }
    }

    /**
     * 保存市级数据
     */
    private void saveMunicipalObj(int id) {
        if (shiObj != null) {
            ArrayList<MunicipalLevelJsonObj> deleteData = new ArrayList<>();
            ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = new ArrayList<>();
            for (int i = 0; i < shiObj.size(); i++) {
                MunicipalLevelJsonObj obj = shiObj.get(i);
                if (obj.getParent_id() == id) {
                    NationalUrbanInfoObj nationalUrbanInfoObj = new NationalUrbanInfoObj();
                    nationalUrbanInfoObj.setId(obj.getId());
                    nationalUrbanInfoObj.setName(obj.getName());
                    nationalUrbanInfoObj.setParent_id(id);
                    nationalUrbanInfoObj.setLevel(2);
                    nationalUrbanInfoObjs.add(nationalUrbanInfoObj);
                    deleteData.add(obj);

                    saveCountyObj(obj.getId());
                }
            }
            shiObj.removeAll(deleteData);
            NationalUrbanInfoRelatedServer.getInstance().saveNationalUrbanInfoAllInfo(nationalUrbanInfoObjs);
        }
    }

    /**
     * 保存县级数据
     */
    private void saveCountyObj(int id) {
        if (xianObj != null) {
            ArrayList<CountyLevelJsonObj> deleteData = new ArrayList<>();

            ArrayList<NationalUrbanInfoObj> nationalUrbanInfoObjs = new ArrayList<>();

            for (int i = 0; i < xianObj.size(); i++) {
                CountyLevelJsonObj obj = xianObj.get(i);
                if (obj.getParent_id() == id) {
                    NationalUrbanInfoObj nationalUrbanInfoObj = new NationalUrbanInfoObj();
                    nationalUrbanInfoObj.setId(obj.getId());
                    nationalUrbanInfoObj.setName(obj.getName());
                    nationalUrbanInfoObj.setParent_id(id);
                    nationalUrbanInfoObj.setLevel(3);
                    nationalUrbanInfoObjs.add(nationalUrbanInfoObj);
                }
            }
            xianObj.removeAll(deleteData);
            NationalUrbanInfoRelatedServer.getInstance().saveNationalUrbanInfoAllInfo(nationalUrbanInfoObjs);
        }
    }

    public ProvincesCitiyDataInit getProvincesCitiyDataInit() { return provincesCitiyDataInit; }

    public void setProvincesCitiyDataInit(ProvincesCitiyDataInit provincesCitiyDataInit) { this.provincesCitiyDataInit = provincesCitiyDataInit; }

    /**
     * 地域数据加载初始化接口
     */
    public interface ProvincesCitiyDataInit {
        void initSuccess();
    }

}
