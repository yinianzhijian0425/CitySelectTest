package tech.yunjing.biconlife.jniplugin.im.emoji;

import android.app.Activity;
import android.content.Context;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.util.ImageUtils;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.bean.EmojiObj;
import tech.yunjing.biconlife.jniplugin.util.ImageUtil;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.adapter.LKBaseAdapter;

/**
 * 表情适配器
 * Created by NPF on 2017/4/20 15:53.
 */
public class EmojiAdapter extends LKBaseAdapter<EmojiObj> {
    private Context context;

    public EmojiAdapter(ArrayList<EmojiObj> objList, Activity activity) {
        super(objList, activity);
        this.context = activity;
    }

    @Override
    protected View lpgetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LinearLayout.inflate(context, R.layout.adapter_emoji, null);
            viewHolder.iv_face = (ImageView) convertView.findViewById(R.id.iv_face);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EmojiObj emojiObj = mObjList.get(position);
        if (emojiObj.emojiResId == R.drawable.face_delete_select) {
            ImageUtil.setBackGroundDrawable(convertView,null);
            viewHolder.iv_face.setImageResource(emojiObj.emojiResId);
        } else if (TextUtils.isEmpty(emojiObj.emojiResDes)) {
            ImageUtil.setBackGroundDrawable(convertView,null);
            viewHolder.iv_face.setImageDrawable(null);
        } else {
            viewHolder.iv_face.setImageResource(emojiObj.emojiResId);
        }
        return convertView;
    }


    class ViewHolder {

        public ImageView iv_face;
    }

}
