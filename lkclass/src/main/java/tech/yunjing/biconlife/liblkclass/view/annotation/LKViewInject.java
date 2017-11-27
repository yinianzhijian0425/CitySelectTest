package tech.yunjing.biconlife.liblkclass.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LKViewInject {

    int value();

    /* parent view id */
    int parentId() default 0;
}
