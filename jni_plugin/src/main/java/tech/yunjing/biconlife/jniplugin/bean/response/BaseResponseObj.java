package tech.yunjing.biconlife.jniplugin.bean.response;

import java.io.Serializable;

/**
 * 接口响应基类对象
 * Created by CQ on 2017/6/27.
 *
 * @param T 表示data的数据类型
 */

public class BaseResponseObj<T> implements Serializable {

    public int code;

    public String message;

    public T data;


}
