package a01.proxy;

/**
 * @author fh
 * @date 2024/1/21
 */
public class UserServiceImpl implements UserService{

    @Override
    public String test() {
        System.out.println("这是本身的方法");
        return "方法返回值";
    }
}
