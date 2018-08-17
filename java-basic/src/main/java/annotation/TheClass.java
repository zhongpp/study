package annotation;


/**
 * Created by zhongpp on 2017-3-18.
 */
@MyAnnotation(key = "name",value="zhongpingping")
public class TheClass {

    @MyAnnotation(key = "param",value = "xxx")
    public  String annotationStr;

    @MyAnnotation(key = "like",value = "read")
    public void doSomething(@MyAnnotation(key = "age",value = "28")String param){};

}
