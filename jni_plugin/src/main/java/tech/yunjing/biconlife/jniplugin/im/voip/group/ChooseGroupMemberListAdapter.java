package tech.yunjing.biconlife.jniplugin.im.voip.group;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;
import tech.yunjing.biconlife.liblkclass.lkbase.uibase.adapter.LKBaseAdapter;
import tech.yunjing.biconlife.liblkclass.widget.LKCircleImageView;

/**
 * 选择群成员adapter
 * Created by Chen.qi on 2017/8/31.
 */

public class ChooseGroupMemberListAdapter extends LKBaseAdapter<GroupMenberListdata> {


    public ChooseGroupMemberListAdapter(ArrayList<GroupMenberListdata> objList, Activity activity) {
        super(objList, activity);
    }

    @Override
    protected View lpgetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.adapter_choose_group_member_list, null);
            viewHolder.iv_choose_checked = (ImageView) convertView.findViewById(R.id.iv_choose_checked);
            viewHolder.iv_choose_members_pic = (LKCircleImageView) convertView.findViewById(R.id.iv_choose_members_pic);
            viewHolder.tv_choose_members_nick = (TextView) convertView.findViewById(R.id.tv_choose_members_nick);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GroupMenberListdata memberInfo = mObjList.get(position);
        if (memberInfo.isCheck) {
            viewHolder.iv_choose_checked.setSelected(true);
        } else {
            viewHolder.iv_choose_checked.setSelected(false);
        }
        LK.image().bind(viewHolder.iv_choose_members_pic, memberInfo.smallImg, LKImageOptions.getOptions(R.mipmap.icon_choose_group_default));

        String nick = memberInfo.nickName;
        String petName = memberInfo.petName;
        if (!TextUtils.isEmpty(petName) && !"null".equals(petName)) {
            nick = petName;
        }
        viewHolder.tv_choose_members_nick.setText(nick);
        return convertView;
    }


    private class ViewHolder {
        /**
         * 选中的人员
         */
        private ImageView iv_choose_checked;

        /**
         * 成员的头像
         */
        private LKCircleImageView iv_choose_members_pic;

        /**
         * 成员的昵称
         */
        private TextView tv_choose_members_nick;

    }

}
