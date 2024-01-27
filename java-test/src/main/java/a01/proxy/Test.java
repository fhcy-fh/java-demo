package a01.proxy;

/**
 * @author fh
 * @date 2024/1/21
 */
public class Test {

    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserService userService = ProxyUtils.proxyK(userServiceImpl);
        userService.test();
    }
}
