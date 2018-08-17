package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhongpp on 2017-3-18.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    public String key();
    public  String value();
}
