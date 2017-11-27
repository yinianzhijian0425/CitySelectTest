package tech.yunjing.biconlife.libbaselib.imgsel.common;
/**
 * Created by Bys on 2017/8/10.
 */

import java.util.List;

/**
 * 作者：Bys on 2017/8/10.
 * 邮箱：baiyinshi@vv.cc
 */
public interface DataHelper<T> {

    boolean addAll(List<T> list);

    boolean addAll(int position, List<T> list);

    void add(T data);

    void add(int position, T data);

    void clear();

    boolean contains(T data);

    T getData(int index);

    void modify(T oldData, T newData);

    void modify(int index, T newData);

    boolean remove(T data);

    void remove(int index);

}