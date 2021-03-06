package tech.yunjing.biconlife.liblkclass.http.xhttp.body;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wyouflf on 15/10/29.
 */
public interface RequestBody {

    long getContentLength();

    void setContentType(String contentType);

    String getContentType();

    void writeTo(OutputStream out) throws IOException;
}
