package tech.yunjing.biconlife.liblkclass.http.xhttp.body;


import tech.yunjing.biconlife.liblkclass.http.xhttp.ProgressHandler;

/**
 * Created by wyouflf on 15/8/13.
 */
public interface ProgressBody extends RequestBody {
    void setProgressHandler(ProgressHandler progressHandler);
}
