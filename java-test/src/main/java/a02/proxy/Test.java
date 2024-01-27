package a02.proxy;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author fh
 * @date 2024/1/21
 */
public class Test {


    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("G:\\java-demo\\java-test\\src\\main\\java\\a02\\proxy\\application.properties");
        properties.load(fileInputStream);

        fileInputStream.close();
        System.out.println(properties);
        String classname = (String) properties.get("classname");
        String method = (String) properties.get("method");
        System.out.println(classname);
        System.out.println(method);

        Class<?> clazz = Class.forName(classname);
        Constructor constructor = clazz.getDeclaredConstructor();
        Object o = constructor.newInstance();
        System.out.println(o);

        Method declaredMethod = clazz.getDeclaredMethod(method);
        declaredMethod.setAccessible(true);
        Object invoke = declaredMethod.invoke(o);
        System.out.println(invoke);

    }
}
