package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhongpp on 2017-3-18.
 */
public class TheClassTest {
    public static void main(String[] args) {
        Class clazz = TheClass.class;
        Annotation[] annotations = clazz.getAnnotations();

        //注解访问一
        for(Annotation annotation : annotations) {
            if(annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("myAnnotation.name() = " + myAnnotation.key());
                System.out.println("myAnnotation.value() = " + myAnnotation.value());
            }
        }

        //注解访问二
        Annotation annotation = clazz.getAnnotation(MyAnnotation.class);
        if(annotation instanceof  MyAnnotation) {
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            System.out.println("myAnnotation.name() = " + myAnnotation.key());
            System.out.println("myAnnotation.value() = " + myAnnotation.value());
        }

        System.out.println("======================================");

        //方法注解
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println("method.getName() = " + method.getName());
        }

        System.out.println("======================================");

        Method[] methods2 = clazz.getDeclaredMethods();
        for (Method method : methods2) {
            Annotation annotationMethod =  method.getAnnotation(MyAnnotation.class);
            if (annotationMethod instanceof  MyAnnotation) {
                System.out.println("annotationMethod = " + ((MyAnnotation) annotationMethod).key());
                System.out.println("annotationMethod = " + ((MyAnnotation) annotationMethod).value());
            }
        }

        System.out.println("*****************************************************");
        //方法参数注解
        Method[] methods3 = clazz.getDeclaredMethods();
        for (Method method : methods3) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            Class[] parameterTypes = method.getParameterTypes();

            int i = 0;
            for (Annotation[] annotations2 : parameterAnnotations) {
                Class paramterType = parameterTypes[i++];
                for (Annotation annotation1 : annotations2) {
                    if (annotation1 instanceof MyAnnotation) {
                        MyAnnotation myAnnotation = (MyAnnotation) annotation1;
                        System.out.println("param = " + paramterType.getName());
                        System.out.println("myAnnotation key() = " + myAnnotation.key());
                        System.out.println("myAnnotation.value() = " + myAnnotation.value());
                    }
                }
            }
        }

        System.out.println("##############################################");
        //变量注解
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Annotation annotation1 = field.getAnnotation(MyAnnotation.class);
            if (annotation1 instanceof  Annotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation1;
                System.out.println("myAnnotation key = " + myAnnotation.key());
                System.out.println("myAnnotation value = " + myAnnotation.value());
            }
        }
    }
}
