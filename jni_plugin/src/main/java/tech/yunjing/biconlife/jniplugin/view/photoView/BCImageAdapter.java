package tech.yunjing.biconlife.jniplugin.view.photoView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 图片展示适配器
 * Created by sun.li on 2017/9/16.
 */

public class BCImageAdapter extends PagerAdapter {
    public static final String TAG = BCImageAdapter.class.getSimpleName();
    private List<String> imageUrls;
    private Context context;

    private ItemOnClick itemOnClick;

    public BCImageAdapter(List<String> imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        String url = imageUrls.get(position);
        LK.image().bind(photoView, url, LKImageOptions.getOptionsType(ImageView.ScaleType.FIT_CENTER, R.mipmap.icon_image_default));
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LKLogUtil.e("onClick: ");
                if (itemOnClick != null) {
                    itemOnClick.onViewClick();
                }
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public ItemOnClick getItemOnClick() {
        return itemOnClick;
    }

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    public interface ItemOnClick {
        void onViewClick();
    }
}
