package tech.yunjing.biconlife.liblkclass.http.xhttp.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tech.yunjing.biconlife.liblkclass.http.xhttp.app.ResponseParser;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpResponse {

    Class<? extends ResponseParser> parser();

}