package tech.yunjing.biconlife.jniplugin.view.pickerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.db.daTable.NationalUrbanInfoObj;
import tech.yunjing.biconlife.jniplugin.server.NationalUrbanInfoRelatedServer;
import tech.yunjing.biconlife.jniplugin.view.pickerview.adapter.ArrayWheelAdapter;
import tech.yunjing.biconlife.jniplugin.view.pickerview.lib.WheelView;
import tech.yunjing.biconlife.jniplugin.view.pickerview.listener.OnItemSelectedListener;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;

/**
 * 地域城市选择控件
 * Created by sun.li on 2017/9/23.
 */

public class ProvincesCitySelectView extends LinearLayout{

    private Context mContext;

    /** 取消按钮*/
    private TextView btn_vpcs_cancel;

    /** 确认按钮*/
    private TextView btn_vpcs_submit;

    /** 省级信息选择器*/
    private WheelView wv_vpcs_provincial_level;

    /** 市级信息选择器*/
    private WheelView wv_vpcs_municipal_level;

    /** 县级信息选择器*/
    private WheelView wv_vpcs_county_level;

    /** 省级数据集合*/
    private ArrayList<NationalUrbanInfoObj> provincialDatas;

    /** 对应市级数据集合*/
    private ArrayList<NationalUrbanInfoObj> municipalDatas;

    /** 对应县级数据集合*/
    private ArrayList<NationalUrbanInfoObj> countyDatas;

    private float fontSize = 18;

    private float lineSpacing = 2.5f;

    private int itemVisibleCount = 9;

    private OnViewClick onViewClick;

    /** 当前选中的省级名称*/
    private String currentSelectProvincialName = "";

    /** 当前选中的省级名称*/
    private String currentSelectMunicipalName = "";

    /** 当前选中的省级名称*/
    private String currentSelectCountyName = "";

    private int currentSelectProvincialId;
    private int currentSelectMunicipalId;
    private int currentSelectCountyId;


    public ProvincesCitySelectView(Context context) {
        super(context);
        this.mContext = context;
        initView();
        initViewEvent();
    }

    public ProvincesCitySelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        initViewEvent();
    }

    public ProvincesCitySelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initViewEvent();
    }

    private void initView(){
        View view = inflate(mContext, R.layout.view_provinces_city_select,null);
        btn_vpcs_cancel = (TextView) view.findViewById(R.id.btn_vpcs_cancel);
        btn_vpcs_submit = (TextView) view.findViewById(R.id.btn_vpcs_submit);
        wv_vpcs_provincial_level = (WheelView) view.findViewById(R.id.wv_vpcs_provincial_level);
        wv_vpcs_municipal_level = (WheelView) view.findViewById(R.id.wv_vpcs_municipal_level);
        wv_vpcs_county_level = (WheelView) view.findViewById(R.id.wv_vpcs_county_level);

        addView(view);
    }

    private void initViewEvent() {
        btn_vpcs_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getOnViewClick()!=null){
                    getOnViewClick().clickCancel();
                }
            }
        });
        btn_vpcs_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = "";
                if(getOnViewClick()!=null){
                    if(currentSelectProvincialId>=0){
                        ids = String.valueOf(currentSelectProvincialId);
                    }
                    if(currentSelectMunicipalId>=0){
                        ids += " "+String.valueOf(currentSelectMunicipalId);
                    }else{
                        ids += " ";
                    }
                    if(currentSelectCountyId>=0){
                        ids += " "+String.valueOf(currentSelectCountyId);
                    }else{
                        ids += " ";
                    }
                    getOnViewClick().clickSubmit(currentSelectProvincialName,currentSelectMunicipalName,currentSelectCountyName,ids);
                }
                LKLogUtil.e(currentSelectProvincialName+" "+currentSelectMunicipalName+" "+currentSelectCountyName+" "+ids);
            }
        });
        initProvincialView();
    }

    /** 初始化省级城市信息控件*/
    private void initProvincialView(){
        provincialDatas = NationalUrbanInfoRelatedServer.getInstance().getCorrespondingLevelUrbanInfoList(1);
        List<String> provincialName = new ArrayList<>();
        if(provincialDatas!=null && provincialDatas.size()>0){
            initMunicipalView(provincialDatas.get(0).getId());
            currentSelectProvincialId = provincialDatas.get(0).getId();
            currentSelectProvincialName = provincialDatas.get(0).getName();
            for (int i = 0; i < provincialDatas.size(); i++) {
                provincialName.add(provincialDatas.get(i).getName());
            }
            wv_vpcs_provincial_level.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    NationalUrbanInfoObj obj = provincialDatas.get(index);
                    initMunicipalView(obj.getId());
                    currentSelectProvincialId = obj.getId();
                    currentSelectProvincialName = obj.getName();
                }
            });
        }else{
            currentSelectProvincialId = -1;
            currentSelectProvincialName = "";
        }
        wv_vpcs_provincial_level.setTextSize(fontSize);
        wv_vpcs_provincial_level.setCyclic(false);
        wv_vpcs_provincial_level.setLineSpacingMultiplier(lineSpacing);
        wv_vpcs_provincial_level.setItemsVisible(itemVisibleCount);
        wv_vpcs_provincial_level.setAdapter(new ArrayWheelAdapter(provincialName, 8));
    }

    /** 初始化对应市级城市信息控件*/
    private void initMunicipalView(int provincialID){
        municipalDatas = NationalUrbanInfoRelatedServer.getInstance().getChildLevelUrbanInfoList(provincialID);
        List<String> municipalName = new ArrayList<>();
        if(municipalDatas!=null && municipalDatas.size()>0){
            initCountyView(municipalDatas.get(0).getId());
            currentSelectMunicipalId = municipalDatas.get(0).getId();
            currentSelectMunicipalName = municipalDatas.get(0).getName();
            for (int i = 0; i < municipalDatas.size(); i++) {
                municipalName.add(municipalDatas.get(i).getName());
            }
            wv_vpcs_municipal_level.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    NationalUrbanInfoObj obj = municipalDatas.get(index);
                    initCountyView(obj.getId());
                    currentSelectMunicipalId = obj.getId();
                    currentSelectMunicipalName = obj.getName();
                }
            });
            wv_vpcs_municipal_level.setCurrentItem(0);
        }else{
            currentSelectMunicipalId = -1;
            currentSelectMunicipalName = "";
            initCountyView(-1);
        }
        wv_vpcs_municipal_level.setTextSize(fontSize);
        wv_vpcs_municipal_level.setCyclic(false);
        wv_vpcs_municipal_level.setLineSpacingMultiplier(lineSpacing);
        wv_vpcs_municipal_level.setItemsVisible(itemVisibleCount);
        wv_vpcs_municipal_level.setAdapter(new ArrayWheelAdapter(municipalName, 8));
    }

    /** 初始化对应县级城市信息控件*/
    private void initCountyView(int municipalID){
        countyDatas = NationalUrbanInfoRelatedServer.getInstance().getChildLevelUrbanInfoList(municipalID);
        List<String> countyName = new ArrayList<>();
        if(countyDatas!=null && countyDatas.size()>0){
            currentSelectCountyId = countyDatas.get(0).getId();
            currentSelectCountyName = countyDatas.get(0).getName();
            for (int i = 0; i < countyDatas.size(); i++) {
                countyName.add(countyDatas.get(i).getName());
            }
            wv_vpcs_county_level.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    NationalUrbanInfoObj obj = countyDatas.get(index);
                    currentSelectCountyId = obj.getId();
                    currentSelectCountyName = obj.getName();
                }
            });
            wv_vpcs_county_level.setCurrentItem(0);
        }else{
            currentSelectCountyId = -1;
            currentSelectCountyName = "";
        }
        wv_vpcs_county_level.setTextSize(fontSize);
        wv_vpcs_county_level.setCyclic(false);
        wv_vpcs_county_level.setLineSpacingMultiplier(lineSpacing);
        wv_vpcs_county_level.setItemsVisible(itemVisibleCount);
        wv_vpcs_county_level.setAdapter(new ArrayWheelAdapter(countyName, 8));
    }

    public OnViewClick getOnViewClick() { return onViewClick; }

    public void setOnViewClick(OnViewClick onViewClick) { this.onViewClick = onViewClick; }

    /** view点击回调*/
    public interface OnViewClick{
        void clickCancel();
        void clickSubmit(String provincialName,String municipalName,String countyName,String ids);
    }

}
