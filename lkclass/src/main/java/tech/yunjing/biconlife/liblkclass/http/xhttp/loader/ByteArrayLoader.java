package tech.yunjing.biconlife.liblkclass.http.xhttp.loader;

import java.io.InputStream;

import tech.yunjing.biconlife.liblkclass.cache.DiskCacheEntity;
import tech.yunjing.biconlife.liblkclass.common.util.IOUtil;
import tech.yunjing.biconlife.liblkclass.http.xhttp.request.UriRequest;

/**
 * Author: wyouflf
 * Time: 2014/05/30
 */
class ByteArrayLoader extends Loader<byte[]> {

    @Override
    public Loader<byte[]> newInstance() {
        return new ByteArrayLoader();
    }

    @Override
    public byte[] load(final InputStream in) throws Throwable {
        return IOUtil.readBytes(in);
    }

    @Override
    public byte[] load(final UriRequest request) throws Throwable {
        request.sendRequest();
        return this.load(request.getInputStream());
    }

    @Override
    public byte[] loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        return null;
    }

    @Override
    public void save2Cache(final UriRequest request) {
    }
}
