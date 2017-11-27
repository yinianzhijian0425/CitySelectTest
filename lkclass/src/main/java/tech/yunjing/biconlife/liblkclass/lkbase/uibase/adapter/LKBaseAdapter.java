package tech.yunjing.biconlife.liblkclass.lkbase.uibase.adapter;

import java.util.ArrayList;
import java.util.HashSet;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * ListView适配器基类
 *
 * @param <T>
 * @author nanPengFei
 */
public abstract class LKBaseAdapter<T> extends BaseAdapter {
    /**
     * 填充的数据集合
     */
    protected ArrayList<T> mObjList;
    /**
     * 填充布局使用到的activity
     */
    public Activity mActivity;
    /**
     * 填充布局使用到的activity
     */
    public Context mContext;


    public LKBaseAdapter(ArrayList<T> objList, Activity activity) {
        this.mObjList = objList;
        this.mActivity = activity;
    }

    public LKBaseAdapter(Context context, ArrayList<T> objList) {
        this.mObjList = objList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mObjList == null ? 0 : mObjList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mObjList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return lpgetView(position, convertView, parent);
    }

    /**
     * 子类重写，进行试图和数据填充
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    protected abstract View lpgetView(int position, View convertView, ViewGroup parent);

    /**
     * 获取Adapter中所有数据
     */
    public ArrayList<T> allData() {
        return mObjList;
    }

    /**
     * 清空数据源并刷新Adapter
     */
    public void clear() {
        if (mObjList != null) {
            mObjList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据并刷新Adapter
     */
    public void addAll(ArrayList<T> objList) {
        if (objList != null && objList.size() > 0) {
            if (this.mObjList == null) {
                this.mObjList = new ArrayList<T>();
            }
            this.mObjList.addAll(objList);
            notifyDataSetChanged();
        }
    }

}
