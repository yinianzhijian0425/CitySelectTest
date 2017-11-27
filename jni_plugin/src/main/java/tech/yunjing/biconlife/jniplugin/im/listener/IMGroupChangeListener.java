package tech.yunjing.biconlife.jniplugin.im.listener;

import android.content.Intent;

import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMMucSharedFile;

import java.util.List;

import tech.yunjing.biconlife.liblkclass.common.util.LKLogUtil;
import tech.yunjing.biconlife.liblkclass.global.LKApplication;


/**
 * 群组变化监听
 * Created by nanPengFei on 2016/10/14 15:56.
 */
public class IMGroupChangeListener implements EMGroupChangeListener {
    private static IMGroupChangeListener mInstance;

    private IMGroupChangeListener() {
    }

    public static IMGroupChangeListener getInstance() {
        if (null == mInstance) {
            synchronized (IMGroupChangeListener.class) {
                if (null == mInstance) {
                    mInstance = new IMGroupChangeListener();
                }
            }
        }
        return mInstance;
    }

    /**
     * //接收到群组加入邀请
     */
    @Override
    public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
        // 收到加入群组的邀请
        LKLogUtil.e("===收到加入群组的邀请===" + groupId + "===" + groupName + "===" + inviter + "===" + reason);
    }

    /**
     * //用户申请加入群
     */
    @Override
    public void onRequestToJoinReceived(String s, String s1, String s2, String s3) {

    }

    /**
     * //加群申请被同意
     */
    @Override
    public void onRequestToJoinAccepted(String s, String s1, String s2) {

    }

    /**
     * //加群申请被拒绝
     */
    @Override
    public void onRequestToJoinDeclined(String s, String s1, String s2, String s3) {

    }

    /**
     * //群组邀请被同意
     */
    @Override
    public void onInvitationAccepted(String groupId, String invitee, String reason) {
        //群组邀请被接受
        LKLogUtil.e("===群组邀请被接受===" + groupId + "===" + invitee + "===" + reason);
    }

    /**
     * //群组邀请被拒绝
     */
    @Override
    public void onInvitationDeclined(String groupId, String invitee, String reason) {
        //群组邀请被拒绝
        LKLogUtil.e("===群组邀请被拒绝===" + groupId + "===" + invitee + "===" + reason);
    }

    /**
     * //用户被管理员移除出群组
     */
    @Override
    public void onUserRemoved(final String groupId, String groupName) {
        LKLogUtil.e("===被创建者移除该群===" + groupId + "===" + groupName);
        Intent intent = new Intent("USER_REMOVED");
        intent.putExtra("groupId", groupId);
        LKApplication.getContext().sendBroadcast(intent);//用户被管理员删除
    }

    /**
     * 群组被创建者解散
     */
    @Override
    public void onGroupDestroyed(final String groupId, String groupName) {
        LKLogUtil.e("========leihe=========" + "群组被创建者解散");
        Intent intent = new Intent("GROUP_DISSOLVE");
        intent.putExtra("groupId", groupId);
        LKApplication.getContext().sendBroadcast(intent);//收到消息发送广播
    }

    /**
     * //接收邀请时自动加入到群组的通知
     */
    @Override
    public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
        LKLogUtil.e("========leihe========" + "接受邀请时自动加入群组的通知");
    }

    /**
     * //成员禁言的通知
     */
    @Override
    public void onMuteListAdded(String s, List<String> list, long l) {

    }

    /**
     * //成员从禁言列表里移除通知
     */
    @Override
    public void onMuteListRemoved(String s, List<String> list) {

    }

    /**
     * //增加管理员的通知
     */
    @Override
    public void onAdminAdded(String s, String s1) {

    }

    /**
     * //管理员移除的通知
     */
    @Override
    public void onAdminRemoved(String s, String s1) {
        LKLogUtil.e("======管理员移除=====");
    }

    /**
     * //群所有者变动通知
     */
    @Override
    public void onOwnerChanged(String s, String s1, String s2) {

    }

    /**
     * //群组加入新成员通知
     */
    @Override
    public void onMemberJoined(String s, String s1) {
        LKLogUtil.e("=================" + "群组加入新成员通知" + "---------" + s + "-----------" + s1);
    }

    /**
     * //群成员退出通知
     */
    @Override
    public void onMemberExited(String s, String s1) {
        LKLogUtil.e("=================" + "群成员退出通知" + "---------" + s + "-----------" + s1);
    }

    /**
     * //群公告变动通知
     */
    @Override
    public void onAnnouncementChanged(String s, String s1) {

    }

    /**
     * //增加共享文件的通知
     */
    @Override
    public void onSharedFileAdded(String s, EMMucSharedFile emMucSharedFile) {

    }

    /**
     * //群共享文件删除通知
     */
    @Override
    public void onSharedFileDeleted(String s, String s1) {

    }
}