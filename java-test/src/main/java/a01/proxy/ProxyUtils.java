package a01.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author fh
 * @date 2024/1/21
 */
public class ProxyUtils {

    public static UserService proxyK(UserServiceImpl userServiceImpl) {
        return (UserService)Proxy.newProxyInstance(
                ProxyUtils.class.getClassLoader(), // 指定类加载器
                new Class[]{UserService.class}, // 指定接口
                new InvocationHandler() { // 指定代理对象要做的事情

                    /**
                     * 代理要做的事情
                     * @param proxy 代理的对象
                     * @param method 调用的方法
                     * @param args 调用方法出入的参数
                     * @return 返回
                     * @throws Throwable 异常
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("test".equals(method.getName())) {
                            System.out.println("代理开始工作");
                        }
                        return method.invoke(userServiceImpl, args);
                    }
                }
        );
    }
}
