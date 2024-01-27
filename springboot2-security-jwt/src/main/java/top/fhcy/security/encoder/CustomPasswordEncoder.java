package top.fhcy.security.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码解析器
 * 使用 BCryptPasswordEncoder 则不需要
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    /**
     * 加密方法
     * @param rawPassword 明文
     * @return 密文
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    /**
     * 校验密码是否相同
     * @param rawPassword 明文
     * @param encodedPassword 密文
     * @return 是否相等
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * 是否升级密码解析策略
     * @param encodedPassword 密文
     * @return 升级成功
     */
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }
}
