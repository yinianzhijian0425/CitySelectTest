package tech.yunjing.biconlife.jniplugin.im.voip.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKJsonUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 多人聊天人员的自定义View的添加
 * 来电的时候所有人员
 * Created by Chen.qi on 2017/9/4 0004.
 */

public class MeetingIncomeAddPic extends LinearLayout {
    private LinearLayout ll_meeting_layout_income_mem;
    private Context mContext;

    public MeetingIncomeAddPic(Context context) {
        this(context, null);

    }


    public MeetingIncomeAddPic(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeetingIncomeAddPic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View mView = layoutInflater.inflate(R.layout.layout_meeting_income_linearlayout, null);
        ll_meeting_layout_income_mem = (LinearLayout) mView.findViewById(R.id.ll_meeting_layout_income_mem);
        this.addView(mView);
    }

    /**
     * 填充来电人员的数据
     */
    public void addMeetingIncomeMemberView(CenterMeetingController mController) {
        ArrayList<GroupMenberListdata> allMember = new ArrayList<>();
        LKLogUtil.e("result来电人员的数据,去除邀请者==" + mController.imObj.members);
        if (mController != null) {
            //所有的人员包括创建者自己
            ArrayList<GroupMenberListdata> groupMem =
                    LKJsonUtil.jsonToArrayList(mController.imObj.members, GroupMenberListdata.class);
            if (groupMem != null) {
                allMember.addAll(groupMem);
                //移除members中的发起者
                for (int i = 0; i < allMember.size(); i++) {
                    GroupMenberListdata groupMD = allMember.get(i);
                    if (groupMD != null && !TextUtils.isEmpty(groupMD.mobile)
                            && groupMD.mobile.equals(mController.imObj.fromMobile)) {
                        allMember.remove(i);
                        break;
                    }
                }
            }
        }
        if (allMember.size() > 0) {
            for (int i = 0; i < allMember.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_picture_income_pic, null);
                ImageView iv_voip_meeting_income_pic = (ImageView) view.findViewById(R.id.iv_voip_meeting_income_pic);
                LK.image().bind(iv_voip_meeting_income_pic, allMember.get(i).avatar,
                        LKImageOptions.getFITXYOptions(R.mipmap.icon_choose_group_default, 8));
                TextView textView = new TextView(mContext);
                LayoutParams tvParams = new LayoutParams(LKCommonUtil.dip2px(10),
                        LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(tvParams);
                ll_meeting_layout_income_mem.addView(view);
                if (i != allMember.size() - 1) {
                    ll_meeting_layout_income_mem.addView(textView);
                }
            }
        }
    }


    /**
     * 清楚所有view数据
     */
    public void clearnMeetingIncomeMemberViews() {
        ll_meeting_layout_income_mem.removeAllViews();
    }

}
