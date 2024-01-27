package a02.proxy;

/**
 * @author fh
 * @date 2024/1/21
 */
public class User {

    private String name;

    public String getUsername() {
        System.out.println("user的getUsername方法");
        return "返回数据";
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
