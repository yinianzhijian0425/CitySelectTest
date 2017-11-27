package tech.yunjing.biconlife.liblkclass.http.xhttp.loader;


import java.io.InputStream;

import tech.yunjing.biconlife.liblkclass.cache.DiskCacheEntity;
import tech.yunjing.biconlife.liblkclass.http.xhttp.request.UriRequest;

/**
 * @author: wyouflf
 * @date: 2014/10/17
 */
class IntegerLoader extends Loader<Integer> {
    @Override
    public Loader<Integer> newInstance() {
        return new IntegerLoader();
    }

    @Override
    public Integer load(InputStream in) throws Throwable {
        return 100;
    }

    @Override
    public Integer load(UriRequest request) throws Throwable {
        request.sendRequest();
        return request.getResponseCode();
    }

    @Override
    public Integer loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        return null;
    }

    @Override
    public void save2Cache(UriRequest request) {

    }
}
