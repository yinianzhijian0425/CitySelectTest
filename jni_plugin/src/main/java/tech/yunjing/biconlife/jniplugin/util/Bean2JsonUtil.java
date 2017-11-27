package tech.yunjing.biconlife.jniplugin.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import tech.yunjing.biconlife.jniplugin.im.bean.IMObj;

/**
 * 音视频透传的数据
 * Created by Chen.qi on 2017/8/30
 */
public class Bean2JsonUtil {

    /**
     * 常规消息扩展数据json串获取
     *
     * @return
     */
    public static String getChatJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("multimediaType", imObj.multimediaType);
            jsonObject.put("fromMobile", imObj.fromMobile);
            jsonObject.put("creatorUID", imObj.creatorUID);
            jsonObject.put("roomNum", imObj.roomNum);
            jsonObject.put("callState", imObj.callState);
            jsonObject.put("fromUID", imObj.fromUID);
            jsonObject.put("multimediaMessageType", imObj.multimediaMessageType);
            jsonObject.put("creatorUserID", imObj.creatorUserID);
            jsonObject.put("multimediaState", imObj.multimediaState);
            jsonObject.put("messageType", imObj.messageType);
            jsonObject.put("creatorMobile", imObj.creatorMobile);
//            JSONObject _jsonObject = new JSONObject(new HashMap<>());
//            jsonObject.put("parameters", _jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }


    /**
     * @return
     */
    public static String getChatEnd(String chatId, IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("creatorMobile", imObj.creatorMobile);
            jsonObject.put("tipMessage", imObj.tipMessage);
            jsonObject.put("callState", imObj.callState);
            jsonObject.put("messageType", imObj.messageType);
            jsonObject.put("groupNick", imObj.groupNick);
            jsonObject.put("nick", imObj.nick);
            jsonObject.put("groupID", imObj.groupID);
            jsonObject.put("avatar", imObj.avatar);
            jsonObject.put("groupAvatar", imObj.groupAvatar);
            jsonObject.put("conversationID", UserInfoManageUtil.getUserId());
            jsonObject.put("duration", imObj.duration);
            jsonObject.put("roomNum", imObj.roomNum);
            jsonObject.put("multimediaType", imObj.multimediaType);
//            JSONObject _jsonObject = new JSONObject(new HashMap<>());
//            jsonObject.put("parameters", _jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }


    /**
     * 常规消息扩展数据json串获取
     *
     * @return
     */
    public static String getChatGroupJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("creatorMobile", imObj.creatorMobile);
            jsonObject.put("multimediaType", imObj.multimediaType);
            jsonObject.put("roomNum", imObj.roomNum);
            jsonObject.put("multimediaState", imObj.multimediaState);
            jsonObject.put("messageType", imObj.messageType);
            jsonObject.put("multimediaMessageType", imObj.multimediaMessageType);
            jsonObject.put("fromMobile", imObj.fromMobile);
            jsonObject.put("groupID", imObj.groupID);
            jsonObject.put("groupNick", imObj.groupNick);
            jsonObject.put("creatorUserID", imObj.creatorUserID);
            jsonObject.put("fromUID", imObj.fromUID);
            jsonObject.put("groupAvatar", imObj.groupAvatar);
            jsonObject.put("creatorName", imObj.creatorName);
            jsonObject.put("callState", imObj.callState);
//            JSONObject _jsonObject = new JSONObject(new HashMap<>());
//            jsonObject.put("parameters", _jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }

    /**
     * 修改群昵称透传json
     *
     * @return
     */
    public static String getGroupNickJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userNick", imObj.userNick);
            jsonObject.put("groupName", imObj.groupName);
            jsonObject.put("groupID", imObj.groupID);
            jsonObject.put("messageType", imObj.messageType);
            jsonObject.put("groupAvatar", imObj.groupAvatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }

    /**
     * 确认转账透传json
     *
     * @return
     */
    public static String getTransferJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderNo", imObj.orderNo);
            jsonObject.put("messageType", imObj.messageType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }

    /**
     * 删除好友透传json
     *
     * @return
     */
    public static String getDeleteJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("messageType", imObj.messageType);
            jsonObject.put("avatar", imObj.avatar);
            jsonObject.put("nickName", imObj.nickName);
            jsonObject.put("userId", imObj.userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }

    /**
     * 新的好友请求透传json
     *
     * @return
     */
    public static String getNewFriendRequest(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("messageType", imObj.messageType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }

    /**
     * 同意好友请求好友透传json
     *
     * @return
     */
    public static String getAgreeJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("messageType", imObj.messageType);
            jsonObject.put("avatar", imObj.avatar);
            jsonObject.put("nickName", imObj.nickName);
            jsonObject.put("userId", imObj.userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }

    /**
     * 创建和添加群成员透传json
     *
     * @return
     */
    public static String getCreateAddJson(IMObj imObj) {
        String jsonString = "";
        if (imObj == null) {
            return jsonString;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("inviterId", imObj.inviterId);
            jsonObject.put("beInvitedIds", imObj.beInvitedIds);
            jsonObject.put("groupId", imObj.groupId);
            if (TextUtils.isEmpty(imObj.groupAvatar)) {
                jsonObject.put("groupAvatar", "");
            } else {
                jsonObject.put("groupAvatar", imObj.groupAvatar);
            }
            jsonObject.put("groupNick", imObj.groupNick);
            jsonObject.put("messageType", imObj.messageType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = jsonObject.toString();
        return jsonString;
    }
}
