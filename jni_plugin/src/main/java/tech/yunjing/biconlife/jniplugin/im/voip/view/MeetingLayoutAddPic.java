package tech.yunjing.biconlife.jniplugin.im.voip.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import tech.yunjing.biconlife.jniplugin.R;
import tech.yunjing.biconlife.jniplugin.im.MyIm.MyImSendOption;
import tech.yunjing.biconlife.jniplugin.im.bean.GroupMenberListdata;
import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;
import tech.yunjing.biconlife.jniplugin.im.key.LKOthersFinalList;
import tech.yunjing.biconlife.jniplugin.im.voip.AgoraManager;
import tech.yunjing.biconlife.jniplugin.im.voip.HxTestKey;
import tech.yunjing.biconlife.jniplugin.im.voip.SocialChatCallStat;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.jniplugin.im.voip.group.ChooseGroupMemberListActivity;
import tech.yunjing.biconlife.jniplugin.util.UserInfoManageUtil;
import tech.yunjing.biconlife.liblkclass.common.util.LKCommonUtil;
import tech.yunjing.biconlife.liblkclass.global.config.LKImageOptions;
import tech.yunjing.biconlife.liblkclass.lkbase.LK;

/**
 * 多人聊天人员的自定义View的添加
 * Created by Chen.qi on 2017/9/4 0004.
 */

public class MeetingLayoutAddPic extends LinearLayout {
    private LinearLayout ll_meeting_all_members;
    private Context mContext;

    public boolean addOtherMember = false;

    private Activity mActivityMeeting;

    private FrameLayout flView[];

    private int checkNum = 0;

    private int mySelfPosition = 0;

    private CenterMeetingController mControlMeet;


    public MeetingLayoutAddPic(Context context) {
        this(context, null);
    }

    public MeetingLayoutAddPic(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeetingLayoutAddPic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View mView = layoutInflater.inflate(R.layout.layout_meeting_all_members_ll, null);
        ll_meeting_all_members = (LinearLayout) mView.findViewById(R.id.ll_meeting_all_members);
        this.addView(mView);
    }


    /**
     * 填充顶部头像数据
     */
    public void addmeetingMemberView(ArrayList<GroupMenberListdata> allList, Activity mActivityMeeting, CenterMeetingController meetingController) {
        this.mActivityMeeting = mActivityMeeting;
        mControlMeet = meetingController;
        int screenWidth = LKCommonUtil.getScreenWidth();
        final ArrayList<GroupMenberListdata> allMember = new ArrayList<>();
        if (allList != null) {
            allMember.addAll(allList);
        }
        flView = new FrameLayout[allMember.size()];
        if (allMember.size() > 0) {
            LinearLayout layout = new LinearLayout(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(params);
            layout.setGravity(Gravity.CENTER_VERTICAL);
            layout.setWeightSum(3);
            LinearLayout layout2 = null;
            if (allMember.size() > 2) {
                layout2 = new LinearLayout(mContext);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                layout2.setLayoutParams(params2);
                layout2.setGravity(Gravity.CENTER_VERTICAL);
                layout2.setWeightSum(3);
            }

            for (int i = 0; i < allMember.size(); i++) {
                checkNum = i;
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_picture_dynamic, null);
                ImageView iv_picture = (ImageView) view.findViewById(R.id.iv_voip_meetingPic);
                VoIPWaitingView vp_voip_waiting = (VoIPWaitingView) view.findViewById(R.id.vp_voip_waiting);
                FrameLayout fl_meeting_video = (FrameLayout) view.findViewById(R.id.fl_meeting_video);
                flView[i] = fl_meeting_video;
                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(screenWidth / 3, LKCommonUtil.dip2px(100));
                view.setLayoutParams(params1);

                if (allMember.get(i).isAccept) {//代表已经接听了
                    vp_voip_waiting.setVisibility(GONE);
                    //判断是自己本人
                    if (!TextUtils.isEmpty(allMember.get(i).mobile) && allMember.get(i).mobile.equals(UserInfoManageUtil.getUserInfo().getPhone())) {
                        if (meetingController.isOpenOrCloseCrema) {
                            mySelfPosition = i;
                            //设置自己本地视图
                            mActivityMeeting.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //先清空容器
                                    flView[checkNum].removeAllViews();
                                    //设置本地前置摄像头预览并启动
                                    AgoraManager.getInstance().setupLocalVideoNew(flView[checkNum], mContext);
                                    flView[checkNum].setVisibility(VISIBLE);
                                }
                            });

                            //点击自己视图进行摄像头前后置转换
                            view.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (UserInfoManageUtil.getUserInfo().getPhone().equals(allMember.get(mySelfPosition).mobile)
                                            && mControlMeet.isOpenOrCloseCrema) {
                                        if (mControlMeet.isCremaSwitch) {
                                            mControlMeet.isCremaSwitch = false;
                                        } else {
                                            mControlMeet.isCremaSwitch = true;
                                        }
                                        AgoraManager.getInstance().onSwitchCamera();
                                    }
                                }
                            });

                        } else {
                            flView[checkNum].removeAllViews();
                            LK.image().bind(iv_picture, allMember.get(i).avatar,
                                    LKImageOptions.getOptions(R.mipmap.icon_choose_group_default));
                            flView[checkNum].setVisibility(GONE);
                            iv_picture.setVisibility(VISIBLE);
                        }

                    } else {
                        //其他人是否打开了摄像头
                        if (allMember.get(i).isMuteVideo) {
                            fl_meeting_video.setVisibility(VISIBLE);
                            mActivityMeeting.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //先清空容器
                                    flView[checkNum].removeAllViews();
                                    //设置本地前置摄像头预览并启动
                                    //注意SurfaceView要创建在主线程
                                    AgoraManager.getInstance().setupRemoteVideo(mContext, Integer.parseInt(allMember.get(checkNum).UID));
                                    flView[checkNum].addView(AgoraManager.getInstance().getSurfaceView(Integer.parseInt(allMember.get(checkNum).UID)));
                                }
                            });
                        } else {
                            flView[checkNum].removeAllViews();
                            flView[checkNum].setVisibility(GONE);
                            LK.image().bind(iv_picture, allMember.get(i).avatar,
                                    LKImageOptions.getOptions(R.mipmap.icon_choose_group_default));
                            iv_picture.setVisibility(VISIBLE);
                        }

                    }
                }
                if (i < 3) {
                    layout.addView(view);
                } else if (i < 6) {
                    layout2.addView(view);
                }

            }
            if (allMember.size() < 3) {
                layout.addView(addImgSelectedView(screenWidth));
            } else if (allMember.size() > 2 && allMember.size() < 6) {
                layout2.addView(addImgSelectedView(screenWidth));
            }

            ll_meeting_all_members.addView(layout);
            if (layout2 != null) {
                ll_meeting_all_members.addView(layout2);
            }
        }

    }

    /**
     * 添加默认的加号按钮
     *
     * @return
     */
    private View addImgSelectedView(int screenWidth) {

        LinearLayout layout = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth / 3, LKCommonUtil.dip2px(100));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(params);
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.layout_add_pic_check, null);
        view1.setLayoutParams(params);
        layout.addView(view1);
        view1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mControlMeet != null) {
                    addOtherMember = true;
                    Intent intent = new Intent();
                    intent.putExtra(HxTestKey.PIN_ZHUANG_GROUP_IMOBJ, mControlMeet.imObj);
                    intent.setClass(mContext, ChooseGroupMemberListActivity.class);
                    mActivityMeeting.startActivityForResult(intent, 100);
                }
            }
        });
        return layout;
    }

    /**
     * 清除所有view数据
     */
    public void clearnMeetingMemberViews() {
        ll_meeting_all_members.removeAllViews();
    }

}
