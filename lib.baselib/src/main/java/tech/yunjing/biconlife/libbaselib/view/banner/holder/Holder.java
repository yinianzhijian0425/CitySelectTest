package tech.yunjing.biconlife.libbaselib.view.banner.holder;

/**
 * Holder
 * @author HJT
 */

import android.content.Context;
import android.view.View;

public interface Holder<T>{
    View createView(Context context);
    void UpdateUI(Context context, int position, T data);
}