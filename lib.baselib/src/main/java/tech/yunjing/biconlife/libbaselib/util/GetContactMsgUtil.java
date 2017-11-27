package tech.yunjing.biconlife.libbaselib.util;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 获取系统联系人信息工具类
 * Created by sun.li on 2017/7/12.
 */
public class GetContactMsgUtil {
    /** 联系人名称*/
    private String name;
    /** 联系人电话*/
    private String phone;

    public GetContactMsgUtil(Context context, Intent data){
        super();
        Uri contactData = data.getData();
        CursorLoader cursorLoader = new CursorLoader(context,contactData,null,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        if(cursor.moveToFirst()){
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            phone = "此联系人暂未存入号码";
            Cursor phones = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null,
                    null);
            if (phones.moveToFirst()) {
                phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phones.close();
        }
        cursor.close();
    }

    /** 获取联系人名称*/
    public String getName() {
        return name;
    }

    /** 获取联系人电话*/
    public String getPhone() {
        return phone;
    }
}
