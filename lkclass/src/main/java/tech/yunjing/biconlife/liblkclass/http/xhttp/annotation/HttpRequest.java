package tech.yunjing.biconlife.liblkclass.http.xhttp.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tech.yunjing.biconlife.liblkclass.http.xhttp.app.DefaultParamsBuilder;
import tech.yunjing.biconlife.liblkclass.http.xhttp.app.ParamsBuilder;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpRequest {

    String host() default "";

    String path();

    Class<? extends ParamsBuilder> builder() default DefaultParamsBuilder.class;

    String[] signs() default "";

    String[] cacheKeys() default "";
}